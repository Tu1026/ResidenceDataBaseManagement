package model.tables.Resident;

import model.tables.Residence.House;
import model.tables.Residence.Residence;
import model.tables.TableData;

import java.util.HashMap;
import java.util.Map;

public class SeniorAdvisor extends TableData {


    public static final String SA_EMPLOYEE_ID = "sAEmployeeID";
    public static final String YEARS_OF_EXPERIENCE = "yearsOfExperience";
    public static final String TEAM_BUDGET = "teamBudget";
    private static final String [] columnNames = {SA_EMPLOYEE_ID, YEARS_OF_EXPERIENCE, TEAM_BUDGET, ResidentInfo.STUDENT_NUMBER,
            House.NAME, Residence.RES_ST_ADRESS, Residence.RES_ZIP_CODE};
            // supervisingHouseName

    public SeniorAdvisor(String sAEmployeeID, String yearsOfExperience, String teamBudget, String studentNumber,
                         String supervisingHouseName, String supervisingResStAddress, String supervisingResZipCode) {
        super(columnNames);

        Map<String, String> data = new HashMap<>();
        data.put(SA_EMPLOYEE_ID, sAEmployeeID);
        data.put(YEARS_OF_EXPERIENCE, yearsOfExperience);
        data.put(TEAM_BUDGET, teamBudget);
        data.put(ResidentInfo.STUDENT_NUMBER, studentNumber);
        data.put(House.NAME, supervisingHouseName);
        data.put(Residence.RES_ST_ADRESS, supervisingResStAddress);
        data.put(Residence.RES_ZIP_CODE, supervisingResZipCode);
        super.setData(data);
    }
}
