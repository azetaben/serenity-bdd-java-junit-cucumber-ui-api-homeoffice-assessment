package homeoffice.StepsDefinitions;

import homeoffice.actions.InventorySteps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

public class InventoryStepDefinitions {

    @Steps
    InventorySteps inventory;

    @Given("I am on the inventory page")
    public void iAmOnTheInventoryPage() {
        inventory.verifyOnInventoryPage();
    }

    @When("I sort the inventory by {string}")
    public void iSortTheInventoryBy(String sortOption) {
        inventory.sortInventoryBy(sortOption);
    }

    @Then("the first inventory item should be {string} priced at {string}")
    public void theFirstInventoryItemShouldBe(String name, String price) {
        inventory.verifyFirstItem(name, price);
    }

    @When("I add the item {string} to the cart")
    public void iAddItemToCart(String itemSlug) {
        inventory.addItemToCart(itemSlug);
    }

    @Then("the cart badge should show {string}")
    public void theCartBadgeShouldShow(String count) {
        inventory.verifyCartBadge(count);
    }
}
