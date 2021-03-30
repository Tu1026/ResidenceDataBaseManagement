package ui;

import controller.Controller;
import interfaces.ControllerDelegate;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.OracleColumnNames;
import model.OracleTableNames;
import model.table.Column;
import model.table.Table;
import model.table.TableRow;

import java.util.ArrayList;
import java.util.List;

public class ResidentInsert extends Application {

    private ControllerDelegate controller;
    private Scene scene;
    private List<String> residence;
    private List<String> house;
    private ComboBox<String> residenceCombo;
    private ComboBox<String> houseCombo;
    private ComboBox<String> unitCombo;
    private ComboBox<String> floorCombo;

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

//        List<String> testList = new ArrayList<>();
//        List<String> testList2 = new ArrayList<>();
//        List<String> testList3 = new ArrayList<>();
//        testList.add("House Name");
//        testList.add("Zipcode");
//        testList.add("Address");
//        testList2.add("Address");
//        testList2.add("Zipcode");
//        testList3.add("2205 West Mall");
//        testList3.add("V6T 1Z4");
//        controller.getDataForStudentInsertion("HOUSE", testList, testList2, testList3, this::getHouseName);


        GridPane masterGridPane = new GridPane();

        VBox emailBox = new VBox(5);
        Label emailLabel = new Label("E-mail");
        TextField emailText = new TextField();
        emailText.prefWidthProperty().bind(emailBox.prefWidthProperty());
        emailBox.getChildren().addAll(emailLabel, emailText);

        VBox StudentNumberVBox = new VBox(5);
        Label StudentNumberLabel = new Label("Student#");
        TextField StudentNumberText = new TextField();
        emailText.prefWidthProperty().bind(StudentNumberVBox.prefWidthProperty());
        StudentNumberVBox.getChildren().addAll(StudentNumberLabel, StudentNumberText);

        VBox NameBox = new VBox(5);
        Label NameLabel = new Label("Name");
        TextField NameText = new TextField();
        NameText.prefWidthProperty().bind(NameBox.prefWidthProperty());
        NameBox.getChildren().addAll(NameLabel, NameText);

        VBox DoBBox = new VBox(5);
        Label DoBLabel = new Label("Date of Birth");
        TextField DoBText = new TextField();
        DoBText.prefWidthProperty().bind(DoBBox.prefWidthProperty());
        DoBBox.getChildren().addAll(DoBLabel, DoBText);

        VBox YearsInResidenceBox = new VBox(5);
        Label YearsInResidenceLabel = new Label("Years in Residence");
        TextField YearsInResidenceText = new TextField();
        YearsInResidenceText.prefWidthProperty().bind(YearsInResidenceBox.prefWidthProperty());
        YearsInResidenceBox.getChildren().addAll(YearsInResidenceLabel, YearsInResidenceText);


        VBox ResidenceBox = new VBox(5);
        Label ResidenceLabel = new Label("Choose From a residence you want to add the resident to");
        ResidenceLabel.setWrapText(true);
        ResidenceLabel.setFont(Font.font("Times New Roman", 14));
        residenceCombo = new ComboBox<>();
        residenceCombo.prefWidthProperty().bind(ResidenceBox.widthProperty());
        List<String> residenceColumnsToGet = new ArrayList<>();
        residenceColumnsToGet.add("Residence Name");
        residenceColumnsToGet.add("Address");
        residenceColumnsToGet.add("Zipcode");
        controller.getDataForStudentInsertion("RESIDENCE", residenceColumnsToGet, new ArrayList<>(), new ArrayList<>(), this::updateResidence);
        ResidenceBox.getChildren().addAll(ResidenceLabel, residenceCombo);



        VBox houseBox = new VBox(5);
        Label houseLabel = new Label("Choose From a house you want to add the resident to");
        houseLabel.setWrapText(true);
        houseLabel.setFont(Font.font("Times New Roman", 14));
        houseCombo = new ComboBox<>();
        houseCombo.prefWidthProperty().bind(houseBox.widthProperty());
        List<String> houseColumnsToGet = new ArrayList<>();
        houseColumnsToGet.add("House Name");

        List<String> houseColumnsToMatch = new ArrayList<>();
        houseColumnsToMatch.add("Address");
        houseColumnsToMatch.add("Zipcode");
        List<String> houseDataToMatch = new ArrayList<>();
        houseBox.getChildren().addAll(houseLabel, houseCombo);

        VBox floorBox = new VBox(5);
        Label floorLabel = new Label("Choose From a house you want to add the resident to");
        floorLabel.setWrapText(true);
        floorLabel.setFont(Font.font("Times New Roman", 14));
        floorCombo = new ComboBox<>();
        floorCombo.prefWidthProperty().bind(floorBox.widthProperty());
        List<String> floorColumnsToGet = new ArrayList<>();
        floorColumnsToGet.add("House Name");

        List<String> floorColumnsToMatch = new ArrayList<>();
        floorColumnsToMatch.add("Address");
        floorColumnsToMatch.add("Zipcode");
        List<String> floorDataToMatch = new ArrayList<>();
        houseBox.getChildren().addAll(floorLabel, floorCombo);


        residenceCombo.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (oldItem == null || !oldItem.equals(newItem)) {
                houseDataToMatch.clear();
                String [] houseMatchData;
                houseMatchData = newItem.split(",");
                for (int i = 1; i < houseMatchData.length; i++){
                    houseMatchData[i].trim();
                    houseDataToMatch.add(houseMatchData[i]);
                }
                controller.getDataForStudentInsertion("HOUSE", houseColumnsToGet, houseColumnsToMatch, houseDataToMatch, this::updateHouse);

            }
        });

        houseCombo.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (oldItem == null || !oldItem.equals(newItem)) {
                floorDataToMatch.clear();
                String [] houseMatchData;
                houseMatchData = newItem.split(",");
                for (int i = 1; i < houseMatchData.length; i++){
                    houseMatchData[i].trim();
                    houseDataToMatch.add(houseMatchData[i]);
                }
                controller.getDataForStudentInsertion("FLOOR", floorColumnsToGet, floorColumnsToMatch, floorDataToMatch, this::updateHouse);

            }
        });



        masterGridPane.add(emailBox,0,1);
        masterGridPane.add(StudentNumberVBox, 1, 1);
        masterGridPane.add(NameBox,2,1);
        masterGridPane.add(DoBBox,3,1);
        masterGridPane.add(YearsInResidenceBox,4,1);
        masterGridPane.add(ResidenceBox,5,1);
        masterGridPane.add(houseBox,6,1);


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

    private void updateHouse(Table table){
        List<Column> columnNames = table.getColumnsList();
        List<String> house = new ArrayList<>();
        houseCombo.getItems().clear();
        houseCombo.getSelectionModel().clearSelection();

        for (TableRow tableRow : table){
            String temp = "";
            for (Column column: columnNames){
                if (temp != "") {
                    temp += ", " + tableRow.get(column);
                }else {
                    temp += tableRow.get(column);
                }
            }
            temp.replaceAll(", $", "");
            house.add(temp);
        }

        for (int i = 0; i < house.size(); i++){
            houseCombo.getItems().add(house.get(i));
            System.out.println(house.get(i));
        }
        this.house = house;
    }

    private void getHouseName(Table table){
        for (Column column: table.getColumnsList()){
            System.out.println(column.name);
        }

        controller.logout();
    }

    private void updateResidence(Table table){
        List<Column> columnNames = table.getColumnsList();
        List<String> residence = new ArrayList<>();
        for (TableRow tableRow : table){
            String temp = "";
            for (Column column: columnNames){
                if (temp != "") {
                    temp += ", " + tableRow.get(column);
                }else {
                    temp += tableRow.get(column);
                }
            }
            temp.replaceAll(", $", "");
            System.out.println(temp);
            residence.add(temp);
        }

        for (int i = 0; i < residence.size(); i++){
            residenceCombo.getItems().add(residence.get(i));
        }
        this.residence = residence;
    }

}
