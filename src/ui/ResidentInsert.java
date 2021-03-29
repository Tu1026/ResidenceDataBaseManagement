package ui;

import interfaces.ControllerDelegate;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ResidentInsert extends Application {

    private ControllerDelegate controller;
    private Scene scene;

//
//    public ResidentInsert(ControllerDelegate controller){
//        this.controller = controller;
//    }

    @Override
    public void start(Stage primaryStage) {
        GridPane masterGridPane = new GridPane();

        Pane zeroPane = new Pane();
        Label emailLabel = new Label();
        TextField emailText = new TextField();
        zeroPane.getChildren().addAll(emailText, emailLabel);
        emailLabel



        HBox emailBox = new HBox();
//        TextField emailText =

//        Scene newScene = new Scene(test, 1600,300);
//        primaryStage.setScene(newScene);
//        primaryStage.show();
    }

    public Scene getScene() {
        return scene;


        ///### Pass (HashMap(column:key, String, tableName)
    }

}
