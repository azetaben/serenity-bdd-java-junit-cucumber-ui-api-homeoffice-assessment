package homeoffice.PageObject;

import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryItemPage extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryItemPage.class);

    private static final String INVENTORY_ITEM_CONTAINER = "[data-test='inventory-container']";
    private static final String INVENTORY_ITEM_DETAILS = "[data-test='inventory-item']";
    private static final String INVENTORY_ITEM_IMAGE = ".inventory_details_img";
    private static final String INVENTORY_ITEM_NAME = "[data-test='inventory-item-name']";
    private static final String INVENTORY_ITEM_DESCRIPTION = "[data-test='inventory-item-desc']";
    private static final String INVENTORY_ITEM_PRICE = "[data-test='inventory-item-price']";
    private static final String ADD_TO_CART_BUTTON = "[data-test='add-to-cart'], [data-test^='add-to-cart-']";
    private static final String REMOVE_BUTTON = "[data-test='remove'], [data-test^='remove-']";
    private static final String BACK_TO_PRODUCTS_BUTTON = "[data-test='back-to-products']";

    public void shouldDisplayInventoryItemPage() {
        LOGGER.info("Validating inventory item detail page is displayed");
        shouldDisplayPage("/inventory-item.html", INVENTORY_ITEM_CONTAINER, INVENTORY_ITEM_DETAILS);
    }

    public void shouldDisplayProductDetails(String productName, String expectedDescription, String expectedPrice) {
        LOGGER.info("Validating inventory item detail values for [{}]", productName);
        shouldHaveText(INVENTORY_ITEM_NAME, productName);
        shouldHaveText(INVENTORY_ITEM_DESCRIPTION, expectedDescription);
        shouldHaveText(INVENTORY_ITEM_PRICE, expectedPrice);
    }

    public void shouldDisplayProductImage(String productName) {
        LOGGER.info("Validating inventory item image for [{}]", productName);
        waitForElementVisible(INVENTORY_ITEM_IMAGE);
        Assertions.assertThat(waitAndGetAttribute(INVENTORY_ITEM_IMAGE, "alt")).isEqualTo(productName);
    }

    public void shouldDisplayRemoveButton() {
        LOGGER.info("Validating Remove button is displayed on inventory item page");
        shouldHaveText(REMOVE_BUTTON, "Remove");
        waitForElementClickable(REMOVE_BUTTON);
    }

    public void shouldDisplayAddToCartButton() {
        LOGGER.info("Validating Add to cart button is displayed on inventory item page");
        shouldHaveText(ADD_TO_CART_BUTTON, "Add to cart");
        waitForElementClickable(ADD_TO_CART_BUTTON);
    }

    public void removeProduct() {
        LOGGER.info("Removing product from inventory item page");
        waitAndClick(REMOVE_BUTTON);
    }

    public void addProductToCart() {
        LOGGER.info("Adding product to cart from inventory item page");
        waitAndClick(ADD_TO_CART_BUTTON);
    }

    public void backToProducts() {
        LOGGER.info("Navigating back to products from inventory item page");
        waitAndClick(BACK_TO_PRODUCTS_BUTTON);
        waitForUrlContaining("/inventory.html");
    }
}
