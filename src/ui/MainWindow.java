package ui;


import interfaces.ControllerDelegate;
import interfaces.MainUIView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

import java.util.ArrayList;
import java.util.List;


public class MainWindow implements MainUIView {


    public Scene tableScene;
    private final MyTableView tableView;
    private final FilterPane filterPane;
    private final ViewColumnsPane<String> viewColumnsPane;
//    private final AggregatePane aggregateTest;
    private boolean isUpdating = false;
    private Button deleteRowButton = null;
    private Button insertButton = null;


    // declare your filter combobox class

    public MainWindow(ControllerDelegate controller){
        controller.setUI(this);
        GridPane outerPane = new GridPane();
        outerPane.setPadding(new Insets(20, 20, 20, 10));

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPercentWidth(85);
        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPercentWidth(15);
        outerPane.getColumnConstraints().addAll(c1, c2);

        RowConstraints topRow = new RowConstraints();
        topRow.setPercentHeight(60);
        RowConstraints bottomRow = new RowConstraints();
        bottomRow.setPercentHeight(40);
        outerPane.getRowConstraints().addAll(topRow, bottomRow);

        /* ==================
         * Top Pane
         * ===================
         */

        /*
         * Left Pane (Table)
         */

        GridPane leftPane = new GridPane();
        leftPane.setPadding(new Insets( 0, 10, 0, 10));
        outerPane.add(leftPane, 0, 0);

        tableView = new MyTableView(controller::updateTable, this::setIsUpdating);
        tableView.prefWidthProperty().bind(leftPane.widthProperty());
        tableView.prefHeightProperty().bind(leftPane.heightProperty());

        //Adding tableColumbs to the 0,0 of the inner gridpane
        leftPane.add(tableView, 0,0);
        tableView.setOnKeyReleased( key -> {
            if (!isUpdating) {
                if (key.getCode() == KeyCode.DELETE || key.getCode() == KeyCode.BACK_SPACE) {
                    System.out.println("Deleting...");
                    List<String> rowData = tableView.getSelectionModel().getSelectedItem();
                    controller.deleteTable(rowData);
                }
            }
        });



        /*
         * Right Pane (Value Selection)
         */


        GridPane innerPaneTableMenu = new GridPane();
        RowConstraints r1 = new RowConstraints();
        r1.setPercentHeight(40);
        RowConstraints r2 = new RowConstraints();
        r2.setPercentHeight(40);
        RowConstraints r3 = new RowConstraints();
        r3.setPercentHeight(20);
        innerPaneTableMenu.getRowConstraints().addAll(r1, r2, r3);
        outerPane.add(innerPaneTableMenu, 1, 0);

        // ----- Table Selection ---- //
        VBox selectInsertDeleteBox = new VBox(10);

        ComboBox<String> selectTables = new ComboBox<>();
        selectTables.setPrefSize(250,36);
        for (String table: OracleTableNames.PRETTY_NAMES) {
            selectTables.getItems().add(table);
        }
        selectTables.getItems().add("Advance Search");

        GridPane.setMargin(selectTables, new Insets(0,0,10, 0));
        selectInsertDeleteBox.getChildren().add(selectTables);

        // ----- Table Updates ---- //

        VBox insertDeleteBox = new VBox(5);

        insertButton = new Button("Insert a Resident");
        insertButton.prefWidthProperty().bind(insertDeleteBox.widthProperty());
        insertDeleteBox.getChildren().add(insertButton);
        selectInsertDeleteBox.getChildren().add(insertDeleteBox);
        insertButton.setOnAction(event -> {
            Stage insertStage = new Stage();
            Scene insertScene = new ResidentInsert(controller).getScene();
            insertStage.setTitle("Insert a Resident Here");
            insertStage.setResizable(false);
            insertStage.setScene(insertScene);
            insertStage.show();
        });
//        insertButton.setVisible(false);
        insertButton.setDisable(true);

        deleteRowButton = new Button("Delete selected Resident");
        deleteRowButton.prefWidthProperty().bind(insertDeleteBox.widthProperty());
        insertDeleteBox.getChildren().add(deleteRowButton);
        deleteRowButton.setOnAction(event -> {
            List<String> listOfStrToDelete = new ArrayList<>();
            ObservableList<ObservableList<String>> selectedItems = tableView.getSelectionModel().getSelectedItems();
            if (selectedItems.size() > 0) {
                String [] rowArr = selectedItems.get(0).toString().split(",");
                for (String str : rowArr) {
                    listOfStrToDelete.add(str.trim());
                }
                controller.deleteTable(listOfStrToDelete);
            }
        });
//        deleteRowButton.setVisible(false);
        deleteRowButton.setDisable(true);



        GridPane.setHalignment(selectTables, HPos.CENTER);
        GridPane.setValignment(selectTables, VPos.TOP);

        // ----- Table Filtering ---- //
        filterPane = new FilterPane();
        filterPane.setKeyReleased(key -> requestFiler(controller));

        viewColumnsPane = new ViewColumnsPane<>((List<String> data) -> requestFiler(controller), "All");

        innerPaneTableMenu.add(selectInsertDeleteBox,0,0);
        innerPaneTableMenu.add(filterPane,0,1);
        innerPaneTableMenu.add(viewColumnsPane, 0, 2);


        /* ==================
         * Bottom Pane
         * ==================
         */

            AdvanceSearchPane searchView = new AdvanceSearchPane(controller);
            outerPane.add(searchView, 0, 1, 2, 1);
            GridPane.setMargin(searchView, new Insets(25, 0,10,11));
            GridPane.setHalignment(searchView, HPos.CENTER);
            GridPane.setValignment(searchView, VPos.CENTER);
            searchView.setDisable(true);


        /*
        Action event for select table
         */
        selectTables.getSelectionModel().selectFirst();
        selectTables.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (!oldItem.equals(newItem)) {
                if(newItem.equals("Advance Search")) {
                    filterPane.setDisable(true);
                    viewColumnsPane.setDisable(true);
                    searchView.setDisable(false);
                    if (!searchView.runIfSelected()) {
                        tableView.buildData(new Table(new String[]{}));
                    }

                }else {
                    controller.loadTable(newItem);
                    deleteRowButton.setDisable(!newItem.equals("Resident"));
                    insertButton.setDisable(!newItem.equals("Resident"));
                    filterPane.setDisable(false);
                    viewColumnsPane.setDisable(false);
                    searchView.setDisable(true);
                }
            }
        });


        /*
         * INITIALIZE
         */

        //Initialize campus as the default table
        controller.loadTable("Campus");
        tableScene = new Scene(outerPane, 1124,798);
    }

    private void requestFiler(ControllerDelegate controller) {
        String filterCol = filterPane.getSelectedColumn();
        String filterText = filterPane.getFilterText();
        List<String> filterColumns = viewColumnsPane.getSelectedColumns();
        System.out.println("Filtering by Column " + filterCol + " with value " + filterText);
        controller.filter(filterText, filterCol.trim(), filterColumns);
    }

    public Scene getScene() {
        return tableScene;
    }


    @Override
    public void updateVisibleTable(Table table) {
        Platform.runLater(() -> {
            List<String> columnNames = new ArrayList<>();
            for (Column column :table.getColumnsList()){
                columnNames.add(OracleColumnNames.GET_PRETTY_COLUMN_NAMES.get(column.name));
            }
            filterPane.updateFilterList(columnNames, table.getName());
            viewColumnsPane.updateFilterList(columnNames, table.getName());
//            aggregateTest.updateComboForAggregate(columnNames, table.getName(), "groupByCombo");
            tableView.buildData(table);
        });
    }

    @Override
    public void displayError(final String errorString){
        final String error = errorString.contains(":")?errorString.split(":")[1]: errorString;
        Platform.runLater( () -> {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error in Manipulating Database");
            a.setContentText(error);
            a.showAndWait();
        });
    }

    @Override public void displayMessage(final String msg){
        Platform.runLater( () -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Success");
            a.setContentText(msg);
            a.showAndWait();
        });
    }

    @Override
    public void reloadLast(ControllerDelegate controller){
        Platform.runLater(() -> requestFiler(controller));
    }

    private void setIsUpdating(boolean isUpdating){
        this.isUpdating = isUpdating;
    }
}
