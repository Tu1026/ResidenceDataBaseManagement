package sql;

import model.AdvanceQueries;

import java.util.HashMap;
import java.util.Map;

import static model.AdvanceQueries.*;

public class AdvancedQueryList {
    public static final Map<AdvanceQueries, String> QUERY_MAP = new HashMap<>();

    public static void buildMap(){
        QUERY_MAP.put(GROUPBY, "select round(months_between(sysdate,to_date(dob,'MM-DD-YYYY'))/12) advisorAge, AVG(YEARSOFEXPERIENCE) avgExperience" +
                " from RESIDENTINFO NATURAL JOIN RESIDENCEADVISOR" +
                " WHERE DOB IS NOT NULL AND YEARSOFEXPERIENCE is not null" +
                " GROUP BY (round(months_between(sysdate,to_date(dob,'MM-DD-YYYY'))/12))" +
                " UNION" +
                " SELECT round(months_between(sysdate,to_date(dob,'MM-DD-YYYY'))/12) age, AVG(YEARSOFEXPERIENCE) avgExperience" +
                " from RESIDENTINFO  NATURAL JOIN SENIORADVISOR" +
                " WHERE DOB IS NOT NULL AND YEARSOFEXPERIENCE is not null" +
                " GROUP BY (round(months_between(sysdate,to_date(dob,'MM-DD-YYYY'))/12))" +
                " ORDER BY advisorAge");



        QUERY_MAP.put(NESTED, "SELECT HOUSENAME, COUNT(*) as StudentsAloneInRes, YEARSINRESIDENCE" +
                " FROM RESIDENTINFO ri NATURAL JOIN  RESIDENTADDRESS ra" +
                " WHERE YEARSINRESIDENCE IS NOT NULL AND NOT EXISTS (SELECT *" +
                " FROM UNIT u, RESIDENTINFO ri2 NATURAL JOIN RESIDENTADDRESS ra2" +
                " WHERE ri.STUDENTNUMBER <> ri2.STUDENTNUMBER AND" +
                " ra.UNUMBER = ra2.UNUMBER" +
                " AND ra.FNUMBER = ra2.FNUMBER" +
                " AND ra.HOUSENAME = ra2.HOUSENAME" +
                " AND ra.RESZIPCODE = ra2.RESZIPCODE" +
                " AND ra.RESSTADDRESS = ra2.RESSTADDRESS)" +
                " GROUP BY (HOUSENAME, YEARSINRESIDENCE)" +
                " HAVING (YEARSINRESIDENCE >= ALL (SELECT YEARSINRESIDENCE" +
                " FROM RESIDENTADDRESS NATURAL JOIN RESIDENTINFO NATURAL JOIN HOUSE WHERE RESIDENTINFO.YEARSINRESIDENCE IS NOT NULL AND ra.HOUSENAME = HOUSENAME))");

        QUERY_MAP.put(JOIN, "SELECT HOUSENAME, COUNT(*) as numOfResidents, round(AVG(round(months_between(sysdate,to_date(dob,'MM-DD-YYYY'))/12)), 2) as AvgAge," +
                " round(MAX(round(months_between(sysdate,to_date(dob,'MM-DD-YYYY'))/12)), 2) as Oldest" +
                " FROM RESIDENTINFO NATURAL JOIN RESIDENTADDRESS NATURAL JOIN HOUSE" +
                " WHERE (round(months_between(sysdate,to_date(dob,'MM-DD-YYYY'))/12)) > ? AND dob IS NOT NULL" +
                " GROUP BY HOUSENAME");

        QUERY_MAP.put(DIVISION, "SELECT *" +
                "FROM HOUSE h" +
                " WHERE NOT EXISTS(SELECT *" +
                " FROM UNIT u" +
                " WHERE (u.CAPACITY = 5)" +
                " AND NOT EXISTS (SELECT *" +
                " FROM HOUSE h2" +
                " WHERE h.HOUSENAME = u.HOUSENAME " +
                " AND u.HOUSENAME = h2.HOUSENAME))");

        QUERY_MAP.put(HAVING, "SELECT HOUSENAME, FNUMBER, SUM(VACANCY) as Vacancies" +
                " FROM UNIT" +
                " GROUP BY FNUMBER, HOUSENAME" +
                " HAVING SUM(VACANCY) > ?");
    }
}
