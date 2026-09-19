package homeoffice.contentdesign;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.WebElementState;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;
import java.util.stream.Stream;

public class ContentActions {
    private static final Logger log = LoggerFactory.getLogger(ContentActions.class);
    private static final PageObject PAGE = new PageObject() {
    };

    public List<String> verifyLoginPageContent() {
        List<String> issues = new ArrayList<>();
        this.verifyPageTitle(issues);
        this.verifyCurrentUrlContains(issues, "saucedemo.com");
        this.addIfNotEquals(issues, "Unexpected app logo", PageContent.APP_LOGO, this.waitUntilVisible(PageLocators.LOGIN_LOGO).getText().trim());
        this.verifyInputPlaceholder(issues, PageLocators.USERNAME_FIELD, PageContent.USERNAME_PLACEHOLDER);
        this.verifyInputPlaceholder(issues, PageLocators.PASSWORD_FIELD, PageContent.PASSWORD_PLACEHOLDER);
        this.addIfNotEquals(issues, "Unexpected login button text", PageContent.LOGIN_BUTTON, this.waitUntilVisible(PageLocators.LOGIN_BUTTON).getDomAttribute("value"));
        this.verifyAcceptedUsernames(issues);
        this.verifyPasswordForAllUsers(issues);
        return issues;
    }

    public List<String> verifyInventoryPageContent() {
        List<String> issues = new ArrayList<>();
        this.verifyPageTitle(issues);
        this.verifyCurrentUrlContains(issues, "/inventory.html");
        this.verifyPageHeading(issues, PageContent.INVENTORY_HEADING);
        this.waitUntilVisible(PageLocators.INVENTORY_CONTAINER);
        this.verifyAllProductsDisplayed(issues);
        this.verifySortOptions(issues);
        this.addIfFalse(issues, "Shopping cart link is not displayed", this.isDisplayed(PageLocators.SHOPPING_CART_LINK));
        return issues;
    }

    public List<String> verifyCartPageContent() {
        List<String> issues = new ArrayList<>();
        this.verifyPageTitle(issues);
        this.verifyCurrentUrlContains(issues, "/cart.html");
        this.verifyPageHeading(issues, PageContent.CART_HEADING);
        this.waitUntilVisible(PageLocators.CART_LIST);
        this.verifyBodyContains(issues, PageContent.CONTINUE_SHOPPING_BUTTON);
        this.verifyBodyContains(issues, PageContent.CHECKOUT_BUTTON);
        return issues;
    }

    public List<String> verifyCheckoutStepOneContent() {
        List<String> issues = new ArrayList<>();
        this.verifyPageTitle(issues);
        this.verifyCurrentUrlContains(issues, "/checkout-step-one.html");
        this.verifyPageHeading(issues, PageContent.CHECKOUT_STEP_ONE_HEADING);
        this.waitUntilVisible(PageLocators.CHECKOUT_STEP_ONE_FORM);
        return issues;
    }

    public List<String> verifyCheckoutStepTwoContent() {
        List<String> issues = new ArrayList<>();
        this.verifyPageTitle(issues);
        this.verifyCurrentUrlContains(issues, "/checkout-step-two.html");
        this.verifyPageHeading(issues, PageContent.CHECKOUT_STEP_TWO_HEADING);
        this.waitUntilVisible(PageLocators.CHECKOUT_STEP_TWO_SUMMARY);
        this.verifyBodyContains(issues, PageContent.FINISH_BUTTON);
        this.verifyBodyContains(issues, PageContent.CANCEL_BUTTON);
        return issues;
    }

    public List<String> verifyCheckoutCompleteContent() {
        List<String> issues = new ArrayList<>();
        this.verifyPageTitle(issues);
        this.verifyCurrentUrlContains(issues, "/checkout-complete.html");
        this.verifyPageHeading(issues, PageContent.CHECKOUT_COMPLETE_HEADING);
        this.addIfNotEquals(issues, "Unexpected checkout complete message", PageContent.CHECKOUT_COMPLETE_MESSAGE, this.waitUntilVisible(PageLocators.CHECKOUT_COMPLETE_HEADER).getText().trim());
        this.addIfFalse(issues, "Checkout complete text is not displayed", this.isDisplayed(PageLocators.CHECKOUT_COMPLETE_TEXT));
        this.verifyBodyContains(issues, PageContent.BACK_HOME_BUTTON);
        return issues;
    }

    public List<String> verifyBurgerMenuContent() {
        List<String> issues = new ArrayList<>();
        List<String> actualLinks = this.burgerMenuLinks();
        this.addIfFalse(issues, "Expected burger menu links " + PageContent.BURGER_MENU_LINKS + " but found " + actualLinks, actualLinks.containsAll(PageContent.BURGER_MENU_LINKS));
        return issues;
    }

    public List verifyExpectedContentForPage(String pageName) {
        PageCollection.TargetPage targetPage = PageCollection.resolve(pageName);
        this.waitUntilVisible(targetPage.readyLocator());
        List var10000;
        switch (targetPage.canonicalName()) {
            case "login" -> var10000 = this.verifyLoginPageContent();
            case "products" -> var10000 = this.verifyInventoryPageContent();
            case "cart" -> var10000 = this.verifyCartPageContent();
            case "checkout your information" -> var10000 = this.verifyCheckoutStepOneContent();
            case "checkout overview" -> var10000 = this.verifyCheckoutStepTwoContent();
            case "checkout complete" -> var10000 = this.verifyCheckoutCompleteContent();
            default -> throw new IllegalArgumentException("No content assertions are configured for page: " + pageName);
        }

        return var10000;
    }

    public String getLoginErrorMessage() {
        return this.waitUntilVisible(PageLocators.LOGIN_ERROR).getText().trim();
    }

    public String expectedLoginErrorForType(String errorType) {
        // Map error type variations to externalized error messages from PageContent
        String normalizedType = this.normalizeLoginErrorType(errorType);

        return switch (normalizedType) {
            case "required username", "username required", "missing username", "epic sadface: username is required" ->
                    PageContent.REQUIRED_USERNAME_ERROR;
            case "required password", "password required", "missing password", "epic sadface: password is required" ->
                    PageContent.REQUIRED_PASSWORD_ERROR;
            case "locked out", "locked out user", "epic sadface: sorry, this user has been locked out" ->
                    PageContent.LOCKED_OUT_USER_ERROR;
            case "invalid credentials", "wrong credentials",
                 "epic sadface: username and password do not match any user in this service" ->
                    PageContent.INVALID_CREDENTIALS_ERROR;
            default -> throw new IllegalArgumentException("Unknown SauceDemo login error type: " + errorType);
        };
    }

    private String normalizeLoginErrorType(String errorType) {
        if (errorType == null) {
            return "";
        } else {
            String normalized = errorType.trim().toLowerCase(Locale.ROOT).replaceAll("\\s+", " ");
            return normalized.endsWith(".") ? normalized.substring(0, normalized.length() - 1) : normalized;
        }
    }

    private void verifyAllProductsDisplayed(List<String> issues) {
        List<PageContent.ProductContent> actualProducts = this.waitForVisibleElements().stream().map((item) -> new PageContent.ProductContent(((WebElementFacade) item.thenFindAll(new By[]{PageLocators.INVENTORY_ITEM_NAME}).get(0)).getText().trim(), ((WebElementFacade) item.thenFindAll(new By[]{PageLocators.INVENTORY_ITEM_PRICE}).get(0)).getText().trim())).toList();
        this.addIfNotEquals(issues, "Unexpected SauceDemo product content", this.sortedProductContent(PageContent.getProducts()), this.sortedProductContent(actualProducts));
    }

    private List<PageContent.ProductContent> sortedProductContent(List<PageContent.ProductContent> products) {
        return products.stream()
                .sorted(Comparator.comparing(PageContent.ProductContent::name)
                        .thenComparing(PageContent.ProductContent::price))
                .toList();
    }

    private void verifySortOptions(List<String> issues) {
        Select sortDropdown = new Select(this.waitUntilVisible(PageLocators.PRODUCT_SORT));
        List<String> actualOptions = sortDropdown.getOptions().stream().map(WebElement::getText).map(String::trim).toList();
        this.addIfNotEquals(issues, "Unexpected SauceDemo sort options", PageContent.SORT_OPTIONS, actualOptions);
    }

    private void verifyPageTitle(List<String> issues) {
        this.addIfNotEquals(issues, "Unexpected page title", PageContent.APP_LOGO, ThucydidesWebDriverSupport.getDriver().getTitle());
    }

    private void verifyPageHeading(List<String> issues, String expectedHeading) {
        this.addIfNotEquals(issues, "Unexpected page heading", expectedHeading, this.waitUntilVisible(PageLocators.PAGE_HEADING).getText().trim());
    }

    private void verifyCurrentUrlContains(List<String> issues, String expectedUrlFragment) {
        String currentUrl = (String) Objects.requireNonNullElse(ThucydidesWebDriverSupport.getDriver().getCurrentUrl(), "");
        this.addIfFalse(issues, "Expected URL to contain '" + expectedUrlFragment + "' but was '" + currentUrl + "'", currentUrl.contains(expectedUrlFragment));
    }

    private void verifyAcceptedUsernames(List<String> issues) {
        String acceptedUsernamesText = this.waitUntilVisible(PageLocators.ACCEPTED_USERNAMES).getText();
        this.addIfFalse(issues, "Accepted usernames label is not displayed", acceptedUsernamesText.contains("Accepted usernames are:"));

        for (String username : PageContent.ACCEPTED_USERNAMES) {
            this.addIfFalse(issues, "Accepted username is not displayed: " + username, acceptedUsernamesText.contains(username));
        }

    }

    private void verifyPasswordForAllUsers(List<String> issues) {
        String passwordText = this.waitUntilVisible(PageLocators.PASSWORD_FOR_ALL_USERS).getText();
        assert PageContent.PASSWORD_LABEL != null;
        this.addIfFalse(issues, "Password label is not displayed", passwordText.contains(PageContent.PASSWORD_LABEL));
        assert PageContent.PASSWORD_FOR_ALL_USERS != null;
        this.addIfFalse(issues, "Password for all users is not displayed", passwordText.contains(PageContent.PASSWORD_FOR_ALL_USERS));
    }

    private void verifyInputPlaceholder(List<String> issues, By locator, String expectedPlaceholder) {
        this.addIfNotEquals(issues, "Unexpected placeholder for " + locator, expectedPlaceholder, this.waitUntilVisible(locator).getDomAttribute("placeholder"));
    }

    private void verifyBodyContains(List<String> issues, String expectedText) {
        String bodyText = this.waitUntilVisible(PageLocators.BODY).getText();
        this.addIfFalse(issues, "Expected text was not displayed: " + expectedText, bodyText.contains(expectedText));
    }

    private List<String> burgerMenuLinks() {
        this.ensureBurgerMenuLinksAreVisible();
        Set<String> renderedLinks = new LinkedHashSet<>(this.mountedMenuLinkTexts());
        return (List<String>) (!renderedLinks.isEmpty() ? new ArrayList(renderedLinks) : this.fallbackMenuLinkLabels());
    }

    private void ensureBurgerMenuLinksAreVisible() {
        if (this.visibleMenuLinkTexts().isEmpty()) {
            this.waitUntilVisible(PageLocators.BURGER_MENU_BUTTON).click();
        }

        (new WebDriverWait(ThucydidesWebDriverSupport.getDriver(), Duration.ofSeconds(5L))).until((driver) -> this.mountedMenuLinkTexts().containsAll(PageContent.BURGER_MENU_LINKS));
    }

    private List<String> visibleMenuLinkTexts() {
        return this.findAll(PageLocators.BURGER_MENU_LINKS).stream().filter(WebElement::isDisplayed).map(WebElementFacade::getText).map(String::trim).filter((text) -> !text.isBlank()).toList();
    }

    private List<String> mountedMenuLinkTexts() {
        return this.findAll(PageLocators.BURGER_MENU_LINKS).stream().map(WebElementFacade::getText).map(String::trim).filter((text) -> !text.isBlank()).toList();
    }

    private List<String> fallbackMenuLinkLabels() {
        return Stream.of(this.menuLabelIfPresent(PageLocators.BURGER_ALL_ITEMS_LINK, "All Items"), this.menuLabelIfPresent(PageLocators.BURGER_ABOUT_LINK, "About"), this.menuLabelIfPresent(PageLocators.BURGER_LOGOUT_LINK, "Logout"), this.menuLabelIfPresent(PageLocators.BURGER_RESET_APP_STATE_LINK, "Reset App State")).filter((text) -> !text.isBlank()).toList();
    }

    private String menuLabelIfPresent(By locator, String label) {
        return this.findAll(locator).stream().anyMatch(WebElement::isDisplayed) ? label : "";
    }

    private void addIfNotEquals(List<String> issues, String message, Object expected, Object actual) {
        if (!Objects.equals(expected, actual)) {
            issues.add(message + ". Expected: " + expected + ", actual: " + actual);
        }

    }

    private void addIfFalse(List<String> issues, String message, boolean condition) {
        if (!condition) {
            issues.add(message);
        }

    }

    private WebElementFacade waitUntilVisible(By locator) {
        WebElementFacade element = PAGE.withDriver(ThucydidesWebDriverSupport.getDriver()).find(locator);
        element.waitUntilVisible();
        return element;
    }

    private List<WebElementFacade> waitForVisibleElements() {
        return this.findAll(PageLocators.INVENTORY_ITEM).stream().filter(WebElementState::isVisible).toList();
    }

    private boolean isDisplayed(By locator) {
        return this.findAll(locator).stream().anyMatch(WebElement::isDisplayed);
    }

    private List<WebElementFacade> findAll(By locator) {
        return PAGE.withDriver(ThucydidesWebDriverSupport.getDriver()).findAll(locator);
    }
}
