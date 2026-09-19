package homeoffice.PageObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckoutStepOnePage extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutStepOnePage.class);

    private static final String CHECKOUT_INFO_CONTAINER = "[data-test='checkout-info-container']";
    private static final String FIRST_NAME_INPUT = "[data-test='firstName']";
    private static final String LAST_NAME_INPUT = "[data-test='lastName']";
    private static final String POSTAL_CODE_INPUT = "[data-test='postalCode']";
    private static final String CANCEL_BUTTON = "[data-test='cancel']";
    private static final String CONTINUE_BUTTON = "[data-test='continue']";
    private static final String ERROR_MESSAGE = "[data-test='error']";

    public void shouldDisplayCheckoutYourInfoPage() {
        LOGGER.info("Validating checkout your information page is displayed");
        shouldDisplayPageWithClickableElements(
                "/checkout-step-one.html",
                java.util.List.of(CHECKOUT_INFO_CONTAINER, FIRST_NAME_INPUT, LAST_NAME_INPUT, POSTAL_CODE_INPUT),
                java.util.List.of(CANCEL_BUTTON, CONTINUE_BUTTON));
    }

    public void enterCheckoutInformation(String firstName, String lastName, String postalCode) {
        LOGGER.info("Entering checkout information for [{}] [{}]", firstName, lastName);
        waitAndType(FIRST_NAME_INPUT, firstName);
        waitAndType(LAST_NAME_INPUT, lastName);
        waitAndType(POSTAL_CODE_INPUT, postalCode);
    }

    public void continueToOverview() {
        LOGGER.info("Continuing to checkout overview");
        waitAndClick(CONTINUE_BUTTON);
    }

    public void cancelCheckout() {
        LOGGER.info("Cancelling checkout from your information page");
        waitAndClick(CANCEL_BUTTON);
        waitForUrlContaining("/cart.html");
    }

    public void shouldDisplayValidationError(String expectedError) {
        LOGGER.info("Validating checkout information error message");
        shouldHaveText(ERROR_MESSAGE, expectedError);
    }
}
