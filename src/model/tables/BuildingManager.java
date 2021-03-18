package model.tables;

import java.util.HashMap;
import java.util.Map;

public final class BuildingManager extends TableData {

    public static final String NAME = "name";
    public static final String BM_EMPLOYEE_ID = "bMEMployeeID";
    public static final String YEARS_OF_EXPERIENCE = "yearsOfExperience";
    public static final String PHONE_NUMBER = "phoneNumber";

    private static final String [] columnNames = {BM_EMPLOYEE_ID, NAME, YEARS_OF_EXPERIENCE, PHONE_NUMBER,
            ResidentialManagingOffice.RMO_ST_ADDRESS, ResidentialManagingOffice.RMO_ZIP_CODE};

    private final String bMEMployeeID;
    private final String name;
    private final String yearsOfExperience;
    private final String phoneNumber;
    private final String rMOStAddress;
    private final String rMOZipCode;

    public BuildingManager(String bMEMployeeID, String name, String yearsOfExperience, String phoneNumber, String rMOStAddress, String rMOZipCode) {
        super(columnNames);
        this.bMEMployeeID = bMEMployeeID;
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
        this.phoneNumber = phoneNumber;
        this.rMOStAddress = rMOStAddress;
        this.rMOZipCode = rMOZipCode;

        Map<String, String> data = new HashMap<>();
        data.put(BM_EMPLOYEE_ID, this.bMEMployeeID);
        data.put(YEARS_OF_EXPERIENCE, this.yearsOfExperience);
        data.put(PHONE_NUMBER, this.phoneNumber);
        data.put(ResidentialManagingOffice.RMO_ST_ADDRESS, this.rMOStAddress);
        data.put(ResidentialManagingOffice.RMO_ZIP_CODE, this.rMOZipCode);
        data.put(NAME, this.name);
        super.setData(data);
    }

    public String getbMEMployeeID() {
        return bMEMployeeID;
    }

    public String getName() {
        return name;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getrMOStAddress() {
        return rMOStAddress;
    }

    public String getrMOZipCode() {
        return rMOZipCode;
    }
}
