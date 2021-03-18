package model.tables.Resident;

import model.tables.Residence.Residence;
import model.tables.TableData;

import java.util.HashMap;
import java.util.Map;

public class ResidentInfo extends TableData {


    public static final String STUDENT_NUMBER = "studentNumber";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String DOB = "dob";
    public static final String YEARS_IN_RESIDENCE = "yearsInResidence";
    private static final String [] columnNames = {STUDENT_NUMBER, EMAIL, NAME, DOB, YEARS_IN_RESIDENCE};

    public ResidentInfo(String studentNumber, String email, String name, String dob, String yearsInResidence) {
        super(columnNames);

        Map<String, String> data = new HashMap<>();
        data.put(STUDENT_NUMBER, studentNumber);
        data.put(EMAIL, email);
        data.put(NAME, name);
        data.put(DOB, dob);
        data.put(yearsInResidence, yearsInResidence);
        super.setData(data);
    }
}
