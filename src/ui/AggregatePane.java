package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.List;

public class AggregatePane extends Application {
    ComboBox<String> groupByBox = new ComboBox<>();

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


    //Todo: Dynamically generate the combobox for group by
    public ComboBox<String> groupByPossibilities(List<String> columns){

        return null;
    }


}
