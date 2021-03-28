package ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.table.Column;

public class Filters extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        GridPane filterPane = new GridPane();
        ComboBox<String> filterColumnNames = new ComboBox<>();
        filterColumnNames.getSelectionModel().selectFirst();
        TextField filter = new TextField();
        filter.setPromptText("Enter what your filtering value here");

        filterPane.add(filterColumnNames, 0, 0);
        filterPane.add(filter, 0, 1);
        Scene scene = new Scene(filterPane, 700, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
