package ui;

import controller.Controller;
import interfaces.ControllerDelegate;
import interfaces.TableViewUI;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import model.table.Column;
import model.table.Table;
import model.table.TableRow;

import java.io.IOException;
import java.util.List;


public class MainWindow implements TableViewUI {

    public Scene tableScene;

    MyTableView tableView;
    public MainWindow(ControllerDelegate controller){
        controller.setUI(this);
        GridPane outerPane = new GridPane();
        GridPane innerPane = new GridPane();
        GridPane innerPaneTableMenu = new GridPane();

        
        outerPane.add(innerPane, 0, 0);
        outerPane.getColumnConstraints().add(new ColumnConstraints(946));
        outerPane.getRowConstraints().add(new RowConstraints(342));
        outerPane.getRowConstraints().add(new RowConstraints(456));

        ComboBox<String> selectTables = new ComboBox<>();
        selectTables.setPrefSize(250,36);
        selectTables.getItems().add("Campus");
        selectTables.getItems().add("Residential Managing Office");
        selectTables.getItems().add("Building Manager");
        selectTables.getItems().add("Senior Advisor");
        selectTables.getItems().add("Residence Advisor");
        selectTables.getItems().add("Residence Budget");
        selectTables.getItems().add("Residence Capacity");
        selectTables.getItems().add("Floor");
        selectTables.getItems().add("House");
        selectTables.getItems().add("Unit");
        selectTables.getItems().add("Resident Address");
        selectTables.getItems().add("Resident Info");
        selectTables.getSelectionModel().selectFirst();

        innerPaneTableMenu.add(selectTables,0,0);
        GridPane.setHalignment(selectTables, HPos.CENTER);
        GridPane.setValignment(selectTables, VPos.TOP);
        outerPane.add(innerPaneTableMenu, 1, 0);


//        outerPane.add(selectTables,1,0);
//        outerPane.setHalignment(selectTables, HPos.CENTER);
//        outerPane.setValignment(selectTables, VPos.CENTER);

        Button goToTable = new Button("Go to Table");
        goToTable.setPrefSize(113,36);
        outerPane.add(goToTable, 1,1);
        GridPane.setHalignment(goToTable, HPos.CENTER);
        GridPane.setValignment(goToTable, VPos.CENTER);

        Button deleteRow = new Button("Delete Row");

        tableView = new MyTableView();
        tableView.setSizeProperties(innerPane.widthProperty(), innerPane.heightProperty());

//        tables.prefWidthProperty().bind(innerPane.widthProperty());
//        tables.prefHeightProperty().bind(innerPane.heightProperty());

        innerPane.add(tableView.getComponent(), 0,0);


        goToTable.setOnAction(e -> {
            String tableState = selectTables.getValue().toString();
            System.out.println(tableState);
            controller.loadTable(tableState.replaceAll(" ", ""));
        });


        controller.performQuery("SELECT * FROM Campus");

        tableScene = new Scene(outerPane, 1124,798);
    }

    public Scene getScene() {
        return tableScene;
    }



    @Override
    public void updateVisibleTable(Table table) {
        tableView.buildData(table);
    }
}
