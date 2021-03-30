package interfaces;

import java.sql.Connection;

public interface ConnectionStateDelegate {
    /**
     * bool containing info whether db is currently connected
     * @return true if connected, false otherwise
     */
    boolean isConnected();

    /**
     * Returns Connection object, can be used to post requests/get data from db
     * @return oracle Connection Object
     */
    Connection getConnection();

    /**
     * String containning connection messages
     * @return String with any messages / errors
     */
    String getMessage();
}
