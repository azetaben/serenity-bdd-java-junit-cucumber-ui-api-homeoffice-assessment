package homeoffice.actions;

import homeoffice.PageObject.CheckoutStepOnePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckoutYourInfoAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutYourInfoAction.class);

    CheckoutStepOnePage checkoutStepOnePage;

    public void i_should_see_checkout_your_info_page() {
        LOGGER.info("Checking checkout your information page is displayed");
        checkoutStepOnePage.shouldDisplayCheckoutYourInfoPage();
    }

    public void i_enter_checkout_information(String firstName, String lastName, String postalCode) {
        LOGGER.info("Entering checkout information");
        checkoutStepOnePage.enterCheckoutInformation(firstName, lastName, postalCode);
    }

    public void i_continue_to_checkout_overview() {
        LOGGER.info("Continuing to checkout overview");
        checkoutStepOnePage.continueToOverview();
    }

    public void i_cancel_checkout_from_your_info_page() {
        LOGGER.info("Cancelling checkout from your information page");
        checkoutStepOnePage.cancelCheckout();
    }

    public void i_should_see_checkout_your_info_error(String expectedError) {
        LOGGER.info("Checking checkout information validation error");
        checkoutStepOnePage.shouldDisplayValidationError(expectedError);
    }
}
