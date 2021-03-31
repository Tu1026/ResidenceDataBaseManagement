package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;

public class AggregatePane extends Application {
    ComboBox<String> groupByCombo = new ComboBox<>();
    ComboBox<String> aggregateFunctionCombo = new ComboBox<>();
    ComboBox<String> columnsCanBePerformedCombo = new ComboBox<>();
    ComboBox<String> columnsToBeDisplayedCombo = new ComboBox<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        HBox placeHolder = new HBox();
        Scene placeHolderScene = new Scene(placeHolder,600,200);
        primaryStage.setScene(placeHolderScene);
        primaryStage.show();
    }


    //Todo: Dynamically generate the combobox for groupBy
    public void groupByCombo(List<String> columns){
        groupByCombo.getItems().clear();
        for(String column : columns){
            groupByCombo.getItems().add(column);
        }
    }


    /*
     * User can choose from these aggregate functions can add more if needed
     */
    public void aggregateFunctionCombo(){
        aggregateFunctionCombo.getItems().add("Sum");
        aggregateFunctionCombo.getItems().add("Average");
        aggregateFunctionCombo.getItems().add("Min");
        aggregateFunctionCombo.getItems().add("Max");
    }


    //Todo: Given list of columns dynamically generate what columns these aggregate functions can perform on
    //We can change it to ComboBoxItemWrap so we can select multiple columns?
    public void columnsCanBePerformedCombo(List<String> columns) {
        columnsCanBePerformedCombo.getItems().clear();
        for (String column : columns) {
            columnsCanBePerformedCombo.getItems().add(column);
        }
    }


    //Todo: Given a list of columns that can be displayed when the selected aggrefateFunciton is used
    //We could use ComboBoxItemWrap here but selecting multiple columns with different aggregate function is tricky?
    public void columnsToBeDisplayedCombo(List<String> columns, String aggregateFunction) {
        columnsToBeDisplayedCombo.getItems().clear();
        for (String column : columns) {
            columnsToBeDisplayedCombo.getItems().add(column);
        }
    }


}
