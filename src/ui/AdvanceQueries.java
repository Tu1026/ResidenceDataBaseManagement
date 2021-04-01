package ui;

public enum AdvanceQueries {
    GROUPBY ("Find the average years of experience of RAs/SRAs per age group: Can this be turned into a nested aggregation instead?"),
    NESTED ("Select the Name of each house, num of students living alone, and the years they have been living in res for all students who live alone and have been in that res at least as long as anyone else"),
    JOIN ("Select # of student in each house who are older than , their avg age, and the age of the oldest student"),
    DIVISION("Find the house the has all the units that have a capacity of 5"),
    HAVING("Find floors in houses that have more than 3 vacancies");

    private final String value;

    AdvanceQueries(String value) {
        this.value = value;
    }


}
