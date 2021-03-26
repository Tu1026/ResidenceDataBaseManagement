package controller;

import handler.ConnectionHandler;
import handler.DataHandler;
import interfaces.ConnectionHandlerDelegate;
import interfaces.ConnectionStateDelegate;
import interfaces.ControllerDelegate;
import interfaces.DataHandlerDelegate;
import javafx.application.Platform;
import model.table.Table;


public class Controller implements ControllerDelegate {
    ConnectionHandlerDelegate connectionHandler;
    DataHandlerDelegate dataHandler;

    public Controller() {
        connectionHandler = new ConnectionHandler();
    }


    /**
     * Requests connection from ConnectionHandler, dispatches connection to DataHandler if successful
     * @param username username string
     * @param pwd password string
     */
    public ConnectionStateDelegate login(String username, String pwd){
        ConnectionStateDelegate cs = connectionHandler.login(username, pwd);
        if (cs.isConnected()) {
            dataHandler = new DataHandler();
            dataHandler.setConnection(cs.getConnection());
        } else{
            System.err.println("Error initializing dataHandler: Not connected to oracle services");
        }
        return cs;
    }

    @Override
    public void performQuery(String query) {
        new Thread(() -> {
            dataHandler.performQuery(query, this::resultCallback);
        });
    }

    @Override
    public void initializeSQL() {

    }

    public void initializeSQLDDL(){
        if (dataHandler != null) {
            dataHandler.initializeDDL();;
        }
    }

    public ConnectionStateDelegate logout(){
        return connectionHandler.close();
    }

    public void resultCallback(Table result){
        Platform.runLater(() -> {

        });
    }
}
