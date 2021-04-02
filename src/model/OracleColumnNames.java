
package model;

import java.util.*;

public class OracleColumnNames {

    public static List<String> ORACLE_COLUMN_NAMES = new ArrayList<>(Arrays.asList("CSTADDRESS", "CZIPCODE", "NAME", "POPULATION", // Campus
            "RMOSTADDRESS", "RMOZIPCODE", "BUDGET", // Residential Managing Office
            "BMEMPLOYEEID", "YEARSOFEXPERIENCE", "PHONENUMBER", // Building Manager
            "RAEMPLOYEEID", "INDIVIDUALBUDGET", "STUDENTNUMBER", "SRASTUDENTNUMBER", "SUPERVISINGFLOORNUM", "SUPERVISINGHOUSENAME",
            "SUPERVISINGRESSTADDRESS", "SUPERVISINGRESZIPCODE", // RA
            "SAEMPLOYEEID", "TEAMBUDGET", // SA
            "FNUMBER", "CAPACITY", "GENDERRESTRICTION", "HOUSENAME", "RESSTADDRESS", "RESZIPCODE", // Floor
            "TYPE", "AGERESTRICTION", // House
            "UNUMBER", "VACANCY", // Unit
            "EMAIL", "DOB", "YEARSINRESIDENCE", // Resident
            "RESNAME",// Residence
            "ADVISORAGE", "AVGEXPERIENCE", // ADVANCED -> GROUPBY
            "STUDENTSALONEINRES",  // Advanced -> NESTED
            "AVGAGE", "OLDEST", "NUMOFRESIDENTS", // ADVANCED -> JOIN
            "VACANCIES")); // ADVANCED -> HAVING
    public static List<String> PRETTY_COLUMN_NAMES = new ArrayList<>(Arrays.asList("Campus Address", "Campus Zip", "Name", "Population", // Campus
            "Office Address", "Office Zipcode", "Budget",  // Residential Managing Office
            "Manager ID", "Years of Experience", "Phone #", // Building Manager
            "RA ID", "Individual Budget", "Student #", "SA Student #", "Assigned Floor", "Assigned House",
            "House Address", "House Zipcode", // RA
            "SA ID", "Team Budget", // SA
            "Floor #", "Capacity", "Gender Restriction", "House Name", "Address", "Zipcode", // Floor
            "Type", "Min. Age", // House
            "Unit #", "Vacancy", // Unit
            "E-Mail", "Date of Birth", "Years in Residence",// Resident
            "Residence Name", // Residence
            "Advisor Age Group", "Average Experience", // ADVANCED -> GROUP BY
            "Students Alone in Res", // ADVANCED -> NESTED
            "Average Age", "Oldest Age", "# of Residents", // ADVANCED -> JOIN
            "Vacancies")); // ADVANCED -> HAVING

    public static Map<String, String> GET_PRETTY_COLUMN_NAMES = new HashMap<>();
    public static Map<String, String> GET_ORACLE_COLUMN_NAMES = new HashMap<>();

    public static void buildMaps() {
        for (int i = 0; i < ORACLE_COLUMN_NAMES.size(); i++) {
            GET_PRETTY_COLUMN_NAMES.put(
                    ORACLE_COLUMN_NAMES.get(i), PRETTY_COLUMN_NAMES.get(i));
            GET_ORACLE_COLUMN_NAMES.put(
                    PRETTY_COLUMN_NAMES.get(i), ORACLE_COLUMN_NAMES.get(i));
        }
    }
}