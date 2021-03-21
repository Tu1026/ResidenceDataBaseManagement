package model.tables;

import java.util.HashMap;
import java.util.Map;

public final class ResidentialManagingOffice extends TableData {

    public static final String RMO_ZIP_CODE = "rMOZipCode";
    public static final String RMO_ST_ADDRESS = "rMOStAddress";
    public static final String NAME = "name";
    public static final String BUDGET = "budget";

    private static final String [] columnNames = {RMO_ST_ADDRESS, RMO_ZIP_CODE, NAME, BUDGET,
            Campus.C_ST_ADDRESS, Campus.C_ZIP_CODE};


    private final String rMOStAddress;
    private final String rMOZipCode;
    private final String name;
    private final String budget;
    private final String cStAddress;
    private final String cZipCode;

    public ResidentialManagingOffice(String rMoStAddress, String rMOZipCode, String name,
                                     String budget, String cStAddress, String cZipCode) {
        super(columnNames);
        this.rMOStAddress = rMoStAddress;
        this.rMOZipCode = rMOZipCode;
        this.name = name;
        this.budget = budget;
        this.cStAddress = cStAddress;
        this.cZipCode = cZipCode;

        Map<String, String> data = new HashMap<>();
        data.put(RMO_ST_ADDRESS, rMOStAddress);
        data.put(RMO_ZIP_CODE, this.rMOZipCode);
        data.put(Campus.C_ST_ADDRESS, this.cStAddress);
        data.put(Campus.C_ZIP_CODE, this.cZipCode);
        data.put(NAME, this.name);
        data.put(BUDGET, this.budget);
        super.setData(data);
    }

    public String getRMOStAddress() {
        return rMOStAddress;
    }

    public String getRMOZipCode() {
        return rMOZipCode;
    }

    public String getName() {
        return name;
    }

    public String getBudget() {
        return budget;
    }

    public String getCStAddress() {
        return cStAddress;
    }

    public String getCZipCode() {
        return cZipCode;
    }
}
