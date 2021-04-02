package ui;

import controller.Controller;
import interfaces.ConnectionStateDelegate;
import interfaces.ControllerDelegate;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.AdvanceQueries;
import model.OracleColumnNames;
import model.OracleTableNames;
import sql.AdvancedQueryList;

public class LoginFrame extends Application {


    public ControllerDelegate controller;
    private static final String LOADING_IMG_PATH = "out/production/CPSC304Project/ui/images/loading_small.gif";
    private Text infoLabel;
    private Text loadingLabel;
    private Button login;
    private ImageView imgView;


    @Override
    public void start(Stage primaryStage) {
        OracleTableNames.buildMaps();
        OracleColumnNames.buildMaps();
        AdvancedQueryList.buildMap();

        Pane layout = new Pane();
        Label userName = new Label("UserName");
        userName.setLayoutX(32);
        userName.setLayoutY(130);

        TextField userNameText = new TextField();
        userNameText.setPromptText("ora_CWL");
        userNameText.setMinHeight(36);

        Label password = new Label("Password");
        password.setLayoutX(32);
        password.setLayoutY(216);



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

        imgView = new ImageView();
        imgView.setLayoutX(272);
        imgView.setLayoutY(255);
        imgView.maxHeight(30);
        imgView.maxWidth(60);
        String path = this.getClass().getResource("/ui/images/loading_small.gif").toExternalForm();
        Image img = new Image(path);
        imgView.setImage(img);
        imgView.setVisible(false);

        userNameText.prefWidthProperty().bind(userBox.widthProperty());
        userNameText.prefHeightProperty().bind(userBox.heightProperty());

        passwordText.prefWidthProperty().bind(userBox.widthProperty());
        passwordText.prefHeightProperty().bind(userBox.heightProperty());
        passwordText.setOnKeyReleased(key -> {
            if (key.getCode() == KeyCode.ENTER) {
                login(primaryStage, userNameText, passwordText);
            }
        });

        login = new Button("Log In");
        login.setLayoutY(325);
        login.setLayoutX(261);
        login.setPrefSize(77, 41);


        infoLabel = new Text("Loggin in...");
        infoLabel.setLayoutY(320);
        infoLabel.setLayoutX(270);
        infoLabel.setTextAlignment(TextAlignment.CENTER);
        infoLabel.setVisible(false);

        loadingLabel = new Text("Logged in! \nLoading DDL/DML...");
        loadingLabel.setTextAlignment(TextAlignment.CENTER);
        loadingLabel.setLayoutY(305);
        loadingLabel.setLayoutX(240);
        loadingLabel.setVisible(false);

        //infoLabel.setPrefSize(140, 25);

        login.setOnAction(e -> {
            login(primaryStage, userNameText, passwordText);
        });

        login.setOnKeyReleased(key -> {
            if (key.getCode() == KeyCode.ENTER) {
                login(primaryStage, userNameText, passwordText);
            }
        });

        layout.getChildren().addAll(password, userName, login, userBox, passwordBox, imgView,  infoLabel, loadingLabel);
        primaryStage.setTitle("Log into the database here");
        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setResizable(false);
        userNameText.requestFocus();
        // autologin
//        userNameText.setText("");
//        passwordText.setText(" ");
//        login(primaryStage, userNameText, passwordText, imgView);
    }

    private void login(Stage primaryStage, TextField userNameText, PasswordField passwordText) {
        imgView.setVisible(true);
        infoLabel.setVisible(true);
        login.setOnKeyReleased(null);


        new Thread(() -> {
            String sUserName = userNameText.getText().toLowerCase().trim();
            String sPassword = passwordText.getText().toLowerCase().trim();

            controller = new Controller();
            ConnectionStateDelegate connectionState = controller.login(sUserName, sPassword);
            if (connectionState.isConnected()) {
                Platform.runLater(() -> {
                    infoLabel.setVisible(false);
                    loadingLabel.setVisible(true);
                });

                controller.initializeSQLDDL();
                Platform.runLater(() -> {
                    MainWindow newTable = new MainWindow(controller);
                    primaryStage.setScene(newTable.getScene());
                    primaryStage.setTitle("Manage Your Residence Database here!");
                });
            } else {
                Platform.runLater( () -> {
                    imgView.setVisible(false);
                    infoLabel.setVisible(false);
                    loadingLabel.setVisible(false);
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setTitle("Error Connecting to Oracle");
                    a.setContentText(connectionState.getMessage());
                    a.showAndWait();
                });
            }
        }).start();
    }


    /**
     * Runs when application stops
     * Attemps to log out of oracle before closing app
     * Avoids problems with multiple users connected
     *
     * @throws Exception
     */
    @Override
    public void stop() throws Exception {
        System.out.println("logging out and stopping...");
        if (!controller.logout().isConnected()) {
            System.out.println("Logged out");
        } else {
            System.out.println("error logging out, force stopping.");
        }
        super.stop();
    }
}
