package model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class TableData {

    private TableModel model;
    private Map<String, String> data;


    public TableData(String [] columnNames){
        model = new TableModel(Arrays.asList(columnNames));
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
