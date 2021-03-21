package model.tables;

import model.TableModel;
import java.util.Map;

public abstract class TableData {

    private final TableModel model;
    private Map<String, String> data;


    public TableData(String [] columnNames){
        model = new TableModel(columnNames);
    }

    protected final void setData(Map<String, String> data){
        this.data = data;
    }

    public final Map<String, String> getData(){
        return data;
    }

    public final TableModel getTableModel(){
        return model;
    };
}