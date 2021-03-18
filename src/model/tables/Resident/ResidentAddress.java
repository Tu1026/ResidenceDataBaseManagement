package model.tables.Resident;

import model.tables.Residence.Floor;
import model.tables.Residence.House;
import model.tables.Residence.Residence;
import model.tables.Residence.Unit;
import model.tables.TableData;

import java.util.HashMap;
import java.util.Map;

public class ResidentAddress extends TableData {

    private static final String [] columnNames = {ResidentInfo.EMAIL, Unit.U_NUMBER, Floor.F_NUMBER, House.NAME, Residence.RES_ST_ADRESS, Residence.RES_ZIP_CODE};

    public ResidentAddress(String email, String uNumber, String fNumber, String houseName, String resStAddress, String resZipCode) {
        super(columnNames);

        Map<String, String> data = new HashMap<>();
        data.put(ResidentInfo.EMAIL, email);
        data.put(Unit.U_NUMBER, uNumber);
        data.put(Floor.F_NUMBER, fNumber);
        data.put(House.NAME, houseName);
        data.put(Residence.RES_ST_ADRESS, resStAddress);
        data.put(Residence.RES_ZIP_CODE, resZipCode);
        super.setData(data);
    }
}
