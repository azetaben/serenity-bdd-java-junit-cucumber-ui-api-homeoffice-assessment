package homeoffice.StepsDefinitions;

import homeoffice.actions.InventoryActions;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

import java.util.List;
import java.util.Map;

public class InventorySteps {

    @Steps
    InventoryActions inventoryActions;

    @Then("the inventory page is displayed")
    public void the_sauce_demo_inventory_page_is_displayed() {
        inventoryActions.i_should_be_on_inventory_page();
    }

    @Then("I should see {int} inventory items")
    public void i_should_see_inventory_items(int expectedItemCount) {
        inventoryActions.i_should_see_inventory_items(expectedItemCount);
    }

    @Then("I should see the following inventory products")
    public void i_should_see_the_following_inventory_products(DataTable dataTable) {
        inventoryActions.i_should_see_inventory_product_names(dataTable.asList());
    }

    @Then("I should see the following inventory product details")
    public void i_should_see_the_following_inventory_product_details(DataTable dataTable) {
        List<Map<String, String>> products = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> product : products) {
            inventoryActions.i_should_see_inventory_product_details(
                    product.get("name"),
                    product.get("description"),
                    product.get("price")
            );
        }
    }

    @Then("I should see an inventory image for {string}")
    @Then("inventory image is displayed for {string}")
    public void i_should_see_an_inventory_image_for(String productName) {
        inventoryActions.i_should_see_inventory_product_image(productName);
    }

    @Then("I should see inventory images for:")
    @Then("inventory images are displayed for:")
    public void i_should_see_inventory_images_for(DataTable dataTable) {
        inventoryActions.i_should_see_inventory_product_images(dataTable.asList());
    }

    @Then("I should see an Add to cart button for {string}")
    @Then("add button is displayed for {string}")
    public void i_should_see_an_add_to_cart_button_for(String productName) {
        inventoryActions.i_should_see_add_to_cart_button_for_product(productName);
    }

    @Then("I should see Add to cart buttons for:")
    @Then("add buttons are displayed for:")
    public void i_should_see_add_to_cart_buttons_for(DataTable dataTable) {
        inventoryActions.i_should_see_add_to_cart_buttons_for_products(dataTable.asList());
    }

    @When("I add a {string} to the cart")
    @When("I add {string} to the cart from the inventory page")
    @When("I add product {string}")
    public void i_add_to_the_cart_from_the_inventory_page(String productName) {
        inventoryActions.i_add_product_to_cart(productName);
    }

    @When("I add products to the cart:")
    @When("I add products:")
    @When("I add the following products to my cart:")
    public void i_add_products(DataTable dataTable) {
        inventoryActions.i_add_products_to_cart(dataTable.asList());
    }

    @Then("I should see a Remove button for {string}")
    @Then("remove button is displayed for {string}")
    public void i_should_see_a_remove_button_for(String productName) {
        inventoryActions.i_should_see_remove_button_for_product(productName);
    }

    @When("I remove product {string} from inventory")
    public void i_remove_product_from_inventory(String productName) {
        inventoryActions.i_remove_product_from_inventory(productName);
    }
}
