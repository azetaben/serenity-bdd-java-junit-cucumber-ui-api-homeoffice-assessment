package homeoffice.PageObject;

import net.serenitybdd.core.pages.WebElementFacade;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static homeoffice.utilities.TextUtil.normalizePrice;


public class CartPage extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartPage.class);

    private static final String CART_CONTENTS_CONTAINER = "[data-test='cart-contents-container']";
    private static final String CART_LIST = "[data-test='cart-list']";
    private static final String CART_QUANTITY_LABEL = "[data-test='cart-quantity-label']";
    private static final String CART_DESCRIPTION_LABEL = "[data-test='cart-desc-label']";
    private static final String CART_ITEM = "[data-test='inventory-item']";
    private static final String CART_ITEM_QUANTITY = "[data-test='item-quantity']";
    private static final String CART_ITEM_NAME = "[data-test='inventory-item-name']";
    private static final String CART_ITEM_DESCRIPTION = "[data-test='inventory-item-desc']";
    private static final String CART_ITEM_PRICE = "[data-test='inventory-item-price']";
    private static final String SHOPPING_CART_BADGE = "[data-test='shopping-cart-badge'], .shopping_cart_badge";
    private static final String CONTINUE_SHOPPING_BUTTON = "[data-test='continue-shopping']";
    private static final String CHECKOUT_BUTTON = "[data-test='checkout']";
    private static final String CHECKOUT_INFORMATION_CONTAINER = "[data-test='checkout-info-container']";

    public void shouldDisplayCartPage() {
        LOGGER.info("Validating SauceDemo cart page is displayed");
        shouldDisplayPage("/cart.html", CART_CONTENTS_CONTAINER, CART_LIST);
    }

    public void shouldDisplayCartHeaders() {
        LOGGER.info("Validating cart header labels");
        shouldHaveText(CART_QUANTITY_LABEL, "QTY");
        shouldHaveText(CART_DESCRIPTION_LABEL, "Description");
    }

    public void shouldDisplayCartItemCount(int expectedItemCount) {
        LOGGER.info("Validating cart item count is {}", expectedItemCount);
        if (getCurrentPageUrl().contains("/cart.html")) {
            shouldHaveElementCount(CART_ITEM, expectedItemCount);
            return;
        }

        shouldDisplayCartBadgeCount(expectedItemCount);
    }

    public void shouldDisplayCartBadgeCount(int expectedItemCount) {
        LOGGER.info("Validating shopping cart badge count is {}", expectedItemCount);
        if (expectedItemCount == 0 && !isElementDisplayed(SHOPPING_CART_BADGE)) {
            return;
        }
        shouldHaveText(SHOPPING_CART_BADGE, String.valueOf(expectedItemCount));
    }

    public void shouldDisplayCartProducts(List<String> expectedProductNames) {
        LOGGER.info("Validating cart products: {}", expectedProductNames);
        Assertions.assertThat(cartProductNames()).containsAll(expectedProductNames);
    }

    public void shouldDisplayExactlyCartProducts(List<String> expectedProductNames) {
        LOGGER.info("Validating exact cart products: {}", expectedProductNames);
        Assertions.assertThat(cartProductNames()).containsExactlyElementsOf(expectedProductNames);
    }

    public void shouldDisplayCartProductDetails(String productName, String expectedQuantity, String expectedDescription, String expectedPrice) {
        LOGGER.info("Validating cart product details for [{}]", productName);
        shouldDisplayItemDetails(
                cartItem(productName),
                CART_ITEM_QUANTITY, expectedQuantity,
                CART_ITEM_DESCRIPTION, expectedDescription,
                CART_ITEM_PRICE, expectedPrice);
    }

    public void shouldDisplayCartProductDetails(List<Map<String, String>> expectedProducts) {
        LOGGER.info("Validating cart product details table");
        for (Map<String, String> product : expectedProducts) {
            shouldDisplayCartProductDetails(
                    product.get("name"),
                    product.get("quantity"),
                    product.get("description"),
                    product.get("price")
            );
        }
    }

    public void shouldDisplayCartProductPriceAndQuantity(String productName, String expectedPrice, int expectedQuantity) {
        shouldDisplayCartProductDetails(productName, String.valueOf(expectedQuantity), null, normalizePrice(expectedPrice));
    }

    public void shouldDisplayRemoveButtonForProduct(String productName) {
        LOGGER.info("Validating Remove button for cart product [{}]", productName);
        cartItem(productName).then(removeButton(productName)).waitUntilClickable();
    }

    public void removeProductFromCart(String productName) {
        LOGGER.info("Removing product from cart: {}", productName);
        cartItem(productName).then(removeButton(productName)).waitUntilClickable().click();
    }

    public void shouldNotDisplayProductInCart(String productName) {
        LOGGER.info("Validating product is not displayed in cart: {}", productName);
        Assertions.assertThat(cartProductNames()).doesNotContain(productName);
    }

    public void continueShopping() {
        LOGGER.info("Clicking Continue Shopping from cart page");
        waitAndClick(CONTINUE_SHOPPING_BUTTON);
        waitForUrlContaining("/inventory.html");
    }

    public void checkout() {
        LOGGER.info("Clicking Checkout from cart page");
        clickWithJavaScript(CHECKOUT_BUTTON);
        waitForUrlContaining("/checkout-step-one.html");
    }

    public void shouldDisplayCheckoutInformationPage() {
        LOGGER.info("Validating SauceDemo checkout information page is displayed");
        shouldDisplayPage("/checkout-step-one.html", CHECKOUT_INFORMATION_CONTAINER);
    }

    public void shouldDisplayCartFooterButtons() {
        LOGGER.info("Validating cart footer buttons");
        shouldContainText(CONTINUE_SHOPPING_BUTTON, "Continue Shopping");
        shouldHaveText(CHECKOUT_BUTTON, "Checkout");
    }

    private List<String> cartProductNames() {
        LOGGER.info("Getting cart product names");
        return textsFor(CART_ITEM_NAME);
    }

    private WebElementFacade cartItem(String productName) {
        return itemNamed(CART_ITEM, CART_ITEM_NAME, productName, "Product in cart");
    }

    private String removeButton(String productName) {
        return sauceDemoDataTestButton("remove", productName);
    }
}
