package model.tables.Resident;

import model.tables.Residence.Floor;
import model.tables.Residence.House;
import model.tables.Residence.Residence;
import model.tables.Residence.Unit;
import model.tables.TableData;

import java.util.HashMap;
import java.util.Map;

public final class ResidentAddress extends TableData {

    private static final String [] columnNames = {ResidentInfo.EMAIL, Unit.U_NUMBER, Floor.F_NUMBER,
            House.NAME, Residence.RES_ST_ADRESS, Residence.RES_ZIP_CODE};


    private final String email;
    private final String uNumber;
    private final String fNumber;
    private final String houseName;
    private final String resStAddress;
    private final String resZipCode;

    public ResidentAddress(String email, String uNumber, String fNumber, String houseName, String resStAddress, String resZipCode) {
        super(columnNames);
        this.email = email;
        this.uNumber = uNumber;
        this.fNumber = fNumber;
        this.houseName = houseName;
        this.resStAddress = resStAddress;
        this.resZipCode = resZipCode;

        Map<String, String> data = new HashMap<>();
        data.put(ResidentInfo.EMAIL, this.email);
        data.put(Unit.U_NUMBER, this.uNumber);
        data.put(Floor.F_NUMBER, this.fNumber);
        data.put(House.NAME, this.houseName);
        data.put(Residence.RES_ST_ADRESS, this.resStAddress);
        data.put(Residence.RES_ZIP_CODE, this.resZipCode);
        super.setData(data);
    }

    public String getEmail() {
        return email;
    }

    public String getUNumber() {
        return uNumber;
    }

    public String getFNumber() {
        return fNumber;
    }

    public String getHouseName() {
        return houseName;
    }

    public String getResStAddress() {
        return resStAddress;
    }

    public String getResZipCode() {
        return resZipCode;
    }
}
