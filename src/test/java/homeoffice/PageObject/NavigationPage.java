package homeoffice.PageObject;

import homeoffice.properties.SauceDemoConfig;
import net.serenitybdd.core.pages.WebElementFacade;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NavigationPage extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(NavigationPage.class);

    public static final String APP_LOGO = ".header_label .app_logo";
    private static final String MENU_BUTTON = "#react-burger-menu-btn";
    private static final String MENU_WRAP = ".bm-menu-wrap";
    private static final String MENU_LIST = ".bm-item-list";
    private static final String ALL_ITEMS_LINK = "[data-test='inventory-sidebar-link']";
    private static final String ABOUT_LINK = "[data-test='about-sidebar-link']";
    private static final String LOGOUT_LINK = "[data-test='logout-sidebar-link']";
    private static final String RESET_APP_STATE_LINK = "[data-test='reset-sidebar-link']";
    private static final String CLOSE_MENU_BUTTON = "#react-burger-cross-btn";
    private static final String LOGIN_BUTTON = "#login-button";
    private static final String PRODUCT_SORT_CONTAINER = "[data-test='product-sort-container']";
    private static final String PRODUCT_SORT_OPTIONS = "[data-test='product-sort-container'] option";
    private static final String INVENTORY_ITEM_NAME = "[data-test='inventory-item-name']";
    private static final String INVENTORY_ITEM_PRICE = "[data-test='inventory-item-price']";

    public void navigateToPath(String path) {
        String url = SauceDemoConfig.absoluteUrlFor(path);
        LOGGER.info("Navigating to [{}]", url);
        clearSauceDemoBrowserStorageIfAvailable();
        openUrl(url);
    }

    private void clearSauceDemoBrowserStorageIfAvailable() {
        try {
            LOGGER.info("Clearing SauceDemo browser storage before navigation");
            evaluateJavascript("window.localStorage.clear(); window.sessionStorage.clear();");
        } catch (RuntimeException exception) {
            LOGGER.debug("Browser storage could not be cleared before navigation", exception);
        }
    }

    public void shouldDisplayHeaderLogo() {
        LOGGER.info("Validating SauceDemo app logo is displayed");
        Assertions.assertThat(waitAndGetText(APP_LOGO)).isEqualTo("Swag Labs");
    }

    public void openNavigationMenu() throws InterruptedException {
        LOGGER.info("Opening SauceDemo navigation menu");
        WebElementFacade menuButton = waitForElementClickable(MENU_BUTTON);
        menuButton.click();
        if (!waitUntilElementDisplayed(MENU_WRAP, 4)) {
            LOGGER.info("Retrying SauceDemo navigation menu open with JavaScript");
            evaluateJavascript("arguments[0].click();", menuButton);
        }
        waitForElementVisible(MENU_WRAP);
        waitForElementVisible(MENU_LIST);
        Thread.sleep(3);
    }

    public void shouldDisplayNavigationMenu() {
        LOGGER.info("Validating SauceDemo navigation menu is displayed");
        waitForElementVisible(MENU_WRAP);
        waitForElementVisible(MENU_LIST);
        Assertions.assertThat(waitAndGetAttribute(MENU_WRAP, "aria-hidden")).isEqualTo("false");
    }

    public void shouldDisplayNavigationMenuLinks() {
        LOGGER.info("Validating SauceDemo navigation menu links");
        List<String> expectedLinks = Arrays.asList("All Items", "About", "Logout", "Reset App State");
        Assertions.assertThat(waitAndGetText(ALL_ITEMS_LINK)).isEqualTo(expectedLinks.get(0));
        Assertions.assertThat(waitAndGetText(ABOUT_LINK)).isEqualTo(expectedLinks.get(1));
        Assertions.assertThat(waitAndGetText(LOGOUT_LINK)).isEqualTo(expectedLinks.get(2));
        Assertions.assertThat(waitAndGetText(RESET_APP_STATE_LINK)).isEqualTo(expectedLinks.get(3));
    }

    public void closeNavigationMenu() {
        LOGGER.info("Closing SauceDemo navigation menu");
        waitAndClick(CLOSE_MENU_BUTTON);
        waitUntilNavigationMenuHidden();
    }

    public void shouldHideNavigationMenu() {
        LOGGER.info("Validating SauceDemo navigation menu is hidden");
        Assertions.assertThat(isNavigationMenuHidden()).isTrue();
    }

    public void navigateToAllItems() {
        LOGGER.info("Navigating to All Items from the SauceDemo navigation menu");
        waitAndClick(ALL_ITEMS_LINK);
        waitForUrlContaining("/inventory.html");
    }

    public void navigateToAbout() {
        LOGGER.info("Navigating to About from the SauceDemo navigation menu");
        waitAndClick(ABOUT_LINK);
        waitForUrlContaining("saucelabs.com");
    }

    public void logout() {
        LOGGER.info("Logging out from the SauceDemo navigation menu");
        waitAndClick(LOGOUT_LINK);
    }

    public void shouldDisplayLoginPageAfterLogout() {
        LOGGER.info("Validating login page after logout");
        waitForElementVisible(LOGIN_BUTTON);
        Assertions.assertThat(getCurrentPageUrl()).doesNotContain("/inventory.html");
    }

    public void resetAppState() {
        LOGGER.info("Resetting SauceDemo application state from navigation menu");
        waitAndClick(RESET_APP_STATE_LINK);
    }

    public void shouldDisplayProductSortDropdown() {
        LOGGER.info("Validating product sort dropdown is displayed");
        waitForElementVisible(PRODUCT_SORT_CONTAINER);
    }

    public void shouldDisplayProductSortOptions() {
        LOGGER.info("Validating product sort dropdown options");
        List<String> expectedOptions = Arrays.asList(
                "Name (A to Z)",
                "Name (Z to A)",
                "Price (low to high)",
                "Price (high to low)"
        );
        List<String> actualOptions = findAll(PRODUCT_SORT_OPTIONS)
                .stream()
                .map(option -> option.getText().trim())
                .collect(Collectors.toList());
        Assertions.assertThat(actualOptions).containsExactlyElementsOf(expectedOptions);
    }

    public void sortProductsBy(String sortOption) {
        LOGGER.info("Sorting inventory products by [{}]", sortOption);
        waitForElementVisible(PRODUCT_SORT_CONTAINER).selectByVisibleText(sortOption);
    }

    public void shouldShowSelectedSortOption(String expectedSortOption) {
        LOGGER.info("Validating selected sort option is [{}]", expectedSortOption);
        Assertions.assertThat(waitForElementVisible(PRODUCT_SORT_CONTAINER).getFirstSelectedOptionVisibleText())
                .isEqualTo(expectedSortOption);
    }

    public void shouldDisplayProductsSortedBy(String sortOption) {
        LOGGER.info("Validating inventory products are sorted by [{}]", sortOption);
        if (sortOption.contains("Name")) {
            shouldDisplayProductsSortedByName(sortOption);
            return;
        }
        shouldDisplayProductsSortedByPrice(sortOption);
    }

    public void shouldNotDisplayProductsSortedBy(String sortOption) {
        LOGGER.info("Validating inventory products are not sorted by [{}]", sortOption);
        if (sortOption.contains("Name")) {
            Assertions.assertThat(productNames())
                    .isNotEqualTo(expectedNamesFor(sortOption));
            return;
        }
        Assertions.assertThat(productPrices())
                .isNotEqualTo(expectedPricesFor(sortOption));
    }

    private void shouldDisplayProductsSortedByName(String sortOption) {
        List<String> actualNames = productNames();
        List<String> expectedNames = expectedNamesFor(sortOption);
        Assertions.assertThat(actualNames).containsExactlyElementsOf(expectedNames);
    }

    private void shouldDisplayProductsSortedByPrice(String sortOption) {
        List<Double> actualPrices = productPrices();
        List<Double> expectedPrices = expectedPricesFor(sortOption);
        Assertions.assertThat(actualPrices).containsExactlyElementsOf(expectedPrices);
    }

    private List<String> expectedNamesFor(String sortOption) {
        List<String> expectedNames = new ArrayList<>(productNames());
        expectedNames.sort(String::compareTo);
        if (sortOption.equals("Name (Z to A)")) {
            expectedNames.sort(Comparator.reverseOrder());
        }
        return expectedNames;
    }

    private List<Double> expectedPricesFor(String sortOption) {
        List<Double> expectedPrices = new ArrayList<>(productPrices());
        expectedPrices.sort(Double::compareTo);
        if (sortOption.equals("Price (high to low)")) {
            expectedPrices.sort(Comparator.reverseOrder());
        }
        return expectedPrices;
    }

    private List<String> productNames() {
        LOGGER.info("Getting product names for sort validation");
        return findAll(INVENTORY_ITEM_NAME)
                .stream()
                .map(productName -> productName.getText().trim())
                .collect(Collectors.toList());
    }

    private List<Double> productPrices() {
        LOGGER.info("Getting product prices for sort validation");
        return findAll(INVENTORY_ITEM_PRICE)
                .stream()
                .map(productPrice -> productPrice.getText().replace("$", "").trim())
                .map(Double::valueOf)
                .collect(Collectors.toList());
    }

    private void waitUntilNavigationMenuHidden() {
        long endTime = System.currentTimeMillis() + 5000;
        while (System.currentTimeMillis() < endTime) {
            if (isNavigationMenuHidden()) {
                return;
            }
            waitABit(100);
        }
        Assertions.assertThat(isNavigationMenuHidden()).isTrue();
    }

    private boolean isNavigationMenuHidden() {
        List<WebElementFacade> menuWrappers = findAll(MENU_WRAP);
        if (menuWrappers.isEmpty()) {
            return true;
        }

        WebElementFacade menuWrapper = menuWrappers.get(0);
        String wrapperAriaHidden = menuWrapper.getAttribute("aria-hidden");
        String wrapperStyle = String.valueOf(menuWrapper.getAttribute("style"));
        String menuListAriaHidden = findAll(MENU_LIST).stream()
                .findFirst()
                .map(menuList -> menuList.getAttribute("aria-hidden"))
                .orElse("");

        return !isElementDisplayed(menuWrapper)
                || "true".equals(wrapperAriaHidden)
                || "true".equals(menuListAriaHidden)
                || wrapperStyle.contains("visibility: hidden")
                || wrapperStyle.contains("translate3d(-100%");
    }

    public void shouldDisplayMenuList(List<String> menuList) {
        String usernamesText = waitAndGetText(MENU_LIST);
        for (String menu : menuList) {
            Assertions.assertThat(usernamesText).contains(menu);
        }
    }
}
