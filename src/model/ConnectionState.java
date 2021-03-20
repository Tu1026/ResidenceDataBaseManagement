package model;

import interfaces.ConnectionStateDelegate;

import java.sql.Connection;

public final class ConnectionState implements ConnectionStateDelegate {
    private Connection connection;
    private Boolean connected;

    public ConnectionState(Connection connection, Boolean isConnected) {
        this.connection = connection;
        this.connected = isConnected;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return connected;
    }
}
