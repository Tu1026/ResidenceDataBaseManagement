package handler;

import model.ConnectionState;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHandler {
    private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
    private Connection connection;
    private static boolean isLoggedIn = false;


    public ConnectionHandler() {
        try {
            // Load the Oracle JDBC driver
            // Note that the path could change for new drivers
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException e) {
            System.out.println("Error: initializing oracle library: " + e.getMessage());
        }
    }


    /**
     *
     * @param username username string
     * @param pwd password string
     * @return ConnectionState with the connectionObject & the current state (logged in, Logged out)
     */
    public ConnectionState login(String username, String pwd) {
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
    public ConnectionState close() {
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
