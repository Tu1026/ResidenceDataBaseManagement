package controller;

import handler.ConnectionHandler;
import handler.DataHandler;
import interfaces.*;
import model.table.Table;
import model.table.UpdateObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Controller implements ControllerDelegate {

    private final ConnectionHandlerDelegate connectionHandler;
    private DataHandlerDelegate dataHandler;
    private TableViewUI ui;
    private String currentTable = null;

    public Controller() {
        connectionHandler = new ConnectionHandler();
    }

    /**
     * Requests connection from ConnectionHandler, dispatches connection to DataHandler if successful
     *
     * @param username username string
     * @param pwd      password string
     */
    public ConnectionStateDelegate login(String username, String pwd) {
        ConnectionStateDelegate cs = connectionHandler.login(username, pwd);
        if (cs.isConnected()) {
            dataHandler = new DataHandler();
            dataHandler.setConnection(cs.getConnection());
        } else {
            System.err.println("Error initializing dataHandler: Not connected to oracle services");
        }
        return cs;
    }

    public void initializeSQLDDL() {
        if (dataHandler != null) {
            dataHandler.initializeDDL();
        }
    }

    public ConnectionStateDelegate logout() {
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

    private void resultCallback(Table resultTable) {
        if (!resultTable.getName().equalsIgnoreCase("no name")) {
            currentTable = resultTable.getName();
        } else {
            currentTable = null;
        }

        System.out.println("Displaying query results in table");
        ui.updateVisibleTable(resultTable);
//        System.out.println("PKs: ");
//        for (String key : resultTable.getPKs().keySet()) {
//            System.out.println("Table --: " + key);
//            for (String str : resultTable.getPKs().get(key)) {
//                System.out.println("   " + str);
//            }
//        }
    }

    public void filter(String filter, String columnName, List<String> columnsToDisplay) {
        if (currentTable != null) {
            new Thread(() -> {
                dataHandler.filterTable(currentTable, filter, columnName, columnsToDisplay, this::resultCallback);
            }).start();
        }
    }

    @Override
    public void getDataForStudentInsertion(String tableName, List<String> columnsToGet, List<String> columnsToMatch, List<String> dataToMatch, Consumer<Table> callback) {
        new Thread(() -> {
            dataHandler.getSpecificTableData(tableName, columnsToGet, columnsToMatch, dataToMatch, callback);
        }).start();
    }

    @Override
    public void updateTable(UpdateObject updateObject){
        if (currentTable != null) {
            new Thread(() -> {
                dataHandler.updateTableData(this.currentTable, updateObject, this::updateResponse, ui::displayError);
            }).start();
        }
    }

    private void updateResponse(String response){
        ui.displayMessage(response);
        ui.reloadLast(this);
    }

    //Todo:
    @Override
    public void deleteTable(List<String> rowData) {
        new Thread(() -> {
            dataHandler.deleteTableData(this.currentTable, rowData, (Table) -> loadTable(this.currentTable), ui::displayError);
        }).start();
    }

    @Override
    public void insertStudent(Map<String, String> data) {
        new Thread(() -> {
            dataHandler.insertTableData(data, ui::displayMessage, ui::displayError);
        }).start();
    }
}
