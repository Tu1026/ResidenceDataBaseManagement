package ui;

import controller.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

        Parent MenuItems = FXMLLoader.load(getClass().getResource("MenuItems.fxml"));
        Scene MenuScene = new Scene(MenuItems);
        Stage MenuStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MenuStage.setScene(MenuScene);
        MenuStage.show();


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
