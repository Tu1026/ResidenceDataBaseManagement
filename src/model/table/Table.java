package model.table;

import java.util.*;

public class Table implements Iterable<TableRow> {

    private final TableModel model;
    private final List<TableRow> rows;
    private TableRow currentRow = null;
    private String name;



    public Table(String [] columNames){
        model = new TableModel(columNames);
        rows = new ArrayList<>();
        this.name = "No Name";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void insert(Column column, String data) {
        if (currentRow == null) {
            currentRow = new TableRow();
        }

        currentRow.insert(column, data);
    }

    public void nextRow(){
        if (currentRow != null) {
            rows.add(currentRow);
            currentRow = new TableRow();
        }
    }

    public String getName() {
        return this.name;
    }

    public Set<Column> getColumns(){
        return model.getColumnNames();
    }

    public List<Column> getColumnsList(){
        return model.getColmnNamesList();
    }

    public int getNumRows(){
        return rows.size();
    }

    public int getNumColumns(){
        return model.getNumColumns();
    }

    @Override
    public Iterator<TableRow> iterator() {
        return rows.iterator();
    }
}
