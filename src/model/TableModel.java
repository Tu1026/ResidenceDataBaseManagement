package model;

import java.util.Collections;
import java.util.List;

public class TableModel {
    private final int numRows;
    private final List<String> columnNames;


    public TableModel(int numRows, List<String> columnNames){
        this.columnNames = columnNames;
        this.numRows = numRows;
    }


    public int getNumRows() {
        return numRows;
    }

    public List<String> getColumnNames() {
        return Collections.unmodifiableList(columnNames);
    }
}
