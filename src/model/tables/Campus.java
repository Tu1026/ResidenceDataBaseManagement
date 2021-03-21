package model.tables;

import java.util.HashMap;
import java.util.Map;

public final class Campus extends TableData {

    public static final String C_ST_ADDRESS = "cStAddress";
    public static final String C_ZIP_CODE = "cZipCode";
    public static final String NAME = "name";
    public static final String POPULATION = "population";
    private static final String [] columnNames = {C_ST_ADDRESS, C_ZIP_CODE, NAME, POPULATION};

    private final String cStAddress;
    private final String cZipCode;
    private final String name;
    private final String population;

    public Campus(String cStAddress, String cZipCode, String name, String population) {
        super(columnNames);
        this.cStAddress = cStAddress;
        this.cZipCode = cZipCode;
        this.name = name;
        this.population = population;

        Map<String, String> data = new HashMap<>();
        data.put(C_ST_ADDRESS, this.cStAddress);
        data.put(C_ZIP_CODE, this.cZipCode);
        data.put(NAME, this.name);
        data.put(POPULATION, this.population);
        super.setData(data);
    }

    public String getCStAddress() {
        return cStAddress;
    }

    public String getCZipCode() {
        return cZipCode;
    }

    public String getName() {
        return name;
    }

    public String getPopulation() {
        return population;
    }
}
