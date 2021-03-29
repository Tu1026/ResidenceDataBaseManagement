package ui;

import controller.Controller;
import interfaces.ControllerDelegate;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.OracleColumnNames;
import model.table.Column;

import java.util.List;

public class FilterPane {
    private ComboBox<String> filterColumnNames;
    private GridPane filterPane;
    private List<String> columnList;
    private ControllerDelegate controller;
    private String tableName;

    public FilterPane(ControllerDelegate controller){
        this.controller = controller;
        filterPane = new GridPane();
        filterColumnNames = new ComboBox<>();

        TextField filter = new TextField();
        filter.setPromptText("Filtering Value");

        VBox LabelAndColumn = new VBox();

        Label filterLabel = new Label("Filter by column:");
        filterLabel.setFont(Font.font("Times New Roman", 17));
        filterLabel.setWrapText(true);
        LabelAndColumn.getChildren().addAll(filterLabel, filterColumnNames);
        filterPane.add(LabelAndColumn, 0,0);
        filterPane.add(filter, 0, 1);

        filter.prefWidthProperty().bind(filterPane.widthProperty());
        filter.setMinHeight(filterPane.getPrefHeight() / 5);
        filterColumnNames.prefWidthProperty().bind(filterPane.widthProperty());
        filterColumnNames.setMinHeight(filterPane.getPrefHeight() / 5);

        RowConstraints filterRows = new RowConstraints();
        filterRows.setPercentHeight(50);
        filterPane.getRowConstraints().addAll(filterRows, filterRows);

        filter.setOnKeyReleased(key -> {
            System.out.println("Filtering by Column " + filterColumnNames.getValue());
            System.out.println("Filtering by value " + filter.getText());
            controller.filter(filter.getText(), filterColumnNames.getValue().trim());
        });

    }

    public GridPane returnPane(){
        return filterPane;
    }

    public void updateFilterList(List<String> columns, String tableName) {
        if (this.tableName != tableName || this.tableName == null){
            columnList = columns;
            filterColumnNames.getSelectionModel().clearSelection();
            filterColumnNames.getItems().clear();
            this.tableName = tableName;
            for (String column : columns) {
                filterColumnNames.getItems().add(column);
            }
            filterColumnNames.getSelectionModel().selectFirst();

        }

    }
}
