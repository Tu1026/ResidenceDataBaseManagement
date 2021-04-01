package ui;

import interfaces.ControllerDelegate;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.AdvanceQueries;

public class AdvanceSearchPane extends BorderPane {
    private final ComboBox<String> advanceCombo = new ComboBox<>();
    private final ControllerDelegate controller;
    private final TextField conditionField;

    public AdvanceSearchPane(ControllerDelegate controller){
        this.controller = controller;

        Label lineLabel = new Label("Advanced Search");
        setStyle("-fx-border-width: 1 0 0 0; -fx-border-color: #838181");

        this.setTop(lineLabel);
        this.setPadding(new Insets(10,10,10,10));

        VBox column = new VBox(15);

        HBox row1 = new HBox();
        row1.getChildren().add(new Label("Find"));
        row1.setSpacing(10);
        row1.setFillHeight(true);
        row1.setAlignment(Pos.BASELINE_CENTER);

        VBox row2 = new VBox();
        conditionField = new TextField();
        conditionField.setPromptText("Please enter an integer");
        conditionField.setMaxWidth(150);
        Button runQuery = new Button("Run Query");
        row2.getChildren().addAll(conditionField, runQuery);
        row2.setSpacing(10);

        row2.setAlignment(Pos.BASELINE_CENTER);


        for(AdvanceQueries query: AdvanceQueries.values()){
            advanceCombo.getItems().add(query.getText());
        }

        advanceCombo.setMaxWidth(300);
        HBox.setHgrow(lineLabel, Priority.ALWAYS);
        row1.getChildren().add(advanceCombo);

        column.getChildren().addAll(row1,row2);
        this.setCenter(column);


        //----------------Setting up action events-------------//
        advanceCombo.valueProperty().addListener((obs, oldItem, newItem) -> {
            if (oldItem == null || !oldItem.equals(newItem)) {
                conditionField.setVisible(newItem.equals(AdvanceQueries.JOIN.getText()) ||
                        newItem.equals(AdvanceQueries.HAVING.getText()));
            }
        });


        // This is where it would call controller ---------------> needs back end handling here
        //Check the enum AdvanceQueries
        runQuery.setOnAction(event -> {
            runIfSelected();
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

    public boolean runIfSelected() {
        if (advanceCombo.getSelectionModel().isEmpty()) {
            return false;
        }
        AdvanceQueries enumVal = AdvanceQueries.getEnum(advanceCombo.getValue());
        if (enumVal == AdvanceQueries.JOIN ||
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
}
