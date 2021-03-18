package model.tables.Resident;

import model.tables.Residence.Floor;
import model.tables.Residence.House;
import model.tables.Residence.Residence;
import model.tables.TableData;

import java.util.HashMap;
import java.util.Map;

public class ResidenceAdvisor extends TableData {

    public static final String RA_EMPLOYEE_ID = "rAEmployeeID";
    public static final String YEARS_OF_EXPERIENCE = "yearsOfExperience";
    public static final String INVIDIDUAL_BUDGET = "InvididualBudget";
    private static final String [] columnNames = {RA_EMPLOYEE_ID, YEARS_OF_EXPERIENCE, INVIDIDUAL_BUDGET, ResidentInfo.STUDENT_NUMBER,
            SeniorAdvisor.SA_EMPLOYEE_ID, Floor.F_NUMBER, House.NAME, Residence.RES_ST_ADRESS, Residence.RES_ZIP_CODE};
                                                //supervising floornumber, houseName, resStAddress, resZipCode

    public ResidenceAdvisor(String rAEmployeeID, String yearsOfExperience, String individualBudget, String studentNumber,
                            String sAEmployeeID, String fNumber, String supervisingHouseName, String supervisingResStAddress, String supervisingResZipCode) {
        super(columnNames);

        Map<String, String> data = new HashMap<>();
        data.put(RA_EMPLOYEE_ID, rAEmployeeID);
        data.put(YEARS_OF_EXPERIENCE, yearsOfExperience);
        data.put(INVIDIDUAL_BUDGET, individualBudget);
        data.put(ResidentInfo.STUDENT_NUMBER, studentNumber);
        data.put(SeniorAdvisor.SA_EMPLOYEE_ID, sAEmployeeID);
        data.put(Floor.F_NUMBER, fNumber);
        data.put(House.NAME, supervisingHouseName);
        data.put(Residence.RES_ST_ADRESS, supervisingResStAddress);
        data.put(Residence.RES_ZIP_CODE, supervisingResZipCode);
        super.setData(data);
    }
}
