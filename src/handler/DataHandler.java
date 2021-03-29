package handler;

import interfaces.DataHandlerDelegate;
import javafx.application.Platform;
import model.OracleColumnNames;
import sql.PrintablePreparedStatement;
import model.table.Column;
import model.table.Table;
import model.table.TableModel;
import model.OracleTableNames;

import javax.swing.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public final class DataHandler implements DataHandlerDelegate {
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * initializes the DDl
     * REQUIRES: connection != null
     */
    public void initializeDDL() {
        new OracleSchemaBuilder().initializeSchema(connection);
    }

    @Override
    public void insertTableData(TableModel data) {
        throw new RuntimeException("Insert TableData not implemented yet");
    }

    @Override
    public void getTableData(String prettyTable, Consumer<Table> callback) {
        String[] tablesToLookup = getTablesToLookup(prettyTable);
        String query = buildTableQuery(tablesToLookup);

        Table table = null;
        try (PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query)) {
            table = buildTable(prettyTable, tablesToLookup, ps);

        } catch (SQLException | ExecutionException | InterruptedException throwables) {
            throwables.printStackTrace();
        } finally {
            callback.accept(table);
        }
    }


    @Override
    public void filterTable(String prettyTable, String filter, String column, Consumer<Table> callback) {
        String[] tablesToLookup = getTablesToLookup(prettyTable);
        String query = buildTableQuery(tablesToLookup);
        filter += "%";
        String lowerCaseFilter = filter.toLowerCase();
        String upperCaseFilter = filter.toUpperCase();
        column = OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get(column);
        query += " WHERE " + column + " LIKE ? OR " + column + " LIKE ?";
        Table table = null;

        try (PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query)) {
            ps.setObject(1, lowerCaseFilter);
            ps.setObject(2, upperCaseFilter);

            table = buildTable(prettyTable, tablesToLookup, ps);

        } catch (SQLException | ExecutionException | InterruptedException throwables) {
            throwables.printStackTrace();
        } finally {
            callback.accept(table);
        }
    }

    @Override
    public void getSpecificTableData(String tableName, List<String> columnsToGet, Map<String, String> data, Consumer<Table> callback) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT ");

        for (int i = 0; i < columnsToGet.size() - 1; i++){
            query.append(OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get(columnsToGet.get(i))).append(", ");
        }
        query.append(OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get(columnsToGet.get(columnsToGet.size() - 1)));

        query.append(" FROM ");
        query.append(tableName);

        if (data.size() > 0){
            // TODO: do stuff here
        }

        String queryStr = query.toString();

        Table table = null;
        try(PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(queryStr), queryStr)){
            table = buildTable(tableName, new String[]{tableName}, ps);

        }catch (SQLException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }finally {
            Table finalTable = table;
            Platform.runLater(() -> {
                callback.accept(finalTable);
            });
        }
    }

    private List<String> getPKForTable(String[] tableToLookup) {
        List<String> PKs = new ArrayList<>();
        for (String table : tableToLookup) {
            String query = "SELECT column_name FROM user_cons_columns WHERE constraint_name = " +
                    "(SELECT constraint_name FROM all_constraints ac  WHERE UPPER(ac.table_name) = UPPER(?) AND CONSTRAINT_TYPE = 'P' AND ac.OWNER = USER)";
            try (PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query)) {
                ps.setObject(1, table);
                ResultSet resultSet = ps.executeQuery();

                while (resultSet.next()) {
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++)
                        PKs.add(resultSet.getString(i));
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return PKs;
    }

    private String buildTableQuery(String[] tablesToLookup) {
        StringBuilder query = new StringBuilder("SELECT * FROM ");
        query.append(tablesToLookup[0]);
        if (tablesToLookup.length > 1) {
            for (int i = 1; i < tablesToLookup.length; i++) {
                query.append(" NATURAL JOIN ").append(tablesToLookup[i]);
            }
        }
        return query.toString();
    }

    private String[] getTablesToLookup(String prettyTable) {
        String[] tablesToLookup = new String[0];
        if (OracleTableNames.GET_ORACLE_NAME.containsKey(prettyTable)) {
            tablesToLookup = new String[]{OracleTableNames.GET_ORACLE_NAME.get(prettyTable)};
        } else if (OracleTableNames.GET_COMPOUND_TABLES.containsKey(prettyTable)) {
            tablesToLookup = OracleTableNames.GET_COMPOUND_TABLES.get(prettyTable);
        }
        return tablesToLookup;
    }

    private Table buildTable(String prettyTable, String[] tablesToLookup, PrintablePreparedStatement ps) throws SQLException, ExecutionException, InterruptedException {
        SwingWorker<List<String>, Void> worker = new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() {
                return getPKForTable(tablesToLookup);
            }
        };
        worker.execute();
        Table table = executeQueryAndParse(ps);

        table.setName(prettyTable);
        table.setPKs(worker.get());
        return table;
    }


    private Table executeQueryAndParse(PreparedStatement ps) throws SQLException {
        System.out.println("Running query ...");
        ResultSet results = ps.executeQuery();
        int cols = results.getMetaData().getColumnCount();
        String[] columnNames = new String[cols];

        for (int i = 1; i <= cols; i++) {
            columnNames[i - 1] = results.getMetaData().getColumnLabel(i);
        }

        Table table = new Table(columnNames);

        while (results.next()) {
            List<Column> column = table.getColumnsList();
            for (int i = 1; i <= cols; i++) {
                table.insert(column.get(i - 1), results.getString(i));
            }
            table.nextRow();
        }
        return table;
    }

    // TODO: Delete this in the future
    @Override
    public void performQuery(String query, Consumer<Table> callback) {
        Table table = null;
        try (PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query)) {
            table = this.executeQueryAndParse(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            callback.accept(table);
        }
    }

}
