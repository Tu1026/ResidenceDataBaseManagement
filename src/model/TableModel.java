package model;

import java.util.Collections;
import java.util.List;

public class TableModel {
    private final List<String> columnNames;


    public TableModel(List<String> columnNames){
        this.columnNames = columnNames;
    }

    public List<String> getColumnNames() {
        return Collections.unmodifiableList(columnNames);
    }

    public int getNumColumns(){
        return columnNames.size();
    }
}
