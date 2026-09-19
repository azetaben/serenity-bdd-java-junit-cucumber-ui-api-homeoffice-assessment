package homeoffice.utilities.tableutils;

import homeoffice.exceptions.TableRowDoesNotExistException;
import homeoffice.webelementdata.TableData;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.exceptions.NoSuchElementException;
import net.serenitybdd.core.pages.WebElementFacade;

import java.util.HashMap;
import java.util.Map;

public class InteractableTableWebElementFinderAndClicker {
    public static Map<String, Integer> deletedRowOnTablePageMap = new HashMap<>();
    private final TableData tableData;
    private final String pageName;
    private int rowNumber;

    public InteractableTableWebElementFinderAndClicker(TableData tableData, String pageName) {
        this.tableData = tableData;
        this.pageName = pageName;
    }

    public void findAndClickInteractableTableWebElement(String interactableElementId, int rowNumber) throws TableRowDoesNotExistException {
        this.rowNumber = rowNumber;
        WebElementFacade row = this.findRowForTable();
        WebElementFacade element = (WebElementFacade) row.thenFindAll(new By[]{(By) By.id(interactableElementId + Integer.toString(this.rowNumber + 1))}).stream().findFirst().orElseThrow(() -> new NoSuchElementException("Interactable table element was not found: " + interactableElementId));
        element.click();
    }

    private WebElementFacade findRowForTable() throws TableRowDoesNotExistException {
        this.checkIfAPreviousRowNumberSmallerThanGivenRowNumberHasBeenDeletedAndDecreaseRowNumberByOneAndAdjustIt();
        WebElementFacade row = this.tableData.getTableBodyRowWebElement(this.rowNumber);
        return row;
    }

    private void checkIfAPreviousRowNumberSmallerThanGivenRowNumberHasBeenDeletedAndDecreaseRowNumberByOneAndAdjustIt() {
        for (Map.Entry<String, Integer> entry : deletedRowOnTablePageMap.entrySet()) {
            this.adjustRowNumberByMinusOneIfThereIsAPreviousRowAlreadyDeleted(entry);
        }

    }

    private void adjustRowNumberByMinusOneIfThereIsAPreviousRowAlreadyDeleted(Map.Entry<String, Integer> entry) {
        if (this.isThereAPreviousRowNumberSmallerThanGivenRowNumberAndHasBeenDeleted(entry)) {
            --this.rowNumber;
        }

    }

    private boolean isThereAPreviousRowNumberSmallerThanGivenRowNumberAndHasBeenDeleted(Map.Entry<String, Integer> entry) {
        return ((String) entry.getKey()).equals(this.pageName) && (Integer) entry.getValue() < this.rowNumber;
    }
}
