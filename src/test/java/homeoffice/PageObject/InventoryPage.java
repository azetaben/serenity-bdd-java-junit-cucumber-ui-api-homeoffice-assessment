package homeoffice.PageObject;

import net.serenitybdd.core.pages.WebElementFacade;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InventoryPage extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryPage.class);

    private static final String INVENTORY_CONTAINER = "[data-test='inventory-container']";
    private static final String INVENTORY_LIST = "[data-test='inventory-list']";
    private static final String INVENTORY_ITEM = "[data-test='inventory-item']";
    private static final String INVENTORY_ITEM_NAME = "[data-test='inventory-item-name']";
    private static final String INVENTORY_ITEM_DESCRIPTION = "[data-test='inventory-item-desc']";
    private static final String INVENTORY_ITEM_PRICE = "[data-test='inventory-item-price']";
    private static final String INVENTORY_ITEM_IMAGE = ".inventory_item_img img";
    private static final String SHOPPING_CART_LINK = "[data-test='shopping-cart-link']";

    public void shouldDisplayInventoryPage() {
        LOGGER.info("Validating SauceDemo inventory page is displayed");
        shouldDisplayPage("/inventory.html", INVENTORY_CONTAINER, INVENTORY_LIST);
    }

    public void shouldDisplayInventoryItemCount(int expectedItemCount) {
        LOGGER.info("Validating inventory item count is {}", expectedItemCount);
        shouldHaveElementCount(INVENTORY_ITEM, expectedItemCount);
    }

    public void shouldDisplayInventoryItemNames(List<String> expectedProductNames) {
        LOGGER.info("Validating inventory item names: {}", expectedProductNames);
        List<String> actualProductNames = productNames();
        Assertions.assertThat(actualProductNames).containsAll(expectedProductNames);
    }

    public void shouldDisplayProductDetails(String productName, String expectedDescription, String expectedPrice) {
        LOGGER.info("Validating product details for [{}]", productName);
        WebElementFacade product = productCard(productName);
        Assertions.assertThat(product.then(INVENTORY_ITEM_DESCRIPTION).waitUntilVisible().getText())
                .isEqualTo(expectedDescription);
        Assertions.assertThat(product.then(INVENTORY_ITEM_PRICE).waitUntilVisible().getText())
                .isEqualTo(expectedPrice);
    }

    public void shouldDisplayProductImage(String productName) {
        LOGGER.info("Validating product image for [{}]", productName);
        WebElementFacade product = productCard(productName);
        product.then(INVENTORY_ITEM_IMAGE).waitUntilVisible();
        Assertions.assertThat(product.then(INVENTORY_ITEM_IMAGE).getAttribute("alt")).isEqualTo(productName);
    }

    public void shouldDisplayAddToCartButtonForProduct(String productName) {
        LOGGER.info("Validating Add to cart button for [{}]", productName);
        waitForElementClickable(productActionButton("add-to-cart", productName));
    }

    public void addProductToCart(String productName) {
        LOGGER.info("Adding product to cart: {}", productName);
        String addButton = productActionButton("add-to-cart", productName);
        String removeButton = productActionButton("remove", productName);
        waitScrollAndClick(addButton);
        if (!isElementDisplayed(removeButton)) {
            LOGGER.info("Retrying add-to-cart click with JavaScript for [{}]", productName);
            clickWithJavaScript(addButton);
        }
        waitForElementClickable(removeButton);
    }

    public void removeProductFromInventory(String productName) {
        LOGGER.info("Removing product from inventory: {}", productName);
        waitScrollAndClick(productActionButton("remove", productName));
    }

    public void shouldShowRemoveButtonForProduct(String productName) {
        LOGGER.info("Validating Remove button after adding product [{}] to cart", productName);
        waitForElementClickable(productActionButton("remove", productName));
    }

    public void openShoppingCart() {
        LOGGER.info("Opening shopping cart from inventory page");
        clickWithJavaScript(SHOPPING_CART_LINK);
        waitForUrlContaining("/cart.html");
    }

    public void openProductDetails(String productName) {
        LOGGER.info("Opening inventory item detail page for [{}]", productName);
        WebElementFacade productTitle = productCard(productName).then(INVENTORY_ITEM_NAME).waitUntilClickable();
        scrollToElement(productTitle);
        productTitle.click();
        if (!waitUntilUrlContains("/inventory-item.html", 2)) {
            LOGGER.info("Retrying inventory item title click with JavaScript for [{}]", productName);
            evaluateJavascript("arguments[0].click();", productTitle);
        }
        waitForUrlContaining("/inventory-item.html");
    }

    public List<String> productNames() {
        LOGGER.info("Getting all inventory product names");
        return textsFor(INVENTORY_ITEM_NAME);
    }

    private WebElementFacade productCard(String productName) {
        return itemNamed(INVENTORY_ITEM, INVENTORY_ITEM_NAME, productName, "Product in inventory");
    }

    private String productActionButton(String action, String productName) {
        return sauceDemoDataTestButton(action, productName);
    }
}
