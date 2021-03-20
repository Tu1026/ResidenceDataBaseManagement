package interfaces;

import model.TableModel;
import model.tables.TableData;

import java.sql.Connection;
import java.util.Set;

public interface DataHandlerDelegate {

    void setConnection(Connection connection);
    void initializeDDL();


    /**
     * Insert the specified model into the database
     * @param data model to be inserted into the database
     */
    void insertTableData(TableModel data);

    /**
     * // to define
     * @param data // to define
     */
    void updateTableData(TableData data); // need some kind of parameter to specify what we want to update I am guessing

    /**
     * //TODO : what exactly do we want to do? (same for all the above)
     * @param dataToLookup list of string to lookup?
     * @return TableData returned
     */
    TableData getTableData(Set<String> dataToLookup); // is this what we want? Not quite sure yet
}
