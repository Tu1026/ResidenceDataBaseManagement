package handler;

import interfaces.DataHandlerDelegate;
import interfaces.SQLParserDelegate;
import model.table.Column;
import model.table.Table;
import model.table.TableModel;
import model.OracleTableNames;
import model.table.TableRow;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public final class DataHandler implements DataHandlerDelegate {

    private static final String DDL_FILE = "out/production/CPSC304Project/sql/scripts/DDL.sql";
    private Connection connection;
    private final SQLParserDelegate sqlParser;

    public DataHandler() {
        sqlParser = new SQLParser();
    }


    public void setConnection(Connection connection) {
        this.connection = connection;
        try {
            connection.setAutoCommit(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void initializeDDL() {
        dropAllTablesIfExist(); // TODO: comment this out if you want to keep all data in tables
        // TODO: Leave this line if you want to clear all tabledata when the application starts
        // In the future, this can be set as an option in the application
        parseDDL();
        parseMDL();
    }

    @Override
    public void insertTableData(TableModel data) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public void getTableData(String tableToLookup, Consumer<Table> callback) {

        Table table = null;

        if (OracleTableNames.TABLE_NAMES.contains(tableToLookup.toUpperCase())) {

            try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + tableToLookup.toUpperCase())) {
                table = query(ps);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } finally {
                callback.accept(table);
            }
        }else {
            System.err.println("Invalid table name: " + tableToLookup);
        }
    }

    private Table query(PreparedStatement ps) throws SQLException {
        ResultSet results = ps.executeQuery();
        connection.commit();

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
        try (PreparedStatement ps = connection.prepareStatement(query)){
            System.out.println("Running " + query + " ...");
            table = this.query(ps);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            callback.accept(table);
        }
    }

    private void parseDDL() {
        List<String> ddlStatements =  sqlParser.parseDDL(new File(DDL_FILE));

        for (String ddlStatement : ddlStatements) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(ddlStatement);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println(ddlStatements.size() + " tables created. Check oracle sidebar to make sure they are present");

    }

    private void parseMDL() {
        List<String> mdlStatements = sqlParser.parseDMLInsertStatement(new File(DDL_FILE));

        try {
            PrintWriter pw = new PrintWriter(new FileWriter("error.txt"));
            for (String mdlStatement : mdlStatements) {
                try {
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate(mdlStatement);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                    throwables.printStackTrace(pw);
                }
            }
        } catch (IOException e) {
            System.out.println("Bleurh");
        }
        System.out.println(mdlStatements.size() + " insert statements run. Double click on tables in sidebar to verify data");
    }


    private void dropAllTablesIfExist() {
        Set<String> tableNames = OracleTableNames.TABLE_NAMES;

        try (Statement stmt = connection.createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery("SELECT table_name FROM user_tables")) { // selects all tables
                while (resultSet.next()) {
                    String tableName = resultSet.getString(1).toUpperCase();
                    if (tableNames.contains(tableName)) {
                        forceDropSpecifiedTable(tableName);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void forceDropSpecifiedTable(String tableName) {
        try (Statement stmt = connection.createStatement()) {
                stmt.execute("DROP TABLE " + tableName + " CASCADE CONSTRAINTS "); // Drop tables, ignore any constraints for deletion
                System.out.println(tableName + " table dropped");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

