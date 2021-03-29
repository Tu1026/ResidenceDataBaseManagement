package ui;

import controller.Controller;
import interfaces.ControllerDelegate;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.OracleColumnNames;
import model.OracleTableNames;
import model.table.Column;
import model.table.Table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResidentInsert extends Application {

    private ControllerDelegate controller;
    private Scene scene;
    private List<String> Residence;
    private String residenceName;
    private String address;
    private String zipcode;


//    public ResidentInsert(ControllerDelegate controller){
//        this.controller = controller;
//    }


    @Override
    public void start(Stage primaryStage) {
        OracleTableNames.buildMaps();
        OracleColumnNames.buildMaps();
        controller = new Controller();
        controller.login("ora_linshuan","a41053539");
        controller.initializeSQLDDL();
        List<String> testList = new ArrayList<>();
        List<String> testList2 = new ArrayList<>();
        List<String> testList3 = new ArrayList<>();
        testList.add("House Name");
        testList.add("Zipcode");
        testList.add("Address");
        testList2.add("Address");
        testList2.add("Zipcode");
        testList3.add("2205 West Mall");
        testList3.add("V6T 1Z4");
        controller.getDataForStudentInsertion("HOUSE", testList, testList2, testList3, this::getHouseName);
        GridPane masterGridPane = new GridPane();

        VBox emailBox = new VBox();
        Label emailLabel = new Label("E-mail");
        TextField emailText = new TextField();
        emailText.prefWidthProperty().bind(emailBox.prefWidthProperty());
        emailBox.getChildren().addAll(emailLabel, emailText);


        masterGridPane.add(emailBox,0,1);


        Scene newScene = new Scene(masterGridPane, 1600,300);
        RowConstraints masterRowConstraint1 = new RowConstraints();
        masterRowConstraint1.setPercentHeight(30);
        RowConstraints masterRowConstraint2 = new RowConstraints();
        masterRowConstraint2.setPercentHeight(70);

        masterGridPane.getRowConstraints().addAll(masterRowConstraint1, masterRowConstraint2);

        primaryStage.setScene(newScene);
        primaryStage.show();
    }

    public Scene getScene() {
        return scene;
        ///### Pass (HashMap(column:key, String, tableName)
    }

    private void getResAddres(Table table){
        for (Column column: table.getColumnsList()){
            System.out.println(column.name);
        }
    }

    private void getHouseName(Table table){
        for (Column column: table.getColumnsList()){
            System.out.println(column.name);
        }
    }
}
