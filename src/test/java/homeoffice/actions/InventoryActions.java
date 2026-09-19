package homeoffice.actions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InventoryActions {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryActions.class);

    homeoffice.PageObject.InventoryPage inventoryPage;

    public void i_should_be_on_inventory_page() {
        LOGGER.info("Checking inventory page is displayed");
        inventoryPage.shouldDisplayInventoryPage();
    }

    public void i_should_see_inventory_items(int expectedItemCount) {
        LOGGER.info("Checking inventory item count is {}", expectedItemCount);
        inventoryPage.shouldDisplayInventoryItemCount(expectedItemCount);
    }

    public void i_should_see_inventory_product_names(List<String> expectedProductNames) {
        LOGGER.info("Checking inventory product names");
        inventoryPage.shouldDisplayInventoryItemNames(expectedProductNames);
    }

    public void i_should_see_inventory_product_details(String productName, String description, String price) {
        LOGGER.info("Checking inventory product details for [{}]", productName);
        inventoryPage.shouldDisplayProductDetails(productName, description, price);
    }

    public void i_should_see_inventory_product_image(String productName) {
        LOGGER.info("Checking inventory product image for [{}]", productName);
        inventoryPage.shouldDisplayProductImage(productName);
    }

    public void i_should_see_inventory_product_images(List<String> productNames) {
        LOGGER.info("Checking inventory product images for {}", productNames);
        productNames.forEach(inventoryPage::shouldDisplayProductImage);
    }

    public void i_should_see_add_to_cart_button_for_product(String productName) {
        LOGGER.info("Checking Add to cart button for [{}]", productName);
        inventoryPage.shouldDisplayAddToCartButtonForProduct(productName);
    }

    public void i_should_see_add_to_cart_buttons_for_products(List<String> productNames) {
        LOGGER.info("Checking Add to cart buttons for {}", productNames);
        productNames.forEach(inventoryPage::shouldDisplayAddToCartButtonForProduct);
    }

    public void i_add_product_to_cart(String productName) {
        LOGGER.info("Adding inventory product to cart: {}", productName);
        inventoryPage.addProductToCart(productName);
    }

    public void i_add_products_to_cart(List<String> productNames) {
        LOGGER.info("Adding inventory products to cart: {}", productNames);
        productNames.forEach(inventoryPage::addProductToCart);
    }

    public void i_remove_product_from_inventory(String productName) {
        LOGGER.info("Removing inventory product from cart: {}", productName);
        inventoryPage.removeProductFromInventory(productName);
    }

    public void i_should_see_remove_button_for_product(String productName) {
        LOGGER.info("Checking Remove button for [{}]", productName);
        inventoryPage.shouldShowRemoveButtonForProduct(productName);
    }

    public void i_open_shopping_cart() {
        LOGGER.info("Opening shopping cart");
        inventoryPage.openShoppingCart();
    }

    public void i_open_inventory_item_details(String productName) {
        LOGGER.info("Opening inventory item details for [{}]", productName);
        inventoryPage.openProductDetails(productName);
    }
}
