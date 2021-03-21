package interfaces;

public interface ConnectionHandlerDelegate {

    /**
     *
     * @param username username string
     * @param pwd password string
     * @return ConnectionState with the connection object & the current state (logged in, Logged out)
     */
    ConnectionStateDelegate login(String username, String pwd);

    /**
     * Closes connection if connection is not null
     * User does not need to be logged in to call this
     * @return ConnectionState with the connection object & the current state
     */
    ConnectionStateDelegate close();
}
