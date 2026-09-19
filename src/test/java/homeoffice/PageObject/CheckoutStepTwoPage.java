package homeoffice.PageObject;

import net.serenitybdd.core.pages.WebElementFacade;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class CheckoutStepTwoPage extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutStepTwoPage.class);

    private static final String CHECKOUT_SUMMARY_CONTAINER = "[data-test='checkout-summary-container']";
    private static final String CART_LIST = "[data-test='cart-list']";
    private static final String CART_QUANTITY_LABEL = "[data-test='cart-quantity-label']";
    private static final String CART_DESCRIPTION_LABEL = "[data-test='cart-desc-label']";
    private static final String CART_ITEM = "[data-test='inventory-item']";
    private static final String ITEM_QUANTITY = "[data-test='item-quantity']";
    private static final String ITEM_NAME = "[data-test='inventory-item-name']";
    private static final String ITEM_DESCRIPTION = "[data-test='inventory-item-desc']";
    private static final String ITEM_PRICE = "[data-test='inventory-item-price']";
    private static final String PAYMENT_INFO_LABEL = "[data-test='payment-info-label']";
    private static final String PAYMENT_INFO_VALUE = "[data-test='payment-info-value']";
    private static final String SHIPPING_INFO_LABEL = "[data-test='shipping-info-label']";
    private static final String SHIPPING_INFO_VALUE = "[data-test='shipping-info-value']";
    private static final String TOTAL_INFO_LABEL = "[data-test='total-info-label']";
    private static final String SUBTOTAL_LABEL = "[data-test='subtotal-label']";
    private static final String TAX_LABEL = "[data-test='tax-label']";
    private static final String TOTAL_LABEL = "[data-test='total-label']";
    private static final String CANCEL_BUTTON = "[data-test='cancel']";
    private static final String FINISH_BUTTON = "[data-test='finish']";

    public void shouldDisplayCheckoutOverviewPage() {
        LOGGER.info("Validating checkout overview page is displayed");
        shouldDisplayPageWithClickableElements(
                "/checkout-step-two.html",
                List.of(CHECKOUT_SUMMARY_CONTAINER, CART_LIST),
                List.of(CANCEL_BUTTON, FINISH_BUTTON));
    }

    public void shouldDisplayOverviewHeaders() {
        LOGGER.info("Validating checkout overview headers");
        shouldHaveText(CART_QUANTITY_LABEL, "QTY");
        shouldHaveText(CART_DESCRIPTION_LABEL, "Description");
    }

    public void shouldDisplayOverviewItemCount(int expectedItemCount) {
        LOGGER.info("Validating checkout overview item count is {}", expectedItemCount);
        shouldHaveElementCount(CART_ITEM, expectedItemCount);
    }

    public void shouldDisplayOverviewProducts(List<String> expectedProductNames) {
        LOGGER.info("Validating checkout overview products: {}", expectedProductNames);
        Assertions.assertThat(overviewProductNames()).containsAll(expectedProductNames);
    }

    public void shouldDisplayOverviewProductDetails(List<Map<String, String>> expectedProducts) {
        LOGGER.info("Validating checkout overview product details table");
        for (Map<String, String> product : expectedProducts) {
            shouldDisplayItemDetails(
                    overviewItem(product.get("name")),
                    ITEM_QUANTITY, product.get("quantity"),
                    ITEM_DESCRIPTION, product.get("description"),
                    ITEM_PRICE, product.get("price"));
        }
    }

    public void shouldDisplaySummaryInformation(String paymentValue, String shippingValue, String subtotal, String tax, String total) {
        LOGGER.info("Validating checkout overview summary information");
        shouldHaveText(PAYMENT_INFO_LABEL, "Payment Information:");
        shouldHaveText(PAYMENT_INFO_VALUE, paymentValue);
        shouldHaveText(SHIPPING_INFO_LABEL, "Shipping Information:");
        shouldHaveText(SHIPPING_INFO_VALUE, shippingValue);
        shouldHaveText(TOTAL_INFO_LABEL, "Price Total");
        shouldHaveText(SUBTOTAL_LABEL, subtotal);
        shouldHaveText(TAX_LABEL, tax);
        shouldHaveText(TOTAL_LABEL, total);
    }

    public void cancelCheckoutOverview() {
        LOGGER.info("Cancelling checkout from overview page");
        waitAndClick(CANCEL_BUTTON);
        waitForUrlContaining("/inventory.html");
    }

    public void finishCheckout() {
        LOGGER.info("Finishing checkout from overview page");
        waitAndClick(FINISH_BUTTON);
        waitForUrlContaining("/checkout-complete.html");
    }

    private List<String> overviewProductNames() {
        LOGGER.info("Getting checkout overview product names");
        return textsFor(ITEM_NAME);
    }

    private WebElementFacade overviewItem(String productName) {
        return itemNamed(CART_ITEM, ITEM_NAME, productName, "Product in checkout overview");
    }
}
