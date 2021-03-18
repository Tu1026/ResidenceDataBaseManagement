package model.tables.Residence;

import model.TableData;
import model.tables.BuildingManager;

import java.util.HashMap;
import java.util.Map;

public class ResidenceBudget extends TableData {


    public static final String BUDGET = "budget";

    private static final String [] columnNames = {Residence.RES_ST_ADRESS, Residence.RES_ZIP_CODE, BUDGET};

    public ResidenceBudget(String resStAddress, String resZipCode, String budget) {
        super(columnNames);

        Map<String, String> data = new HashMap<>();
        data.put(Residence.RES_ST_ADRESS, resStAddress);
        data.put(BUDGET, budget);
        data.put(Residence.RES_ZIP_CODE, resZipCode);
        super.setData(data);
    }
}
