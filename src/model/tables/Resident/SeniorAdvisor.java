package model.tables.Resident;

import model.tables.Residence.House;
import model.tables.Residence.Residence;
import model.tables.TableData;

import java.util.HashMap;
import java.util.Map;

public final class SeniorAdvisor extends TableData {
    public static final String SA_EMPLOYEE_ID = "sAEmployeeID";
    public static final String YEARS_OF_EXPERIENCE = "yearsOfExperience";
    public static final String TEAM_BUDGET = "teamBudget";
    private static final String [] columnNames = {SA_EMPLOYEE_ID, YEARS_OF_EXPERIENCE, TEAM_BUDGET, ResidentInfo.STUDENT_NUMBER,
            House.NAME, Residence.RES_ST_ADRESS, Residence.RES_ZIP_CODE};
            // supervisingHouseName

    private final String sAEmployeeID;
    private final String yearsOfExperience;
    private final String teamBudget;
    private final String studentNumber;
    private final String supervisingHouseName;
    private final String supervisingResStAddress;
    private final String supervisingResZipCode;

    public SeniorAdvisor(String sAEmployeeID, String yearsOfExperience,
                         String teamBudget, String studentNumber, String supervisingHouseName,
                         String supervisingResStAddress, String supervisingResZipCode) {
        super(columnNames);
        this.sAEmployeeID = sAEmployeeID;
        this.yearsOfExperience = yearsOfExperience;
        this.teamBudget = teamBudget;
        this.studentNumber = studentNumber;
        this.supervisingHouseName = supervisingHouseName;
        this.supervisingResStAddress = supervisingResStAddress;
        this.supervisingResZipCode = supervisingResZipCode;

        Map<String, String> data = new HashMap<>();
        data.put(SA_EMPLOYEE_ID, this.sAEmployeeID);
        data.put(YEARS_OF_EXPERIENCE, this.yearsOfExperience);
        data.put(TEAM_BUDGET, this.teamBudget);
        data.put(ResidentInfo.STUDENT_NUMBER, this.studentNumber);
        data.put(House.NAME, this.supervisingHouseName);
        data.put(Residence.RES_ST_ADRESS, this.supervisingResStAddress);
        data.put(Residence.RES_ZIP_CODE, this.supervisingResZipCode);
        super.setData(data);
    }

    public String getsAEmployeeID() {
        return sAEmployeeID;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getTeamBudget() {
        return teamBudget;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getSupervisingHouseName() {
        return supervisingHouseName;
    }

    public String getSupervisingResStAddress() {
        return supervisingResStAddress;
    }

    public String getSupervisingResZipCode() {
        return supervisingResZipCode;
    }
}
