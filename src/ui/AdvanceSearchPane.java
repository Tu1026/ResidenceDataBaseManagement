package ui;

import interfaces.ControllerDelegate;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.OracleTableNames;

import java.util.ArrayList;
import java.util.Arrays;

public class AdvanceSearchPane extends BorderPane {
    ComboBox<String> advanceCombo = new ComboBox<>();
    ControllerDelegate controller;

    private final ArrayList<String> listOfQueries = new ArrayList<>(
            Arrays.asList(
                    "Find the average years of experience of RAs/SRAs per age group: Can this be turned into a nested aggregation instead?",
                    "Select the Name of each house, num of students living alone, and the years they have been living in res for all students who live alone and have been in that res at least as long as anyone else",
                    "Select # of student in each house who are older than , their avg age, and the age of the oldest student",
                    "Find the house the has all the units that have a capacity of 5",
                    "Find floors in houses that have more than 3 vacancies"
            )
    );

    public AdvanceSearchPane(ControllerDelegate controller){
        this.controller = controller;

        Label lineLabel = new Label("Advanced Search");
        setStyle("-fx-border-width: 1 0 0 0; -fx-border-color: #838181");

        this.setTop(lineLabel);
        this.setPadding(new Insets(10,10,10,10));

        VBox column1 = new VBox();
        HBox row = new HBox();
        row.getChildren().add(new Label("Find"));
        row.setSpacing(10);
        row.setFillHeight(true);
        row.setAlignment(Pos.BASELINE_CENTER);

        for(String str: listOfQueries){
            advanceCombo.getItems().add(str);
        }

        row.getChildren().add(advanceCombo);

        this.setCenter(row);
        BorderPane.setMargin(row, new Insets(20,0,0,0));

    }

    public Node getPane(){
        return this;
    }


}
