package homeoffice.maps;

import homeoffice.PageObject.*;
import homeoffice.utilities.TextUtil;

import java.util.HashMap;
import java.util.Map;

public final class PageTitles {
    private static final Map<String, String> PAGE_TITLE_MAP = new HashMap<>();
    private static final String SAUCE_DEMO_TITLE = "Swag Labs";

    static {
        register(LoginPage.class, SAUCE_DEMO_TITLE, "login", "login page", "home", "signin", "sign in");
        register(InventoryPage.class, SAUCE_DEMO_TITLE, "products", "product", "inventory", "inventory page", "inventory.html");
        register(InventoryItemPage.class, SAUCE_DEMO_TITLE, "inventory item", "inventory-item", "inventory item page", "inventory-item.html", "product details", "product detail");
        register(CartPage.class, SAUCE_DEMO_TITLE, "cart", "cart page", "your cart", "basket", "cart.html");
        register(CheckoutStepOnePage.class, SAUCE_DEMO_TITLE, "checkout your information", "checkout information", "checkout step one", "checkout-step-one", "checkout-step-one.html");
        register(CheckoutStepTwoPage.class, SAUCE_DEMO_TITLE, "checkout overview", "overview", "checkout step two", "checkout-step-two", "checkout-step-two.html");
        register(CheckoutCompletePage.class, SAUCE_DEMO_TITLE, "checkout complete", "complete", "checkout done", "checkout-complete", "checkout-complete.html");
        register(FooterPage.class, SAUCE_DEMO_TITLE, "footer", "footer links");
        register(BasePage.class, SAUCE_DEMO_TITLE, "base", "base page", SAUCE_DEMO_TITLE);
    }

    private PageTitles() {
    }

    public static String getTitleForPage(String pageName) {
        if (pageName == null) {
            return "";
        }
        String pageTitle = PAGE_TITLE_MAP.get(normalize(pageName));
        return pageTitle != null ? pageTitle : pageName;
    }

    public static String getTitleForPageClass(Class<?> pageClass) {
        if (pageClass == null) {
            throw new IllegalArgumentException("Page class must not be null.");
        }
        String pageTitle = PAGE_TITLE_MAP.get(normalize(pageClass.getSimpleName()));
        if (pageTitle == null) {
            throw new IllegalArgumentException("Unknown SauceDemo page class: " + pageClass.getName());
        }
        return pageTitle;
    }

    public static boolean hasTitleForPage(String pageName) {
        if (pageName == null) {
            return false;
        }
        return PAGE_TITLE_MAP.containsKey(normalize(pageName));
    }

    private static void register(Class<?> pageClass, String title, String... aliases) {
        PAGE_TITLE_MAP.put(normalize(pageClass.getSimpleName()), title);

        for (String alias : aliases) {
            PAGE_TITLE_MAP.put(normalize(alias), title);
        }
    }

    private static String normalize(String pageName) {
        return TextUtil.normalizeAlias(pageName);
    }
}
