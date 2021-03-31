package ui;

import interfaces.ControllerDelegate;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AggregatePane extends Application {
    private ComboBox<String> groupByCombo = new ComboBox<>();
    private ComboBox<String> aggregateFunctionComboHaving = new ComboBox<>();
    private ComboBox<String> columnsCanBePerformedCombo = new ComboBox<>();
    private TextField aggregateCondition = new TextField();
    private ComboBox<String> aggregateFunctionComboSelect = new ComboBox<>();
    private ComboBox<String> columnsToBeDisplayedCombo = new ComboBox<>();
    private ControllerDelegate controller;

    private GridPane masterGridPane = new GridPane();
    private String tableName = null;
    private List<String> columnNames;

    //What comboBoxes we have
    private final ArrayList<Control> listOfControls = new ArrayList<>(
            Arrays.asList(
                    groupByCombo,
                    aggregateFunctionComboHaving,
                    columnsCanBePerformedCombo,
                    aggregateCondition,
                    aggregateFunctionComboSelect,
                    columnsToBeDisplayedCombo
            )
    );


    //Their labels
    private final ArrayList<String> comboLabels = new ArrayList<>(
            Arrays.asList(
                    "Group by this",
                    "Having",
                    "",
                    "",
                    "Show",
                    ""
            )
    );

    public static void main(String[] args) {
        launch(args);
    }

    public AggregatePane(ControllerDelegate controller) {
        this.controller = controller;
        ColumnConstraints masterColumnConstraint = new ColumnConstraints();

        //------------------Setting up row constraints-------///
        RowConstraints masterRowConstraintsTop = new RowConstraints();
        RowConstraints masterRowConstraintsBot = new RowConstraints();

        masterRowConstraintsTop.setPercentHeight(35);
        masterRowConstraintsBot.setPercentHeight(65);

        masterGridPane.getRowConstraints().addAll(masterRowConstraintsTop, masterRowConstraintsBot);
        masterColumnConstraint.setPercentWidth(100.0 / (listOfControls.size() - 1));


        //-----------Setting up combos---------------------//
        for (int i = 0; i < listOfControls.size(); i++) {
            VBox tempBox = simpleLabelAdding(listOfControls.get(i), comboLabels.get(i));
            masterGridPane.add(tempBox, i, 1);
            masterGridPane.getColumnConstraints().add(masterColumnConstraint);
        }
        aggregateFunctionCombo();


        //------------Action Events----------------------//
        groupByCombo.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (oldItem == null || !oldItem.equals(newItem)) {
                updateComboForAggregate(this.columnNames, this.tableName,"columnsCanBePerformedCombo");
            }
        });

        //---------Text field-----//
        aggregateCondition.setPromptText("You can use > = < operators");


    }

    public Pane getMasterGridPane() {
        return masterGridPane;
    }

    @Override
    public void start(Stage primaryStage) {
        ColumnConstraints masterColumnConstraint = new ColumnConstraints();

        //------------------Setting up row constraints-------///
        RowConstraints masterRowConstraintsTop = new RowConstraints();
        RowConstraints masterRowConstraintsBot = new RowConstraints();
        masterRowConstraintsTop.setPercentHeight(35);
        masterRowConstraintsBot.setPercentHeight(65);
        masterGridPane.getRowConstraints().addAll(masterRowConstraintsTop, masterRowConstraintsBot);
        masterColumnConstraint.setPercentWidth(100.0 / (listOfControls.size() - 1));


        //-----------Setting up combos---------------------//
        for (int i = 0; i < listOfControls.size(); i++) {
            VBox tempBox = simpleLabelAdding(listOfControls.get(i), comboLabels.get(i));
            masterGridPane.add(tempBox, i, 1);
            masterGridPane.getColumnConstraints().add(masterColumnConstraint);
        }
        aggregateFunctionCombo();


        //--------------Combos on action-------------------//


        //---------Text field-----//
        aggregateCondition.setPromptText("You can use > >= = < <= operators");


        //Initializing
        //--------------------------------------------------------------//
        Scene placeHolderScene = new Scene(masterGridPane, 600, 200);
        primaryStage.setScene(placeHolderScene);
        primaryStage.show();
    }


    //Put a given JAVAFX Control inside of a VBox with a label
    private VBox simpleLabelAdding(Control node, String labelText) {
        VBox vbox = new VBox(5);
        Label label = new Label(labelText);
        label.setWrapText(true);
        label.setFont(Font.font("Times New Roman", 14));
        node.prefWidthProperty().bind(vbox.widthProperty());
        vbox.getChildren().addAll(label, node);
        return vbox;
    }


    public void updateComboForAggregate(List<String> globalColumns, String tableName, String comboString) {
        ComboBox<String> combo = null;
        List <String> columns = globalColumns;
            switch (comboString) {
                case ("groupByCombo"):
                    combo = groupByCombo;
                    this.tableName = tableName;
                    this.columnNames = columns;
                    break;
                case ("columnsCanBePerformedCombo"):
                    combo = columnsCanBePerformedCombo;
                    columns.remove(groupByCombo.getValue());
                    break;
                case ("columnsToBeDisplayedCombo"):
                    combo = columnsToBeDisplayedCombo;
                    break;
            }
            combo.getSelectionModel().clearSelection();
            combo.getItems().clear();
            combo.getItems().addAll(columns);
            combo.getSelectionModel().selectFirst();
    }



    /*
     * User can choose from these aggregate functions can add more if needed
     */
    private void aggregateFunctionCombo(){
        aggregateFunctionComboHaving.getItems().add("Sum");
        aggregateFunctionComboHaving.getItems().add("Average");
        aggregateFunctionComboHaving.getItems().add("Min");
        aggregateFunctionComboHaving.getItems().add("Max");
        aggregateFunctionComboSelect.getItems().add("Sum");
        aggregateFunctionComboSelect.getItems().add("Average");
        aggregateFunctionComboSelect.getItems().add("Min");
        aggregateFunctionComboSelect.getItems().add("Max");
    }
//
//    public ComboBox<String> updateCombo(String comboName){
//        ComboBox<String> combo = null;
//        switch (comboName) {
//            case ("groupByCombo"):
//                combo = groupByCombo;
//                break;
//            case ("columnsCanBePerformedCombo"):
//                combo = columnsCanBePerformedCombo;
//                break;
//            case ("columnsToBeDisplayedCombo"):
//                combo = columnsToBeDisplayedCombo;
//                break;
//        }
//        return combo;
//    }

//
//    //Todo: Given list of columns dynamically generate what columns these aggregate functions can perform on
//    //We can change it to ComboBoxItemWrap so we can select multiple columns?
//    private void columnsCanBePerformedCombo(List<String> columns) {
//        if (this.tableName == null || !this.tableName.equals(tableName)){
//            groupByCombo.getSelectionModel().clearSelection();
//            groupByCombo.getItems().clear();
//            this.tableName = tableName;
//            groupByCombo.getItems().addAll(columns);
//            groupByCombo.getSelectionModel().selectFirst();
//        }
//    }
//
//
//    //Todo: Given a list of columns that can be displayed when the selected aggrefateFunciton is used
//    //We could use ComboBoxItemWrap here but selecting multiple columns with different aggregate function is tricky?
//    private void columnsToBeDisplayedCombo(List<String> columns, String aggregateFunction) {
//        columnsToBeDisplayedCombo.getItems().clear();
//        for (String column : columns) {
//            columnsToBeDisplayedCombo.getItems().add(column);
//        }
//    }




}
