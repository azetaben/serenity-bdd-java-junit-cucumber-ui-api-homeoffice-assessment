package homeoffice.actions;

import homeoffice.PageObject.InventoryItemPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InventoryItemAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryItemAction.class);

    InventoryItemPage inventoryItemPage;

    public void i_should_see_inventory_item_page() {
        LOGGER.info("Checking inventory item detail page is displayed");
        inventoryItemPage.shouldDisplayInventoryItemPage();
    }

    public void i_should_see_inventory_item_details(String productName, String description, String price) {
        LOGGER.info("Checking inventory item details for [{}]", productName);
        inventoryItemPage.shouldDisplayProductDetails(productName, description, price);
    }

    public void i_should_see_inventory_item_image(String productName) {
        LOGGER.info("Checking inventory item image for [{}]", productName);
        inventoryItemPage.shouldDisplayProductImage(productName);
    }

    public void i_should_see_remove_button() {
        LOGGER.info("Checking inventory item Remove button");
        inventoryItemPage.shouldDisplayRemoveButton();
    }

    public void i_should_see_add_to_cart_button() {
        LOGGER.info("Checking inventory item Add to cart button");
        inventoryItemPage.shouldDisplayAddToCartButton();
    }

    public void i_remove_product() {
        LOGGER.info("Removing product from inventory item detail page");
        inventoryItemPage.removeProduct();
    }

    public void i_add_product_to_cart() {
        LOGGER.info("Adding product to cart from inventory item detail page");
        inventoryItemPage.addProductToCart();
    }

    public void i_go_back_to_products() {
        LOGGER.info("Going back to products from inventory item detail page");
        inventoryItemPage.backToProducts();
    }
}
