package homeoffice.StepsDefinitions;

import homeoffice.actions.CommonSteps;
import homeoffice.actions.GenericApplicationActions;
import homeoffice.actions.LoginActions;
import homeoffice.actions.NavigationAction;
import homeoffice.exceptions.TableRowDoesNotExistException;
import homeoffice.support.QuietBrowserLogging;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

import java.util.List;

import static homeoffice.utilities.RobotHelper.dismissPasswordSavePrompt;
import static org.junit.Assert.assertTrue;

public class CommonDefSteps {

    @BeforeAll
    public static void quietBrowserStartupLogs() {
        QuietBrowserLogging.install();
    }

    private final GenericApplicationActions genericApplicationActions = new GenericApplicationActions();

    @Steps
    LoginActions loginActions;
    @Steps
    NavigationAction navigationAction;

    @Steps
    CommonSteps commonSteps;


    @When("I logged in as {string} with password {string}")
    public void i_use_the_generic_page_to_login_as_with_password(String username, String password) {
        genericApplicationActions.loginWithDetails(username, password);
        dismissPasswordSavePrompt();
    }

    @When("I tap {string}")
    public void i_tap(String elementName) {
        genericApplicationActions.clickElement(elementName);
    }

    @When("I add {string} to the cart")
    public void i_use_the_generic_page_to_add_to_the_cart(String productName) {
        genericApplicationActions.addProductToCart(productName);
    }

    @Then("I can see {string} for {string}")
    public void i_can_see_for(String actionName, String productName) {
        genericApplicationActions.assertProductActionVisible(actionName, productName);
    }

    @When("I input checkout information {string} {string} {string}")
    public void i_use_the_generic_page_to_enter_checkout_information(String firstName, String lastName, String postalCode) {
        genericApplicationActions.enterCheckoutInformation(firstName, lastName, postalCode);
    }

    @Then("the page element {string} should be visible")
    public void the_generic_page_element_should_be_visible(String elementName) {
        genericApplicationActions.assertElementVisible(elementName);
    }

    @Then("the accepted users list should contain {string}")
    public void the_generic_accepted_users_list_contains(String username) {
        genericApplicationActions.assertAcceptedUsersListContains(username);
    }
    @Then("the cart badge count should be {int}")
    @Then("cart badge count should be {int}")
    @Then("cart item count should be {int}")
    public void the_generic_cart_item_count_should_be(int expectedCount) {
        genericApplicationActions.assertCartItemCount(expectedCount);
    }

    @Then("cart badge icon should not be visible")
    public void cart_badge_icon_should_not_be_visible() {
        genericApplicationActions.assertCartBadgeIconIsNotVisible();
    }

    @Then("the page should contain")
    @Then("I should see the following displayed on the page:")
    public void the_generic_page_should_contain(DataTable dataTable) {
        genericApplicationActions.assertPageContains(dataTable.asList());
    }

    @Then("I can see {string}")
    @Then("I should see {string}")
    @Then("I should see {string} message")
    public void i_should_see_text_on_the_current_page(String expectedText) {
        genericApplicationActions.assertPageContains(List.of(expectedText));
    }

    @Then("the button title should be {string}")
    public void the_generic_button_title_should_be(String expectedButtonTitle) {
        genericApplicationActions.assertButtonTitle(expectedButtonTitle);
    }

    @Then("the title should be {string}")
    public void the_generic_link_title_should_be(String expectedLinkTitle) {
        genericApplicationActions.assertLinkTitle(expectedLinkTitle);
    }

    @Then("the fields should be editable")
    public void the_generic_fields_should_be_editable() {
        genericApplicationActions.assertFieldsAreEditable();
    }

    @Then("the fields should be blank")
    public void the_generic_fields_should_be_blank() {
        genericApplicationActions.assertFieldsAreBlank();
    }

    @Then("the table should be below the heading")
    public void the_generic_table_should_be_below_the_heading() {
        genericApplicationActions.assertTableBelowHeading();
    }

    @Then("the table should contain")
    public void the_generic_table_should_contain(DataTable dataTable) throws TableRowDoesNotExistException {
        genericApplicationActions.assertTableContents(dataTable.asMap(String.class, String.class));
    }

    @Then("the message below heading should be {string}")
    public void the_generic_message_below_heading_should_be(String expectedMessage) {
        genericApplicationActions.assertMessageBelowHeading(expectedMessage);
    }

    @Given("I am logged in as {string}")
    public void i_am_logged_in_as(String usernameOrAlias) {
        navigationAction.i_navigate_to("/");
        loginActions.i_login_as_accepted_user(usernameOrAlias);
        navigationAction.i_should_be_on_page("inventory");
        dismissPasswordSavePrompt();
    }

    @Given("my cart is empty")
    public void my_cart_is_empty() {
        genericApplicationActions.assertCartBadgeIconIsNotVisible();
    }

    @Then("I close the google password manager popup")
    public void i_close_the_google_password_manager_popup() {
        dismissPasswordSavePrompt();
    }

    @Then("I should see the following links:")
    public void iShouldSeeTheFollowingLinks(DataTable dataTable) {
        commonSteps.i_should_see_menu_links(dataTable.asList());
    }



}
