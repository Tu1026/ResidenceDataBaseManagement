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

public final class DataHandler implements DataHandlerDelegate {

    private static final String DDL_FILE = "out/production/CPSC304Project/sql/scripts/DDL.sql";
    private Connection connection;
    private final SQLParserDelegate sqlParser;

    public DataHandler() {
        sqlParser = new SQLParser();
    }


    public void setConnection(Connection connection) {
        this.connection = connection;
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

   // @Override
   // public void updateTableData(TableRow data) {
       // throw new RuntimeException("Not implemented yet");
    //}

//    @Override
//    public TableRow getTableData(Set<String> dataToLookup) {
//        throw new RuntimeException("Not implemented yet");
//        return null;
//    }

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

    //I changed this to accomodate for UI testing
    public ResultSet getTableData(String sql) throws SQLException{
        ResultSet rs;
        try (Statement stmt = connection.createStatement()) {
            rs = connection.createStatement().executeQuery(sql);
            }
        catch (SQLException e) {
            rs = null;
            e.printStackTrace();
        }
        return rs;
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


// What you want to do, from the POV of the UI, only using information from RESULT SET (without the formatting)
//        System.out.println("============================== ResultsList Method ==============================");
//        try {
//            Statement stmt = connection.createStatement();
//            ResultSet results = stmt.executeQuery("SELECT * FROM RESIDENTIALMANAGINGOFFICE JOIN CAMPUS r USING(CZIPCODE, CSTADDRESS)");
//
//
//            int cols = results.getMetaData().getColumnCount();
//            for (int i = 1; i <= cols; i++ ){
//                System.out.format("%-40s", results.getMetaData().getColumnLabel(i));
//            }
//            System.out.println();
//            while (results.next()){
//                for (int i = 1; i <= cols; i++) {
//                    System.out.format("%-40s", results.getString(i));
//                }
//                System.out.println();
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//
//        // My method:
//        // Parsing: On the end of DataHandler
//
//        Table table = null;
//
//        try {
//            Statement stmt = connection.createStatement();
//            ResultSet results = stmt.executeQuery("SELECT * FROM RESIDENTIALMANAGINGOFFICE JOIN CAMPUS r USING(CZIPCODE, CSTADDRESS)");
//
//            int cols = results.getMetaData().getColumnCount();
//            String [] columnNames = new String [cols];
//            for (int i = 1; i <= cols; i++ ){
//                columnNames[i-1] = results.getMetaData().getColumnLabel(i);
//            }
//
//            table = new Table(columnNames);
//
//            while (results.next()){
//                List<Column>  column = table.getColumnsList();
//                for (int i = 1; i <= cols; i++) {
//                    table.insert(column.get(i-1), results.getString(i));
//                }
//                table.nextRow();
//            }
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//
//        // Displaying, on the end of the UI:
//
//        System.out.println("============================== My Method ==============================");
//
//        // Pass in TableList to UI
//        if (table != null) {
//            Set<Column> columnNames = table.getColumns();
//
//            for (Column column: columnNames) {
//                System.out.format("%-40s", column.name); // in UI, add to table columns instead, probably don't need to loop
//            }
//
//            System.out.println(); // for display purposes only
//            for (TableRow tablerow : table) { ;
//                for (Column colName: columnNames) {
//                    System.out.format("%-40s", tablerow.get(colName)); // in UI, add to row instead
//                }
//                System.out.println(); // for display purposes only
//            }
//        }
