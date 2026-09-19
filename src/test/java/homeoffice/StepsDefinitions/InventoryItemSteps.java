package homeoffice.StepsDefinitions;

import homeoffice.actions.InventoryItemAction;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

public class InventoryItemSteps {

    @Steps
    InventoryItemAction inventoryItemAction;

    @Then("the inventory item detail page is displayed")
    public void the_sauce_demo_inventory_item_detail_page_is_displayed() {
        inventoryItemAction.i_should_see_inventory_item_page();
    }

    @Then("I should see inventory item details name {string} description {string} price {string}")
    public void i_should_see_sauce_demo_inventory_item_details(String productName, String description, String price) {
        inventoryItemAction.i_should_see_inventory_item_details(productName, description, price);
    }

    @Then("I should see inventory item image for {string}")
    public void i_should_see_sauce_demo_inventory_item_image_for(String productName) {
        inventoryItemAction.i_should_see_inventory_item_image(productName);
    }

    @Then("I should see the inventory item Remove button")
    public void i_should_see_the_sauce_demo_inventory_item_remove_button() {
        inventoryItemAction.i_should_see_remove_button();
    }

    @Then("I should see the inventory item Add to cart button")
    public void i_should_see_the_sauce_demo_inventory_item_add_to_cart_button() {
        inventoryItemAction.i_should_see_add_to_cart_button();
    }

    @When("I remove the product from the inventory item page")
    public void i_remove_the_product_from_the_inventory_item_page() {
        inventoryItemAction.i_remove_product();
    }

    @When("I add the product to cart from the inventory item page")
    public void i_add_the_product_to_cart_from_the_inventory_item_page() {
        inventoryItemAction.i_add_product_to_cart();
    }

    @When("I go back to products from the inventory item page")
    public void i_go_back_to_sauce_demo_products_from_the_inventory_item_page() {
        inventoryItemAction.i_go_back_to_products();
    }
}
