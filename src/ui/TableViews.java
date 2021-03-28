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


public class TableViews extends Application implements TableViewUI {

    TableView<ObservableList<String>> tables = new TableView<>();
    ObservableList<ObservableList<String>> data;

    public Scene tableScene;


    public TableViews(ControllerDelegate controller){
        controller.setUI(this);
        GridPane outerPane = new GridPane();
        GridPane innerPane = new GridPane();
        GridPane innerPaneTableMenu = new GridPane();

        
        outerPane.add(innerPane, 0, 0);
        outerPane.getColumnConstraints().add(new ColumnConstraints(946));
        outerPane.getRowConstraints().add(new RowConstraints(342));
        outerPane.getRowConstraints().add(new RowConstraints(456));

        ComboBox selectTables = new ComboBox();
        selectTables.setPrefSize(250,36);
        selectTables.getItems().add("Campus");
        selectTables.getItems().add("Residential Managing Office");
        selectTables.getItems().add("Building Manager");
        selectTables.getItems().add("Senior Advisor");
        selectTables.getItems().add("Residence Advisor");
        selectTables.getItems().add("Residential Address");
        selectTables.getItems().add("Residence Budget");
        selectTables.getItems().add("Residence Capacity");
        selectTables.getItems().add("Floor");
        selectTables.getItems().add("House");
        selectTables.getItems().add("Unit");
        selectTables.getItems().add("Resident Info");
        selectTables.getSelectionModel().selectFirst();

        innerPaneTableMenu.add(selectTables,0,0);
        innerPaneTableMenu.setHalignment(selectTables, HPos.CENTER);
        innerPaneTableMenu.setValignment(selectTables, VPos.TOP);
        outerPane.add(innerPaneTableMenu, 1, 0);


//        outerPane.add(selectTables,1,0);
//        outerPane.setHalignment(selectTables, HPos.CENTER);
//        outerPane.setValignment(selectTables, VPos.CENTER);

        Button goToTable = new Button("Go to Table");
        goToTable.setPrefSize(113,36);
        outerPane.add(goToTable, 1,1);
        outerPane.setHalignment(goToTable, HPos.CENTER);
        outerPane.setValignment(goToTable, VPos.CENTER);

        Button deleteRow = new Button("Delete Row");

        tables.prefWidthProperty().bind(innerPane.widthProperty());
        tables.prefHeightProperty().bind(innerPane.heightProperty());
        innerPane.add(tables, 0,0);


        goToTable.setOnAction(e -> {
            String tableState = selectTables.getValue().toString();
            System.out.println(tableState);
            controller.loadTable(tableState.replaceAll(" ", ""));
        });


        controller.performQuery("SELECT * FROM Campus");
        tables.setStyle("-fx-border-color: #000000");
        tableScene = new Scene(outerPane, 1124,798);
    }

    public Scene getScene() {
        return tableScene;
    }

    //    Following the tutorial here to help generate dynamic columns https://blog.ngopal.com.np/2011/10/19/dyanmic-tableview-data-from-database/
    public void buildData(Table table) {
        data = FXCollections.observableArrayList();
        tables.getItems().clear();
        tables.getColumns().clear();

        try {

            /*q
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             *********************************
             */

            if (table != null) {
                List<Column> columnNames = table.getColumnsList();
                for (int i = 0; i < columnNames.size(); i++ ){
                    final  int j = i;
                    TableColumn<ObservableList<String>, String> col = new TableColumn<>(columnNames.get(i).name);
                    col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                    tables.getColumns().add(col);
                    System.out.println("Column [" + i + "] ");
                }

                /*
                 * ******************************
                 * Data added to ObservableList *
                 *******************************
                 */

                for (TableRow tablerow : table) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (Column column : columnNames) {
                        row.add(tablerow.get(column));
                    }
                    data.add(row);

                }
            }
            //FINALLY ADDED TO TableView
            tables.setItems(data);
            tables.refresh();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        GridPane outerPane = new GridPane();
        GridPane innerPane = new GridPane();
        outerPane.add(innerPane, 0, 0);
        outerPane.getColumnConstraints().add(new ColumnConstraints(946));
        outerPane.getRowConstraints().add(new RowConstraints(276));

        Controller controller = new Controller();
        controller.login("ora_linshuan", "a41053539");
        controller.initializeSQLDDL();

        tables.prefWidthProperty().bind(innerPane.widthProperty());
        tables.prefHeightProperty().bind(innerPane.heightProperty());
        innerPane.add(tables, 0,0);
        tables.setStyle("-fx-border-color: #000000");
        tableScene = new Scene(outerPane, 1124,798);

    }

    @Override
    public void updateVisibleTable(Table table) {
        buildData(table);
    }
}
