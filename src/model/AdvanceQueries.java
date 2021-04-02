package model;

public enum AdvanceQueries {
    GROUPBY ("The average years of experience of RAs & SRAs per age group"),
    NESTED ("The number of students who are living on their own and have been in residence the longest in a house"),
    JOIN ("The # of student in each house who are older than <X>, their avg age, and the age of the oldest student"),
    DIVISION("The house that has all the units with capacity of 5"),
    HAVING("The floors in houses that have more than <X> vacancies");

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
