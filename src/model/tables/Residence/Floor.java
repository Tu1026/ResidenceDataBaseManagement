package model.tables.Residence;

import model.tables.TableData;

import java.util.HashMap;
import java.util.Map;

public class Floor extends TableData {

    public static final String F_NUMBER = "fNumber";
    public static final String CAPACITY = "capacity";
    public static final String GENDER_RESTRICTION = "genderRestriction";

    private static final String [] columnNames = {F_NUMBER, CAPACITY, GENDER_RESTRICTION, House.NAME,
            Residence.RES_ST_ADRESS, Residence.RES_ZIP_CODE};

    private final String fNumber;
    private final String capacity;
    private final String genderRestriction;
    private final String houseName;
    private final String resStAddress;
    private final String resZipCode;

    public Floor(String fNumber, String capacity, String genderRestriction, String houseName, String resStAddress, String resZipCode) {
        super(columnNames);
        this.fNumber = fNumber;
        this.capacity = capacity;
        this.genderRestriction = genderRestriction;
        this.houseName = houseName;
        this.resStAddress = resStAddress;
        this.resZipCode = resZipCode;

        Map<String, String> data = new HashMap<>();
        data.put(F_NUMBER, this.fNumber);
        data.put(CAPACITY, this.capacity);
        data.put(House.NAME, this.houseName);
        data.put(GENDER_RESTRICTION, this.genderRestriction);
        data.put(Residence.RES_ST_ADRESS, this.resStAddress);
        data.put(Residence.RES_ZIP_CODE, this.resZipCode);
        super.setData(data);
    }

    public String getFNumber() {
        return fNumber;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getGenderRestriction() {
        return genderRestriction;
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
