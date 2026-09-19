package homeoffice.actions;
;
import homeoffice.PageObject.general.ApplicationFlowPage;
import homeoffice.PageObject.general.ApplicationFlowPageImpl;
import homeoffice.exceptions.TableRowDoesNotExistException;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class GenericApplicationActions {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenericApplicationActions.class);

    public void loginWithDetails(String username, String password) {
        LOGGER.info("Logging in through the generic application page as [{}]", username);
        applicationFlowPage().loginDetail(username, password);
    }

    public void clickElement(String elementName) {
        LOGGER.info("Clicking generic application element [{}]", elementName);
        applicationFlowPage().click(elementName);
    }

    public void addProductToCart(String productName) {
        LOGGER.info("Adding product through the generic application page [{}]", productName);
        applicationFlowPage().addProductToCart(productName);
    }

    public void assertProductActionVisible(String actionName, String productName) {
        LOGGER.info("Checking [{}] action is visible for product [{}]", actionName, productName);
        applicationFlowPage().assertProductActionIsVisible(actionName, productName);
    }

    public void enterCheckoutInformation(String firstName, String lastName, String postalCode) {
        LOGGER.info("Entering checkout information through the generic application page");
        applicationFlowPage().personalCheckoutInformation(firstName, lastName, postalCode);
    }

    public void assertElementVisible(String elementName) {
        applicationFlowPage().assertElementIsDisplayed(elementName, true);
    }

    public void assertAcceptedUsersListContains(String username) {
        applicationFlowPage().assertAcceptedUsersListContains(username);
    }

    public void assertPageContains(List<String> expectedContent) {
        applicationFlowPage().assertPageContainsContent(expectedContent);
    }

    public void assertCartItemCount(int expectedCount) {
        applicationFlowPage().assertCartItemCount(expectedCount);
    }

    public void assertCartBadgeIconIsNotVisible() {
        applicationFlowPage().assertCartBadgeIconIsNotVisible();
    }

    public void assertButtonTitle(String expectedButtonTitle) {
        applicationFlowPage().assertCorrectButtonTitle(expectedButtonTitle);
    }

    public void assertLinkTitle(String expectedLinkTitle) {
        applicationFlowPage().assertCorrectLinkTitle(expectedLinkTitle);
    }

    public void assertFieldsAreEditable() {
        applicationFlowPage().assertFieldsAreEditable();
    }

    public void assertFieldsAreBlank() {
        applicationFlowPage().assertThatAllFieldsAreBlank();
    }

    public void assertTableBelowHeading() {
        applicationFlowPage().assertTableBelowHeading();
    }

    public void assertTableContents(Map<String, String> expectedContents) throws TableRowDoesNotExistException {
        applicationFlowPage().assertCorrectTableContents(expectedContents);
    }

    public void assertMessageBelowHeading(String expectedMessage) {
        applicationFlowPage().assertCorrectMessageBelowHeading(expectedMessage);
    }

    private ApplicationFlowPage applicationFlowPage() {
        WebDriver driver = ThucydidesWebDriverSupport.getDriver();
        if (driver == null) {
            throw new IllegalStateException("No WebDriver is registered. Run UI features with a Serenity Cucumber runner or navigate before using generic page steps.");
        }
        return new ApplicationFlowPageImpl().withDriver(driver);
    }
}
