package model.table;

import model.table.Column;

import java.util.HashMap;
import java.util.Map;

public class TableRow {
    private final Map<Column, String> data;


    public TableRow(){
        data = new HashMap<>();
    }

    public String get(Column column){
        return data.get(column);
    }

    public void insert(Column column, String attributeData){
        if (attributeData == null) {
            attributeData = "No Data";
        }
        data.put(column, attributeData.trim());
    }
}