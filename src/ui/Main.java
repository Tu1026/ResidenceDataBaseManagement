package ui;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {


    private final String username = "";
    private final String pwd = "";

    @Override
    public void start(Stage primaryStage) throws Exception {
        if (username.trim().equals("") || pwd.trim().equals("")) {
            // only for testing purposes
            System.err.println("ERROR: set your username and password in the fields in ui.Main");
           System.exit(1);
        }

        new Controller().login(username, pwd);
        // should print "<username> logged in to oracle"
        System.exit(0);
    }

    public static void main(String[] args)  {
        launch(args);
    }
}
