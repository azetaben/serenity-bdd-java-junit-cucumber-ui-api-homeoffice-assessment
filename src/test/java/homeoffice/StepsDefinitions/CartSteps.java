package homeoffice.StepsDefinitions;

import homeoffice.actions.CartAction;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static homeoffice.utilities.TextUtil.normalizePrice;

public class CartSteps {

    @Steps
    CartAction cartAction;

    @Then("the cart page is displayed")
    public void the_sauce_demo_cart_page_is_displayed() {
        cartAction.i_should_see_cart_page();
    }

    @Then("the cart headers are displayed")
    public void the_sauce_demo_cart_headers_are_displayed() {
        cartAction.i_should_see_cart_headers();
    }

    @Then("cart badge is {int}")
    @Then("I should see {int} cart items")
    public void i_should_see_sauce_demo_cart_items(int expectedItemCount) {
        cartAction.i_should_see_cart_item_count(expectedItemCount);
    }

    @Then("cart badge is {string}")
    @Then("the cart badge should display {string}")
    public void cart_badge_is(String expectedItemCount) {
        cartAction.i_should_see_cart_badge_count(Integer.parseInt(expectedItemCount));
    }

    @Then("I should see the following cart products")
    public void i_should_see_the_following_sauce_demo_cart_products(DataTable dataTable) {
        cartAction.i_should_see_cart_products(dataTable.asList());
    }

    @Then("cart contains exactly:")
    public void cart_contains_exactly(DataTable dataTable) {
        cartAction.i_should_see_exact_cart_products(dataTable.asList());
    }

    @Then("cart product {string} has price {string} and quantity {int}")
    @Then("cart product {string} has $ price {string} and quantity {int}")
    public void cart_product_has_price_and_quantity(String productName, String expectedPrice, int expectedQuantity) {
        cartAction.i_should_see_cart_product_price_and_quantity(productName, expectedPrice, expectedQuantity);
    }

    @Then("I should see the item in the cart")
    @Then("I should see the cart product details")
    @Then("I should see the following cart product details")
    @Then("the cart should contain:")
    public void i_should_see_the_following_sauce_demo_cart_product_details(DataTable dataTable) {
        cartAction.i_should_see_cart_product_details(normalizeCartProductDetails(dataTable.asMaps(String.class, String.class)));
    }

    @Then("I should see a cart Remove button for {string}")
    public void i_should_see_a_saucedemo_cart_remove_button_for(String productName) {
        cartAction.i_should_see_remove_button_for_product(productName);
    }

    @When("I remove {string} from the cart")
    @When("I remove product {string} from cart")
    public void i_remove_from_the_sauce_demo_cart(String productName) {
        cartAction.i_remove_product_from_cart(productName);
    }

    @Then("I should not see {string} in the cart")
    @Then("cart does not contain product {string}")
    public void i_should_not_see_in_the_sauce_demo_cart(String productName) {
        cartAction.i_should_not_see_product_in_cart(productName);
    }

    @Then("the cart footer buttons are displayed")
    public void the_sauce_demo_cart_footer_buttons_are_displayed() {
        cartAction.i_should_see_cart_footer_buttons();
    }

    @When("I continue shopping from the cart page")
    @When("I continue shopping from the cart")
    public void i_continue_shopping_from_the_sauce_demo_cart() {
        cartAction.i_continue_shopping();
    }

    @When("I checkout from the cart page")
    @When("I checkout from the cart")
    @When("I proceed to checkout")
    public void i_checkout_from_the_sauce_demo_cart() {
        cartAction.i_checkout();
    }

    @Then("the checkout information page is displayed")
    public void the_sauce_demo_checkout_information_page_is_displayed() {
        cartAction.i_should_see_checkout_information_page();
    }

    private List<Map<String, String>> normalizeCartProductDetails(List<Map<String, String>> products) {
        return products.stream().map(this::normalizeCartProductDetail).toList();
    }

    private Map<String, String> normalizeCartProductDetail(Map<String, String> product) {
        Map<String, String> normalized = new HashMap<>();
        product.forEach((key, value) -> {
            String normalizedKey = normalizeCartDetailHeader(key);
            String normalizedValue = "price".equals(normalizedKey) ? normalizePrice(value) : value;
            normalized.put(normalizedKey, normalizedValue);
        });
        return normalized;
    }

    private String normalizeCartDetailHeader(String header) {
        String normalizedHeader = header == null ? "" : header.trim().toLowerCase().replaceAll("\\s+", " ");
        return switch (normalizedHeader) {
            case "product title", "product name", "title" -> "name";
            case "price($)", "price ($)", "$ price", "price" -> "price";
            case "qty" -> "quantity";
            default -> normalizedHeader;
        };
    }
}
