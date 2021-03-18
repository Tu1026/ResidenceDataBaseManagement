package model.tables.Residence;

import model.tables.Residence.House;
import model.tables.Residence.Residence;
import model.tables.TableData;

import java.util.HashMap;
import java.util.Map;

public class Floor extends TableData {

    public static final String F_NUMBER = "fNumber";
    public static final String CAPACITY = "capacity";
    public static final String GENDER_RESTRICTION = "genderRestriction";

    private static final String [] columnNames = {F_NUMBER, CAPACITY, GENDER_RESTRICTION, House.NAME,
            Residence.RES_ST_ADRESS, Residence.RES_ZIP_CODE};

    public Floor(String fNumber, String capacity, String genderRestriction, String houseName, String resStAddress, String resZipCode) {
        super(columnNames);

        Map<String, String> data = new HashMap<>();
        data.put(F_NUMBER, fNumber);
        data.put(CAPACITY, capacity);
        data.put(House.NAME, houseName);
        data.put(GENDER_RESTRICTION, genderRestriction);
        data.put(Residence.RES_ST_ADRESS, resStAddress);
        data.put(Residence.RES_ZIP_CODE, resZipCode);
        super.setData(data);
    }
}
