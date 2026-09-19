package homeoffice.maps;


import homeoffice.PageObject.CartPage;
import homeoffice.PageObject.CheckoutCompletePage;
import homeoffice.PageObject.CheckoutStepOnePage;
import homeoffice.PageObject.CheckoutStepTwoPage;
import homeoffice.PageObject.InventoryPage;
import homeoffice.utilities.TextUtil;

import java.util.HashMap;
import java.util.Map;

public final class TablePageToInputPageMap {
    private static final Map<String, String> TABLE_TO_INPUT_PAGE_NAMES = new HashMap<>();
    private static final Map<String, Class<?>> TABLE_TO_INPUT_PAGE_CLASSES = new HashMap<>();

    static {
        register("cart", "inventory", CartPage.class, InventoryPage.class);
        register("cart page", "inventory", CartPage.class, InventoryPage.class);
        register("your cart", "inventory", CartPage.class, InventoryPage.class);
        register("basket", "inventory", CartPage.class, InventoryPage.class);
        register("checkout overview", "checkout information", CheckoutStepOnePage.class, CheckoutStepOnePage.class);
        register("checkout step two", "checkout information", CheckoutStepTwoPage.class, CheckoutStepOnePage.class);
        register("checkout-step-two", "checkout information", CheckoutCompletePage.class, CheckoutStepOnePage.class);
        register("checkout-step-two.html", "checkout information", CheckoutStepTwoPage.class, CheckoutStepOnePage.class);
        register("checkout overview cart items", "cart", CheckoutStepTwoPage.class, CartPage.class);
    }

    private TablePageToInputPageMap() {
    }

    public static String getPageInputNameFromItsRelatedTablePageName(String tablePageName) {
        String pageName = TABLE_TO_INPUT_PAGE_NAMES.get(normalize(tablePageName));
        if (pageName == null) {
            throw new IllegalArgumentException("No SauceDemo input page mapping configured for table page: " + tablePageName);
        } else {
            return pageName;
        }
    }

    public static Class<?> getInputPageClassFromItsRelatedTablePageName(String tablePageName) {
        Class<?> pageClass = TABLE_TO_INPUT_PAGE_CLASSES.get(normalize(tablePageName));
        if (pageClass == null) {
            throw new IllegalArgumentException("No SauceDemo input page class mapping configured for table page: " + tablePageName);
        } else {
            return pageClass;
        }
    }

    private static void register(String tablePageName, String inputPageName, Class<?> tablePageClass, Class<?> inputPageClass) {
        String normalizedTablePageName = normalize(tablePageName);
        TABLE_TO_INPUT_PAGE_NAMES.put(normalizedTablePageName, inputPageName);
        TABLE_TO_INPUT_PAGE_CLASSES.put(normalizedTablePageName, inputPageClass);
        TABLE_TO_INPUT_PAGE_CLASSES.put(normalize(tablePageClass.getSimpleName()), inputPageClass);
    }

    private static String normalize(String pageName) {
        return TextUtil.normalizeAlias(pageName);
    }
}
