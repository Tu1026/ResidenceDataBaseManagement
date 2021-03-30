package ui;


import interfaces.ControllerDelegate;
import interfaces.TableViewUI;
import javafx.application.Platform;
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


public class MainWindow implements TableViewUI {


    public Scene tableScene;
    private List<Column> columns = new ArrayList<>();
    private final MyTableView tableView;
    private final FilterPane filterPane;
    private final ViewColumnsPane<String> viewColumnsPane;
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
        RowConstraints r1 = new RowConstraints();
        r1.setPercentHeight(20);
        RowConstraints r2 = new RowConstraints();
        r2.setPercentHeight(40);
        RowConstraints r3 = new RowConstraints();
        r3.setPercentHeight(40);
        innerPaneTableMenu.getRowConstraints().addAll(r1, r2, r3);

        outerPane.add(innerPane, 0, 0);
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
        selectBoxAndInsertGrid.getRowConstraints().addAll(heightConstraints, heightConstraints, heightConstraints);

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

        GridPane.setHalignment(selectTables, HPos.CENTER);
        GridPane.setValignment(selectTables, VPos.TOP);
        outerPane.add(innerPaneTableMenu, 1, 0);

        innerPaneTableMenu.add(selectBoxAndInsertGrid,0,0);

        SearchView searchView = new SearchView(controller);
        outerPane.add(searchView, 0, 1, 2, 1);
        GridPane.setMargin(searchView, new Insets(25, 0,10,11));
        GridPane.setHalignment(searchView, HPos.CENTER);
        GridPane.setValignment(searchView, VPos.CENTER);


        tableView = new MyTableView();
        tableView.prefWidthProperty().bind(innerPane.widthProperty());
        tableView.prefHeightProperty().bind(innerPane.heightProperty());

        //Adding tableColumbs to the 0,0 of the inner gridpane
        innerPane.add(tableView, 0,0);
        tableView.setOnKeyReleased( key -> {
            if (key.getCode() == KeyCode.DELETE || key.getCode() == KeyCode.BACK_SPACE){
                System.out.println("Deleting...");
                List<String> rowData = tableView.getSelectionModel().getSelectedItem();
                controller.deleteTable(rowData);
            }
        });

        filterPane = new FilterPane();
        filterPane.setKeyReleased(key -> {
            requestFiler(controller);
        });

        viewColumnsPane = new ViewColumnsPane<>((List<String> data) -> {
           requestFiler(controller);
        }, "All");


        innerPaneTableMenu.add(filterPane,0,1);
        innerPaneTableMenu.add(viewColumnsPane, 0, 2);
        //Initialize campus as the default table
        controller.loadTable("Campus");
        tableScene = new Scene(outerPane, 1124,798);
        innerPane.setGridLinesVisible(true);
        //innerPaneTableMenu.setGridLinesVisible(true);
        //innerPaneTableMenu.setStyle("-fx-border-width: 1 1 1 1; -fx-border-color: #838181");
        innerPaneTableMenu.setPadding(new Insets(5,5,5,5));
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
}
