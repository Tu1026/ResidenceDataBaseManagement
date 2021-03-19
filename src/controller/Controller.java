package controller;

import handler.ConnectionHandler;
import handler.DataHandler;
import model.ConnectionState;

public class Controller {
    ConnectionHandler connectionHandler;
    DataHandler dataHandler;

    public Controller() {
        connectionHandler = new ConnectionHandler();
    }


    /**
     * Requests connection from ConnectionHandler, dispatches connection to DataHandler if successful
     * @param username username string
     * @param pwd password string
     */
    public void login(String username, String pwd){
        ConnectionState cs = connectionHandler.login(username, pwd);
        if (cs.isConnected()) {
            dataHandler = new DataHandler();
            dataHandler.setConnection(cs.getConnection());
        } else{
            System.err.println("Error initializing dataHandler: Not connected to oracle services");
        }
    }

    public void initializeSQLDDL(){
        if (dataHandler != null) {
            dataHandler.initializeDDL();;
        }
    }

    public void logout(){
        connectionHandler.close();
    }
}
