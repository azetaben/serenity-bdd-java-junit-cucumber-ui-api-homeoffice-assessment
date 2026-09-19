package homeoffice.PageObject;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.By;

public class SauceDemoInventory extends PageObject {

    public static final By INVENTORY_ITEM_LABEL  = By.className("inventory_item_name");
    public static final By INVENTORY_ITEM_PRICE  = By.className("inventory_item_price");
    public static final By SORT_DROPDOWN         = By.cssSelector("[data-test='product-sort-container']");
    public static final By SHOPPING_CART_LINK    = By.id("shopping_cart_link");
    public static final By SHOPPING_CART_BADGE   = By.className("shopping_cart_badge");

    public static By addToCartButtonFor(String itemSlug) {
        return By.id("add-to-cart-" + itemSlug);
    }

    public static By removeFromCartButtonFor(String itemSlug) {
        return By.id("remove-" + itemSlug);
    }
}
