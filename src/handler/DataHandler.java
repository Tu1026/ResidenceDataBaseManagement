package handler;

import interfaces.DataHandlerDelegate;
import interfaces.SQLParserDelegate;
import javafx.collections.ObservableList;
import model.TableModel;
import model.TableNames;
import model.tables.TableData;

import java.io.File;
import java.io.IOException;
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

        List<String> ddlStatements = parseDDL();
        for (String ddlStatement : ddlStatements) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(ddlStatement);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        System.out.println(ddlStatements.size() + " tables created. Check oracle sidebar to make sure they are present");
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

    private List<String> parseDDL() {
        return sqlParser.parseDDL(new File(DDL_FILE));
    }


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

    //I am adding this method to test out the UI's reponse in terms of executing queries
    // Resultset nicely fetches data from sql as well as retaining metadata
    public ResultSet executeQuery(String sql) throws SQLException{
        ResultSet result;
        result = connection.createStatement().executeQuery(sql);
        return result;
    }
}
