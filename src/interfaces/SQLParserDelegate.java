package interfaces;

import java.io.File;
import java.util.List;

public interface SQLParserDelegate {

    /**
     * Parses specified file as SQL DDL file
     * Ignores comments, removes spaces, newline characters and tabs
     * @param file file containing the location of the desired SQL DDL
     * @return List of Strings containing the parsed statements. Every entry is a statement
     */
    List<String> parseDDL(File file);

    /**
     * Parses specified file as SQL DML file with only insert statements
     * Ignores comments, removes spaces, newline characters and tabs
     * @param file file containing the location of the desired SQL DDL
     * @return List of Strings containing the parsed statements. Every entry is a statement
     */
    List<String> parseDMLInsertStatement(File file);

}
