package ui;

import interfaces.ControllerDelegate;
import javafx.application.Application;
import javafx.scene.Scene;
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
        VBox test = new VBox();

        Scene newScene = new Scene(test, 1600,300);
        primaryStage.setScene(newScene);
        primaryStage.show();
    }

    public Scene getScene() {
        return scene;
    }
}
