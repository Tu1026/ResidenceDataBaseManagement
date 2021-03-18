package ui;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        new Controller().login("ora_jmhirsch", "a64927676");
    }

    public static void main(String[] args)  {
        launch(args);
    }
}
