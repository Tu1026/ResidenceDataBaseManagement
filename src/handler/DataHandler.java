package handler;

import model.TableNames;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public final class DataHandler {

    private static final String DDL_FILE = "out/production/CPSC304Project/model/DDL.sql";
    private Connection connection;

    public DataHandler() {

    }


    public void setConnection(Connection connection) {
        this.connection = connection;
        initializeDDL(); // TODO: have this be called by the controller if the option is set by the user
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

    private List<String> parseDDL() {
        return new SQLParser().parseSQLDDL(new File(DDL_FILE));
    }


    private void dropAllTablesIfExist() {
        Set<String> tableNames = TableNames.TABLE_NAMES;

        try (Statement stmt = connection.createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery("SELECT table_name FROM user_tables")) { // selects all tables
                while (resultSet.next()) { //
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
