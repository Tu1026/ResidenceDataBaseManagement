package interfaces;

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

}
