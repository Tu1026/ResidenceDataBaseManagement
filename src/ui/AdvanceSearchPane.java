package ui;

import com.sun.rowset.internal.Row;
import interfaces.ControllerDelegate;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.AdvanceQueries;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static model.AdvanceQueries.*;

public class AdvanceSearchPane extends GridPane {
    private final ComboBox<String> advanceCombo = new ComboBox<>();
    private final ControllerDelegate controller;
    private final TextField conditionField;
    private final TextArea queryExplaination;
    private final Map<AdvanceQueries, String> queryDescription = new HashMap<AdvanceQueries, String>() {{
        put(GROUPBY, "RAs, residence advisors, or SRA, senior residence advisors, are employees who work in the residence to support the residents. " +
                "They could be long time employees who have " +
                "worked for many years in the residence, or they could also be brand new employees that have never worked in a residence before. " +
                "Therefore, you can run this query to see for each of the age group what is the average years of experience among all the advisors");

        put(NESTED, "A person is considered to be living on their own when there are no other people in the same unit. " +
                "This query allows you find the number of people who are currently living on their own and " +
                "have been in residence longer than everyone else in each house");

        put(JOIN, "Students of different age are all able to live in residence. Using this query you can find " +
                "of all the students older than <X> what is the average age and the oldest age in each house");
        put(DIVISION, "Different units have different capacity. Using this query you can find the house and information " +
                "about it that has 'all' of the units with capacity 5 and no other house has a unit with a capacity of 5");

        put(HAVING, "A unit can have vacancies if it can still allow new residents to move in. This query will show you how" +
                " all the floors that have more than <X> vacancies in a house");
    }};


    public AdvanceSearchPane(ControllerDelegate controller){
        this.controller = controller;

        Label lineLabel = new Label("Advanced Search");
        Label queryLabel = new Label("  Detail explanation of this search:");
        setStyle("-fx-border-width: 1 0 0 0; -fx-border-color: #838181");

        //-------------------Set up constraints-----------------------//
        RowConstraints topRow = new RowConstraints();
        topRow.setPercentHeight(20);
        RowConstraints midRow = new RowConstraints();
        midRow.setPercentHeight(60);
        ColumnConstraints firstColumn = new ColumnConstraints();
        firstColumn.setPercentWidth(20);
        ColumnConstraints secondColumn = new ColumnConstraints();
        secondColumn.setPercentWidth(40);

        this.getRowConstraints().addAll(topRow, midRow);
        this.getColumnConstraints().addAll(firstColumn, secondColumn, secondColumn);


        this.add(lineLabel,0,0);
        this.setPadding(new Insets(10,10,10,10));

        VBox column = new VBox(15);

        VBox row1 = new VBox();
        row1.getChildren().add(new Label("Find:"));
        row1.setSpacing(10);
//        row1.setFillHeight(true);
        row1.setAlignment(Pos.BASELINE_CENTER);

        VBox row2 = new VBox();
        conditionField = new TextField();
        conditionField.setPromptText("Please enter an integer");
        conditionField.setMaxWidth(150);
        Button runQuery = new Button("Run Query");
        row2.getChildren().addAll(conditionField, runQuery);
        row2.setSpacing(10);

        row2.setAlignment(Pos.BASELINE_CENTER);


        for(AdvanceQueries query: AdvanceQueries.values()) {
                advanceCombo.getItems().add(query.getText());
        }

        advanceCombo.setMaxWidth(300);
        HBox.setHgrow(lineLabel, Priority.ALWAYS);
        row1.getChildren().add(advanceCombo);
        conditionField.setMaxWidth(300);

        column.getChildren().addAll(row1,row2);
        this.add(column,1,1);

        VBox thirdColumnBox = new VBox();
        queryExplaination = new TextArea("This area will give you a short description of what the advance query does");
        queryExplaination.setEditable(false);
        queryExplaination.setFocusTraversable(false);
        queryExplaination.setWrapText(true);

        thirdColumnBox.getChildren().addAll(new Label("What this query does exactly:"), queryExplaination);
        queryExplaination.prefWidthProperty().bind(thirdColumnBox.widthProperty());
        queryExplaination.prefHeightProperty().bind(thirdColumnBox.heightProperty());


        this.add(queryExplaination,2,1);


        //----------------Setting up action events-------------//
        advanceCombo.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (oldItem == null || !oldItem.equals(newItem)) {
                if (! newItem.equals(JOIN.getText()) && !newItem.equals(HAVING.getText())) {
                    conditionField.clear();
                }
                conditionField.setVisible(newItem.equals(JOIN.getText()) ||
                        newItem.equals(AdvanceQueries.HAVING.getText()));
                        updateQueryDescription(AdvanceQueries.getEnum(newItem));
            }
        });


        // This is where it would call controller ---------------> needs back end handling here
        //Check the enum AdvanceQueries
        runQuery.setOnAction(event -> {
            runIfSelected();
        });

        this.add(queryLabel,2,0);

        BorderPane.setMargin(row1, new Insets(20,0,0,0));

    }


    public void displayError(final String msg){
        Platform.runLater( () -> {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Invalid operation");
            a.setContentText(msg);
            a.showAndWait();
        });
    }

    public boolean runIfSelected() {
        if (advanceCombo.getSelectionModel().isEmpty()) {
            return false;
        }
        AdvanceQueries enumVal = AdvanceQueries.getEnum(advanceCombo.getValue());
        if (enumVal == JOIN ||
                enumVal == AdvanceQueries.HAVING) {
            if (conditionField.getText().trim().isEmpty()){
                displayError("You must enter a condition for this special query");
            }else if (!conditionField.getText().trim().matches("\\d+")){
                displayError("You can only enter a integer for the condition");
            } else {
                controller.runAdvancedQuery(enumVal, conditionField.getText());
            }
        } else {
            if(!conditionField.getText().trim().isEmpty()){
                displayError("This type of query cannot have condition");
            }else {
                controller.runAdvancedQuery(enumVal, "");
            }
        }
        return true;
    }

    private void updateQueryDescription(AdvanceQueries advance) {
        queryExplaination.setText(queryDescription.get(advance));
    }
}
