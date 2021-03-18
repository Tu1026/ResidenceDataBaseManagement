package model.tables.Residence;

import model.tables.BuildingManager;
import model.tables.ResidentialManagingOffice;
import model.tables.TableData;

import java.util.HashMap;
import java.util.Map;

public class Residence extends TableData {
    public static final String RES_ST_ADRESS = "resStAdress";
    public static final String RES_ZIP_CODE = "resZipCode";
    public static final String RES_NAME = "resName";


    private static final String [] columnNames = {RES_ST_ADRESS, RES_ZIP_CODE, RES_NAME,
            ResidentialManagingOffice.RMO_ST_ADDRESS, ResidentialManagingOffice.RMO_ZIP_CODE, BuildingManager.BM_EMPLOYEE_ID};

    public Residence(String resStAddress, String resZipCode, String resName, String rMOStAddress, String rMOZipCode, String bMEmployeeID) {
        super(columnNames);

        Map<String, String> data = new HashMap<>();
        data.put(RES_NAME, resName);
        data.put(resStAddress, resStAddress);
        data.put(resZipCode, resZipCode);
        data.put(ResidentialManagingOffice.RMO_ST_ADDRESS, rMOStAddress);
        data.put(ResidentialManagingOffice.RMO_ZIP_CODE, rMOZipCode);
        data.put(BuildingManager.BM_EMPLOYEE_ID, bMEmployeeID);
        super.setData(data);
    }
}
