package controller;

import handler.ConnectionHandler;
import handler.DataHandler;
import interfaces.*;
import javafx.application.Platform;
import model.table.Table;

public class Controller implements ControllerDelegate {

    ConnectionHandlerDelegate connectionHandler;
    DataHandlerDelegate dataHandler;
    TableViewUI ui;

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
        System.out.println("Query initializing...");
        new Thread(() -> {
            dataHandler.performQuery(query, this::resultCallback);
        }).start();

        // This will be deleted in the future
//        new Thread(() -> {
//            try {
//                System.err.println("Updating table to data from Campus in 4 seconds...");
//                Thread.sleep(4000);
//                System.out.println("Starting Campus Query");
//                loadTable("Campus");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
    }

    public void initializeSQLDDL(){
        if (dataHandler != null) {
            dataHandler.initializeDDL();;
        }
    }

    public ConnectionStateDelegate logout(){
        return connectionHandler.close();
    }

    @Override
    public void setUI(TableViewUI ui) {
        this.ui = ui;
    }

    @Override
    public void loadTable(String tableName) {
        new Thread(() -> {
            dataHandler.getTableData(tableName, this::resultCallback);
        }).start();
    }

    private void resultCallback(Table resultTable){
        Platform.runLater(() -> {
            System.out.println("Displaying query results in table");
            ui.updateVisibleTable(resultTable);
        });
    }
}
