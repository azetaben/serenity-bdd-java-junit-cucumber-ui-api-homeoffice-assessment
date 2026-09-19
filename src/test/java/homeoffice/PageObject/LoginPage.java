package homeoffice.PageObject;

import homeoffice.utilities.RobotHelper;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoginPage extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginPage.class);

    private static final String BASE_URL = "https://www.saucedemo.com";
    InventoryPage inventoryPage;

    private static final String USERNAME_TEXT_BOX = "#user-name";
    private static final String PASSWORD_TEXT_BOX = "#password";
    private static final String LOGIN_BUTTON = "#login-button";
    private static final String LOGIN_LOGO = ".login_logo";
    private static final String LOGIN_FORM = "#login_button_container";
    private static final String LOGIN_ERROR = "[data-test='error']";
    private static final String ACCEPTED_USERNAMES = "#login_credentials";
    private static final String LOGIN_PASSWORD = "[data-test='login-password']";
    private static final String PRODUCTS_TITLE = "[data-test='title'], .title";
    private static final String INVENTORY_ITEMS = "[data-test='inventory-item'], .inventory_item";

    public void launchSauceDemo() {
        LOGGER.info("Opening SauceDemo login page");
        openUrl(BASE_URL + "/");
    }

    public void openInventoryPageDirectly() {
        LOGGER.info("Opening SauceDemo inventory page directly");
        openUrl(BASE_URL + "/inventory.html");
    }

    public void loginWithCredentials(String username, String password) {
        LOGGER.info("Logging in to SauceDemo as [{}]", username);
        waitForLoginFormReady();
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        RobotHelper.dismissPasswordSavePrompt();

    }

    public void enterUsername(String username) {
        waitAndType(USERNAME_TEXT_BOX, username);
    }

    public void enterPassword(String password) {
        waitAndType(PASSWORD_TEXT_BOX, password);
    }

    public void clickLogin() {
        waitAndClick(LOGIN_BUTTON);
    }

    public void shouldDisplayLoginForm() {
        LOGGER.info("Validating SauceDemo login form is present and visible");
        waitForLoginFormReady();
    }

    private void waitForLoginFormReady() {
        waitForElementVisible(LOGIN_LOGO);
        waitForElementVisible(LOGIN_FORM);
        waitForElementVisible(USERNAME_TEXT_BOX);
        waitForElementVisible(PASSWORD_TEXT_BOX);
        waitForElementVisible(LOGIN_BUTTON);
    }

    public void shouldDisplayAcceptedUsernames(List<String> expectedUsernames) {
        String usernamesText = waitAndGetText(ACCEPTED_USERNAMES);
        for (String username : expectedUsernames) {
            Assertions.assertThat(usernamesText).contains(username);
        }
    }

    public void shouldDisplayPasswordForAllUsers(String expectedPassword) {
        Assertions.assertThat(waitAndGetText(LOGIN_PASSWORD)).contains(expectedPassword);
    }

    public void shouldDisplayErrorMessage(String expectedError) {
        shouldHaveText(LOGIN_ERROR, expectedError);
    }

    public void shouldBeOnInventoryPage() {
        waitForUrlContaining("/inventory.html");
        Assertions.assertThat(getCurrentPageUrl()).contains("/inventory.html");
        shouldHaveText(PRODUCTS_TITLE, "Products");
    }

    public void shouldDisplayProductCount(int expectedCount) {
        shouldHaveElementCount(INVENTORY_ITEMS, expectedCount);
    }

    public String currentUrl() {
        return getCurrentPageUrl();
    }
}
