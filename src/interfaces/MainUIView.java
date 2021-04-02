package interfaces;

import model.table.Table;

public interface MainUIView {

    /**
     * Receives updates when a table has been queried to display changes
     * @param table table with queried data
     */
    void updateVisibleTable(Table table);

    void displayError(String errorString);

    void displayMessage(String msg);

    void reloadLast(ControllerDelegate controllerDelegate);
}
