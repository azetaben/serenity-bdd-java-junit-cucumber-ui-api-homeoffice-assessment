package homeoffice.webelementdata;


import homeoffice.exceptions.TableRowDoesNotExistException;
import homeoffice.utilities.WebElementFinderUtils;
import net.serenitybdd.core.pages.WebElementFacade;

import java.util.ArrayList;
import java.util.List;

public class TableData {
    private WebElementFacade tableHeadersRow;
    private WebElementFacade tableBody;
    private List<String> expectedOrderOfHeaderIds;
    private List<String> expectedHeaderTitles;
    private List<String> actualHeaderTitles = new ArrayList();
    private List<String> expectedTableBodyContent;
    private List<String> expectedOrderOfTableBodyContentIds;
    private List<String> actualTableBodyContent = new ArrayList();
    private List<WebElementFacade> tableRows = new ArrayList();
    private List<List<String>> tableContentWithinAllRows = new ArrayList();

    public TableData(WebElementFacade tableHeadersRow, WebElementFacade tableBody, List<String> expectedOrderOfHeaderIds, List<String> expectedHeaderTitles, List<String> expectedTableBodyContent, List<String> expectedOrderOfTableBodyContentIds) {
        this.tableBody = tableBody;
        this.tableHeadersRow = tableHeadersRow;
        this.expectedOrderOfHeaderIds = expectedOrderOfHeaderIds;
        this.expectedHeaderTitles = expectedHeaderTitles;
        this.expectedTableBodyContent = expectedTableBodyContent;
        this.expectedOrderOfTableBodyContentIds = expectedOrderOfTableBodyContentIds;
    }

    public TableData(WebElementFacade tableBody) {
        this.tableBody = tableBody;
    }

    public List<String> getActualTableBodyContentForRow(int rowNumber) throws TableRowDoesNotExistException {
        if (this.actualTableBodyContent.isEmpty()) {
            for (WebElementFacade element : WebElementFinderUtils.findAllChildElementsOfParentElementInActualOrder(this.getTableBodyRowWebElement(rowNumber))) {
                if (this.childElementIsACellWithText(element)) {
                    this.actualTableBodyContent.add(element.getText());
                }
            }
        }

        return this.actualTableBodyContent;
    }

    public WebElementFacade getTableBodyRowWebElement(int rowNumber) throws TableRowDoesNotExistException {
        return WebElementFinderUtils.findTableRow(this.getTableBodyId(), rowNumber);
    }

    private boolean childElementIsACellWithText(WebElementFacade element) {
        return element.getText() != null && this.childElementIsACell(element);
    }

    private boolean childElementIsACell(WebElementFacade element) {
        return element.getTagName().equals("th") || element.getTagName().equals("td");
    }

    public String getTableBodyId() {
        return this.tableBody.getAttribute("id");
    }

    public List<String> getActualHeaderTitles() {
        if (this.actualHeaderTitles.isEmpty()) {
            for (WebElementFacade element : WebElementFinderUtils.findAllChildElementsOfParentElementInActualOrder(this.getTableHeadersRowId())) {
                if (element.getText() != null) {
                    this.actualHeaderTitles.add(element.getText());
                }
            }
        }

        return this.actualHeaderTitles;
    }

    public String getTableHeadersRowId() {
        return this.tableHeadersRow.getAttribute("id");
    }

    public List<String> getExpectedTableBodyContent() {
        return this.expectedTableBodyContent;
    }

    public List<String> getExpectedOrderOfHeaderIds() {
        return this.expectedOrderOfHeaderIds;
    }

    public List<String> getExpectedHeaderTitles() {
        return this.expectedHeaderTitles;
    }

    public List<String> getExpectedOrderOfTableBodyContentIds() {
        return this.expectedOrderOfTableBodyContentIds;
    }

    public List<WebElementFacade> getAllActualTableCells() {
        List<WebElementFacade> rows = this.getAllActualTableRows();
        List<WebElementFacade> rowCells = new ArrayList();

        for (WebElementFacade element : rows) {
            if (this.childElementIsACell(element)) {
                rowCells.add(element);
            }
        }

        return rowCells;
    }

    public List<WebElementFacade> getAllActualTableRows() {
        if (this.tableRows.isEmpty()) {
            this.tableRows = WebElementFinderUtils.findAllTableRows(this.getTableBodyId());
        }

        return this.tableRows;
    }

    public List<List<String>> getActualTableBodyContentForAllRows() throws TableRowDoesNotExistException {
        if (this.tableContentWithinAllRows.isEmpty()) {
            for (int i = 0; i < this.getAllActualTableRows().size(); ++i) {
                this.tableContentWithinAllRows.add(this.getActualTableBodyContentForRow(i));
            }
        }

        return this.tableContentWithinAllRows;
    }
}
