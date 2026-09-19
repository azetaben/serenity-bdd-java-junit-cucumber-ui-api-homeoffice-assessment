package homeoffice.StepsDefinitions;

import homeoffice.actions.LoginActions;
import homeoffice.utilities.RobotHelper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

public class LoginSteps {

    @Steps
    LoginActions loginActions;

    @Given("I open inventory page directly")
    public void i_open_inventory_page_directly() {
        loginActions.i_open_inventory_page_directly();
    }

    @When("I login as {string} with password {string}")
    @When("I login with username {string} and password {string}")
    public void i_login_with_username_and_password(String username, String password) {
        loginActions.i_login_with_credentials(username, password);
    }

    @When("I login as an accepted {string} user")
    public void i_login_as_an_accepted_user(String usernameOrAlias) {
        loginActions.i_login_as_accepted_user(usernameOrAlias);
    }

    @When("I input accepted username {string} and password for all users")
    public void i_input_accepted_username_and_password_for_all_users(String usernameOrAlias) {
        loginActions.i_input_accepted_username_and_password_for_all_users(usernameOrAlias);
    }

    @Then("the login page is displayed")
    @Then("I should see the login form is present and visible")
    @Then("the login form should be present and visible")
    public void the_login_form_should_be_present_and_visible() {
        loginActions.i_should_see_the_login_form();
    }

    @Then("the accepted usernames are displayed")
    public void the_accepted_usernames_are_displayed(DataTable dataTable) {
        loginActions.i_should_see_accepted_usernames(dataTable.asList());
    }

    @Then("the accepted users list contains {string}")
    public void the_accepted_users_list_contains(String username) {
        loginActions.i_should_see_accepted_username(username);
    }

    @Then("the password for all users is {string}")
    public void the_password_for_all_users_is(String expectedPassword) {
        loginActions.i_should_see_password_for_all_users(expectedPassword);
    }

    @Then("I should see login error {string}")
    public void i_should_see_login_error(String expectedError) {
        loginActions.i_should_see_login_error(expectedError);
    }

    @Then("Login result should be {string}")
    public void login_result_should_be(String result) {
        loginActions.i_should_see_login_result(result);
    }

    @Then("I should see {int} product items")
    public void i_should_see_sauce_demo_product_items(int expectedCount) {
        loginActions.i_should_see_product_items(expectedCount);
    }
}
