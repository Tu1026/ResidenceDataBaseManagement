package model.tables.Residence;

import model.tables.TableData;

import java.util.HashMap;
import java.util.Map;

public final class House extends TableData {

    public static final String NAME = "name";
    public static final String CAPACITY = "capacity";
    public static final String TYPE = "type";
    public static final String AGE_RESTRICTION = "ageRestriction";
    private static final String [] columnNames = {NAME, CAPACITY, TYPE, AGE_RESTRICTION, Residence.RES_ST_ADRESS, Residence.RES_ZIP_CODE};

    private final String name;
    private final String capacity;
    private final String type;
    private final String ageRestriction;
    private final String resStAddress;
    private final String resZipCode;

    public House(String name, String capacity, String type, String ageRestriction,
                 String resStAddress, String resZipCode) {
        super(columnNames);
        this.name = name;
        this.capacity = capacity;
        this.type = type;
        this.ageRestriction = ageRestriction;
        this.resStAddress = resStAddress;
        this.resZipCode = resZipCode;

        Map<String, String> data = new HashMap<>();
        data.put(NAME, this.name);
        data.put(CAPACITY, this.capacity);
        data.put(TYPE, this.type);
        data.put(AGE_RESTRICTION, this.ageRestriction);
        data.put(Residence.RES_ST_ADRESS, this.resStAddress);
        data.put(Residence.RES_ZIP_CODE, this.resZipCode);
        super.setData(data);
    }

    public String getName() {
        return name;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getType() {
        return type;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public String getResStAddress() {
        return resStAddress;
    }

    public String getResZipCode() {
        return resZipCode;
    }
}
