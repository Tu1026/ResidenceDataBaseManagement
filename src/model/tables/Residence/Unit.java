package model.tables.Residence;

import model.tables.TableData;

import java.util.HashMap;
import java.util.Map;

public class Unit extends TableData {

    public static final String U_NUMBER = "uNumber";
    public static final String CAPACITY = "capacity";
    public static final String GENDER_RESTRICTION = "genderRestriction";
    public static final String VACANCY = "vacancy";
    private static final String [] columnNames = {U_NUMBER, CAPACITY, GENDER_RESTRICTION, VACANCY,
            Floor.F_NUMBER, House.NAME, Residence.RES_ST_ADRESS, Residence.RES_ZIP_CODE};

    public Unit(String uNumber, String capacity, String genderRestriction, String vancancy, String fNumber,
                String houseName, String resStAddress, String resZipCode) {
        super(columnNames);

        Map<String, String> data = new HashMap<>();
        data.put(U_NUMBER, uNumber);
        data.put(CAPACITY, capacity);
        data.put(VACANCY, vancancy);
        data.put(Floor.F_NUMBER, fNumber);
        data.put(House.NAME, houseName);
        data.put(GENDER_RESTRICTION, genderRestriction);
        data.put(Residence.RES_ST_ADRESS, resStAddress);
        data.put(Residence.RES_ZIP_CODE, resZipCode);
        super.setData(data);
    }
}
