package homeoffice.contentdesign;

import homeoffice.properties.FrameworkConfig;
import homeoffice.utilities.ProductDataManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Sauce Demo page content - Now uses externalized configuration instead of hardcoding values.
 * All product data is loaded from products.json via ProductDataManager.
 */
public final class PageContent {
    public static final String APP_LOGO = "Swag Labs";
    private static final Logger log = LoggerFactory.getLogger(PageContent.class);
    private static final FrameworkConfig CONFIG = FrameworkConfig.getInstance();
    public static final String ACCEPTED_USERNAMES_LABEL = CONFIG.getString("accepted_usernames_label", "Accepted usernames are:");
    public static final List<String> ACCEPTED_USERNAMES = List.of(
            Objects.requireNonNull(CONFIG.getString("user.standard.username", "standard_user")),
            Objects.requireNonNull(CONFIG.getString("user.locked.out.username", "locked_out_user")),
            Objects.requireNonNull(CONFIG.getString("user.problem.username", "problem_user")),
            Objects.requireNonNull(CONFIG.getString("user.performance.glitch.username", "performance_glitch_user")),
            Objects.requireNonNull(CONFIG.getString("user.error.username", "error_user")),
            Objects.requireNonNull(CONFIG.getString("user.visual.username", "visual_user"))
    );
    public static final String PASSWORD_LABEL = CONFIG.getString("password_label", "Password for all users:");
    public static final String PASSWORD_FOR_ALL_USERS = CONFIG.getString("user.password.for.all", "secret_sauce");
    public static final String USERNAME_PLACEHOLDER = CONFIG.getString("username_placeholder", "Username");
    public static final String PASSWORD_PLACEHOLDER = CONFIG.getString("password_placeholder", "Password");
    public static final String LOGIN_BUTTON = CONFIG.getString("button.label.login", "Login");
    public static final String INVENTORY_HEADING = CONFIG.getString("page.products.heading", "Products");
    public static final List<String> SORT_OPTIONS = List.of(
            Objects.requireNonNull(CONFIG.getString("sort.option.name.az", "Name (A to Z)")),
            Objects.requireNonNull(CONFIG.getString("sort.option.name.za", "Name (Z to A)")),
            Objects.requireNonNull(CONFIG.getString("sort.option.price.low.to.high", "Price (low to high)")),
            Objects.requireNonNull(CONFIG.getString("sort.option.price.high.to.low", "Price (high to low)"))
    );
    public static final String CART_HEADING = CONFIG.getString("page.cart.heading", "Your Cart");
    public static final String CHECKOUT_STEP_ONE_HEADING = CONFIG.getString("page.checkout.step.one.heading", "Checkout: Your Information");
    public static final String CHECKOUT_STEP_TWO_HEADING = CONFIG.getString("page.checkout.step.two.heading", "Checkout: Overview");
    public static final String CHECKOUT_COMPLETE_HEADING = CONFIG.getString("page.checkout.complete.heading", "Checkout: Complete!");
    public static final String CHECKOUT_COMPLETE_MESSAGE = CONFIG.getString("message.checkout.complete", "Thank you for your order!");
    public static final String ADD_TO_CART_BUTTON = CONFIG.getString("button.label.add.to.cart", "Add to cart");
    public static final String REMOVE_BUTTON = CONFIG.getString("button.label.remove", "Remove");
    public static final String CONTINUE_SHOPPING_BUTTON = CONFIG.getString("button.label.continue.shopping", "Continue Shopping");
    public static final String CHECKOUT_BUTTON = CONFIG.getString("button.label.checkout", "Checkout");
    public static final String CONTINUE_BUTTON = CONFIG.getString("button.label.continue", "Continue");
    public static final String FINISH_BUTTON = CONFIG.getString("button.label.finish", "Finish");
    public static final String CANCEL_BUTTON = CONFIG.getString("button.label.cancel", "Cancel");
    public static final String BACK_HOME_BUTTON = CONFIG.getString("button.label.back.home", "Back Home");
    public static final List<String> BURGER_MENU_LINKS = List.of(
            Objects.requireNonNull(CONFIG.getString("menu.item.all.items", "All Items")),
            Objects.requireNonNull(CONFIG.getString("menu.item.about", "About")),
            Objects.requireNonNull(CONFIG.getString("menu.item.logout", "Logout")),
            Objects.requireNonNull(CONFIG.getString("menu.item.reset.app.state", "Reset App State"))
    );
    public static final String REQUIRED_USERNAME_ERROR = CONFIG.getString("error.username.required", "Epic sadface: Username is required");
    public static final String REQUIRED_PASSWORD_ERROR = CONFIG.getString("error.password.required", "Epic sadface: Password is required");
    public static final String LOCKED_OUT_USER_ERROR = CONFIG.getString("error.locked.out", "Epic sadface: Sorry, this user has been locked out.");
    public static final String INVALID_CREDENTIALS_ERROR = CONFIG.getString("error.wrong.credentials", "Epic sadface: Username and password do not match any user in this service");
    private static final ProductDataManager PRODUCT_MANAGER = homeoffice.utilities.ProductDataManager.getInstance();

    private PageContent() {
    }

    /**
     * Product names loaded from externalized products.json configuration
     */
    public static List<String> getProductNames() {
        return PRODUCT_MANAGER.getAllProductNames();
    }

    /**
     * Products loaded from externalized products.json configuration
     */
    public static List<ProductContent> getProducts() {
        return PRODUCT_MANAGER.getAllProducts().stream()
                .map(p -> new ProductContent(p.getName(), p.getPrice()))
                .collect(Collectors.toList());
    }

    public static record ProductContent(String name, String price) {
    }
}
