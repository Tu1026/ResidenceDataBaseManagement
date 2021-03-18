package model.tables.Residence;

import model.tables.Residence.Residence;
import model.tables.TableData;

import java.util.HashMap;
import java.util.Map;

public class House extends TableData {

    public static final String NAME = "name";
    public static final String CAPACITY = "capacity";
    public static final String TYPE = "type";
    public static final String AGE_RESTRICTION = "ageRestriction";
    private static final String [] columnNames = {NAME, CAPACITY, TYPE, AGE_RESTRICTION, Residence.RES_ST_ADRESS, Residence.RES_ZIP_CODE};

    public House(String name, String capacity, String type, String ageRestriction, String resStAddress, String resZipCode) {
        super(columnNames);

        Map<String, String> data = new HashMap<>();
        data.put(NAME, name);
        data.put(CAPACITY, capacity);
        data.put(TYPE, type);
        data.put(AGE_RESTRICTION, ageRestriction);
        data.put(Residence.RES_ST_ADRESS, resStAddress);
        data.put(Residence.RES_ZIP_CODE, resZipCode);
        super.setData(data);
    }
}
