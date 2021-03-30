
package ui;

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
import model.table.UpdateObject;

import java.util.*;
import java.util.function.Consumer;

public class MyTableView extends TableView<ObservableList<String>>  {

    private final Consumer<UpdateObject> fireUpdateRequest;
    public MyTableView(Consumer<UpdateObject> fireUpdateRequest) {
        //this.setStyle("-fx-border-color: #9f9d9d");
        this.setStyle("-fx-focus-color: transparent; -fx-focus-faint-color: transparent; -fx-border-width: 1 1 1 1; -fx-border-color: #757575");
        this.setTableMenuButtonVisible(false);
        this.fireUpdateRequest = fireUpdateRequest;
    }

    public TableView<ObservableList<String>> getComponent(){
        return this;
    }

    //    Following the tutorial here to help generate dynamic columns https://blog.ngopal.com.np/2011/10/19/dyanmic-tableview-data-from-database/
    public void buildData(Table table) {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        this.getItems().clear();
        this.getColumns().clear();

        try {

            /*
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
                    col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                    col.setCellFactory(TextFieldTableCell.forTableColumn());
                    col.setOnEditCommit((e) -> handleEdit(e, table));
                    this.getColumns().add(col);
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
            this.setItems(data);
            this.setEditable(true);
            this.refresh();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    private void handleEdit(TableColumn.CellEditEvent<ObservableList<String>, String> e, Table table) {

        Map<String, List<String>> PKs = table.getPKs();

        LinkedHashMap<String, String> conditionsToCheck = new LinkedHashMap<>();

        List<String> kks = new ArrayList<>();

        for (String s: PKs.keySet()){
            kks = PKs.get(s);
            break;
        }

        String colToUpdate = "";
        String newValue = "";

        TableColumn<ObservableList<String>, String> m = e.getTableColumn();
        String editedColName = m.textProperty().getValue();

        ObservableList<String> row = e.getRowValue();


        for (int i = 0; i < this.getColumns().size(); i++) {
            TableColumn<ObservableList<String>, ?> currentColumn = this.getColumns().get(i);

            String currentColName = currentColumn.textProperty().getValue();
            if (currentColName.equals(editedColName)) {
                colToUpdate = currentColName;
                newValue = e.getNewValue();
                row.set(i, e.getOldValue());
                this.buildData(table);
            }

            if (kks.contains(OracleColumnNames.GET_ORACLE_COLUMN_NAMES.get(currentColName))){
                conditionsToCheck.put(currentColName, row.get(i));
            }
        }

            System.out.println(colToUpdate + " .. " + newValue);

        UpdateObject obj = new UpdateObject();
        obj.colToUpdate = colToUpdate;
        obj.newValue = newValue;
        obj.conditionsToCheck = conditionsToCheck;

        fireUpdateRequest.accept(obj);
    }
}