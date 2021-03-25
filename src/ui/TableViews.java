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
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.util.Callback;


import java.io.IOException;
import java.sql.ResultSet;

public class TableViews extends Application {

    private TableView tables;

//    Following the tutorial here to help generate dynamic columns https://blog.ngopal.com.np/2011/10/19/dyanmic-tableview-data-from-database/
    public void buildData(ResultSet rs) {
            ObservableList<ObservableList> data;
            data = FXCollections.observableArrayList();
            DataHandler handler = new DataHandler();
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



    @Override
    public void start(Stage primaryStage) throws IOException {
        Controller controller = new Controller();
        controller.login("ora_linshuan", "a41053539");
        controller.initializeSQLDDL();
        ResultSet rs;
        try {
            rs = controller.executeSQL("SELECT * FROM Authors");
        } catch (Exception e) {
            e.printStackTrace();
            rs = null;
        }
        tables = new TableView();
        buildData(rs);

        primaryStage.setWidth(285);
//        stage.setMaxWidth(285);
//        stage.setMinWidth(285);
        primaryStage.setTitle("Java Fx 2.0 DataBase Connection");
        primaryStage.setResizable(false);
        //Main Scene
        Scene scene = new Scene(tables);

        primaryStage.setScene(scene);
        primaryStage.show();





//        Parent root = FXMLLoader.load(getClass().getResource("TableViews.fxml"));
//        primaryStage.setTitle("Residence Database");
//
//        Scene scene = new Scene(root, 1024, 768);
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }
}
