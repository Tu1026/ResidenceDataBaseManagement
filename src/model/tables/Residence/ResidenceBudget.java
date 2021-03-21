package model.tables.Residence;

import model.tables.TableData;

import java.util.HashMap;
import java.util.Map;

public final class ResidenceBudget extends TableData {


    public static final String BUDGET = "budget";

    private static final String [] columnNames = {Residence.RES_ST_ADRESS, Residence.RES_ZIP_CODE, BUDGET};


    private final String resStAddress;
    private final String resZipCode;
    private final String budget;

    public ResidenceBudget(String resStAddress, String resZipCode, String budget) {
        super(columnNames);
        this.resStAddress = resStAddress;
        this.resZipCode = resZipCode;
        this.budget = budget;

        Map<String, String> data = new HashMap<>();
        data.put(Residence.RES_ST_ADRESS, this.resStAddress);
        data.put(BUDGET, this.budget);
        data.put(Residence.RES_ZIP_CODE, this.resZipCode);
        super.setData(data);
    }

    public String getResStAddress() {
        return resStAddress;
    }

    public String getResZipCode() {
        return resZipCode;
    }

    public String getBudget() {
        return budget;
    }
}
