package ui;

import interfaces.ControllerDelegate;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.AdvanceQueries;

public class AdvanceSearchPane extends BorderPane {
    ComboBox<String> advanceCombo = new ComboBox<>();
    ControllerDelegate controller;

    public AdvanceSearchPane(ControllerDelegate controller){
        this.controller = controller;

        Label lineLabel = new Label("Advanced Search");
        setStyle("-fx-border-width: 1 0 0 0; -fx-border-color: #838181");

        this.setTop(lineLabel);
        this.setPadding(new Insets(10,10,10,10));

        VBox column = new VBox(30);

        HBox row1 = new HBox();
        row1.getChildren().add(new Label("Find"));
        row1.setSpacing(10);
        row1.setFillHeight(true);
        row1.setAlignment(Pos.BASELINE_CENTER);

        HBox row2 = new HBox();
        TextField condition = new TextField();
        condition.setPromptText("No need for condition for this query");
        Button runQuery = new Button("Run Query");
        row2.getChildren().addAll(condition, runQuery);
        row2.setSpacing(10);
        row2.setFillHeight(true);
        row2.setAlignment(Pos.BASELINE_CENTER);


        for(AdvanceQueries query: AdvanceQueries.values()){
            advanceCombo.getItems().add(query.getText());
        }

        advanceCombo.setMaxWidth(row1.getMaxWidth()*0.7);
        HBox.setHgrow(lineLabel, Priority.ALWAYS);
        row1.getChildren().add(advanceCombo);

        column.getChildren().addAll(row1,row2);
        this.setCenter(column);


        //----------------Setting up action events-------------//
        advanceCombo.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (oldItem == null || !oldItem.equals(newItem)) {
                if (newItem.equals(AdvanceQueries.JOIN.getText()) ||
                        newItem.equals(AdvanceQueries.HAVING.getText())) {
                    condition.setPromptText("Please enter an integer");
                }
                else {
                    condition.setPromptText("No need for condition for this query");
                }
            }
        });


        // This is where it would call controller ---------------> needs back end handling here
        //Check the enum AdvanceQueries
        runQuery.setOnAction(event -> {
            AdvanceQueries enumVal = AdvanceQueries.getEnum(advanceCombo.getValue());
            if (enumVal == AdvanceQueries.JOIN ||
                    enumVal == AdvanceQueries.HAVING) {
                if (condition.getText().trim().isEmpty()){
                    displayError("You must enter a condition for this special query");
                }else if (!condition.getText().trim().matches("\\d+")){
                    displayError("You can only enter a integer for the condition");
                } else {
                    controller.runAdvancedQuery(enumVal, condition.getText());
                }
            } else {
                if(!condition.getText().trim().isEmpty()){
                    displayError("This type of query cannot have condition");
                }else {
                    controller.runAdvancedQuery(enumVal, "");
                }
            }
        });

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


}
