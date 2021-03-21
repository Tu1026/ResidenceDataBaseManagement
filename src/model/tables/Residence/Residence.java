package model.tables.Residence;

import model.tables.BuildingManager;
import model.tables.ResidentialManagingOffice;
import model.tables.TableData;

import java.util.HashMap;
import java.util.Map;

public final class Residence extends TableData {
    public static final String RES_ST_ADRESS = "resStAdress";
    public static final String RES_ZIP_CODE = "resZipCode";
    public static final String RES_NAME = "resName";


    private static final String [] columnNames = {RES_ST_ADRESS, RES_ZIP_CODE, RES_NAME,
            ResidentialManagingOffice.RMO_ST_ADDRESS, ResidentialManagingOffice.RMO_ZIP_CODE, BuildingManager.BM_EMPLOYEE_ID};

    private final String resStAddress;
    private final String resZipCode;
    private final String resName;
    private final String rMOStAddress;
    private final String rMOZipCode;
    private final String bMEmployeeID;

    public Residence(String resStAddress, String resZipCode, String resName, String rMOStAddress, String rMOZipCode, String bMEmployeeID) {
        super(columnNames);
        this.resStAddress = resStAddress;
        this.resZipCode = resZipCode;
        this.resName = resName;
        this.rMOStAddress = rMOStAddress;
        this.rMOZipCode = rMOZipCode;
        this.bMEmployeeID = bMEmployeeID;

        Map<String, String> data = new HashMap<>();
        data.put(RES_NAME, this.resName);
        data.put(this.resStAddress, this.resStAddress);
        data.put(this.resZipCode, this.resZipCode);
        data.put(ResidentialManagingOffice.RMO_ST_ADDRESS, this.rMOStAddress);
        data.put(ResidentialManagingOffice.RMO_ZIP_CODE, this.rMOZipCode);
        data.put(BuildingManager.BM_EMPLOYEE_ID, this.bMEmployeeID);
        super.setData(data);
    }

    public String getResStAddress() {
        return resStAddress;
    }

    public String getResZipCode() {
        return resZipCode;
    }

    public String getResName() {
        return resName;
    }

    public String getRMOStAddress() {
        return rMOStAddress;
    }

    public String getRMOZipCode() {
        return rMOZipCode;
    }

    public String getBMEmployeeID() {
        return bMEmployeeID;
    }
}
