package ui;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class AdvanceSearchPane extends Application {
    GridPane masterGridPane = new GridPane();
    ComboBox<String> advanceCombo = new ComboBox<>();

    public static void main(String[] args) {
        launch(args);
    }

    private final ArrayList<String> listOfQueries = new ArrayList<>(
            Arrays.asList(
                    "Find the average years of experience of RAs/SRAs per age group: Can this be turned into a nested aggregation instead?",
                    "Select the Name of each house, num of students living alone, and the years they have been living in res for all students who live alone and have been in that res at least as long as anyone else",
                    "Select # of student in each house who are older than , their avg age, and the age of the oldest student",
                    "Find the house the has all the units that have a capacity of 5",
                    "Find floors in houses that have more than 3 vacancies"
            )
    );

    @Override
    public void start(Stage primaryStage) {
        //------------------Setting up row constraints-------///
        RowConstraints masterRowConstraintsTop = new RowConstraints();
        RowConstraints masterRowConstraintsBot = new RowConstraints();

        masterRowConstraintsTop.setPercentHeight(35);
        masterRowConstraintsBot.setPercentHeight(65);

        masterGridPane.getRowConstraints().addAll(masterRowConstraintsTop, masterRowConstraintsBot);

        VBox advanceBox = new VBox();
        Label advanceSearchLabel = new Label("Find");
        advanceSearchLabel.setFont(Font.font("Times New Roman", 16));

        for(String str: listOfQueries){
            advanceCombo.getItems().add(str);
        }

        advanceBox.getChildren().addAll(advanceSearchLabel,advanceCombo);
        masterGridPane.add(advanceBox,0,1);
        Scene scene = new Scene(masterGridPane, 600,600);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public Node getPane(){
        return masterGridPane;
    }




}
