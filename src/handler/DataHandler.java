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
        Statement stmt = null;
        try {

            List<String> ddlStatements = parseDDL();
            for (String ddlStatement : ddlStatements) {
                stmt = connection.createStatement();
                stmt.execute(ddlStatement);
            }

            System.out.println(ddlStatements.size() + " tables created. Check oracle sidebar to make sure they are present");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    private List<String> parseDDL() {
        ArrayList<String> statements = new ArrayList<>();
        String delim = ";";

        try (Scanner scanner = new Scanner(new File(DDL_FILE))) {
            while (scanner.hasNextLine()) {
                StringBuilder stmt = new StringBuilder();
                while (scanner.hasNextLine()) {
                    String nextLine = scanner.nextLine().trim();
                    if (!nextLine.equals("") && !(nextLine.contains("--"))) {
                        stmt.append(nextLine.replaceAll("\\s+", " "));

                    }
                    if (stmt.toString().contains(delim)) {
                        statements.add(stmt.toString().replaceAll(";", ""));
                        break;
                    }
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return statements;
    }


    private void dropAllTablesIfExist() {

        Set<String> currentTables = new HashSet<>();

        try (Statement stmt = connection.createStatement()) {
            try (ResultSet resultSet = stmt.executeQuery("SELECT table_name FROM user_tables")){
                while (resultSet.next()) {
                    currentTables.add(resultSet.getString(1).toUpperCase());
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Set<String> tableNames = TableNames.getTableNames();
        for (String tableName: currentTables) {
            try (Statement stmt = connection.createStatement()) {
                if (tableNames.contains(tableName)) {
                    stmt.execute("DROP TABLE " + tableName + " CASCADE CONSTRAINTS ");
                    System.out.println(tableName + " table dropped");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
