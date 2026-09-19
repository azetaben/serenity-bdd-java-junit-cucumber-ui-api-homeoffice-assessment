package homeoffice.StepsDefinitions;

import homeoffice.actions.CheckoutYourInfoAction;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

import java.util.HashMap;
import java.util.Map;

public class CheckoutYourInfoSteps {

    @Steps
    CheckoutYourInfoAction checkoutYourInfoAction;

    @Then("the checkout your information page is displayed")
    public void the_sauce_demo_checkout_your_information_page_is_displayed() {
        checkoutYourInfoAction.i_should_see_checkout_your_info_page();
    }

    @When("I enter checkout information first name {string} last name {string} postal code {string}")
    public void i_enter_sauce_demo_checkout_information(String firstName, String lastName, String postalCode) {
        checkoutYourInfoAction.i_enter_checkout_information(firstName, lastName, postalCode);
    }

    @When("I enter checkout information:")
    public void i_enter_checkout_information(DataTable dataTable) {
        Map<String, String> checkoutInformation = normalizeCheckoutInformation(dataTable.asMaps(String.class, String.class).get(0));
        checkoutYourInfoAction.i_enter_checkout_information(
                checkoutInformation.get("first name"),
                checkoutInformation.get("last name"),
                checkoutInformation.get("postal code")
        );
    }

    @When("I complete the checkout information:")
    public void i_complete_the_checkout_information(DataTable dataTable) {
        i_enter_checkout_information(dataTable);
        checkoutYourInfoAction.i_continue_to_checkout_overview();
    }

    @When("I continue from the checkout your information page")
    public void i_continue_from_the_sauce_demo_checkout_your_information_page() {
        checkoutYourInfoAction.i_continue_to_checkout_overview();
    }

    @When("I cancel from the checkout your information page")
    public void i_cancel_from_the_sauce_demo_checkout_your_information_page() {
        checkoutYourInfoAction.i_cancel_checkout_from_your_info_page();
    }

    @Then("I should see checkout your information error {string}")
    public void i_should_see_sauce_demo_checkout_your_information_error(String expectedError) {
        checkoutYourInfoAction.i_should_see_checkout_your_info_error(expectedError);
    }

    private Map<String, String> normalizeCheckoutInformation(Map<String, String> checkoutInformation) {
        Map<String, String> normalized = new HashMap<>();
        checkoutInformation.forEach((key, value) -> normalized.put(normalizeCheckoutInformationKey(key), value));
        return normalized;
    }

    private String normalizeCheckoutInformationKey(String key) {
        String normalizedKey = key == null ? "" : key.trim().toLowerCase().replaceAll("\\s+", " ");
        return switch (normalizedKey) {
            case "firstname", "first" -> "first name";
            case "lastname", "surname", "last" -> "last name";
            case "postcode", "zip", "zip code" -> "postal code";
            default -> normalizedKey;
        };
    }
}
