package model.table;

import java.util.*;

public final class TableModel {
    private final Set<Column> columnNames;
    private final List<Column> columNamesList;

    public TableModel(String [] columnNames){
        this.columnNames = new LinkedHashSet<>();
        this.columNamesList = new ArrayList<>();

        for (String colStr: columnNames) {
            Column column = new Column(colStr);
            if (this.columnNames.contains(column)) {
                column = insertDuplicate(column);
            }
            this.columnNames.add(column);
            this.columNamesList.add(column);
        }
    }

    public final Set<Column> getColumnNames() {
        return (Collections.unmodifiableSet(columnNames));
    }

    public final int getNumColumns(){
        return columnNames.size();
    }

    public final List<Column> getColmnNamesList() {
        return Collections.unmodifiableList(columNamesList);
    }

    private Column insertDuplicate(Column colName) {
        String originalName = colName.name;
        int counter = 1;
        while (this.columnNames.contains(colName)) {
            colName = new Column(originalName + " (" + counter + ")");
            counter++;
        }
        return colName;
    }
}
