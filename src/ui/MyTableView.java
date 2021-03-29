
package ui;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import model.OracleColumnNames;
import model.table.Column;
import model.table.Table;
import model.table.TableRow;

import java.util.List;

public class MyTableView {


    TableView<ObservableList<String>> tableView = new TableView<>();
    ObservableList<ObservableList<String>> data;

    public MyTableView() {


        tableView.setStyle("-fx-border-color: #000000");
    }


    public TableView<ObservableList<String>> getComponent(){
        return tableView;
    }

    public void setSizeProperties(ReadOnlyDoubleProperty width, ReadOnlyDoubleProperty height){
        tableView.prefWidthProperty().bind(width);
        tableView.prefHeightProperty().bind(height);
    }


    //    Following the tutorial here to help generate dynamic columns https://blog.ngopal.com.np/2011/10/19/dyanmic-tableview-data-from-database/
    public void buildData(Table table) {
        data = FXCollections.observableArrayList();
        tableView.getItems().clear();
        tableView.getColumns().clear();

        try {

            /*q
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             *********************************
             */

            if (table != null) {
                List<Column> columnNames = table.getColumnsList();
                for (int i = 0; i < columnNames.size(); i++ ){
                    final  int j = i;
                    String columnName = OracleColumnNames.GET_PRETTY_COLUMN_NAMES.get(columnNames.get(i).name);
                    TableColumn<ObservableList<String>, String> col = new TableColumn<>(columnName);
                    col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                    col.setCellFactory(TextFieldTableCell.forTableColumn());
                    col.setOnEditCommit(e -> {
                        ObservableList<String> row = e.getRowValue();
                        row.set(j, e.getNewValue());
                    });
                    tableView.getColumns().add(col);
                    System.out.println("Column [" + i + "] ");
                }

                /*
                 * ******************************
                 * Data added to ObservableList *
                 *******************************
                 */

                for (TableRow tablerow : table) {
                    ObservableList<String> row = FXCollections.observableArrayList();
                    for (Column column : columnNames) {
                        row.add(tablerow.get(column));
                    }
                    data.add(row);

                }
            }
            //FINALLY ADDED TO TableView
            tableView.setItems(data);
            tableView.setEditable(true);
            tableView.refresh();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

}