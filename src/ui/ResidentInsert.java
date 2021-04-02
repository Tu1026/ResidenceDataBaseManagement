package ui;

import interfaces.ControllerDelegate;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.OracleColumnNames;
import model.table.Column;
import model.table.Table;
import model.table.TableRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResidentInsert {

    private ControllerDelegate controller;
    private Scene scene;
    private List<String> residence;
    private List<String> house;
    private List<String> floor;
    private List<String> unit;
    private ComboBox<String> residenceCombo;
    private ComboBox<String> houseCombo;
    private ComboBox<String> unitCombo;
    private ComboBox<String> floorCombo;
    private HashMap<String, String> insertMap;

    public ResidentInsert(ControllerDelegate controller){
        this.controller = controller;
        ColumnConstraints insertColumnConstraints = new ColumnConstraints();
        insertColumnConstraints.setPercentWidth(10);


        GridPane masterGridPane = new GridPane();

        VBox emailBox = new VBox(5);
        Label emailLabel = new Label("E-mail");
        emailLabel.setFont(Font.font("Times New Roman", 14));
        TextField emailText = new TextField();
        emailText.prefWidthProperty().bind(emailBox.prefWidthProperty());
        emailBox.getChildren().addAll(emailLabel, emailText);

        VBox StudentNumberVBox = new VBox(5);
        Label StudentNumberLabel = new Label("Student#");
        StudentNumberLabel.setFont(Font.font("Times New Roman", 14));
        TextField StudentNumberText = new TextField();
        emailText.prefWidthProperty().bind(StudentNumberVBox.prefWidthProperty());
        StudentNumberVBox.getChildren().addAll(StudentNumberLabel, StudentNumberText);

        VBox NameBox = new VBox(5);
        Label NameLabel = new Label("Name");
        NameLabel.setFont(Font.font("Times New Roman", 14));
        TextField NameText = new TextField();
        NameText.prefWidthProperty().bind(NameBox.prefWidthProperty());
        NameBox.getChildren().addAll(NameLabel, NameText);

        VBox DoBBox = new VBox(5);
        Label DoBLabel = new Label("Date of Birth");
        DoBLabel.setFont(Font.font("Times New Roman", 14));
        TextField DoBText = new TextField();
        DoBText.prefWidthProperty().bind(DoBBox.prefWidthProperty());
        DoBBox.getChildren().addAll(DoBLabel, DoBText);

        VBox YearsInResidenceBox = new VBox(5);
        Label YearsInResidenceLabel = new Label("Years in Residence");
        YearsInResidenceLabel.setFont(Font.font("Times New Roman", 14));
        TextField YearsInResidenceText = new TextField();
        YearsInResidenceText.prefWidthProperty().bind(YearsInResidenceBox.prefWidthProperty());
        YearsInResidenceBox.getChildren().addAll(YearsInResidenceLabel, YearsInResidenceText);


        VBox ResidenceBox = new VBox(5);
        Label ResidenceLabel = new Label("Choose From a residence");
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
        Label houseLabel = new Label("Choose From a house");
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
        Label floorLabel = new Label("Choose From a floor");
        floorLabel.setWrapText(true);
        floorLabel.setFont(Font.font("Times New Roman", 14));
        floorCombo = new ComboBox<>();
        floorCombo.prefWidthProperty().bind(floorBox.widthProperty());
        List<String> floorColumnsToGet = new ArrayList<>();
        floorColumnsToGet.add("Floor #");

        List<String> floorColumnsToMatch = new ArrayList<>();
        floorColumnsToMatch.add("House Name");
        List<String> floorDataToMatch = new ArrayList<>();
        floorBox.getChildren().addAll(floorLabel, floorCombo);


        VBox unitBox = new VBox(5);
        Label unitLabel = new Label("Choose From a unit");
        unitLabel.setWrapText(true);
        unitLabel.setFont(Font.font("Times New Roman", 14));
        unitCombo = new ComboBox<>();
        unitCombo.prefWidthProperty().bind(unitBox.widthProperty());
        List<String> unitColumnsToGet = new ArrayList<>();
        unitColumnsToGet.add("Unit #");

        List<String> unitColumnsToMatch = new ArrayList<>();
        unitColumnsToMatch.add("Floor #");
        List<String> unitDataToMatch = new ArrayList<>();
        unitBox.getChildren().addAll(unitLabel, unitCombo);

        VBox insertBox = new VBox(5);
        Label insertLabel = new Label("           ");
        emailLabel.setFont(Font.font("Times New Roman", 14));
        Button insertTheResidentIntoDB = new Button("Insert the Resident");
        insertTheResidentIntoDB.prefWidthProperty().bind(insertBox.widthProperty());
        insertBox.getChildren().addAll(insertLabel, insertTheResidentIntoDB);
        insertTheResidentIntoDB.setOnAction(event -> {
            if(residenceCombo.getValue() == null || houseCombo.getValue() == null || floorCombo.getValue() == null || unitCombo.getValue() == null){
                Platform.runLater( () -> {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Error in data entry for resident insertion");
                    a.setContentText("You must select all the dropdown options!!");
                    a.showAndWait();
                });
            } else {
                insertMap = new HashMap<>();
                insertMap.put(OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get("E-Mail"), emailText.getText());
                insertMap.put(OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get("Student #"), StudentNumberText.getText());
                insertMap.put(OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get("Name"), NameText.getText());
                insertMap.put(OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get("Date of Birth"), DoBText.getText());
                insertMap.put(OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get("Years in Residence"), YearsInResidenceText.getText());
                insertMap.put(OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get("Unit #"), unitCombo.getValue());
                insertMap.put(OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get("Floor #"), floorCombo.getValue());
                insertMap.put(OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get("House Name"), houseCombo.getValue());
                insertMap.put(OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get("Address"), residenceCombo.getValue().split(",")[1].trim());
                insertMap.put(OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get("Zipcode"), residenceCombo.getValue().split(",")[2].trim());
                controller.insertStudent(insertMap);
            }
        });

        residenceCombo.valueProperty().addListener((obs, oldItem, newItem) -> {
            houseCombo.getItems().clear();
            houseCombo.getSelectionModel().clearSelection();
            floorCombo.getItems().clear();
            floorCombo.getSelectionModel().clearSelection();
            unitCombo.getItems().clear();
            unitCombo.getSelectionModel().clearSelection();

            houseDataToMatch.clear();
            String [] houseMatchData;
            houseMatchData = newItem.split(",");
            for (int i = 1; i < houseMatchData.length; i++){
                houseDataToMatch.add(houseMatchData[i]);
            }
            controller.getDataForStudentInsertion("HOUSE", houseColumnsToGet, houseColumnsToMatch, houseDataToMatch, this::updateHouse);

        });

        houseCombo.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (oldItem == null || (newItem != null && !oldItem.equals(newItem))) {
                floorDataToMatch.clear();
                floorDataToMatch.add(newItem.trim());
                controller.getDataForStudentInsertion("FLOOR", floorColumnsToGet, floorColumnsToMatch, floorDataToMatch, this::updateFloor);
            }
        });

        floorCombo.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (oldItem == null || (newItem != null && !oldItem.equals(newItem))) {
                unitDataToMatch.clear();
                unitDataToMatch.add(newItem.trim());
                controller.getDataForStudentInsertion("UNIT", unitColumnsToGet, unitColumnsToMatch, unitDataToMatch, this::updateUnit);
            }
        });


        masterGridPane.add(emailBox,0,1);
        masterGridPane.add(StudentNumberVBox, 1, 1);
        masterGridPane.add(NameBox,2,1);
        masterGridPane.add(DoBBox,3,1);
        masterGridPane.add(YearsInResidenceBox,4,1);
        masterGridPane.add(ResidenceBox,5,1);
        masterGridPane.add(houseBox,6,1);
        masterGridPane.add(floorBox,7,1);
        masterGridPane.add(unitBox,8,1);
        masterGridPane.add(insertBox,9,1);
//        masterGridPane.setGridLinesVisible(true);

        Scene newScene = new Scene(masterGridPane, 1600,300);
        RowConstraints masterRowConstraint1 = new RowConstraints();
        masterRowConstraint1.setPercentHeight(30);
        RowConstraints masterRowConstraint2 = new RowConstraints();
        masterRowConstraint2.setPercentHeight(70);

        masterGridPane.setHgap(10); //horizontal gap in pixels => that's what you are asking for
        masterGridPane.setVgap(10); //vertical gap in pixels
        masterGridPane.setPadding(new Insets(10, 10, 10, 10));

        masterGridPane.getRowConstraints().addAll(masterRowConstraint1, masterRowConstraint2);
        masterGridPane.getColumnConstraints().addAll(insertColumnConstraints,insertColumnConstraints,insertColumnConstraints,insertColumnConstraints,
                insertColumnConstraints, insertColumnConstraints,insertColumnConstraints,insertColumnConstraints,insertColumnConstraints,insertColumnConstraints);


//        masterGridPane.setGridLinesVisible(true);
        masterGridPane.setStyle("-fx-background-color: #efefc1");
        this.scene = newScene;
    }

    public Scene getScene() {
        return scene;
    }

    private void updateHouse(Table table){
        List<Column> columnNames = table.getColumnsList();
        List<String> house = new ArrayList<>();
        houseCombo.getItems().clear();
        houseCombo.getSelectionModel().clearSelection();

        for (TableRow tableRow : table){
            StringBuilder temp = new StringBuilder();
            for (Column column: columnNames){
                if (!temp.toString().equals("")) {
                    temp.append(", ").append(tableRow.get(column));
                }else {
                    temp.append(tableRow.get(column));
                }
            }
            house.add(temp.toString());
        }

        for (String s : house) {
            houseCombo.getItems().add(s);
            System.out.println(s);
        }
        this.house = house;
    }


    private void updateFloor(Table table){
        List<Column> columnNames = table.getColumnsList();
        List<String> floor = new ArrayList<>();
        floorCombo.getItems().clear();
        floorCombo.getSelectionModel().clearSelection();

        for (TableRow tableRow : table){
            StringBuilder temp = new StringBuilder();
            for (Column column: columnNames){
                if (!temp.toString().equals("")) {
                    temp.append(", ").append(tableRow.get(column));
                }else {
                    temp.append(tableRow.get(column));
                }
            }
            floor.add(temp.toString());
        }

        for (String s : floor) {
            floorCombo.getItems().add(s);
            System.out.println(s);
        }
        this.floor = floor;
    }

    private void updateUnit(Table table){
        List<Column> columnNames = table.getColumnsList();
        List<String> unit = new ArrayList<>();
        unitCombo.getItems().clear();
        unitCombo.getSelectionModel().clearSelection();

        for (TableRow tableRow : table){
            StringBuilder temp = new StringBuilder();
            for (Column column: columnNames){
                if (!temp.toString().equals("")) {
                    temp.append(", ").append(tableRow.get(column));
                }else {
                    temp.append(tableRow.get(column));
                }
            }
            unit.add(temp.toString());
        }

        for (String s : unit) {
            unitCombo.getItems().add(s);
            System.out.println(s);
        }
        this.unit = unit;
    }

    private void updateResidence(Table table){
        List<Column> columnNames = table.getColumnsList();
        List<String> residence = new ArrayList<>();
        for (TableRow tableRow : table){
            StringBuilder temp = new StringBuilder();
            for (Column column: columnNames){
                if (!temp.toString().equals("")) {
                    temp.append(", ").append(tableRow.get(column));
                }else {
                    temp.append(tableRow.get(column));
                }
            }
            System.out.println(temp);
            residence.add(temp.toString());
        }

        for (String s : residence) {
            residenceCombo.getItems().add(s);
        }
        this.residence = residence;
    }


}
