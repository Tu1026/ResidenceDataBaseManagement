package handler;

import model.TableNames;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DataHandler {

    private static final String DDL_FILE = "out/production/CPSC304Project/model/DDL.sql";
    private Connection connection;

    public DataHandler() {

    }


    public void setConnection(Connection connection) {
        this.connection = connection;
        initializeDDL();
    }

    public void initializeDDL() {
        dropAllTablesIfExist();

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
        ArrayList<String> statements = new ArrayList<>();
        String delim = ";";

        try (Scanner scanner = new Scanner(new File(DDL_FILE))) {
            StringBuilder sqlStatementString = new StringBuilder();
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine().trim();
                if (lineHasDataNotAComment(nextLine)) {
                    sqlStatementString.append(nextLine.replaceAll("\\s+", " ")); // replace all whitespace chars by single space
                }
                if (sqlStatementString.toString().contains(delim)) {
                    statements.add(sqlStatementString.toString().replaceAll(delim, ""));
                    sqlStatementString = new StringBuilder();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return statements;
    }

    private boolean lineHasDataNotAComment(String line) {
        return !line.equals("") && !(line.contains("--"));
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
}
