package homeoffice.PageObject.general;


import homeoffice.enums.WebElements;
import homeoffice.exceptions.TableRowDoesNotExistException;

public interface InteractableTablePage extends TablePage {
    void clickTableElementOnRow(WebElements var1, int var2, String var3) throws TableRowDoesNotExistException;
}
