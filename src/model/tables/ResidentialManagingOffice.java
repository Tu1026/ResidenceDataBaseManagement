package model.tables;

import model.TableData;

import java.util.HashMap;
import java.util.Map;

public class ResidentialManagingOffice extends TableData {

    public static final String RMO_ZIP_CODE = "rMOZipCode";
    public static final String RMO_ST_ADDRESS = "rMOStAddress";
    public static final String NAME = "name";
    public static final String BUDGET = "budget";

    private static final String [] columnNames = {RMO_ST_ADDRESS, RMO_ZIP_CODE, NAME, BUDGET,
            Campus.C_ST_ADDRESS, Campus.C_ZIP_CODE};

    public ResidentialManagingOffice(String rMOStAddress, String rMOZipCode, String name, String budget,
                                     String cStAddress, String cZipCode) {
        super(columnNames);

        Map<String, String> data = new HashMap<>();
        data.put(RMO_ST_ADDRESS, rMOStAddress);
        data.put(RMO_ZIP_CODE, rMOZipCode);
        data.put(Campus.C_ST_ADDRESS, cStAddress);
        data.put(Campus.C_ZIP_CODE, cZipCode);
        data.put(NAME, name);
        data.put(BUDGET, budget);
        super.setData(data);
    }
}
