package homeoffice.maps;

import homeoffice.utilities.TextUtil;
import java.util.HashMap;
import java.util.Map;

public final class PageReadyLocators {
    private static final Map<String, String> PAGE_READY_LOCATORS = new HashMap<>();

    static {
        register("login", ".login_logo", "login page", "home", "signin", "sign in");
        register("inventory", "[data-test='inventory-container']", "products", "product", "inventory page", "inventory.html");
        register("inventory item", "[data-test='inventory-item']", "inventory-item", "inventory item page", "inventory-item.html", "product details", "product detail");
        register("cart", "[data-test='cart-contents-container']", "cart page", "your cart", "basket", "cart.html");
        register("checkout your information", "[data-test='checkout-info-container']", "checkout information", "checkout step one", "checkout-step-one", "checkout-step-one.html");
        register("checkout overview", "[data-test='checkout-summary-container']", "overview", "checkout step two", "checkout-step-two", "checkout-step-two.html");
        register("checkout complete", "[data-test='checkout-complete-container']", "complete", "checkout done", "checkout-complete", "checkout-complete.html");
        register("footer", "[data-test='footer']", "footer links");
    }

    private PageReadyLocators() {
    }

    public static String getReadyLocatorForPage(String pageName) {
        String locator = PAGE_READY_LOCATORS.get(normalize(pageName));
        if (locator == null) {
            throw new IllegalArgumentException("Unknown page ready locator alias: " + pageName);
        }
        return locator;
    }

    private static void register(String pageName, String readyLocator, String... aliases) {
        PAGE_READY_LOCATORS.put(normalize(pageName), readyLocator);

        for (String alias : aliases) {
            PAGE_READY_LOCATORS.put(normalize(alias), readyLocator);
        }
    }

    private static String normalize(String pageName) {
        if (pageName != null && !pageName.isBlank()) {
            return TextUtil.normalizeAlias(pageName);
        }
        throw new IllegalArgumentException("Page name must not be blank.");
    }
}
