package homeoffice.contentdesign;

import org.openqa.selenium.By;

import java.util.Locale;
import java.util.Map;

final class PageCollection {

    private static final Map<String, TargetPage> PAGES = Map.of(
            "login", new TargetPage("login", PageLocators.LOGIN_LOGO),
            "products", new TargetPage("products", PageLocators.INVENTORY_CONTAINER),
            "inventory", new TargetPage("products", PageLocators.INVENTORY_CONTAINER),
            "cart", new TargetPage("cart", PageLocators.CART_LIST),
            "checkout your information", new TargetPage("checkout your information", PageLocators.CHECKOUT_STEP_ONE_FORM),
            "checkout information", new TargetPage("checkout your information", PageLocators.CHECKOUT_STEP_ONE_FORM),
            "checkout overview", new TargetPage("checkout overview", PageLocators.CHECKOUT_STEP_TWO_SUMMARY),
            "checkout complete", new TargetPage("checkout complete", PageLocators.CHECKOUT_COMPLETE_HEADER)
    );

    private PageCollection() {
    }

    static TargetPage resolve(String pageName) {
        TargetPage targetPage = PAGES.get(normalize(pageName));
        if (targetPage == null) {
            throw new IllegalArgumentException("No page collection entry is configured for page: " + pageName);
        }
        return targetPage;
    }

    private static String normalize(String pageName) {
        if (pageName == null || pageName.isBlank()) {
            throw new IllegalArgumentException("Page name must not be blank.");
        }
        return pageName.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", " ");
    }

    record TargetPage(String canonicalName, By readyLocator) {
    }
}
