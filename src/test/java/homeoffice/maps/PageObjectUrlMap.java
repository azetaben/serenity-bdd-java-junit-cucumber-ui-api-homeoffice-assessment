package homeoffice.maps;

import homeoffice.properties.SauceDemoConfig;
import homeoffice.utilities.TextUtil;

import java.util.HashMap;
import java.util.Map;

public final class PageObjectUrlMap {
    private static final Map<String, String> PAGE_URL_MAP = new HashMap<>();

    static {
        register("login", "");
        register("home", "");
        register("signin", "");
        register("sign in", "");
        register("products", "inventory.html");
        register("inventory", "inventory.html");
        register("inventory page", "inventory.html");
        register("inventory.html", "inventory.html");
        register("inventory item", "inventory-item.html");
        register("inventory-item", "inventory-item.html");
        register("inventory item page", "inventory-item.html");
        register("inventory-item.html", "inventory-item.html");
        register("product details", "inventory-item.html");
        register("product detail", "inventory-item.html");
        register("cart", "cart.html");
        register("cart page", "cart.html");
        register("basket", "cart.html");
        register("your cart", "cart.html");
        register("cart.html", "cart.html");
        register("checkout your information", "checkout-step-one.html");
        register("checkout information", "checkout-step-one.html");
        register("checkout step one", "checkout-step-one.html");
        register("checkout-step-one", "checkout-step-one.html");
        register("checkout-step-one.html", "checkout-step-one.html");
        register("checkout overview", "checkout-step-two.html");
        register("checkout step two", "checkout-step-two.html");
        register("checkout-step-two", "checkout-step-two.html");
        register("checkout-step-two.html", "checkout-step-two.html");
        register("checkout complete", "checkout-complete.html");
        register("checkout-complete", "checkout-complete.html");
        register("checkout-complete.html", "checkout-complete.html");
        register("complete", "checkout-complete.html");
        register("checkout done", "checkout-complete.html");
        register("footer", "");
        register("footer links", "");
    }

    private PageObjectUrlMap() {
    }

    public static String getUrlForPage(String pageName) {
        String pageUrl = PAGE_URL_MAP.get(normalize(pageName));
        if (pageUrl == null) {
            throw new IllegalArgumentException("Unknown SauceDemo page: " + pageName);
        } else {
            return pageUrl;
        }
    }

    public static String getUrlForPageOrLiteral(String pageNameOrUrl) {
        if (pageNameOrUrl == null) {
            return "";
        }

        String value = pageNameOrUrl.trim();
        if (isAbsoluteUrl(value)) {
            return value;
        }

        String pageUrl = PAGE_URL_MAP.get(normalize(value));
        return pageUrl != null ? pageUrl : value;
    }

    private static void register(String pageName, String path) {
        PAGE_URL_MAP.put(normalize(pageName), absoluteUrlFor(path));
    }

    private static String absoluteUrlFor(String path) {
        return SauceDemoConfig.absoluteUrlFor(path);
    }

    private static boolean isAbsoluteUrl(String value) {
        return value.startsWith("http://") || value.startsWith("https://");
    }

    private static String replacePlaceholder(String url, String placeholder, String value) {
        return url.replace(placeholder, value == null ? "" : value);
    }

    private static String normalize(String pageName) {
        if (pageName != null && !pageName.isBlank()) {
            return TextUtil.normalizeAlias(pageName);
        } else {
            throw new IllegalArgumentException("Page name must not be blank.");
        }
    }
}
