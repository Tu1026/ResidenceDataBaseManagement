package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import model.table.Column;

import java.util.List;

public class FilterPane {
    private  ComboBox<String> filterColumnNames;
    private GridPane filterPane;
    public FilterPane(){
        filterPane = new GridPane();
        filterColumnNames = new ComboBox<>();

        TextField filter = new TextField();
        filter.setPromptText("Enter what your filtering value here");

        filterPane.add(filterColumnNames, 0, 0);
        filterPane.add(filter, 0, 1);

        filter.prefWidthProperty().bind(filterPane.widthProperty());
        filter.setMinHeight(filterPane.getPrefHeight() / 5);
        filterColumnNames.prefWidthProperty().bind(filterPane.widthProperty());
        filterColumnNames.setMinHeight(filterPane.getPrefHeight() / 5);

        RowConstraints filterRows = new RowConstraints();
        filterRows.setPercentHeight(50);
        filterPane.getRowConstraints().addAll(filterRows, filterRows);

    }
    public GridPane returnPane(){
        return filterPane;
    }

    public void updateFilterList(List<Column> columns) {
        filterColumnNames.getSelectionModel().clearSelection();
        filterColumnNames.getItems().clear();
        for (Column column : columns) {
            filterColumnNames.getItems().add(column.name);
        }
        filterColumnNames.getSelectionModel().selectFirst();
    }
}
