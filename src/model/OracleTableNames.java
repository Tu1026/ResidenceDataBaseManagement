package model;

import java.util.*;

public class OracleTableNames {

    public static final Set<String> TABLE_NAMES = new HashSet<>(Arrays.asList("CAMPUS", "RESIDENTIALMANAGINGOFFICE", "BUILDINGMANAGER", "RESIDENCEADVISOR",
            "RESIDENTADDRESS", "RESIDENTINFO", "SENIORADVISOR", "FLOOR", "HOUSE", "RESIDENCE", "RESIDENCEBUDGET",
            "RESIDENCECAPACITY", "UNIT")); // names should correspond with tableNames present in DDL.sql
}
