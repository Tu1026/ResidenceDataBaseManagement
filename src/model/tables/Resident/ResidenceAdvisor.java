package model.tables.Resident;

import model.tables.Residence.Floor;
import model.tables.Residence.House;
import model.tables.Residence.Residence;
import model.tables.TableData;

import java.util.HashMap;
import java.util.Map;

public final class ResidenceAdvisor extends TableData {

    public static final String RA_EMPLOYEE_ID = "rAEmployeeID";
    public static final String YEARS_OF_EXPERIENCE = "yearsOfExperience";
    public static final String INVIDIDUAL_BUDGET = "InvididualBudget";
    private static final String [] columnNames = {RA_EMPLOYEE_ID, YEARS_OF_EXPERIENCE, INVIDIDUAL_BUDGET, ResidentInfo.STUDENT_NUMBER,
            SeniorAdvisor.SA_EMPLOYEE_ID, Floor.F_NUMBER, House.NAME, Residence.RES_ST_ADRESS, Residence.RES_ZIP_CODE};
                                                //supervising floornumber, houseName, resStAddress, resZipCode



    private final String rAEmployeeID;
    private final String yearsOfExperience;
    private final String individualBudget;
    private final String studentNumber;
    private final String sAEmployeeID;
    private final String fNumber;
    private final String supervisingHouseName;
    private final String supervisingResStAddress;
    private final String supervisingResZipCode;

    public ResidenceAdvisor(String rAEmployeeID, String yearsOfExperience, String individualBudget, String studentNumber,
                            String sAEmployeeID, String fNumber, String supervisingHouseName,
                            String supervisingResStAddress, String supervisingResZipCode) {
        super(columnNames);
        this.rAEmployeeID = rAEmployeeID;
        this.yearsOfExperience = yearsOfExperience;
        this.individualBudget = individualBudget;
        this.studentNumber = studentNumber;
        this.sAEmployeeID = sAEmployeeID;
        this.fNumber = fNumber;
        this.supervisingHouseName = supervisingHouseName;
        this.supervisingResStAddress = supervisingResStAddress;
        this.supervisingResZipCode = supervisingResZipCode;

        Map<String, String> data = new HashMap<>();
        data.put(RA_EMPLOYEE_ID, this.rAEmployeeID);
        data.put(YEARS_OF_EXPERIENCE, this.yearsOfExperience);
        data.put(INVIDIDUAL_BUDGET, this.individualBudget);
        data.put(ResidentInfo.STUDENT_NUMBER, this.studentNumber);
        data.put(SeniorAdvisor.SA_EMPLOYEE_ID, this.sAEmployeeID);
        data.put(Floor.F_NUMBER, this.fNumber);
        data.put(House.NAME, this.supervisingHouseName);
        data.put(Residence.RES_ST_ADRESS, this.supervisingResStAddress);
        data.put(Residence.RES_ZIP_CODE, this.supervisingResZipCode);
        super.setData(data);
    }


    public String getRAEmployeeID() {
        return rAEmployeeID;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getIndividualBudget() {
        return individualBudget;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getSAEmployeeID() {
        return sAEmployeeID;
    }

    public String getFNumber() {
        return fNumber;
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
