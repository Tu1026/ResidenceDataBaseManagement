package ui;

import controller.Controller;
import interfaces.ConnectionStateDelegate;
import interfaces.ControllerDelegate;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.OracleColumnNames;
import model.OracleTableNames;

public class LoginFrame extends Application {


    public ControllerDelegate controller;


    @Override
    public void start(Stage primaryStage) {
        OracleTableNames.buildMaps();
        OracleColumnNames.buildMaps();
        Pane layout = new Pane();
        Label userName = new Label("UserName");
        userName.setLayoutX(32);
        userName.setLayoutY(130);

        Label password = new Label("Password");
        password.setLayoutX(32);
        password.setLayoutY(216);

        TextField userNameText = new TextField();
        userNameText.setPromptText("ora_CWL");
        userNameText.setMinHeight(36);

        PasswordField passwordText = new PasswordField();
        passwordText.setPromptText("a<studentNumber>");

        HBox userBox = new HBox();
        userBox.getChildren().add(userNameText);
        userBox.setLayoutX(115);
        userBox.setLayoutY(126);
        userBox.setMinHeight(35);
        userBox.setPrefWidth(464);

        HBox passwordBox = new HBox(passwordText);
        passwordBox.setLayoutX(115);
        passwordBox.setLayoutY(212);
        passwordBox.setPrefHeight(35);
        passwordBox.setPrefWidth(464);


        userNameText.prefWidthProperty().bind(userBox.widthProperty());
        userNameText.prefHeightProperty().bind(userBox.heightProperty());

        passwordText.prefWidthProperty().bind(userBox.widthProperty());
        passwordText.prefHeightProperty().bind(userBox.heightProperty());

        Button login = new Button("Log In");
        login.setLayoutY(316);
        login.setLayoutX(300);
        login.setPrefSize(77,41);


        login.setOnAction(e -> {
            String sUserName = userNameText.getText().toLowerCase().trim();
            String sPassword = passwordText.getText().toLowerCase().trim();

            controller = new Controller();
            ConnectionStateDelegate connectionState = controller.login(sUserName, sPassword);
            if (connectionState.isConnected()){
                controller.initializeSQLDDL();
                MainWindow newTable = new MainWindow(controller);
                primaryStage.setScene(newTable.getScene());
                primaryStage.setTitle("Manage Your Residence Database here!");

            }else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Error Connecting to Oracle");
                a.setContentText(connectionState.getMessage());
                a.showAndWait();
            }
        });

        layout.getChildren().addAll(password, userName, login, userBox, passwordBox);
        primaryStage.setTitle("Log into the database here");
        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /**
     * Runs when application stops
     * Attemps to log out of oracle before closing app
     * Avoids problems with multiple users connected
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        System.out.println("logging out and stopping...");
        if (! controller.logout().isConnected()) {
            System.out.println("Logged out");
        }else{
            System.out.println("error logging out, force stopping.");
        }
        super.stop();
    }
}
