package ui;

import controller.Controller;
import interfaces.ControllerDelegate;
import interfaces.TableViewUI;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import model.table.Column;
import model.table.Table;
import model.table.TableRow;


import java.io.IOException;
import java.util.List;


public class TableViews extends Application implements TableViewUI {

    TableView<ObservableList<String>> tables = new TableView<>();
    public Controller c = null;
    ObservableList<ObservableList<String>> data;

    public Scene tableScene;


    public TableViews(ControllerDelegate controller) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TableViews.fxml"));
        data = FXCollections.observableArrayList();
        loader.setController(this);
        controller.setUI(this);
        Parent root;
        try {
            root = loader.load();

        } catch (Exception e) {
            root = null;
        }

        controller.performQuery("SELECT * FROM RESIDENTIALMANAGINGOFFICE JOIN CAMPUS r USING(CZIPCODE, CSTADDRESS)");

        tables.prefWidthProperty().bind(p1.widthProperty());
        tables.prefHeightProperty().bind(p1.heightProperty());
        tables.setStyle("-fx-border-color: #000000");
        p1.getChildren().add(tables);
        tableScene = new Scene(root);
//        return tableScene
//        primaryStage.setTitle("Residence Database");
//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.show();
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


    @FXML
    private GridPane p1;


    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TableViews.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        ControllerDelegate controller = new Controller();
        controller.login("ora_linshuan", "a41053539");
        controller.initializeSQLDDL();

        controller.performQuery("SELECT table_name FROM user_tables");
        tables.prefWidthProperty().bind(p1.widthProperty());
        tables.prefHeightProperty().bind(p1.heightProperty());
        tables.setStyle("-fx-border-color: #000000");

        primaryStage.setTitle("Residence Database");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        p1.getChildren().add(tables);
        primaryStage.show();


    }

    @Override
    public void updateVisibleTable(Table table) {
        buildData(table);
    }
}
