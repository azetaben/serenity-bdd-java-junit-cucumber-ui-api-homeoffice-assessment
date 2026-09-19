package homeoffice.pagecollections;

import homeoffice.PageObject.*;
import homeoffice.properties.SauceDemoConfig;
import homeoffice.utilities.TextUtil;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public final class PageCollection {
    private static final Map<String, TargetPage> PAGES = new HashMap<>();
    static BasePage currentPage;

    static {
        register("login", "", ".login_logo", LoginPage::new, "home", "signin", "sign in", "login page");
        register("products", "inventory.html", "[data-test='inventory-container']", InventoryPage::new, "inventory", "product", "inventory page", "inventory.html");
        register("inventory item", "inventory-item.html", "[data-test='inventory-item']", InventoryItemPage::new, "inventory-item", "inventory item page", "inventory-item.html", "product details", "product detail");
        register("cart", "cart.html", "[data-test='cart-contents-container']", CartPage::new, "your cart", "basket", "cart page", "cart.html");
        register("checkout your information", "checkout-step-one.html", "[data-test='checkout-info-container']", CheckoutStepOnePage::new, "checkout step one", "checkout-step-one", "checkout-step-one.html", "checkout information");
        register("checkout overview", "checkout-step-two.html", "[data-test='checkout-summary-container']", CheckoutStepTwoPage::new, "checkout step two", "checkout-step-two", "checkout-step-two.html", "overview");
        register("checkout complete", "checkout-complete.html", "[data-test='checkout-complete-container']", CheckoutCompletePage::new, "checkout-complete", "checkout-complete.html", "complete", "checkout done");
        register("footer", "", "[data-test='footer']", FooterPage::new, "footer links");
        register("navigation", "", ".header_label .app_logo", NavigationPage::new, "menu", "header", "nav");
    }

    private PageCollection() {
    }

    public static TargetPage resolve(String pageName) {
        if (pageName != null && !pageName.isBlank()) {
            TargetPage targetPage = PAGES.get(normalize(pageName));
            if (targetPage == null) {
                throw new IllegalArgumentException("Unknown page alias: '" + pageName + "'. Add it to PageCollection.");
            } else {
                return targetPage;
            }
        } else {
            throw new IllegalArgumentException("Page name must not be blank.");
        }
    }

    public static Optional<TargetPage> resolveByUrl(String urlOrPath) {
        String normalizedUrlOrPath = normalize(urlOrPath);
        return normalizedUrlOrPath.isBlank()
                ? Optional.of(resolve("login"))
                : PAGES.values()
                .stream()
                .filter(page -> !page.urlFragment().isBlank())
                .filter(page -> normalizedUrlOrPath.contains(normalize(page.urlFragment())))
                .findFirst();
    }

    public static String urlFor(String pageName) {
        return SauceDemoConfig.absoluteUrlFor(resolve(pageName).urlFragment());
    }

    public static String urlForPageOrLiteral(String pageNameOrUrl) {
        if (pageNameOrUrl == null) {
            return "";
        }

        String value = pageNameOrUrl.trim();
        if (isAbsoluteUrl(value)) {
            return value;
        }

        TargetPage targetPage = PAGES.get(normalize(value));
        return targetPage == null ? value : SauceDemoConfig.absoluteUrlFor(targetPage.urlFragment());
    }

    public static BasePage pageFor(String pageName) {
        currentPage = resolve(pageName).newPage();
        return currentPage;
    }

    public static <T extends BasePage> T pageFor(String pageName, Class<T> pageType) {
        BasePage page = pageFor(pageName);
        if (!pageType.isInstance(page)) {
            throw new IllegalStateException("Configured page " + page.getClass().getSimpleName()
                    + " does not implement " + pageType.getSimpleName() + ".");
        }
        return pageType.cast(page);
    }

    public static void setCurrentPage(BasePage page) {
        currentPage = page;
    }

    private static void register(
            String canonicalName,
            String urlFragment,
            String readyLocator,
            Supplier<? extends BasePage> pageFactory,
            String... aliases) {
        TargetPage page = new TargetPage(canonicalName, urlFragment, readyLocator, pageFactory);
        PAGES.put(normalize(canonicalName), page);

        for (String alias : aliases) {
            PAGES.put(normalize(alias), page);
        }

    }

    private static String normalize(String value) {
        return value == null ? "" : TextUtil.normalizeAlias(value);
    }

    public static BasePage getCurrentPage() {
        if (currentPage == null) {
            currentPage = pageFor("login");
        }

        return currentPage;
    }

    public static <T> T getCurrentPageAs(Class<T> pageType) {
        BasePage page = getCurrentPage();
        if (!pageType.isInstance(page)) {
            String var10002 = page.getClass().getSimpleName();
            throw new IllegalStateException("Current page " + var10002 + " does not implement " + pageType.getSimpleName() + ".");
        } else {
            return pageType.cast(page);
        }
    }

    private static boolean isAbsoluteUrl(String value) {
        return value.startsWith("http://") || value.startsWith("https://");
    }

    public record TargetPage(
            String canonicalName,
            String urlFragment,
            String readyLocator,
            Supplier<? extends BasePage> pageFactory) {

        public BasePage newPage() {
            BasePage page = pageFactory.get();
            WebDriver driver = ThucydidesWebDriverSupport.getDriver();
            return driver == null ? page : page.withDriver(driver);
        }
    }
}
