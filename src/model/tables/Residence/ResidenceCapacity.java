package model.tables.Residence;

import model.tables.BuildingManager;
import model.tables.TableData;

import java.util.HashMap;
import java.util.Map;

public class ResidenceCapacity extends TableData {


    public static final String CAPACITY = "Capacity";

    private static final String [] columnNames = {Residence.RES_NAME, BuildingManager.BM_EMPLOYEE_ID, CAPACITY};


    private final String resName;
    private final String bMEMployeeID;
    private final String capacity;

    public ResidenceCapacity(String resName, String bMEMployeeID, String capacity) {
        super(columnNames);
        this.resName = resName;
        this.bMEMployeeID = bMEMployeeID;
        this.capacity = capacity;

        Map<String, String> data = new HashMap<>();
        data.put(Residence.RES_NAME, this.resName);
        data.put(CAPACITY, this.capacity);
        data.put(BuildingManager.BM_EMPLOYEE_ID, this.bMEMployeeID);
        super.setData(data);
    }

    public String getResName() {
        return resName;
    }

    public String getBMEMployeeID() {
        return bMEMployeeID;
    }

    public String getCapacity() {
        return capacity;
    }
}
