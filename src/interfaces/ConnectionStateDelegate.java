package interfaces;

import java.sql.Connection;

public interface ConnectionStateDelegate {
    boolean isConnected();
    Connection getConnection();
}
