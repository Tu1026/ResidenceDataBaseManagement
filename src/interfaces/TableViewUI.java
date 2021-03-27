package interfaces;

import model.table.Table;

public interface TableViewUI {

    /**
     * Receives updates when a table has been queried to display changes
     * @param table table with queried data
     */
    void updateVisibleTable(Table table);
}
