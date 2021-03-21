package handler;

import interfaces.DataHandlerDelegate;
import interfaces.SQLParserDelegate;
import model.TableModel;
import model.TableNames;
import model.tables.TableData;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public final class DataHandler implements DataHandlerDelegate {

    private static final String DDL_FILE = "out/production/CPSC304Project/sql/scripts/DDL.sql";
    private Connection connection;
    private SQLParserDelegate sqlParser;

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

    @Override
    public void updateTableData(TableData data) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public TableData getTableData(Set<String> dataToLookup) {
        throw new RuntimeException("Not implemented yet");
        //return null;
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

        for (String mdlStatement: mdlStatements) {
            try {
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(mdlStatement);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
//            String tableName = getTableNameFromMDL(mdlStatement);
//            String[] columns  = getColumNamesFromMDL(mdlStatement);
//            String[] data = getDataFromMDL(mdlStatement);
//            addSpecifiedData(tableName, columns, data);
//            System.out.println(tableName);
        }
        System.out.println(mdlStatements.size() + " insert statements run. Double click on tables in sidebar to verify data");


        try {
            Statement stmt = connection.createStatement();
            ResultSet results = stmt.executeQuery("SELECT * FROM RESIDENTIALMANAGINGOFFICE JOIN CAMPUS r USING(CZIPCODE, CSTADDRESS)");


            int cols = results.getMetaData().getColumnCount();
            for (int i = 1; i <= cols; i++ ){
                System.out.format("%-40s", results.getMetaData().getColumnLabel(i));
            }
            System.out.println();
            while (results.next()){
                for (int i = 1; i <= cols; i++) {
                    System.out.format("%-40s", results.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

//    private void addSpecifiedData(String tableName, String[] columns, String [] data) {
//        if (tableName.equalsIgnoreCase("Campus")) {
//            addDataIntoCampus(columns, data);
//        }
//    }
//
//    private void addDataIntoCampus(String [] columns, String [] data) {
//        System.out.println(columns);
//    }
//
//    private String getTableNameFromMDL(String mdlStatement) {
//        int idxFirstLeftParen = mdlStatement.indexOf('(');
//        return mdlStatement.substring(0, idxFirstLeftParen).replace("INSERT INTO",  "").trim();
//    }
//
//    private String [] getColumNamesFromMDL(String mdlStatement) {
//        int idxFirstLeftParen = mdlStatement.indexOf('(');
//        int idxFirstRightParen = mdlStatement.indexOf(')');
//        return mdlStatement.substring(idxFirstLeftParen + 1, idxFirstRightParen).trim().split(", ");
//    }
//
//    private String [] getDataFromMDL(String mdlStatement) {
//        int idxLastLeftParen = mdlStatement.lastIndexOf('(');
//        int idxLastRightParen = mdlStatement.lastIndexOf(')');
//        return mdlStatement.substring(idxLastLeftParen + 1, idxLastRightParen).replaceAll("\"", "").trim().split(", ");
//    }

    private void dropAllTablesIfExist() {
        Set<String> tableNames = TableNames.TABLE_NAMES;

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
