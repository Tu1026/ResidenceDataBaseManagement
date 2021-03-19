package ui;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;

public class Main extends Application {

    private static String username = null;
    private static String password = null;
    @Override
    public void start(Stage primaryStage) throws Exception {

        if (username == null || password == null) {
            // only for testing purposes
            System.err.println("ERROR: set your username and password when prompted");
           System.exit(1);
        }

        new Controller().login(username, password);
        // should print "<username> logged in to oracle"
        System.exit(0);
    }

    public static void main(String[] args)  {

        username = JOptionPane.showInputDialog("Enter oracle username");
        password = JOptionPane.showInputDialog("Enter oracle password");

        launch(args);
    }
}
