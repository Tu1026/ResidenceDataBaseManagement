package handler;

import interfaces.SQLParserDelegate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SQLParser implements SQLParserDelegate {

    private static final String END_DDL_SCRIPT_STR =  "----- END DDL SCRIPT";
    private static final String SEMICOLON_DELIM = ";";


    /**
     * Parses specified file as SQL DDL file
     * Ignores comments, removes spaces, newline characters and tabs
     * @param file file containing the location of the desired SQL DDL
     * @return ArrayList of Strings containing the parsed statements. Every entry is a statement
     */
    public List<String> parseDDL(File file) {
        List<String> statements = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            statements = buildArrayList(scanner, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return statements;
    }

    /**
     * Parses specified file as SQL DML file with
     * Ignores comments, removes spaces, newline characters and tabs
     * Searches for string specified END_DDL_SCRIPT_STR. If string is not present, no data will be added
     * @param file file containing the location of the desired SQL DDL
     * @return ArrayList of Strings containing the parsed statements. Every entry is a statement
     */
    public List<String> parseDMLInsertStatement(File file){
        List<String> statements = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            loopUntil_END_DDL_SCRIPT_STR_found(scanner);
            statements = buildArrayList(scanner, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return statements;
    }

    // Parses scanner input, returns arraylist of data
    // each statement should be separated by a semicolon
    // whitespaces, tabs, and newlines are condensed to a single whitespace char
    private List<String> buildArrayList(Scanner scanner, boolean breakOnCondition) {
        StringBuilder sqlStatementString = new StringBuilder();
        List<String> statements = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine().trim();
            if (breakOnCondition && nextLine.equals(END_DDL_SCRIPT_STR)) {
                break;
            }

            if (lineHasDataNotAComment(nextLine)) {
                sqlStatementString.append(condenseWhitespace(nextLine)); // replace all whitespace chars by single space
            }
            if (sqlStatementString.toString().contains(SEMICOLON_DELIM)) {
                String statement = sqlStatementString.toString();
                if (!breakOnCondition) {
                    statement = statement.replace("VALUES",  " VALUES");
                }
                statements.add(removeSemicolons(statement));
                sqlStatementString.setLength(0); // clear string
            }
        }

        return statements;
    }

    private String removeSemicolons(String str) {
        return str.replaceAll(SEMICOLON_DELIM, "");
    }

    private String condenseWhitespace(String str) {
        return str.replaceAll("\\s+", " ");
    }

    private void loopUntil_END_DDL_SCRIPT_STR_found(Scanner scanner) {
        while (scanner.hasNextLine()){
            if (scanner.nextLine().trim().equals(END_DDL_SCRIPT_STR)) {
                break;
            }
        }
    }

    private boolean lineHasDataNotAComment(String line) {
        return !line.equals("") && !(line.contains("--"));
    }
}
