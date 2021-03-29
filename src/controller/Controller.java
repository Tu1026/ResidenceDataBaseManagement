package controller;

import handler.ConnectionHandler;
import handler.DataHandler;
import interfaces.*;
import javafx.application.Platform;
import model.table.Table;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Controller implements ControllerDelegate {

    private ConnectionHandlerDelegate connectionHandler;
    private DataHandlerDelegate dataHandler;
    private TableViewUI ui;
    private String currentTable = null;

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

    // TODO: delete this, queries should be prepared.
    @Override
    public void performQuery(String query) {
        System.out.println("Query initializing...");
        new Thread(() -> {
            dataHandler.performQuery(query, this::resultCallback);
        }).start();
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
            dataHandler.getTableData(tableName.trim(), this::resultCallback);
        }).start();
    }

    private void resultCallback(Table resultTable){
        if (! resultTable.getName().equalsIgnoreCase("no name")) {
            currentTable = resultTable.getName();
        }else{
            currentTable = null;
        }

        Platform.runLater(() -> {
            System.out.println("Displaying query results in table");
            ui.updateVisibleTable(resultTable);
        });

        for (String str: resultTable.getPKs()) {
            System.out.println(str);
        }
    }

    public void filter(String filter, String columnName){
        if (currentTable != null){
            new Thread(() -> {
                dataHandler.filterTable(currentTable, filter, columnName, this::resultCallback);
            }).start();
        }
    }

    @Override
    public void getDataForStudentInsertion(String tableName, List<String> columnsToGet, Map<String, String> data, Consumer<Table> callback) {
        new Thread(() -> {

        });
    }
}
