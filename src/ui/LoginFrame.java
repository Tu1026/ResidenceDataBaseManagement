package ui;

import controller.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginFrame extends Application {

    @FXML
    private TextField userName;

    @FXML
    private PasswordField password;
    @FXML
    void logIntoDB(ActionEvent event) throws IOException {
        String sUserName = userName.getText().toLowerCase().trim();
        String sPassword = password.getText().toLowerCase().trim();
        Controller controller = new Controller();
        controller.login(sUserName, sPassword);
        controller.initializeSQLDDL();
        System.exit(0);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginFrame.fxml"));
        primaryStage.setTitle("Log into the database here");

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
