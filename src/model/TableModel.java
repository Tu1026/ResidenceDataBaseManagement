package model;

import java.util.*;

public final class TableModel {
    private final Set<String> columnNames;


    public TableModel(String [] columnNames){
        this.columnNames = new LinkedHashSet<>(Arrays.asList(columnNames));
    }

    public final Set<String> getColumnNames() {
        return (Collections.unmodifiableSet(columnNames));
    }

    public final int getNumColumns(){
        return columnNames.size();
    }
}
