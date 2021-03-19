package handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class SQLParser {

    /**
     * Parses specified file as SQL DDL file
     * Ignores comments, removes spaces, newline characters and tabs
     * @param file file containing the location of the desired SQL DDL
     * @return ArrayList of Strings containing the parsed statements. Every entry is a statement
     */
    public ArrayList<String> parseSQLDDL(File file) {
        ArrayList<String> statements = new ArrayList<>();
        String delim = ";";

        try (Scanner scanner = new Scanner(file)) {
            StringBuilder sqlStatementString = new StringBuilder();
            while (scanner.hasNextLine()) {
                String nextLine = scanner.nextLine().trim();
                if (lineHasDataNotAComment(nextLine)) {
                    sqlStatementString.append(nextLine.replaceAll("\\s+", " ")); // replace all whitespace chars by single space
                }
                if (sqlStatementString.toString().contains(delim)) {
                    statements.add(sqlStatementString.toString().replaceAll(delim, ""));
                    sqlStatementString = new StringBuilder();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return statements;
    }

    private boolean lineHasDataNotAComment(String line) {
        return !line.equals("") && !(line.contains("--"));
    }
}
