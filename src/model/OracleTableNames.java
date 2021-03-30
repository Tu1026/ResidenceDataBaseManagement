
package model;

import java.util.*;

public class OracleTableNames {

    public static final List<String> TABLE_NAMES = new ArrayList<>(Arrays.asList("CAMPUS", "RESIDENTIALMANAGINGOFFICE", "BUILDINGMANAGER", "RESIDENCEADVISOR",
            "SENIORADVISOR", "FLOOR", "HOUSE", "UNIT", "RESIDENCE", "RESIDENCECAPACITY", "RESIDENCEBUDGET",
            "RESIDENTINFO", "RESIDENTADDRESS")); // names should correspond with tableNames present in DDL old.sql

    public static final List<String> PRETTY_NAMES = new ArrayList<>(Arrays.asList("Campus", "Managing Office", "Building Manager", "Residence Advisor",
            "Senior Advisor", "Floor", "House","Unit"));

    public static final Map<String, String> GET_ORACLE_NAME = new HashMap<>();
    public static final Map<String, String> GET_PRETTY_NAME = new HashMap<>();

    public static Map<String, String []> GET_COMPOUND_TABLES = new HashMap<>();

    public static void buildMaps(){
        for (int i = 0; i < PRETTY_NAMES.size(); i++){
            GET_ORACLE_NAME.put(PRETTY_NAMES.get(i), TABLE_NAMES.get(i));
            GET_PRETTY_NAME.put(TABLE_NAMES.get(i), PRETTY_NAMES.get(i));
        }

        PRETTY_NAMES.add("Resident");
        PRETTY_NAMES.add("Residence");

        String [] resident = {"RESIDENTINFO", "RESIDENTADDRESS"};
        String [] residence = {"RESIDENCE", "RESIDENCEBUDGET", "RESIDENCECAPACITY"};

        GET_COMPOUND_TABLES.put("Resident", resident);
        GET_COMPOUND_TABLES.put("Residence", residence);
    }
}