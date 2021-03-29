package interfaces;

import model.table.Table;
import model.table.TableModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;


public interface DataHandlerDelegate {

    /**
     * Sets the connection object to be used to communicate with oracle db
     * @param connection object to get/send data
     */
    void setConnection(Connection connection);

    /**
     * Runs SQL DDL create statements, drops tables.
     */
    void initializeDDL();


    void performQuery(String query, Consumer<Table> callback);

    /**
     * Insert the specified model into the database
     * @param data model to be inserted into the database
     */
    void insertTableData(TableModel data);

    /**
     * // to define
     * @param data // to define
     */
    //void updateTableData(TableRow data); // need some kind of parameter to specify what we want to update I am guessing

    //I temporarly modded it to return Resultset, a set can be used by javafx
    /**
     * //TODO : what exactly do we want to do? (same for all the above)
//     * @param dataToLookup list of string to lookup?
     * @return TableData returned
     */

    //ResultSet getTableData(String sql); // is this what we want? Not quite sure yet

    /**
     * Queries a specific table, returns the data in a callback
     * @param tableToLookup string of table to lookup
     * @param callback function to call once query is complete
     */
     void getTableData(String tableToLookup, Consumer<Table> callback);

    void filterTable(String tableToLookup, String filter, String column, Consumer<Table> callback);

    void getSpecificTableData(String tableName, List<String> columnsToGet, Map<String, String> data, Consumer<Table> callback);

}
