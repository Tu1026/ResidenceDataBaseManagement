package ui;

import com.sun.rowset.internal.Row;
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
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import model.OracleTableNames;
import model.table.Column;
import model.table.Table;
import model.table.TableRow;
import sun.awt.image.GifImageDecoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainWindow implements TableViewUI {

    public Scene tableScene;
    private List<Column> columns = new ArrayList<>();
    private MyTableView tableView;
    private  ComboBox<String> filterColumnNames;
    // declare your filter combobox class

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


        tableView = new MyTableView();
        tableView.setSizeProperties(innerPane.widthProperty(), innerPane.heightProperty());

//        tables.prefWidthProperty().bind(innerPane.widthProperty());
//        tables.prefHeightProperty().bind(innerPane.heightProperty());

        //Adding tableColumbs to the 0,0 of the inner gridpane
        innerPane.add(tableView.getComponent(), 0,0);

        //On click event for the goTotable button
        goToTable.setOnAction(e -> {
            String tableState = selectTables.getValue().toString();
            System.out.println(tableState);
            controller.loadTable(tableState.replaceAll(" ", ""));
        });


        GridPane filterPane = new GridPane();
        filterColumnNames = new ComboBox<>();

        TextField filter = new TextField();
        filter.setPromptText("Enter what your filtering value here");

        filterPane.add(filterColumnNames, 0, 0);
        filterPane.add(filter, 0, 1);
        innerPaneTableMenu.add(filterPane, 0, 1);

        filter.prefWidthProperty().bind(filterPane.widthProperty());
        filter.setMinHeight(filterPane.getPrefHeight() / 5);
        filterColumnNames.prefWidthProperty().bind(filterPane.widthProperty());
        filterColumnNames.setMinHeight(filterPane.getPrefHeight() / 5);


        RowConstraints filterRows = new RowConstraints();
        filterRows.setPercentHeight(50);
        innerPaneTableMenu.getRowConstraints().addAll(filterRows, filterRows);
        filterPane.getRowConstraints().addAll(filterRows, filterRows);


        innerPaneTableMenu.setGridLinesVisible(true);
        filterPane.setGridLinesVisible(true);
        innerPane.setGridLinesVisible(true);
        outerPane.setGridLinesVisible(true);

        //Initialize campus as the default table
        controller.loadTable("Campus");
        tableScene = new Scene(outerPane, 1124,798);
    }

    public Scene getScene() {
        return tableScene;
    }


    public void updateColums(List<Column> colums){
        this.columns = colums;
        filterColumnNames.getSelectionModel().clearSelection();
        filterColumnNames.getItems().clear();
        for (Column column : columns) {
            filterColumnNames.getItems().add(column.name);
        }
        filterColumnNames.getSelectionModel().selectFirst();

    }

    @Override
    public void updateVisibleTable(Table table) {
        updateColums(table.getColumnsList());
        tableView.buildData(table);
    }
}
