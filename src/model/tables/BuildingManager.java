package model.tables;

import java.util.HashMap;
import java.util.Map;

public class BuildingManager extends TableData {

    public static final String NAME = "name";
    public static final String BM_EMPLOYEE_ID = "bMEMployeeID";
    public static final String YEARS_OF_EXPERIENCE = "yearsOfExperience";
    public static final String PHONE_NUMBER = "phoneNumber";

    private static final String [] columnNames = {BM_EMPLOYEE_ID, NAME, YEARS_OF_EXPERIENCE, PHONE_NUMBER,
            ResidentialManagingOffice.RMO_ST_ADDRESS, ResidentialManagingOffice.RMO_ZIP_CODE};

    public BuildingManager(String bMEMployeeID, String name, String yearsOfExperience, String phoneNumber, String rMOStAddress, String rMOZipCode) {
        super(columnNames);

        Map<String, String> data = new HashMap<>();
        data.put(BM_EMPLOYEE_ID, bMEMployeeID);
        data.put(YEARS_OF_EXPERIENCE, yearsOfExperience);
        data.put(PHONE_NUMBER, phoneNumber);
        data.put(ResidentialManagingOffice.RMO_ST_ADDRESS, rMOStAddress);
        data.put(ResidentialManagingOffice.RMO_ZIP_CODE, rMOZipCode);
        data.put(NAME, name);
        super.setData(data);
    }
}
