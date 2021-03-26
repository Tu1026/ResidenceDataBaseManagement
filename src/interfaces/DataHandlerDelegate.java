package interfaces;

import model.table.Table;
import model.table.TableModel;

import java.sql.Connection;
import java.sql.ResultSet;
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

    //TableRow getTableData(Set<String> dataToLookup); // is this what we want? Not quite sure yet

}
