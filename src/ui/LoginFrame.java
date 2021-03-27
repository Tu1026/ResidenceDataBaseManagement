package ui;

import controller.Controller;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.io.IOException;

public class LoginFrame extends Application {


    public static Controller con;




    @Override
    public void start(Stage primaryStage) {
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
            Controller con = new Controller();
            Boolean connected = con.login(sUserName, sPassword);
            if (connected){
                con.initializeSQLDDL();
                TableViews newTable = new TableViews("Authors", con);
                primaryStage.setScene(newTable.getScene());

            }else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Incorrect Credentials");
                a.setContentText("Incorrect Username or Password");
                a.showAndWait();
            }


        });

        layout.getChildren().addAll(password, userName, login, userBox, passwordBox);
        primaryStage.setTitle("Log into the database here");
        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();


    }




}
