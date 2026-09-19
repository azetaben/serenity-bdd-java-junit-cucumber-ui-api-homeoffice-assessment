package homeoffice.actions;

import homeoffice.PageObject.NavigationPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NavigationAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(NavigationAction.class);

    NavigationPage navigationPage;
    private final GenericApplicationActions genericApplicationActions = new GenericApplicationActions();

    public void i_navigate_to(String path) {
        LOGGER.info("Navigating to path [{}]", path);
        navigationPage.navigateToPath(path);
    }

    public void i_should_be_on_page(String pageName) {
        LOGGER.info("Checking current page is [{}]", pageName);
        navigationPage.shouldBeOnPage(pageName);
    }

    public void i_should_see_page_header(String expectedHeader) {
        LOGGER.info("Checking page header is [{}]", expectedHeader);
        navigationPage.shouldDisplayPageHeader(expectedHeader);
    }

    public void i_should_see_page_title(String expectedTitle) {
        LOGGER.info("Checking page title is [{}]", expectedTitle);
        navigationPage.shouldDisplayPageTitle(expectedTitle);
    }

    public void i_should_see_page_url(String expectedPageOrUrl) {
        LOGGER.info("Checking page URL is [{}]", expectedPageOrUrl);
        navigationPage.shouldDisplayPageUrl(expectedPageOrUrl);
    }

    public void i_logout_from_page(String pageName) throws InterruptedException {
        LOGGER.info("Logging out from [{}] page", pageName);
        navigationPage.shouldBeOnPage(pageName);
        navigationPage.openNavigationMenu();
        navigationPage.logout();
    }

    public void i_perform_action_from_page(String actionName, String pageName) throws InterruptedException {
        String normalizedActionName = actionName == null ? "" : actionName.trim().toLowerCase();
        LOGGER.info("Performing [{}] from [{}] page", normalizedActionName, pageName);
        if (normalizedActionName.equals("logout")) {
            i_logout_from_page(pageName);
        } else {
            navigationPage.shouldBeOnPage(pageName);
            genericApplicationActions.clickElement(actionName);
        }
    }

    public void i_should_see_sauce_demo_header_logo() {
        LOGGER.info("Checking SauceDemo header logo");
        navigationPage.shouldDisplayHeaderLogo();
    }

    public void i_open_navigation_menu() throws InterruptedException {
        LOGGER.info("Opening navigation menu");
        navigationPage.openNavigationMenu();
    }

    public void i_should_see_navigation_menu() {
        LOGGER.info("Checking navigation menu is displayed");
        navigationPage.shouldDisplayNavigationMenu();
    }

    public void i_should_see_navigation_menu_links() {
        LOGGER.info("Checking navigation menu links");
        navigationPage.shouldDisplayNavigationMenuLinks();
    }

    public void i_close_navigation_menu() {
        LOGGER.info("Closing navigation menu");
        navigationPage.closeNavigationMenu();
    }

    public void i_should_not_see_navigation_menu() {
        LOGGER.info("Checking navigation menu is hidden");
        navigationPage.shouldHideNavigationMenu();
    }

    public void i_select_all_items_from_navigation_menu() {
        LOGGER.info("Selecting All Items from navigation menu");
        navigationPage.navigateToAllItems();
    }

    public void i_select_about_from_navigation_menu() {
        LOGGER.info("Selecting About from navigation menu");
        navigationPage.navigateToAbout();
    }

    public void i_logout_from_navigation_menu() {
        LOGGER.info("Selecting Logout from navigation menu");
        navigationPage.logout();
    }

    public void i_should_be_logged_out() {
        LOGGER.info("Checking user is logged out");
        navigationPage.shouldDisplayLoginPageAfterLogout();
    }

    public void i_reset_app_state_from_navigation_menu() {
        LOGGER.info("Selecting Reset App State from navigation menu");
        navigationPage.resetAppState();
    }

    public void i_should_see_product_sort_dropdown() {
        LOGGER.info("Checking product sort dropdown is displayed");
        navigationPage.shouldDisplayProductSortDropdown();
    }

    public void i_should_see_product_sort_options() {
        LOGGER.info("Checking product sort dropdown options");
        navigationPage.shouldDisplayProductSortOptions();
    }

    public void i_sort_products_by(String sortOption) {
        LOGGER.info("Sorting products by [{}]", sortOption);
        navigationPage.sortProductsBy(sortOption);
    }

    public void i_should_see_selected_sort_option(String expectedSortOption) {
        LOGGER.info("Checking selected sort option is [{}]", expectedSortOption);
        navigationPage.shouldShowSelectedSortOption(expectedSortOption);
    }

    public void i_should_see_products_sorted_by(String sortOption) {
        LOGGER.info("Checking products are sorted by [{}]", sortOption);
        navigationPage.shouldDisplayProductsSortedBy(sortOption);
    }

    public void i_should_not_see_products_sorted_by(String sortOption) {
        LOGGER.info("Checking products are not sorted by [{}]", sortOption);
        navigationPage.shouldNotDisplayProductsSortedBy(sortOption);
    }
}
