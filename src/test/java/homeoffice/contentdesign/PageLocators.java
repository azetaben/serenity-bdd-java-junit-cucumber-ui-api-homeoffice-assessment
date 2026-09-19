package homeoffice.contentdesign;

import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PageLocators {
    public static final By BODY = By.tagName("body");
    public static final By PAGE_HEADING = By.cssSelector("[data-test='title'], .title");
    public static final By LOGIN_LOGO = By.className("login_logo");
    public static final By USERNAME_FIELD = By.cssSelector("input[data-test='username']");
    public static final By PASSWORD_FIELD = By.cssSelector("input[data-test='password']");
    public static final By LOGIN_BUTTON = By.cssSelector("input[data-test='login-button']");
    public static final By LOGIN_ERROR = By.cssSelector("h3[data-test='error']");
    public static final By ACCEPTED_USERNAMES = By.id("login_credentials");
    public static final By PASSWORD_FOR_ALL_USERS = By.className("login_password");
    public static final By INVENTORY_CONTAINER = By.id("inventory_container");
    public static final By INVENTORY_ITEM = By.cssSelector("[data-test='inventory-item']");
    public static final By INVENTORY_ITEM_NAME = By.cssSelector("[data-test='inventory-item-name']");
    public static final By INVENTORY_ITEM_PRICE = By.cssSelector("[data-test='inventory-item-price']");
    public static final By PRODUCT_SORT = By.cssSelector("[data-test='product-sort-container']");
    public static final By SHOPPING_CART_LINK = By.className("shopping_cart_link");
    public static final By CART_LIST = By.className("cart_list");
    public static final By CHECKOUT_STEP_ONE_FORM = By.cssSelector("[data-test='checkout-info-container']");
    public static final By CHECKOUT_STEP_TWO_SUMMARY = By.className("summary_info");
    public static final By CHECKOUT_COMPLETE_HEADER = By.cssSelector("[data-test='complete-header']");
    public static final By CHECKOUT_COMPLETE_TEXT = By.cssSelector("[data-test='complete-text']");
    public static final By BURGER_MENU_BUTTON = By.id("react-burger-menu-btn");
    public static final By BURGER_MENU_LINKS = By.cssSelector(".bm-item.menu-item");
    public static final By BURGER_ALL_ITEMS_LINK = By.id("inventory_sidebar_link");
    public static final By BURGER_ABOUT_LINK = By.id("about_sidebar_link");
    public static final By BURGER_LOGOUT_LINK = By.id("logout_sidebar_link");
    public static final By BURGER_RESET_APP_STATE_LINK = By.id("reset_sidebar_link");
    private static final Logger log = LoggerFactory.getLogger(PageLocators.class);

    private PageLocators() {
    }
}
