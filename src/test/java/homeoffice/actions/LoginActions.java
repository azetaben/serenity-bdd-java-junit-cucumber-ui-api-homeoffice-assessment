package homeoffice.actions;

import homeoffice.PageObject.LoginPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class LoginActions {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginActions.class);
    private static final String ACCEPTED_USER_PASSWORD = "secret_sauce";
    private static final Map<String, String> ACCEPTED_USER_ALIASES = Map.ofEntries(
            Map.entry("regular", "standard_user"),
            Map.entry("standard", "standard_user"),
            Map.entry("standard user", "standard_user"),
            Map.entry("standard_user", "standard_user"),
            Map.entry("locked out", "locked_out_user"),
            Map.entry("locked out user", "locked_out_user"),
            Map.entry("locked_out_user", "locked_out_user"),
            Map.entry("problem", "problem_user"),
            Map.entry("problem user", "problem_user"),
            Map.entry("problem_user", "problem_user"),
            Map.entry("performance glitch", "performance_glitch_user"),
            Map.entry("performance glitch user", "performance_glitch_user"),
            Map.entry("performance_glitch_user", "performance_glitch_user"),
            Map.entry("error", "error_user"),
            Map.entry("error user", "error_user"),
            Map.entry("error_user", "error_user"),
            Map.entry("visual", "visual_user"),
            Map.entry("visual user", "visual_user"),
            Map.entry("visual_user", "visual_user")
    );

    LoginPage loginPage;

    public void i_open_inventory_page_directly() {
        LOGGER.info("Opening protected inventory page without logging in");
        loginPage.openInventoryPageDirectly();
    }

    public void i_login_with_credentials(String username, String password) {
        loginPage.loginWithCredentials(username, password);
    }

    public void i_login_as_accepted_user(String usernameOrAlias) {
        String username = acceptedUsername(usernameOrAlias);
        LOGGER.info("Logging in as accepted user [{}] resolved from [{}]", username, usernameOrAlias);
        loginPage.loginWithCredentials(username, ACCEPTED_USER_PASSWORD);
    }

    public void i_input_accepted_username_and_password_for_all_users(String usernameOrAlias) {
        String username = acceptedUsername(usernameOrAlias);
        LOGGER.info("Entering accepted username [{}] and password for all users", username);
        loginPage.enterUsername(username);
        loginPage.enterPassword(ACCEPTED_USER_PASSWORD);
    }

    public void i_should_see_the_login_form() {
        LOGGER.info("Checking SauceDemo login form is present and visible");
        loginPage.shouldDisplayLoginForm();
    }

    public void i_should_see_accepted_usernames(List<String> acceptedUsernames) {
        loginPage.shouldDisplayAcceptedUsernames(acceptedUsernames);
    }

    public void i_should_see_accepted_username(String acceptedUsername) {
        LOGGER.info("Checking accepted users list contains [{}]", acceptedUsername);
        loginPage.shouldDisplayAcceptedUsernames(List.of(acceptedUsername));
    }

    public void i_should_see_password_for_all_users(String expectedPassword) {
        loginPage.shouldDisplayPasswordForAllUsers(expectedPassword);
    }

    public void i_should_see_login_error(String expectedError) {
        loginPage.shouldDisplayErrorMessage(expectedError);
    }

    public void i_should_see_login_result(String result) {
        String normalizedResult = result == null ? "" : result.trim().toUpperCase().replace("-", "_").replace(" ", "_");
        LOGGER.info("Checking login result [{}]", normalizedResult);

        switch (normalizedResult) {
            case "SUCCESS" -> loginPage.shouldBeOnInventoryPage();
            case "LOCKED_OUT" ->
                    loginPage.shouldDisplayErrorMessage("Epic sadface: Sorry, this user has been locked out.");
            case "INVALID", "INVALID_CREDENTIALS" ->
                    loginPage.shouldDisplayErrorMessage("Epic sadface: Username and password do not match any user in this service");
            case "USERNAME_REQUIRED" -> loginPage.shouldDisplayErrorMessage("Epic sadface: Username is required");
            case "PASSWORD_REQUIRED" -> loginPage.shouldDisplayErrorMessage("Epic sadface: Password is required");
            default -> throw new IllegalArgumentException("Unsupported login result: " + result);
        }
    }

    public void i_should_be_on_inventory_page() {
        loginPage.shouldBeOnInventoryPage();
    }

    public void i_should_see_product_items(int expectedCount) {
        loginPage.shouldDisplayProductCount(expectedCount);
    }

    private String acceptedUsername(String usernameOrAlias) {
        String normalized = usernameOrAlias == null ? "" : usernameOrAlias.trim().toLowerCase().replace("-", " ");
        String username = ACCEPTED_USER_ALIASES.get(normalized);
        if (username == null) {
            username = usernameOrAlias == null ? "" : usernameOrAlias.trim();
        }
        return username;
    }
}
