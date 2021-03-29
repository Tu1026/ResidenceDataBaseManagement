package interfaces;

import model.table.Table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface ControllerDelegate {

 ConnectionStateDelegate login(String username, String password);

 /**
  * Performs specified query, returns all data to TableViewUI
  * @param query queryy to perform
  */
 void performQuery(String query);

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
 void setUI(TableViewUI ui);

 /**
  * Loads specified table from Oracle, returns all columns to TableViewUI
  * @param tableName table to query
  */
 void loadTable(String tableName);

 void filter(String filter, String column);

 void getDataForStudentInsertion(String tableName, List<String> columnsToGet, Map<String, String> data, Consumer<Table> callback);

}
