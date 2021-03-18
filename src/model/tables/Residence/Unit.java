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


    private final String uNumber;
    private final String capacity;
    private final String genderRestriction;
    private final String vancancy;
    private final String fNumber;
    private final String houseName;
    private final String resStAddress;
    private final String resZipCode;

    public Unit(String uNumber, String capacity, String genderRestriction, String vancancy, String fNumber, String houseName, String resStAddress, String resZipCode) {
        super(columnNames);
        this.uNumber = uNumber;
        this.capacity = capacity;
        this.genderRestriction = genderRestriction;
        this.vancancy = vancancy;
        this.fNumber = fNumber;
        this.houseName = houseName;
        this.resStAddress = resStAddress;
        this.resZipCode = resZipCode;

        Map<String, String> data = new HashMap<>();
        data.put(U_NUMBER, this.uNumber);
        data.put(CAPACITY, this.capacity);
        data.put(VACANCY, this.vancancy);
        data.put(Floor.F_NUMBER, this.fNumber);
        data.put(House.NAME, this.houseName);
        data.put(GENDER_RESTRICTION, this.genderRestriction);
        data.put(Residence.RES_ST_ADRESS, this.resStAddress);
        data.put(Residence.RES_ZIP_CODE, this.resZipCode);
        super.setData(data);
    }

    public String getuNumber() {
        return uNumber;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getGenderRestriction() {
        return genderRestriction;
    }

    public String getVancancy() {
        return vancancy;
    }

    public String getfNumber() {
        return fNumber;
    }

    public String getHouseName() {
        return houseName;
    }

    public String getResStAddress() {
        return resStAddress;
    }

    public String getResZipCode() {
        return resZipCode;
    }
}
