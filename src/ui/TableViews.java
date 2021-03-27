package ui;

import controller.Controller;
import handler.DataHandler;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.util.Callback;


import java.io.IOException;
import java.sql.ResultSet;


public class TableViews extends Application {

    TableView tables = new TableView();

    public Scene tableScene;


    public TableViews(String mode, Controller c){
        GridPane outerPane = new GridPane();
        GridPane innerPane = new GridPane();
        
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

        outerPane.add(selectTables,1,0);
        outerPane.setHalignment(selectTables, HPos.CENTER);
        outerPane.setValignment(selectTables, VPos.CENTER);

        Button goToTable = new Button("Go to Table");
        goToTable.setPrefSize(113,36);
        outerPane.add(goToTable, 1,1);
        outerPane.setHalignment(goToTable, HPos.CENTER);
        outerPane.setValignment(goToTable, VPos.CENTER);

        ResultSet rs;
        try {
            rs = c.executeSQL("SELECT table_name FROM user_tables");
        } catch (Exception e) {
            e.printStackTrace();
            rs = null;
        }
        buildData(rs);
        tables.prefWidthProperty().bind(innerPane.widthProperty());
        tables.prefHeightProperty().bind(innerPane.heightProperty());
        innerPane.add(tables, 0,0);
        tables.setStyle("-fx-border-color: #000000");
        tableScene = new Scene(outerPane, 1124,798);
    }

    public Scene getScene(){
        return tableScene;
    }

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

        ResultSet rs;
        try {
            rs = controller.executeSQL("SELECT table_name FROM user_tables");
        } catch (Exception e) {
            e.printStackTrace();
            rs = null;
        }
        buildData(rs);
        tables.prefWidthProperty().bind(innerPane.widthProperty());
        tables.prefHeightProperty().bind(innerPane.heightProperty());
        innerPane.add(tables, 0,0);
        tables.setStyle("-fx-border-color: #000000");
        tableScene = new Scene(outerPane, 1124,798);


    }
}
