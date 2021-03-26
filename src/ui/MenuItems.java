package ui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.tables.Campus;

import java.io.IOException;
import java.util.Set;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Node;

public class MenuItems extends Application {


        private Button CampusB = new Button("Campus");

        private Button ResManB = new Button("Residential Managing Office");

        private Button BuildManB = new Button("Building Manager");

        private Button SRAB = new Button ("Senior Advisor");

        private Button RAB = new Button("Residence Advisor");

        private Button ResAddressB = new Button("Residential Address");

        private Button ResBudgetB = new Button("Residence Budget");

        private Button ResCapB = new Button("Residence Capacity");

        private Button ResB = new Button("Residence");

        private Button FloorB = new Button("Floor");

        private Button HouseB = new Button("House");

        private Button UnitB = new Button("Unit");

        private Button ResInfoB = new Button("Resident Info");

        public static Scene menuScene;


        @Override
        public void start(Stage primaryStage) throws IOException {

//            Parent root = FXMLLoader.load(getClass().getResource("MenuItems.fxml"));
            GridPane grids = new GridPane();
            grids.add(FloorB, 0,0);
            grids.add(UnitB, 0, 1);
            grids.addRow(5);


            primaryStage.setTitle("Menu");
            Scene menuScene = new Scene(grids, 1024, 768);
            primaryStage.setScene(menuScene);
            primaryStage.show();

        }

//        @FXML
//        private void goIntoTables (ActionEvent event) throws Exception {
//            Stage stage;
//            Parent root;
//
//            if(event.getSource()== CampusB){
//                stage = (Stage) CampusB.getScene().getWindow();
//                root = FXMLLoader.load(getClass().getResource("FXML2.fxml"));
//            }
//            else{
//                stage = (Stage) CampusB.getScene().getWindow();
//                root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
//            }
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
//        }

    public static void main(String[] args) {
        launch(args);
    }
}
