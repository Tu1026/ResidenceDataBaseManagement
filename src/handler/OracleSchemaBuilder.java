package handler;

import model.OracleTableNames;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;

public class OracleSchemaBuilder {
    private static final String DDL_FILE = "out/production/CPSC304Project/sql/scripts/DDL.sql";

    private final SQLParser sqlParser;

    public OracleSchemaBuilder() {
        sqlParser = new SQLParser();
    }

    public void initializeSchema(Connection connection){
        dropAllTablesIfExist(connection); // TODO: comment this out if you want to keep all data in tables
        // TODO: Leave this line if you want to clear all tabledata when the application starts
        // In the future, this can be set as a button in the application
        parseDDL(connection);
        parseMDL(connection);
    }

    private void parseDDL(Connection connection) {
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

    private void parseMDL(Connection connection) {
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


    private void dropAllTablesIfExist(Connection connection) {
        Set<String> tableNames = OracleTableNames.TABLE_NAMES;

        try (Statement stmt = connection.createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery("SELECT table_name FROM user_tables")) { // selects all tables
                while (resultSet.next()) {
                    String tableName = resultSet.getString(1).toUpperCase();
                    if (tableNames.contains(tableName)) {
                        forceDropSpecifiedTable(connection, tableName);
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void forceDropSpecifiedTable(Connection connection, String tableName) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DROP TABLE " + tableName + " CASCADE CONSTRAINTS "); // Drop tables, ignore any constraints for deletion
            System.out.println(tableName + " table dropped");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
