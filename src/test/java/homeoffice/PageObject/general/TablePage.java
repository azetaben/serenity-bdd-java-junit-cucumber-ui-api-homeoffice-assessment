package homeoffice.PageObject.general;

import homeoffice.enums.WebElements;
import homeoffice.exceptions.TableRowDoesNotExistException;

import java.util.Map;

public interface TablePage {
    void assertTableHasElementInEveryRow(WebElements var1);

    void assertCorrectTableContents(Map<String, String> var1) throws TableRowDoesNotExistException;
}
