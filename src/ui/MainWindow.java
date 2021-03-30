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
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.OracleColumnNames;
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
    FilterPane filterPane;
    private String currTable;
    // declare your filter combobox class

    public MainWindow(ControllerDelegate controller){
        controller.setUI(this);
        GridPane outerPane = new GridPane();
        GridPane innerPane = new GridPane();
        innerPane.setPadding(new Insets( 0, 10, 0, 10));
        outerPane.setPadding(new Insets(20, 20, 20, 10));

        GridPane innerPaneTableMenu = new GridPane();
        RowConstraints heightConstraints = new RowConstraints();
        heightConstraints.setPercentHeight(50);
        innerPaneTableMenu.getRowConstraints().addAll(heightConstraints, heightConstraints);

        outerPane.add(innerPane, 0, 0);
        outerPane.getColumnConstraints().add(new ColumnConstraints(946));
        outerPane.getRowConstraints().add(new RowConstraints(342));
        outerPane.getRowConstraints().add(new RowConstraints(456));


        ComboBox<String> selectTables = new ComboBox<>();
        selectTables.setPrefSize(250,36);
        for (String table: OracleTableNames.PRETTY_NAMES) {
            selectTables.getItems().add(table);
        }
        selectTables.getSelectionModel().selectFirst();
        selectTables.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (!oldItem.equals(newItem)) {
                controller.loadTable(newItem);
            }
        });

        GridPane selectBoxAndInsertGrid = new GridPane();

        VBox insertAndUpdateVbox = new VBox();
        selectBoxAndInsertGrid.add(selectTables, 0,0);
        selectBoxAndInsertGrid.getRowConstraints().addAll(heightConstraints, heightConstraints);

        Button insertButton = new Button("Insert a Resident");
        insertButton.prefWidthProperty().bind(insertAndUpdateVbox.prefWidthProperty());
        insertAndUpdateVbox.getChildren().add(insertButton);
        selectBoxAndInsertGrid.add(insertAndUpdateVbox, 0, 1);
        insertButton.setOnAction(event -> {
            Stage insertStage = new Stage();
            VBox test = new VBox();
            Scene newScene = new Scene(test, 950,300);
            insertStage.setScene(newScene);
            insertStage.show();
        });

        VBox deleteAndUpdate = new VBox();
        Button deleteRowButton = new Button("Delete the selected row");
        deleteAndUpdate.getChildren().add(deleteRowButton);
        innerPaneTableMenu.add(deleteAndUpdate,0,1);
        deleteRowButton.setOnAction(event -> {
            List<String> listOfStrToDelete = new ArrayList<String>();
            String[] stringAr = new String[0];
            stringAr = tableView.getComponent().getSelectionModel().getSelectedItems().get(0).toString().split(",");
            for (String str : stringAr) {
                listOfStrToDelete.add(str.trim());
            }
            controller.deleteTable(listOfStrToDelete);
        });


        GridPane.setHalignment(selectTables, HPos.CENTER);
        GridPane.setValignment(selectTables, VPos.TOP);
        outerPane.add(innerPaneTableMenu, 1, 0);
        innerPaneTableMenu.getRowConstraints().addAll(heightConstraints, heightConstraints);

        innerPaneTableMenu.add(selectBoxAndInsertGrid,0,0);
//        GridPane bottomRight = new GridPane();
//        bottomRight.getRowConstraints().addAll(heightConstraints, heightConstraints);


        Button goToTable = new Button("Go to Table");
        goToTable.setPrefSize(113,36);
        outerPane.add(goToTable, 1,1);
        GridPane.setHalignment(goToTable, HPos.CENTER);
        GridPane.setValignment(goToTable, VPos.CENTER);


        tableView = new MyTableView();
        tableView.setSizeProperties(innerPane.widthProperty(), innerPane.heightProperty());

        //Adding tableColumbs to the 0,0 of the inner gridpane
        innerPane.add(tableView.getComponent(), 0,0);
        tableView.getComponent().setOnKeyReleased( key -> {
            if (key.getCode() == KeyCode.DELETE || key.getCode() == KeyCode.BACK_SPACE){
                System.out.println("Deleting...");
                List<String> rowData = tableView.getComponent().getSelectionModel().getSelectedItem();
                controller.deleteTable(rowData);
            }
        });

        //On click event for the goTotable button
        goToTable.setOnAction(e -> {
            String tableState = selectTables.getValue();
            System.out.println(tableState);
            controller.loadTable(tableState);
        });

        filterPane = new FilterPane(controller);
        innerPaneTableMenu.add(filterPane.returnPane(),0,2,1,2);
        //Initialize campus as the default table
        controller.loadTable("Campus");
        tableScene = new Scene(outerPane, 1124,798);
        innerPane.setGridLinesVisible(true);
        innerPaneTableMenu.setGridLinesVisible(true);
    }

    public Scene getScene() {
        return tableScene;
    }

    @Override
    public void updateVisibleTable(Table table) {
        List<String> columnNames = new ArrayList<>();
        for (Column column :table.getColumnsList()){
            columnNames.add(OracleColumnNames.GET_PRETTY_COLUMN_NAMES.get(column.name));
        }
        filterPane.updateFilterList(columnNames, table.getName());
        tableView.buildData(table);
    }

    //Use this to display error
    public void displayError(String errorString){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error in Manipulating Database");
        if (errorString.contains(":")) {
            errorString = errorString.split(":")[1];
        }
        a.setContentText(errorString);
        a.showAndWait();
    }

}
