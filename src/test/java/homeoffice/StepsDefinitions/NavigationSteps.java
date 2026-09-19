package homeoffice.StepsDefinitions;

import homeoffice.actions.InventoryActions;
import homeoffice.actions.NavigationAction;
import io.cucumber.java.en.But;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

public class NavigationSteps {

    @Steps
    NavigationAction navigationAction;
    @Steps
    InventoryActions inventoryActions;

    @Given("I navigate to {string} login page")
    @Given("I navigate to {string}")
    public void i_navigate_to(String path) {
        navigationAction.i_navigate_to(path);

    }

    @Then("I should be redirected to the {string} page")
    @Then("I should be on the {string} page")
    public void i_should_be_on_the_page(String pageName) {
        navigationAction.i_should_be_on_page(pageName);

    }

    @Then("the page header should be {string}")
    public void the_page_header_should_be(String expectedHeader) {
        navigationAction.i_should_see_page_header(expectedHeader);

    }

    @Then("the page title should be {string}")
    public void the_page_title_should_be(String expectedTitle) {
        navigationAction.i_should_see_page_title(expectedTitle);

    }

    @Then("the page URL should be {string}")
    public void the_page_url_should_be(String expectedPageOrUrl) {
        navigationAction.i_should_see_page_url(expectedPageOrUrl);

    }

    @When("I logout from the {string} page")
    public void i_logout_from_the_page(String pageName) throws InterruptedException {
        navigationAction.i_logout_from_page(pageName);

    }

    @When("I {string} from the {string} page")
    public void i_perform_action_from_the_page(String actionName, String pageName) throws InterruptedException {
        navigationAction.i_perform_action_from_page(actionName, pageName);

    }

    @Then("the header logo is displayed")
    public void the_sauce_demo_header_logo_is_displayed() {
        navigationAction.i_should_see_sauce_demo_header_logo();

    }

    @When("I open the navigation menu")
    public void i_open_the_sauce_demo_navigation_menu() throws InterruptedException {
        navigationAction.i_open_navigation_menu();

    }

    @Then("the navigation menu is displayed")
    public void the_sauce_demo_navigation_menu_is_displayed() {
        navigationAction.i_should_see_navigation_menu();

    }

    @Then("the navigation menu links are displayed")
    public void the_sauce_demo_navigation_menu_links_are_displayed() {
        navigationAction.i_should_see_navigation_menu_links();

    }

    @When("I close the navigation menu")
    public void i_close_the_sauce_demo_navigation_menu() {
        navigationAction.i_close_navigation_menu();

    }

    @Then("the navigation menu is hidden")
    public void the_sauce_demo_navigation_menu_is_hidden() {
        navigationAction.i_should_not_see_navigation_menu();
    }

    @When("I select All Items from the navigation menu")
    public void i_select_all_items_from_the_sauce_demo_navigation_menu() {
        navigationAction.i_select_all_items_from_navigation_menu();
    }

    @When("I select About from the navigation menu")
    public void i_select_about_from_the_sauce_demo_navigation_menu() {
        navigationAction.i_select_about_from_navigation_menu();

    }

    @When("I select Logout from the navigation menu")
    public void i_select_logout_from_the_sauce_demo_navigation_menu() {
        navigationAction.i_logout_from_navigation_menu();

    }

    @Then("I should be logged out of SauceDemo")
    public void i_should_be_logged_out_of_sauce_demo() {
        navigationAction.i_should_be_logged_out();

    }

    @When("I select Reset App State from the navigation menu")
    public void i_select_reset_app_state_from_the_sauce_demo_navigation_menu() {
        navigationAction.i_reset_app_state_from_navigation_menu();

    }

    @Then("the product sort dropdown is displayed")
    public void the_sauce_demo_product_sort_dropdown_is_displayed() {
        navigationAction.i_should_see_product_sort_dropdown();

    }

    @Then("the product sort options are displayed")
    public void the_sauce_demo_product_sort_options_are_displayed() {
        navigationAction.i_should_see_product_sort_options();

    }

    @When("I sort products by {string}")
    public void i_sort_sauce_demo_products_by(String sortOption) {
        navigationAction.i_sort_products_by(sortOption);

    }

    @Then("the selected sort option should be {string}")
    public void the_selected_sauce_demo_sort_option_should_be(String expectedSortOption) {
        navigationAction.i_should_see_selected_sort_option(expectedSortOption);

    }

    @Then("the products should be sorted by {string}")
    public void the_sauce_demo_products_should_be_sorted_by(String sortOption) {
        navigationAction.i_should_see_products_sorted_by(sortOption);

    }

    @But("the products should not be sorted by {string}")
    public void the_sauce_demo_products_should_not_be_sorted_by(String sortOption) {
        navigationAction.i_should_not_see_products_sorted_by(sortOption);
    }

    @When("I open the shopping cart")
    @When("I open the cart")
    public void i_open_the_shopping_cart() {
        inventoryActions.i_open_shopping_cart();
    }

    @When("I open {string} from the inventory page")
    public void i_open_from_the_inventory_page(String productName) {
        inventoryActions.i_open_inventory_item_details(productName);
    }
}
