package model.tables;

import model.TableData;

import java.util.HashMap;
import java.util.Map;

public class Campus extends TableData {

    public static final String C_ST_ADDRESS = "cStAddress";
    public static final String C_ZIP_CODE = "cZipCode";
    public static final String NAME = "name";
    public static final String POPULATION = "population";
    private static final String [] columnNames = {C_ST_ADDRESS, C_ZIP_CODE, NAME, POPULATION};

    public Campus(String cStAddress, String cZipCode, String name, String population) {
        super(columnNames);

        Map<String, String> data = new HashMap<>();
        data.put(C_ST_ADDRESS, cStAddress);
        data.put(C_ZIP_CODE, cZipCode);
        data.put(NAME, name);
        data.put(POPULATION, population);
        super.setData(data);
    }
}
