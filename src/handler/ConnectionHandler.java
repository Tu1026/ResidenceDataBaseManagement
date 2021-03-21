package handler;

import interfaces.ConnectionHandlerDelegate;
import interfaces.ConnectionStateDelegate;
import model.ConnectionState;
import oracle.jdbc.driver.OracleDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionHandler implements ConnectionHandlerDelegate {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
//    private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
    private Connection connection;
    private boolean isLoggedIn = false;


    public ConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            DriverManager.registerDriver(new OracleDriver());
        } catch (SQLException e) {
            System.out.println("Error initializing oracle library: " + e.getMessage());
        }
    }

    public ConnectionStateDelegate login(String username, String pwd) {
        if (!isLoggedIn) {
            try {
                connection = DriverManager.getConnection(ORACLE_URL, username, pwd);
                System.out.println(username + " logged in to oracle");
                isLoggedIn = true;
            } catch (SQLException e){
                System.err.println("Error logging in: " + e.getMessage());
            }
        } else{
            System.out.println("Error logging in: already logged in. \nClose the connection first and try again");
        }

        return new ConnectionState(connection, isLoggedIn);
    }

    /**
     * Closes connection if connection is not null
     * User does not need to be logged in to call this
     */
    public ConnectionStateDelegate close() {
        try {
            if (connection != null) {
                connection.close();
            }
            isLoggedIn = false;
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }

        return new ConnectionState(connection, isLoggedIn);
    }


}
