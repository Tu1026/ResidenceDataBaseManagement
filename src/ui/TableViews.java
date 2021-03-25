package ui;

import controller.Controller;
import handler.DataHandler;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.util.Callback;


import java.io.IOException;
import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;

public class TableViews extends Application {

    TableView tables = new TableView();



    //    Following the tutorial here to help generate dynamic columns https://blog.ngopal.com.np/2011/10/19/dyanmic-tableview-data-from-database/
    public void buildData(ResultSet rs) {
            ObservableList<ObservableList> data;
            data = FXCollections.observableArrayList();
            try {

                /**
                 * ********************************
                 * TABLE COLUMN ADDED DYNAMICALLY *
                 *********************************
                 */
                for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                    //We are using non property style for making dynamic table
                    final int j = i;
                    TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        }
                    });

                    tables.getColumns().addAll(col);
                    System.out.println("Column [" + i + "] ");
                }

                /**
                 * ******************************
                 * Data added to ObservableList *
                 *******************************
                 */
                while (rs.next()) {
                    //Iterate Row
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        //Iterate Column
                        row.add(rs.getString(i));
                    }
                    System.out.println("Row [1] added " + row);
                    data.add(row);

                }

                //FINALLY ADDED TO TableView
                tables.setItems(data);
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
        Controller controller = new Controller();
        controller.initializeSQLDDL();

        ResultSet rs;
        try {
            rs = controller.executeSQL("SELECT * FROM Authors");
        } catch (Exception e) {
            e.printStackTrace();
            rs = null;
        }
        buildData(rs);
        tables.prefWidthProperty().bind(p1.widthProperty());
        tables.prefHeightProperty().bind(p1.heightProperty());
        tables.setStyle("-fx-border-color: black");

        primaryStage.setTitle("Residence Database");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        p1.getChildren().add(tables);
//        primaryStage.minWidthProperty().bind(scene.heightProperty().multiply(1.5));
//        primaryStage.minHeightProperty().bind(scene.widthProperty().divide(1.5));
        primaryStage.show();


    }
}
