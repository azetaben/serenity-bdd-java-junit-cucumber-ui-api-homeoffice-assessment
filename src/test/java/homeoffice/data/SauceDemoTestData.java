package homeoffice.data;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static homeoffice.config.SauceDemoConfiguration.property;
import static java.util.Map.entry;

public final class SauceDemoTestData {
    public static final String DEFAULT_ACCEPTED_USER = "standard user";
    public static final String DEFAULT_CHECKOUT_INFORMATION = "valid customer";
    public static final String LOGIN_PAGE_URL_ALIAS = "SauceDemo login page";
    public static final String ABOUT_PAGE_URL_ALIAS = "SauceDemo about page";

    private static final Map<String, Credentials> USERS = Map.ofEntries(
            entry("standard user", new Credentials(property("user.standard.username"), property("user.password.for.all"))),
            entry("locked user", new Credentials(property("user.locked.out.username"), property("user.password.for.all"))),
            entry("problem user", new Credentials(property("user.problem.username"), property("user.password.for.all"))),
            entry("performance user", new Credentials(property("user.performance.glitch.username"), property("user.password.for.all"))),
            entry("error user", new Credentials(property("user.error.username"), property("user.password.for.all"))),
            entry("visual user", new Credentials(property("user.visual.username"), property("user.password.for.all"))),
            entry("invalid username", new Credentials(property("user.invalid.username"), property("user.password.for.all"))),
            entry("invalid password", new Credentials(property("user.standard.username"), property("user.invalid.password"))),
            entry("both fields empty", new Credentials("", "")),
            entry("missing username", new Credentials("", property("user.password.for.all"))),
            entry("missing password", new Credentials(property("user.standard.username"), "")),
            entry("uppercase username", new Credentials(property("user.uppercase.username"), property("user.password.for.all"))),
            entry("whitespace credentials", new Credentials(property("user.whitespace.username"), property("user.whitespace.password"))),
            entry("sql injection", new Credentials(property("user.sql.injection"), property("user.sql.injection"))),
            entry("script injection", new Credentials(property("user.script.injection"), property("user.password.for.all")))
    );

    private static final Product BACKPACK = productFromProperties("backpack");
    private static final Product BIKE_LIGHT = productFromProperties("bike.light");
    private static final Product BOLT_T_SHIRT = productFromProperties("bolt.t.shirt");
    private static final Product FLEECE_JACKET = productFromProperties("fleece.jacket");
    private static final Product ONESIE = productFromProperties("onesie");
    private static final Product RED_T_SHIRT = productFromProperties("red.t.shirt");

    private static final Map<String, Product> PRODUCTS = Map.ofEntries(
            entry("backpack", BACKPACK),
            entry("Sauce Labs Backpack", BACKPACK),
            entry("sauce-labs-backpack", BACKPACK),
            entry("bike light", BIKE_LIGHT),
            entry("Sauce Labs Bike Light", BIKE_LIGHT),
            entry("sauce-labs-bike-light", BIKE_LIGHT),
            entry("bolt t-shirt", BOLT_T_SHIRT),
            entry("Sauce Labs Bolt T-Shirt", BOLT_T_SHIRT),
            entry("sauce-labs-bolt-t-shirt", BOLT_T_SHIRT),
            entry("fleece jacket", FLEECE_JACKET),
            entry("Sauce Labs Fleece Jacket", FLEECE_JACKET),
            entry("sauce-labs-fleece-jacket", FLEECE_JACKET),
            entry("onesie", ONESIE),
            entry("Sauce Labs Onesie", ONESIE),
            entry("sauce-labs-onesie", ONESIE),
            entry("red t-shirt", RED_T_SHIRT),
            entry("Test.allTheThings() T-Shirt (Red)", RED_T_SHIRT),
            entry("test.allthethings()-t-shirt-(red)", RED_T_SHIRT)
    );

    private static final Map<String, String> URLS = Map.of(
            "SauceDemo login page", property("page.login.url"),
            "SauceDemo inventory page", property("page.inventory.url"),
            "SauceDemo checkout overview page", property("page.checkout.step.two.url"),
            "SauceDemo checkout complete page", property("page.checkout.complete.url"),
            "SauceDemo about page", property("page.about.url")
    );

    private static final Map<String, String> PAGE_TITLES = Map.of(
            "inventory", property("page.products.title")
    );

    private static final Map<String, PageType> PAGES = Map.ofEntries(
            entry("login", PageType.LOGIN),
            entry("home", PageType.LOGIN),
            entry("signin", PageType.LOGIN),
            entry("sign in", PageType.LOGIN),
            entry("saucedemo login", PageType.LOGIN),
            entry("saucedemo login page", PageType.LOGIN),
            entry("inventory", PageType.INVENTORY),
            entry("products", PageType.INVENTORY),
            entry("product", PageType.INVENTORY),
            entry("product inventory", PageType.INVENTORY),
            entry("inventory page", PageType.INVENTORY),
            entry("inventory.html", PageType.INVENTORY),
            entry("saucedemo inventory", PageType.INVENTORY),
            entry("inventory item", PageType.INVENTORY_ITEM),
            entry("inventory-item", PageType.INVENTORY_ITEM),
            entry("inventory item page", PageType.INVENTORY_ITEM),
            entry("inventory-item.html", PageType.INVENTORY_ITEM),
            entry("product detail", PageType.INVENTORY_ITEM),
            entry("product details", PageType.INVENTORY_ITEM),
            entry("cart", PageType.CART),
            entry("shopping cart", PageType.CART),
            entry("your cart", PageType.CART),
            entry("basket", PageType.CART),
            entry("cart.html", PageType.CART),
            entry("checkout information", PageType.CHECKOUT_INFORMATION),
            entry("checkout your information", PageType.CHECKOUT_INFORMATION),
            entry("checkout step one", PageType.CHECKOUT_INFORMATION),
            entry("checkout-step-one", PageType.CHECKOUT_INFORMATION),
            entry("checkout-step-one.html", PageType.CHECKOUT_INFORMATION),
            entry("checkout overview", PageType.CHECKOUT_OVERVIEW),
            entry("checkout step two", PageType.CHECKOUT_OVERVIEW),
            entry("checkout-step-two", PageType.CHECKOUT_OVERVIEW),
            entry("checkout-step-two.html", PageType.CHECKOUT_OVERVIEW),
            entry("overview", PageType.CHECKOUT_OVERVIEW),
            entry("checkout complete", PageType.CHECKOUT_COMPLETE),
            entry("checkout confirmation", PageType.CHECKOUT_COMPLETE),
            entry("checkout-complete", PageType.CHECKOUT_COMPLETE),
            entry("checkout-complete.html", PageType.CHECKOUT_COMPLETE),
            entry("complete", PageType.CHECKOUT_COMPLETE),
            entry("checkout done", PageType.CHECKOUT_COMPLETE),
            entry("about", PageType.ABOUT),
            entry("saucedemo about", PageType.ABOUT),
            entry("saucedemo about page", PageType.ABOUT)
    );

    private static final Map<String, String> LOGIN_ERRORS = Map.of(
            "locked user", property("error.locked.out"),
            "invalid credentials", property("error.wrong.credentials"),
            "missing username", property("error.username.required"),
            "missing password", property("error.password.required"),
            "protected route", property("error.protected.route")
    );

    private static final Map<String, CheckoutInformation> CHECKOUT_INFORMATION = Map.of(
            "valid customer", checkoutInformationFromProperties("valid"),
            "missing first name", new CheckoutInformation("", property("checkout.last.name"), property("checkout.postal.code")),
            "missing last name", new CheckoutInformation(property("checkout.first.name"), "", property("checkout.postal.code")),
            "missing postal code", new CheckoutInformation(property("checkout.first.name"), property("checkout.last.name"), ""),
            "whitespace customer", checkoutInformationFromProperties("whitespace"),
            "long customer", checkoutInformationFromProperties("long"),
            "special character customer", checkoutInformationFromProperties("special.character"),
            "international postal code customer", checkoutInformationFromProperties("international"),
            "script customer", checkoutInformationFromProperties("script")
    );

    private static final Map<String, String> CHECKOUT_ERRORS = Map.of(
            "missing first name", property("error.first.name.required"),
            "missing last name", property("error.last.name.required"),
            "missing postal code", property("error.postal.code.required")
    );

    private static final Map<String, Totals> CHECKOUT_TOTALS = Map.of(
            "backpack", new Totals(property("checkout.backpack.subtotal"), property("checkout.backpack.tax"), property("checkout.backpack.total"))
    );

    private static final Map<String, String> COMPLETE_ORDER_CONTENT = Map.of(
            "success header", property("message.checkout.complete"),
            "dispatch message", property("message.dispatch")
    );

    private static final Map<String, String> FOOTER_LINKS = Map.of(
            "Twitter", property("footer.twitter.url"),
            "Facebook", property("footer.facebook.url"),
            "LinkedIn", property("footer.linkedin.url")
    );

    private static final Map<String, String> FOOTER_CONTENT = Map.of(
            "copyright owner", property("footer.copyright.owner")
    );

    private SauceDemoTestData() {
    }

    private static Product productFromProperties(String key) {
        return new Product(
                property("product." + key + ".slug"),
                property("product." + key + ".name"),
                property("product." + key + ".price"),
                property("product." + key + ".description"));
    }

    private static CheckoutInformation checkoutInformationFromProperties(String key) {
        return new CheckoutInformation(
                property("checkout." + key + ".first.name"),
                property("checkout." + key + ".last.name"),
                property("checkout." + key + ".postal.code"));
    }

    public static Credentials user(String alias) {
        return valueFrom(USERS, alias, "user");
    }

    public static Product product(String alias) {
        return valueFrom(PRODUCTS, alias, "product");
    }

    public static List<Product> products(List<String> aliases) {
        return aliases.stream().map(SauceDemoTestData::product).toList();
    }

    public static String url(String alias) {
        return valueFrom(URLS, alias, "URL");
    }

    public static String pageTitle(String alias) {
        return valueFrom(PAGE_TITLES, alias, "page title");
    }

    public static PageType pageType(String alias) {
        return valueFrom(PAGES, alias, "page");
    }

    public static String loginError(String alias) {
        return valueFrom(LOGIN_ERRORS, alias, "login error");
    }

    public static CheckoutInformation checkoutInformation(String alias) {
        return valueFrom(CHECKOUT_INFORMATION, alias, "checkout information");
    }

    public static String checkoutError(String alias) {
        return valueFrom(CHECKOUT_ERRORS, alias, "checkout error");
    }

    public static Totals checkoutTotals(String productAlias) {
        return valueFrom(CHECKOUT_TOTALS, productAlias, "checkout totals");
    }

    public static String completeOrderContent(String alias) {
        return valueFrom(COMPLETE_ORDER_CONTENT, alias, "checkout complete content");
    }

    public static String footerLink(String network) {
        return valueFrom(FOOTER_LINKS, network, "footer link");
    }

    public static String footerContent(String alias) {
        return valueFrom(FOOTER_CONTENT, alias, "footer content");
    }

    private static <T> T valueFrom(Map<String, T> values, String alias, String dataType) {
        T value = values.get(alias);
        if (value != null) {
            return value;
        }

        String normalizedAlias = normalize(alias);
        return values.entrySet().stream()
                .filter(entry -> normalize(entry.getKey()).equals(normalizedAlias))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown " + dataType + " alias: " + alias));
    }

    private static String normalize(String value) {
        return value.trim().toLowerCase(Locale.ROOT);
    }

    public enum PageType {
        LOGIN,
        INVENTORY,
        INVENTORY_ITEM,
        CART,
        CHECKOUT_INFORMATION,
        CHECKOUT_OVERVIEW,
        CHECKOUT_COMPLETE,
        ABOUT
    }

    public record Credentials(String username, String password) {
    }

    public record Product(String slug, String name, String price, String descriptionText) {
    }

    public record CheckoutInformation(String firstName, String lastName, String postalCode) {
    }

    public record Totals(String subtotal, String tax, String total) {
    }
}
