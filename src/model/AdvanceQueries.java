package model;

public enum AdvanceQueries {
    GROUPBY ("The average years of experience of RAs & SRAs per age group"),
    NESTED ("For each house, the number of residents who live alone and have been in the residence the longest"),
    JOIN ("The # of student in each house who are older than <X>, their avg age, and the age of the oldest student"),
    DIVISION("The house that has all the units with capacity of 5"),
    HAVING("The floors in houses that have more than <X> vacancies"),
    GROUPBYEXPLAIN("RAs, residence advisors, or SRA, senior residence advisors, are employees who work in the residence to support the residents. They could be long time employees who have " +
            "worked for many years in the residence before but they could also be brand new employees that have never worked in residence before. " +
            "Therefore, you can run this query to see for each of the age group what is the average years of experience among all the advisors"),
    NESTEDEXPLAIN(""),
    JOINEXPLAIN("Students of different age are all able to live in residence. Using this query you can find of all the students older than <X> what is the average age and the oldest age in each house"),
    DIVISIONEXPLAIN("Different units have different capacity. Using this query you can find the house that has 'all' of the units with capacity 5 and no other house has a unit with a capacity of 5"),
    HAVINGEXPLAIN("A unit can have vacancies if it can still allow new residents to move in. This query will show you how all the floors that have more than <X> frequency in a house");

    private final String value;

    AdvanceQueries(String value) {
        this.value = value;
    }

    public String getText() {
        return this.value;
    }

    public static AdvanceQueries getEnum(String text){
        for (AdvanceQueries enums: AdvanceQueries.values()) {
            if (enums.getText().equals(text)) {
                return enums;
            }
        }
        return null;
    }
}
