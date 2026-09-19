package homeoffice.StepsDefinitions;

import homeoffice.actions.CheckoutCompleteAction;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;

public class CheckoutCompleteSteps {

    @Steps
    CheckoutCompleteAction checkoutCompleteAction;

    @Then("the checkout complete page is displayed")
    public void the_sauce_demo_checkout_complete_page_is_displayed() {
        checkoutCompleteAction.i_should_see_checkout_complete_page();
    }

    @Then("I should see checkout complete header {string} and text {string}")
    public void i_should_see_sauce_demo_checkout_complete_header_and_text(String expectedHeader, String expectedText) {
        checkoutCompleteAction.i_should_see_checkout_complete_message(expectedHeader, expectedText);
    }

    @When("I go back to products from the checkout complete page")
    @When("I return to the inventory page")
    public void i_go_back_to_sauce_demo_products_from_the_checkout_complete_page() {
        checkoutCompleteAction.i_go_back_to_products();
    }

    @Then("the order should be completed successfully")
    public void the_order_should_be_completed_successfully() {
        checkoutCompleteAction.i_should_see_checkout_complete_page();
    }

    @Then("I should see a confirmation message")
    public void i_should_see_a_confirmation_message() {
        checkoutCompleteAction.i_should_see_checkout_complete_message(
                "Thank you for your order!",
                "Your order has been dispatched, and will arrive just as fast as the pony can get there!"
        );
    }

    @Then("I should see the Generate PDF order button")
    public void i_should_see_the_generate_pdf_order_button() {
        checkoutCompleteAction.i_should_see_generate_pdf_order_button();
    }

    @When("I download the PDF order to {word}")
    public void i_download_the_pdf_order_to(String downloadDirectoryName) {
        checkoutCompleteAction.i_download_pdf_order_to(downloadDirectoryName);
    }

}
