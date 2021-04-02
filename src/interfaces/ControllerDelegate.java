package interfaces;

import model.AdvanceQueries;
import model.table.Table;
import model.UpdateObject;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface ControllerDelegate {

 ConnectionStateDelegate login(String username, String password);

 /**
  * Loads initial SQL script
  */
 void initializeSQLDDL();

 /**
  * Logout of Oracle DBMS, returns ConnectionStateDelegate
  * @return ConnectionStateDelegate with confirmation of logout statuses
  */
 ConnectionStateDelegate logout();

 /**
  * Sets specified TableUI
  * @param ui ui to use to display data
  */
 void setUI(MainUIView ui);

 /**
  * Loads specified table from Oracle, returns all columns to TableViewUI
  * @param tableName table to query
  */
 void loadTable(String tableName);

 void filter(String filter, String column, List<String> columnsToDisplay);

 void getDataForStudentInsertion(String tableName, List<String> columnsToGet, List<String> columnsToMatch, List<String> dataToMatch, Consumer<Table> callback);


 void updateTable(UpdateObject updateObject);

 void deleteTable(List<String> rowData);

 void insertStudent(Map<String, String> data);

 void runAdvancedQuery(AdvanceQueries query, String input);

}
