package ui;

import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.controlsfx.control.CheckComboBox;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ViewColumnsPane<T> extends GridPane {
    private final CheckComboBox<T> displayColumnNames;
    private String tableName;

    public ViewColumnsPane(Consumer<List<T>> getCheckedItems){
        displayColumnNames = new CheckComboBox<>();

        VBox labelAndColumn = new VBox();
        labelAndColumn.setSpacing(7);

        Label filterLabel = new Label("Display Columns: ");
        filterLabel.setFont(Font.font("Times New Roman", 16));
        filterLabel.setWrapText(true);
        labelAndColumn.getChildren().addAll(filterLabel, displayColumnNames);
        add(labelAndColumn, 0,0);

        displayColumnNames.prefWidthProperty().bind(this.widthProperty());
        displayColumnNames.setMinHeight(25);
        //displayColumnNames.getCheckModel().getCheckedItems().addListener((ListChangeListener<T>) c -> {
//            while (c.next()) {
//                if (c.wasRemoved()){
        // if (displayColumnNames.getCheckModel().getSelectedItems().size() == 0) {
        //   displayColumnNames.getCheckModel().selectLast();
        //T item = displayColumnNames.getCheckModel().getSelectedItems().get(0);
        //displayColumnNames.getItemBooleanProperty(item).setValue(true);
        //displayColumnNames.checkModelProperty().
//                    }
//                }
//            }
        List<T> columns = new ArrayList<>();
        try {
            // columns = displayColumnNames.getCheckModel().getCheckedItems();
        }catch (Exception e){

        }
        getCheckedItems.accept(columns);
        //  });

        RowConstraints filterRows = new RowConstraints();
        filterRows.setPercentHeight(50);
        this.getRowConstraints().addAll(filterRows, filterRows);
    }



    public void updateFilterList(List<T> columns, String tableName) {
        if (this.tableName == null || !this.tableName.equals(tableName)){
            displayColumnNames.getItems().clear();
            this.tableName = tableName;
            displayColumnNames.getItems().addAll(columns);
            displayColumnNames.getCheckModel().selectAll();
        }
    }
}
