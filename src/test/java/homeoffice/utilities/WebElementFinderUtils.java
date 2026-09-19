package homeoffice.utilities;

import homeoffice.exceptions.TableRowDoesNotExistException;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.List;

public class WebElementFinderUtils {
    private static final PageObject PAGE = new PageObject() {
    };

    public static List<WebElementFacade> findAllChildElementsOfParentElementInActualOrder(String idOfParentElement) {
        WebElementFacade parentWebElement = PAGE.withDriver(ThucydidesWebDriverSupport.getDriver()).find(By.id(idOfParentElement));
        return parentWebElement.thenFindAll(new By[]{By.xpath(".//*")});
    }

    public static WebElementFacade findTableRow(String idOfTableBodyElement, int rowNumber) throws TableRowDoesNotExistException {
        List<WebElementFacade> rowsInActualOrder = findAllTableRows(idOfTableBodyElement);
        TableRowDoesNotExistException.checkIfTableRowExistsOrElseThrowException("The specified row does not exist within the Table.", rowsInActualOrder, rowNumber);
        return !rowsInActualOrder.isEmpty() ? (WebElementFacade) rowsInActualOrder.get(rowNumber) : null;
    }

    public static List<WebElementFacade> findAllTableRows(String idOfTableBodyElement) {
        List<WebElementFacade> childWebElementsInActualOrder = findAllChildElementsOfParentElementInActualOrder(idOfTableBodyElement);
        List<WebElementFacade> rowsInActualOrder = new ArrayList<>();

        for (WebElementFacade element : childWebElementsInActualOrder) {
            if (element.getTagName().equals("tr")) {
                rowsInActualOrder.add(element);
            }
        }

        return rowsInActualOrder;
    }

    public static List<WebElementFacade> findAllChildElementsOfParentElementInActualOrder(WebElementFacade parentWebElement) {
        List<WebElementFacade> childWebElementsInActualOrder = parentWebElement.thenFindAll(new By[]{By.xpath(".//*")});
        String tagName = ((WebElementFacade) childWebElementsInActualOrder.get(childWebElementsInActualOrder.size() - 1)).getText();
        return childWebElementsInActualOrder;
    }
}
