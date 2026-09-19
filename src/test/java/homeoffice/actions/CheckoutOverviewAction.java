package homeoffice.actions;

import homeoffice.PageObject.CheckoutStepTwoPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class CheckoutOverviewAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutOverviewAction.class);

    CheckoutStepTwoPage checkoutOverviewPage;

    public void i_should_see_checkout_overview_page() {
        LOGGER.info("Checking checkout overview page is displayed");
        checkoutOverviewPage.shouldDisplayCheckoutOverviewPage();
    }

    public void i_should_see_checkout_overview_headers() {
        LOGGER.info("Checking checkout overview headers");
        checkoutOverviewPage.shouldDisplayOverviewHeaders();
    }

    public void i_should_see_checkout_overview_item_count(int expectedItemCount) {
        LOGGER.info("Checking checkout overview item count is {}", expectedItemCount);
        checkoutOverviewPage.shouldDisplayOverviewItemCount(expectedItemCount);
    }

    public void i_should_see_checkout_overview_products(List<String> expectedProductNames) {
        LOGGER.info("Checking checkout overview products");
        checkoutOverviewPage.shouldDisplayOverviewProducts(expectedProductNames);
    }

    public void i_should_see_checkout_overview_product_details(List<Map<String, String>> expectedProducts) {
        LOGGER.info("Checking checkout overview product details");
        checkoutOverviewPage.shouldDisplayOverviewProductDetails(expectedProducts);
    }

    public void i_should_see_checkout_summary(String paymentValue, String shippingValue, String subtotal, String tax, String total) {
        LOGGER.info("Checking checkout overview summary");
        checkoutOverviewPage.shouldDisplaySummaryInformation(paymentValue, shippingValue, subtotal, tax, total);
    }

    public void i_cancel_checkout_overview() {
        LOGGER.info("Cancelling checkout overview");
        checkoutOverviewPage.cancelCheckoutOverview();
    }

    public void i_finish_checkout() {
        LOGGER.info("Finishing checkout");
        checkoutOverviewPage.finishCheckout();
    }
}
