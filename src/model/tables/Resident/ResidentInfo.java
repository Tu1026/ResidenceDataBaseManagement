package model.tables.Resident;

import model.tables.TableData;

import java.util.HashMap;
import java.util.Map;

public final class ResidentInfo extends TableData {


    public static final String STUDENT_NUMBER = "studentNumber";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String DOB = "dob";
    public static final String YEARS_IN_RESIDENCE = "yearsInResidence";
    private static final String [] columnNames = {STUDENT_NUMBER, EMAIL, NAME, DOB, YEARS_IN_RESIDENCE};


    private final String studentNumber;
    private final String email;
    private final String name;
    private final String dob;
    private final String yearsInResidence;


    public ResidentInfo(String studentNumber, String email, String name, String dob, String yearsInResidence) {
        super(columnNames);
        this.studentNumber = studentNumber;
        this.email = email;
        this.name = name;
        this.dob = dob;
        this.yearsInResidence = yearsInResidence;

        Map<String, String> data = new HashMap<>();
        data.put(STUDENT_NUMBER, this.studentNumber);
        data.put(EMAIL, this.email);
        data.put(NAME, this.name);
        data.put(DOB, this.dob);
        data.put(this.yearsInResidence, this.yearsInResidence);
        super.setData(data);
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getDob() {
        return dob;
    }

    public String getYearsInResidence() {
        return yearsInResidence;
    }
}
