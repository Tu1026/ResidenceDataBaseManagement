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
    public void insertTableData(Map<String, String> data, Consumer<String> onSuccess, Consumer<String> onError) {


        String resInfo = "INSERT INTO RESIDENTINFO VALUES (?, ?, ?, ?, ?)";
        String resAddress = "INSERT INTO RESIDENTADDRESS VALUES (?, ?, ?, ?, ?, ?)";


        try(PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(resInfo), resInfo)){
            connection.setAutoCommit(false);
            ps.setObject(1, data.get("STUDENTNUMBER"));
            ps.setObject(2, data.get("EMAIL"));
            ps.setObject(3, data.get("NAME"));
            ps.setObject(4, data.get("DOB"));
            ps.setObject(5, data.get("YEARSINRESIDENCE"));
            ps.executeUpdate();

            PrintablePreparedStatement ps2 = new PrintablePreparedStatement(connection.prepareStatement(resAddress), resAddress);
            ps2.setObject(1, data.get("EMAIL"));
            ps2.setObject(2, data.get("UNUMBER"));
            ps2.setObject(3, data.get("FNUMBER"));
            ps2.setObject(4, data.get("HOUSENAME"));
            ps2.setObject(5, data.get("RESSTADDRESS"));
            ps2.setObject(6, data.get("RESZIPCODE"));
            ps2.executeUpdate();


            connection.commit();
            connection.setAutoCommit(true);
            onSuccess.accept("Student '" + data.get("NAME") + "' inserted successfully");
        } catch (SQLException throwables) {
            onError.accept(throwables.getMessage());
            throwables.printStackTrace();
        }

    }

    @Override
    public void updateTableData(String prettyTableName, List<String> columnsToUpdate, Consumer<Table> onSuccess, Consumer<String> onError) {
        String [] tablesToLookup = getTablesToLookup(prettyTableName);



        if (tablesToLookup.length == 1){

        }
    }

    @Override
    public void deleteTableData(String prettyTableName, List<String> columnsToUpdate, Consumer<Table> onSuccess, Consumer<String> onError) {
        if (! prettyTableName.equalsIgnoreCase("Resident")) {
            onError.accept("Cannot delete data from table " + prettyTableName);
            return;
        }

        String query = "DELETE FROM ResidentInfo WHERE StudentNumber LIKE ?";

        try (PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query)){
            ps.setObject(1, columnsToUpdate.get(1) + "%");
            ps.executeUpdate();
            onSuccess.accept(null);
        } catch (SQLException throwables) {
            onError.accept(throwables.getMessage());
            throwables.printStackTrace();
        }

    }

    @Override
    public void getTableData(String prettyTable, Consumer<Table> onSuccess) {
        String[] tablesToLookup = getTablesToLookup(prettyTable);
        String query = buildTableQuery(tablesToLookup);

        Table table = null;
        try (PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query)) {
            table = buildTable(prettyTable, tablesToLookup, ps);

        } catch (SQLException | ExecutionException | InterruptedException throwables) {
            throwables.printStackTrace();
        } finally {
            onSuccess.accept(table);
        }
    }


    @Override
    public void filterTable(String prettyTable, String filter, String column, List<String> columnsToDisplay, Consumer<Table> onSuccess) {
        String[] tablesToLookup = getTablesToLookup(prettyTable);
        String query = buildTableQuery(tablesToLookup, columnsToDisplay);
        filter = "%" + filter.trim() + "%";
        String lowerCaseFilter = filter.toLowerCase();
        column = OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get(column);
        if (filter.length() != 2) {
            query += " WHERE LOWER(" + column + ") LIKE ?";
        }
        Table table = null;

        try (PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query)) {
            if (filter.length() != 2) {
                ps.setObject(1, lowerCaseFilter);
            }
            table = buildTable(prettyTable, tablesToLookup, ps);

        } catch (SQLException | ExecutionException | InterruptedException throwables) {
            throwables.printStackTrace();
        } finally {
            onSuccess.accept(table);
        }
    }

    @Override
    public void getSpecificTableData(String tableName, List<String> columnsToGet, List<String> columnsToMatch, List<String> dataToMatch, Consumer<Table> callback) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT ");

        for (int i = 0; i < columnsToGet.size() - 1; i++){
            query.append(OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get(columnsToGet.get(i))).append(", ");
        }
        query.append(OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get(columnsToGet.get(columnsToGet.size() - 1)));

        query.append(" FROM ");
        query.append(tableName);

        if (columnsToMatch.size() > 0 && dataToMatch.size() > 0){
            query.append(" WHERE ");

            for (String key: columnsToMatch) {
                query.append(OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get(key)).append( " LIKE ").append("?").append(" AND ");
            }
        }

        String queryStr = query.toString();
        queryStr = queryStr.replaceAll(" AND $", "").trim();

        Table table = null;
        try(PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(queryStr), queryStr)){
            for (int i = 0; i < columnsToMatch.size(); i++) {
                ps.setString(i+1, "%" + dataToMatch.get(i).trim() +"%");
            }
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

//    private Map<String, List<String>> getPKForTable(String[] tableToLookup) {
//        Map<String, List<String>> PKs = new HashMap<>();
//        for (String table : tableToLookup) {
//            PKs.put(table, getPKForTable(table));
//        }
//        return PKs;
//    }
//
//    private List<String> getPKForTable(String oracleTableName) {
//        List<String> PK = new ArrayList<>();
//        String query = "SELECT column_name FROM user_cons_columns WHERE constraint_name = " +
//                "(SELECT constraint_name FROM all_constraints ac  WHERE UPPER(ac.table_name) = UPPER(?) AND CONSTRAINT_TYPE = 'P' AND ac.OWNER = USER)";
//        try (PrintablePreparedStatement ps = new PrintablePreparedStatement(connection.prepareStatement(query), query, false)) {
//            ps.setObject(1, oracleTableName);
//            ResultSet resultSet = ps.executeQuery();
//
//            while (resultSet.next()) {
//                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++)
//                    PK.add(resultSet.getString(i));
//            }
//        }catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return PK;
//    }


    private String buildTableQuery(String[] tablesToLookup) {
        List<String> singleQuery = new ArrayList<>(Collections.singletonList("All"));
        return buildTableQuery(tablesToLookup, singleQuery);
    }

    private String buildTableQuery(String[] tablesToLookup, List<String> columnsToQuery) {
        StringBuilder query = new StringBuilder("SELECT ");

        if (columnsToQuery.size() == 1 && columnsToQuery.get(0).equalsIgnoreCase("All")){
            query.append("* ");
        }else {
            for (int i = 0; i < columnsToQuery.size() - 1; i++) {
                query.append(OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get(columnsToQuery.get(i))).append(", ");
            }

            query.append(OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get(columnsToQuery.get(columnsToQuery.size() - 1))).append(" ");
        }

       query.append("FROM ");
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
//        SwingWorker<Map<String, List<String>>, Void> worker = new SwingWorker<Map<String, List<String>>, Void>() {
//            @Override
//            protected Map<String, List<String>> doInBackground() {
//                //return getPKForTable(tablesToLookup);
//                return null;
//            }
//        };
//        worker.execute();
        Table table = executeQueryAndParse(ps);

        table.setName(prettyTable);
        //table.setPKs(worker.get());
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
}
