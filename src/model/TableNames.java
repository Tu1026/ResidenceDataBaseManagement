package model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TableNames {

    private static final String [] tableNames = {"CAMPUS", "RESIDENTIALMANAGINGOFFICE", "BUILDINGMANAGER", "RESIDENCEADVISOR",
    "RESIDENTADDRESS", "RESIDENTINFO", "SENIORADVISOR", "FLOOR", "HOUSE", "RESIDENCE", "RESIDENCEBUDGET",
    "RESIDENCECAPACITY", "UNIT"};

    public static Set<String> getTableNames(){
        return new HashSet<>(Arrays.asList(tableNames));
    }
}
