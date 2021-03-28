package handler;

import interfaces.DataHandlerDelegate;
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
    public void getTableData(String tableToLookup, Consumer<Table> callback) {
        String query = "SELECT * FROM " + tableToLookup;

        if (OracleTableNames.TABLE_NAMES.contains(tableToLookup.toUpperCase())) {
            Table table = null;
            try (PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query)) {
                table = buildTable(tableToLookup, ps);

            } catch (SQLException | ExecutionException | InterruptedException throwables) {
                throwables.printStackTrace();
            } finally {
                callback.accept(table);
            }
        }else {
            System.err.println("Invalid table name: " + tableToLookup);
        }
    }

    private Table buildTable(String tableToLookup, PrintablePreparedStatement ps) throws SQLException, ExecutionException, InterruptedException {
        SwingWorker<List<String>, Void> worker = new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                return getPKForTable(tableToLookup);
            };
        };
        worker.execute();
        Table table = executeQueryAndParse(ps);

        table.setName(tableToLookup);
        table.setPKs(worker.get());
        return table;
    }

    @Override
    public void filterTable(String tableToLookup, String filter, String column, Consumer<Table> callback){
        filter += "%";
        String query = "SELECT * FROM " + tableToLookup + " WHERE  ?  LIKE ?";

        if (OracleTableNames.TABLE_NAMES.contains(tableToLookup.toUpperCase())) {
            Table table = null;
            try (PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query)) {
                ps.setObject(1, column);
                ps.setObject(2, filter);

                table = buildTable(tableToLookup, ps);

            } catch (SQLException | ExecutionException | InterruptedException throwables) {
                throwables.printStackTrace();
            } finally {
                callback.accept(table);
            }
        }else {
            System.err.println("Invalid table name: " + tableToLookup);
        }
    }

    private List<String> getPKForTable(String tableToLookup) {
        List<String> PKs = new ArrayList<>();
        String query = "SELECT column_name FROM user_cons_columns WHERE constraint_name = " +
                "(SELECT constraint_name FROM all_constraints ac  WHERE UPPER(ac.table_name) = UPPER(?) AND CONSTRAINT_TYPE = 'P' AND ac.OWNER = USER)";
        try (PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query)) {
            ps.setObject(1, tableToLookup);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++)
                PKs.add(resultSet.getString(i));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return PKs;
    }

    // TODO: Delete this in the future
    private Table executeQueryAndParse(PrintablePreparedStatement ps) throws SQLException {
        System.out.println("Running query ...");
        ResultSet results = ps.executeQuery();
        int cols = results.getMetaData().getColumnCount();
        String [] columnNames = new String [cols];

        for (int i = 1; i <= cols; i++ ){
            columnNames[i-1] = results.getMetaData().getColumnLabel(i);
        }

        Table table = new Table(columnNames);

        while (results.next()){
            List<Column>  column = table.getColumnsList();
            for (int i = 1; i <= cols; i++) {
                table.insert(column.get(i-1), results.getString(i));
            }
            table.nextRow();
        }
        return table;
    }

    @Override
    public void performQuery(String query, Consumer<Table> callback) {
        Table table = null;
        try (PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query)){
            table = this.executeQueryAndParse(ps);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            callback.accept(table);
        }
    }
}
