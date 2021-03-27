package model;

import interfaces.ConnectionStateDelegate;

import java.sql.Connection;

public final class ConnectionState implements ConnectionStateDelegate {
    private final Connection connection;
    private final Boolean connected;
    private final String message;

    public ConnectionState(Connection connection, Boolean isConnected, String message) {
        this.connection = connection;
        this.connected = isConnected;
        this.message = message;
    }

    public ConnectionState(Connection connection, Boolean isConnected) {
        this(connection, isConnected, "No Messages");
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean isConnected() {
        return connected;
    }

    public String getMessage() {
        return this.message;
    }
}
