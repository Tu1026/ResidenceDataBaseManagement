package model;

import java.sql.Connection;

public class ConnectionState {
    private Connection connection;
    private Boolean connected;

    public ConnectionState(Connection connection, Boolean isConnected) {
        this.connection = connection;
        this.connected = isConnected;
    }

    public Connection getConnection() {
        return connection;
    }

    public Boolean isConnected() {
        return connected;
    }
}
