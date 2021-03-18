package handler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHandler {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";



    public ConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println("Error:" + " " + e.getMessage());
        }
    }


    public void login() {

    }

    public void close() {

    }


}
