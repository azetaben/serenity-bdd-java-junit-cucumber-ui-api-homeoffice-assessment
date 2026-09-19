package homeoffice.maps;


import homeoffice.PageObject.*;
import homeoffice.utilities.TextUtil;

import java.util.HashMap;
import java.util.Map;

public final class PageHeadings {
    private static final Map<String, String> PAGE_HEADING_MAP = new HashMap<>();

    static {
        register(LoginPage.class, "Swag Labs", "login", "login page", "home", "signin", "sign in");
        register(InventoryPage.class, "Products", "products", "product", "inventory", "inventory page", "inventory.html");
        register(InventoryItemPage.class, "Product details", "inventory item", "inventory-item", "inventory item page", "inventory-item.html", "product details", "product detail");
        register(CartPage.class, "Your Cart", "cart", "cart page", "your cart", "basket", "cart.html");
        register(CheckoutStepOnePage.class, "Checkout: Your Information", "checkout your information", "checkout information", "checkout step one", "checkout-step-one", "checkout-step-one.html");
        register(CheckoutStepTwoPage.class, "Checkout: Overview", "checkout overview", "overview", "checkout step two", "checkout-step-two", "checkout-step-two.html");
        register(CheckoutCompletePage.class, "Checkout: Complete!", "checkout complete", "complete", "checkout done", "checkout-complete", "checkout-complete.html");
        register(FooterPage.class, "Footer", "footer", "footer links");
        register(BasePage.class, "Swag Labs", "base", "base page");
    }

    private PageHeadings() {
    }

    public static String getHeadingForPage(String pageName) {
        String pageHeading = PAGE_HEADING_MAP.get(normalize(pageName));
        return pageHeading != null ? pageHeading : pageName;
    }

    public static String getHeadingForPageClass(Class<?> pageClass) {
        if (pageClass == null) {
            throw new IllegalArgumentException("Page class must not be null.");
        } else {
            String pageHeading = PAGE_HEADING_MAP.get(normalize(pageClass.getSimpleName()));
            if (pageHeading == null) {
                throw new IllegalArgumentException("Unknown SauceDemo page class: " + pageClass.getName());
            } else {
                return pageHeading;
            }
        }
    }

    public static boolean hasHeadingForPage(String pageName) {
        return PAGE_HEADING_MAP.containsKey(normalize(pageName));
    }

    private static void register(Class<?> pageClass, String heading, String... aliases) {
        PAGE_HEADING_MAP.put(normalize(pageClass.getSimpleName()), heading);

        for (String alias : aliases) {
            PAGE_HEADING_MAP.put(normalize(alias), heading);
        }

    }

    private static String normalize(String pageName) {
        return TextUtil.normalizeAlias(pageName);
    }
}
