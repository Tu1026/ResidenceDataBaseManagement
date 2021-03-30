package ui;

import interfaces.ControllerDelegate;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.OracleTableNames;

public class SearchView extends BorderPane {

    private final ControllerDelegate controller;
    private final Button okButton;

    public SearchView(ControllerDelegate controller) {
        this.controller = controller;

        okButton = new Button("ok");

        Label lineLabel = new Label("Advanced Search");
        setStyle("-fx-border-width: 1 0 0 0; -fx-border-color: #838181");

        this.setTop(lineLabel);
        this.setPadding(new Insets(10,10,10,10));

        HBox row = new HBox();
        row.getChildren().add(new Label("Find"));
        row.setSpacing(10);
        row.setFillHeight(true);
        row.setAlignment(Pos.BASELINE_CENTER);

        ComboBox<String> tableSelection = new ComboBox<>();
        tableSelection.getItems().addAll(OracleTableNames.PRETTY_NAMES);
        row.getChildren().add(tableSelection);

        this.setCenter(row);
        BorderPane.setMargin(row, new Insets(10,0,0,0));

    }
}
