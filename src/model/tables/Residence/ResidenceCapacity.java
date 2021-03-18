package model.tables.Residence;

import model.tables.BuildingManager;
import model.tables.TableData;

import java.util.HashMap;
import java.util.Map;

public class ResidenceCapacity extends TableData {


    public static final String CAPACITY = "Capacity";

    private static final String [] columnNames = {Residence.RES_NAME, BuildingManager.BM_EMPLOYEE_ID, CAPACITY};

    public ResidenceCapacity(String resName, String bMEMployeeID, String capacity) {
        super(columnNames);

        Map<String, String> data = new HashMap<>();
        data.put(Residence.RES_NAME, resName);
        data.put(CAPACITY, capacity);
        data.put(BuildingManager.BM_EMPLOYEE_ID, bMEMployeeID);
        super.setData(data);
    }
}
