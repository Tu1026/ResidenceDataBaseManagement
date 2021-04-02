package interfaces;

import model.AdvanceQueries;
import model.table.Table;
import model.UpdateObject;

import java.sql.Connection;
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

    /**
     * Insert the specified model into the database
     * @param data model to be inserted into the database
     */
    void insertTableData(Map<String, String> data, Consumer<String> onSuccess, Consumer<String> onError);


    void updateTableData(String prettyTableName, UpdateObject updateObject, Consumer<String> onSuccess, Consumer<String> onError);

    void deleteTableData(String prettyTableName, List<String> columnsToUpdate, Consumer<Table> onSuccess, Consumer<String> onError);


    /**
     * Queries a specific table, returns the data in a callback
     * @param tableToLookup string of table to lookup
     * @param callback function to call once query is complete
     */
     void getTableData(String tableToLookup, Consumer<Table> callback);

    void filterTable(String tableToLookup, String filter, String column, List<String> columnsToDisplay, Consumer<Table> callback);

    void getSpecificTableData(String tableName, List<String> columnsToGet, List<String> columnsToMatch, List<String> dataToMatch, Consumer<Table> callback);

    void runAdvancedQuery(AdvanceQueries query, String input, Consumer<Table> onSuccess, Consumer<String> onError);

}
