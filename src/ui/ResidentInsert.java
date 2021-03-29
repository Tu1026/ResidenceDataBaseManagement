package ui;

import controller.Controller;
import interfaces.ControllerDelegate;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
        controller.login("ora_jmhirsch","a64927676");
        HashMap<String, String> testMap = new HashMap<>();
        List<String> testList = new ArrayList<>();
        testList.add("Residence Name");
        testList.add("Address");
        testList.add("Zipcode");
        controller.getDataForStudentInsertion("RESIDENCE", testList, testMap, this::getResAddres);
        GridPane masterGridPane = new GridPane();

        Pane zeroPane = new Pane();
        Label emailLabel = new Label("E-mail");
        TextField emailText = new TextField();

        zeroPane.getChildren().addAll(emailText, emailLabel);
        masterGridPane.add(zeroPane,0,0);


        Scene newScene = new Scene(masterGridPane, 1600,300);
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
}
