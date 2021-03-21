package ui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuItems extends Application {

        @FXML
        private Button CampusB;

        @FXML
        private Button ResManB;

        @FXML
        private Button ResInfoB;

        @FXML
        private Button ResAddressB;

        @FXML
        private Button RAB;

        @FXML
        private Button SRAB;

        @FXML
        private Button FloorB;

        @FXML
        private Button HouseB;

        @FXML
        private Button ResB;

        @FXML
        private Button ResBudgetB;

        @FXML
        private Button ResCapB;

        @FXML
        private Button UnitB;

        @FXML
        private Button BuildManB;


    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MenuItems.fxml"));
        primaryStage.setTitle("Menu");

        Scene scene = new Scene(root, 1024, 768);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
