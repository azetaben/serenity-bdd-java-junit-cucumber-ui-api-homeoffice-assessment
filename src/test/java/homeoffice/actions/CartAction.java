package homeoffice.actions;


import homeoffice.PageObject.CartPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class CartAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartAction.class);

    CartPage cartPage;

    public void i_should_see_cart_page() {
        LOGGER.info("Checking SauceDemo cart page is displayed");
        cartPage.shouldDisplayCartPage();
    }

    public void i_should_see_cart_headers() {
        LOGGER.info("Checking SauceDemo cart headers");
        cartPage.shouldDisplayCartHeaders();
    }

    public void i_should_see_cart_item_count(int expectedItemCount) {
        LOGGER.info("Checking SauceDemo cart item count is {}", expectedItemCount);
        cartPage.shouldDisplayCartItemCount(expectedItemCount);
    }

    public void i_should_see_cart_badge_count(int expectedItemCount) {
        LOGGER.info("Checking SauceDemo cart badge count is {}", expectedItemCount);
        cartPage.shouldDisplayCartBadgeCount(expectedItemCount);
    }

    public void i_should_see_cart_products(List<String> expectedProductNames) {
        LOGGER.info("Checking SauceDemo cart products");
        cartPage.shouldDisplayCartProducts(expectedProductNames);
    }

    public void i_should_see_exact_cart_products(List<String> expectedProductNames) {
        LOGGER.info("Checking exact SauceDemo cart products");
        cartPage.shouldDisplayExactlyCartProducts(expectedProductNames);
    }

    public void i_should_see_cart_product_price_and_quantity(String productName, String expectedPrice, int expectedQuantity) {
        LOGGER.info("Checking SauceDemo cart product price and quantity for [{}]", productName);
        cartPage.shouldDisplayCartProductPriceAndQuantity(productName, expectedPrice, expectedQuantity);
    }

    public void i_should_see_cart_product_details(List<Map<String, String>> expectedProducts) {
        LOGGER.info("Checking SauceDemo cart product details");
        cartPage.shouldDisplayCartProductDetails(expectedProducts);
    }

    public void i_should_see_remove_button_for_product(String productName) {
        LOGGER.info("Checking Remove button for cart product [{}]", productName);
        cartPage.shouldDisplayRemoveButtonForProduct(productName);
    }

    public void i_remove_product_from_cart(String productName) {
        LOGGER.info("Removing cart product [{}]", productName);
        cartPage.removeProductFromCart(productName);
    }

    public void i_should_not_see_product_in_cart(String productName) {
        LOGGER.info("Checking cart no longer contains product [{}]", productName);
        cartPage.shouldNotDisplayProductInCart(productName);
    }

    public void i_continue_shopping() {
        LOGGER.info("Continuing shopping from cart page");
        cartPage.continueShopping();
    }

    public void i_checkout() {
        LOGGER.info("Checking out from cart page");
        cartPage.checkout();
    }

    public void i_should_see_cart_footer_buttons() {
        LOGGER.info("Checking cart footer buttons");
        cartPage.shouldDisplayCartFooterButtons();
    }

    public void i_should_see_checkout_information_page() {
        LOGGER.info("Checking checkout information page is displayed");
        cartPage.shouldDisplayCheckoutInformationPage();
    }
}
