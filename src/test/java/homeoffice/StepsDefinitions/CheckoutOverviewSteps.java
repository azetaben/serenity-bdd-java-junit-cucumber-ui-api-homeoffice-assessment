package homeoffice.StepsDefinitions;

import homeoffice.actions.CheckoutOverviewAction;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

import java.util.HashMap;
import java.util.Map;

public class CheckoutOverviewSteps {

    @Steps
    CheckoutOverviewAction checkoutOverviewAction;

    @Then("the checkout overview page is displayed")
    public void the_sauce_demo_checkout_overview_page_is_displayed() {
        checkoutOverviewAction.i_should_see_checkout_overview_page();
    }

    @Then("the checkout overview headers are displayed")
    public void the_sauce_demo_checkout_overview_headers_are_displayed() {
        checkoutOverviewAction.i_should_see_checkout_overview_headers();
    }

    @Then("I should see {int} checkout overview items")
    public void i_should_see_sauce_demo_checkout_overview_items(int expectedItemCount) {
        checkoutOverviewAction.i_should_see_checkout_overview_item_count(expectedItemCount);
    }

    @Then("I should see the following checkout overview products")
    public void i_should_see_the_following_sauce_demo_checkout_overview_products(DataTable dataTable) {
        checkoutOverviewAction.i_should_see_checkout_overview_products(dataTable.asList());
    }

    @Then("I should see the following checkout overview product details")
    public void i_should_see_the_following_sauce_demo_checkout_overview_product_details(DataTable dataTable) {
        checkoutOverviewAction.i_should_see_checkout_overview_product_details(dataTable.asMaps(String.class, String.class));
    }

    @Then("I should see checkout summary payment {string} shipping {string} subtotal {string} tax {string} total {string}")
    public void i_should_see_sauce_demo_checkout_summary(String paymentValue, String shippingValue, String subtotal, String tax, String total) {
        checkoutOverviewAction.i_should_see_checkout_summary(
                paymentValue,
                shippingValue,
                checkoutSummaryAmount("Item total", subtotal),
                checkoutSummaryAmount("Tax", tax),
                checkoutSummaryAmount("Total", total)
        );
    }

    @Then("I should see checkout summary")
    @Then("the checkout summary should display:")
    public void i_should_see_checkout_summary(DataTable dataTable) {
        Map<String, String> summary = normalizeCheckoutSummary(dataTable.asMap(String.class, String.class));
        checkoutOverviewAction.i_should_see_checkout_summary(
                checkoutSummaryValue(summary, "payment", "payment information"),
                checkoutSummaryValue(summary, "shipping", "shipping information"),
                checkoutSummaryAmount("Item total", checkoutSummaryValue(summary, "subtotal", "item total")),
                checkoutSummaryAmount("Tax", checkoutSummaryValue(summary, "tax")),
                checkoutSummaryAmount("Total", checkoutSummaryValue(summary, "total"))
        );
    }

    @When("I cancel from the checkout overview page")
    public void i_cancel_from_the_sauce_demo_checkout_overview_page() {
        checkoutOverviewAction.i_cancel_checkout_overview();
    }

    @When("I finish from the checkout overview page")
    @When("I place the order")
    public void i_finish_from_the_sauce_demo_checkout_overview_page() {
        checkoutOverviewAction.i_finish_checkout();
    }

    private Map<String, String> normalizeCheckoutSummary(Map<String, String> summary) {
        Map<String, String> normalized = new HashMap<>();
        summary.forEach((key, value) -> normalized.put(normalizeCheckoutSummaryKey(key), value));
        return normalized;
    }

    private String normalizeCheckoutSummaryKey(String key) {
        return key == null ? "" : key.trim().toLowerCase().replaceAll("\\s+", " ");
    }

    private String checkoutSummaryValue(Map<String, String> summary, String... keys) {
        for (String key : keys) {
            String value = summary.get(normalizeCheckoutSummaryKey(key));
            if (value != null) {
                return value;
            }
        }
        throw new IllegalArgumentException("Missing checkout summary value for: " + String.join("/", keys));
    }

    private String checkoutSummaryAmount(String label, String value) {
        String amount = value == null ? "" : value.trim();
        return amount.contains(":") ? amount : label + ": " + amount;
    }
}
