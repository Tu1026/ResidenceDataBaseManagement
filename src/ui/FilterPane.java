package ui;

import controller.Controller;
import interfaces.ControllerDelegate;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.OracleColumnNames;
import model.table.Column;

import java.security.Key;
import java.util.List;

public class FilterPane extends GridPane {
    private final ComboBox<String> filterColumnNames;
    private String tableName;
    private final TextField filter;

    public FilterPane(){
        filterColumnNames = new ComboBox<>();


        filter = new TextField();
        filter.setPromptText("Filtering Value");

        VBox labelAndColumn = new VBox();
        labelAndColumn.setSpacing(7);

        Label filterLabel = new Label("Filter By Column:");
        filterLabel.setFont(Font.font("Times New Roman", 16));
        filterLabel.setWrapText(true);
        labelAndColumn.getChildren().addAll(filterLabel, filterColumnNames, filter);
        add(labelAndColumn, 0,0);

        filter.prefWidthProperty().bind(this.widthProperty());
        filter.setMinHeight(25);
        filterColumnNames.prefWidthProperty().bind(this.widthProperty());
        filterColumnNames.setMinHeight(25);
        filterColumnNames.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (oldItem!= null && newItem != null && !oldItem.equals(newItem)) {
                filter.clear();
            }
        });

        RowConstraints filterRows = new RowConstraints();
        filterRows.setPercentHeight(50);
        this.getRowConstraints().addAll(filterRows, filterRows);
    }

    public void setKeyReleased(EventHandler<KeyEvent> event){
        filter.setOnKeyReleased(event);
    }

    public String getFilterText() {
        return filter.getText();
    }

    public String getSelectedColumn() {
        return filterColumnNames.getValue();
    }

    public void updateFilterList(List<String> columns, String tableName) {
        if (this.tableName == null || !this.tableName.equals(tableName)){
            filterColumnNames.getSelectionModel().clearSelection();
            filterColumnNames.getItems().clear();
            this.tableName = tableName;
            filterColumnNames.getItems().addAll(columns);
            filterColumnNames.getSelectionModel().selectFirst();
            filter.setText("");
        }
    }
}
