package homeoffice.PageObject;

import homeoffice.maps.PageTitles;
import homeoffice.pagecollections.PageCollection;
import homeoffice.pagecollections.PageCollection.TargetPage;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.PageObject;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class BasePage extends PageObject {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);
    private static final long DEFAULT_TIMEOUT_IN_SECONDS = 10;
    private static final String PAGE_HEADER = "[data-test='title'], .title";

    protected WebElementFacade waitForElementVisible(String locator) {
        LOGGER.info("Waiting for element to be visible: {}", locator);
        return $(locator).waitUntilVisible();
    }

    protected WebElementFacade waitForElementVisible(String locator, long timeoutInSeconds) {
        LOGGER.info("Waiting for element to be visible within {} seconds: {}", timeoutInSeconds, locator);
        return $(locator).withTimeoutOf(Duration.ofSeconds(timeoutInSeconds)).waitUntilVisible();
    }

    protected WebElementFacade waitForElementClickable(String locator) {
        LOGGER.info("Waiting for element to be clickable: {}", locator);
        return $(locator).waitUntilClickable();
    }

    protected WebElementFacade waitForElementClickable(String locator, long timeoutInSeconds) {
        LOGGER.info("Waiting for element to be clickable within {} seconds: {}", timeoutInSeconds, locator);
        return $(locator).withTimeoutOf(Duration.ofSeconds(timeoutInSeconds)).waitUntilClickable();
    }

    protected WebElementFacade waitForElementPresent(String locator) {
        LOGGER.info("Waiting for element to be present: {}", locator);
        return $(locator).waitUntilPresent();
    }

    protected WebElementFacade waitForElementInvisible(String locator) {
        LOGGER.info("Waiting for element to be invisible: {}", locator);
        return $(locator).waitUntilNotVisible();
    }

    protected boolean waitUntilElementDisplayed(String locator, long timeoutInSeconds) {
        LOGGER.info("Waiting up to {} seconds for element to be displayed: {}", timeoutInSeconds, locator);
        long endTime = System.currentTimeMillis() + Duration.ofSeconds(timeoutInSeconds).toMillis();
        while (System.currentTimeMillis() < endTime) {
            if (isElementDisplayed(locator)) {
                return true;
            }
            waitABit(100);
        }
        return isElementDisplayed(locator);
    }

    protected void waitForPageTitle(String expectedTitle) {
        LOGGER.info("Waiting for page title: {}", expectedTitle);
        waitForTitleToAppear(expectedTitle);
    }

    protected void clickWhenReady(WebElementFacade element) {
        try {
            this.waitFor(element).click();
        } catch (RuntimeException exception) {
            element.click();
        }

    }

    protected void clearAndType(WebElementFacade element, String value) {
        String text = value == null ? "" : value;
        element.click();
        element.clear();
        element.type(text);
    }

    protected void waitForUrlContaining(String expectedUrlText) {
        LOGGER.info("Waiting for URL to contain: {}", expectedUrlText);
        long endTime = System.currentTimeMillis() + Duration.ofSeconds(DEFAULT_TIMEOUT_IN_SECONDS).toMillis();
        while (!getCurrentPageUrl().contains(expectedUrlText) && System.currentTimeMillis() < endTime) {
            waitABit(250);
        }
        if (!getCurrentPageUrl().contains(expectedUrlText)) {
            throw new AssertionError("Expected current URL to contain [" + expectedUrlText + "] but was [" + getCurrentPageUrl() + "]");
        }
    }

    protected boolean waitUntilUrlContains(String expectedUrlText, long timeoutInSeconds) {
        LOGGER.info("Waiting up to {} seconds for URL to contain: {}", timeoutInSeconds, expectedUrlText);
        long endTime = System.currentTimeMillis() + Duration.ofSeconds(timeoutInSeconds).toMillis();
        while (System.currentTimeMillis() < endTime) {
            if (getCurrentPageUrl().contains(expectedUrlText)) {
                return true;
            }
            waitABit(250);
        }
        return getCurrentPageUrl().contains(expectedUrlText);
    }

    public void shouldBeOnPage(String pageName) {
        TargetPage targetPage = PageCollection.resolve(pageName);
        LOGGER.info("Validating current page is [{}] with URL fragment [{}] and ready locator [{}]",
                targetPage.canonicalName(), targetPage.urlFragment(), targetPage.readyLocator());
        waitForUrlContaining(targetPage.urlFragment());
        waitForElementVisible(targetPage.readyLocator());
        PageCollection.setCurrentPage(this);
    }

    public void shouldDisplayPageHeader(String expectedHeader) {
        String expected = expectedHeader == null ? "" : expectedHeader.trim();
        String actual = visibleHeaderTexts().stream().findFirst().orElse("");
        Assertions.assertThat(actual)
                .as("Current page header")
                .isEqualTo(expected);
    }

    public void shouldDisplayPageTitle(String expectedTitle) {
        String expected = PageTitles.getTitleForPage(expectedTitle).trim();
        Assertions.assertThat(getTitle())
                .as("Current page title")
                .isEqualTo(expected);
    }

    public void shouldDisplayPageUrl(String expectedPageOrUrl) {
        String expected = PageCollection.urlForPageOrLiteral(expectedPageOrUrl).trim();
        Assertions.assertThat(getCurrentPageUrl())
                .as("Current page URL")
                .isEqualTo(expected);
    }

    protected void waitAndClick(String locator) {
        LOGGER.info("Clicking element: {}", locator);
        waitForElementClickable(locator).click();
    }

    protected void waitAndType(String locator, String value) {
        LOGGER.info("Typing into element: {}", locator);
        String text = value == null ? "" : value;
        WebElementFacade element = waitForElementVisible(locator);
        clearAndType(element, text);
        if (!waitForElementValue(locator, text)) {
            typeWithActions(locator, text);
            if (!waitForElementValue(locator, text)) {
                typeWithJavaScript(locator, text);
                if (!waitForElementValue(locator, text)) {
                    String actualValue = waitAndGetAttribute(locator, "value");
                    throw new AssertionError("Expected element [" + locator + "] value to be [" + text + "] but was [" + actualValue + "]");
                }
            }
        }
    }

    protected boolean waitForElementValue(String locator, String expectedValue) {
        LOGGER.info("Waiting for element value to be [{}]: {}", expectedValue, locator);
        long endTime = System.currentTimeMillis() + Duration.ofSeconds(DEFAULT_TIMEOUT_IN_SECONDS).toMillis();
        String actualValue = waitAndGetAttribute(locator, "value");
        while (!expectedValue.equals(actualValue) && System.currentTimeMillis() < endTime) {
            waitABit(100);
            actualValue = waitAndGetAttribute(locator, "value");
        }
        return expectedValue.equals(actualValue);
    }

    protected void waitAndClear(String locator) {
        LOGGER.info("Clearing element: {}", locator);
        waitForElementVisible(locator).clear();
    }

    protected void waitAndScrollToElement(String locator) {
        LOGGER.info("Scrolling to element after wait: {}", locator);
        scrollToElement(waitForElementPresent(locator));
    }

    protected void waitScrollAndClick(String locator) {
        LOGGER.info("Scrolling to and clicking element: {}", locator);
        WebElementFacade element = waitForElementClickable(locator);
        scrollToElement(element);
        element.click();
    }

    protected void waitScrollAndType(String locator, String value) {
        LOGGER.info("Scrolling to and typing into element: {}", locator);
        WebElementFacade element = waitForElementVisible(locator);
        scrollToElement(element);
        element.clear();
        element.type(value == null ? "" : value);
    }

    protected void scrollToElement(String locator) {
        LOGGER.info("Scrolling to element: {}", locator);
        scrollToElement(waitForElementPresent(locator));
    }

    protected void scrollToElement(WebElementFacade element) {
        LOGGER.info("Scrolling to resolved element");
        evaluateJavascript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
    }

    protected void scrollToTop() {
        LOGGER.info("Scrolling to top of page");
        evaluateJavascript("window.scrollTo(0, 0);");
    }

    protected void scrollToBottom() {
        LOGGER.info("Scrolling to bottom of page");
        evaluateJavascript("window.scrollTo(0, document.body.scrollHeight);");
    }

    protected void clickWithJavaScript(String locator) {
        LOGGER.info("Clicking element with JavaScript: {}", locator);
        evaluateJavascript("arguments[0].click();", waitForElementPresent(locator));
    }

    protected void typeWithJavaScript(String locator, String value) {
        LOGGER.info("Typing into element with JavaScript: {}", locator);
        WebElementFacade element = waitForElementPresent(locator);
        evaluateJavascript(
                "arguments[0].value = arguments[1];"
                        + "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));"
                        + "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                element,
                value == null ? "" : value);
    }

    protected void clickWithActions(String locator) {
        LOGGER.info("Clicking element with Serenity withAction(): {}", locator);
        WebElementFacade element = waitForElementClickable(locator);
        scrollToElement(element);
        withAction().moveToElement(element).click().perform();
    }

    protected void typeWithActions(String locator, String value) {
        LOGGER.info("Typing into element with Serenity withAction(): {}", locator);
        WebElementFacade element = waitForElementVisible(locator);
        scrollToElement(element);
        element.clear();
        withAction().moveToElement(element).click().sendKeys(value == null ? "" : value).perform();
    }

    protected void hoverOverElement(String locator) {
        LOGGER.info("Hovering over element with Serenity withAction(): {}", locator);
        WebElementFacade element = waitForElementVisible(locator);
        scrollToElement(element);
        withAction().moveToElement(element).perform();
    }

    protected void doubleClickWithActions(String locator) {
        LOGGER.info("Double-clicking element with Serenity withAction(): {}", locator);
        WebElementFacade element = waitForElementClickable(locator);
        scrollToElement(element);
        withAction().doubleClick(element).perform();
    }

    protected void contextClickWithActions(String locator) {
        LOGGER.info("Context-clicking element with Serenity withAction(): {}", locator);
        WebElementFacade element = waitForElementClickable(locator);
        scrollToElement(element);
        withAction().contextClick(element).perform();
    }

    protected String waitAndGetText(String locator) {
        LOGGER.info("Getting text from element: {}", locator);
        return waitForElementVisible(locator).getText();
    }

    protected String waitAndGetAttribute(String locator, String attributeName) {
        LOGGER.info("Getting attribute [{}] from element: {}", attributeName, locator);
        return waitForElementVisible(locator).getAttribute(attributeName);
    }

    protected boolean isElementDisplayed(String locator) {
        LOGGER.info("Checking whether element is displayed: {}", locator);
        List<WebElementFacade> elements = findAll(locator);
        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }

    protected int countElements(String locator) {
        LOGGER.info("Counting elements matching: {}", locator);
        return findAll(locator).size();
    }

    protected void shouldDisplayPage(String expectedUrlText, String... readyLocators) {
        LOGGER.info("Validating page URL contains [{}] and ready locators are visible", expectedUrlText);
        waitForUrlContaining(expectedUrlText);
        for (String readyLocator : readyLocators) {
            waitForElementVisible(readyLocator);
        }
    }

    protected void shouldDisplayPageWithClickableElements(
            String expectedUrlText, List<String> visibleLocators, List<String> clickableLocators) {
        shouldDisplayPage(expectedUrlText, visibleLocators.toArray(String[]::new));
        for (String clickableLocator : clickableLocators) {
            waitForElementClickable(clickableLocator);
        }
    }

    protected void shouldHaveText(String locator, String expectedText) {
        Assertions.assertThat(waitAndGetText(locator)).isEqualTo(expectedText);
    }

    protected void shouldContainText(String locator, String expectedText) {
        Assertions.assertThat(waitAndGetText(locator)).contains(expectedText);
    }

    protected void shouldHaveElementCount(String locator, int expectedCount) {
        Assertions.assertThat(countElements(locator)).isEqualTo(expectedCount);
    }

    protected List<String> textsFor(String locator) {
        LOGGER.info("Getting text values for elements matching: {}", locator);
        return findAll(locator)
                .stream()
                .map(WebElementFacade::getText)
                .map(String::trim)
                .toList();
    }

    protected WebElementFacade itemNamed(
            String itemLocator, String itemNameLocator, String itemName, String itemDescription) {
        LOGGER.info("Finding {} [{}]", itemDescription, itemName);
        return findAll(itemLocator)
                .stream()
                .filter(item -> item.then(itemNameLocator).getText().equals(itemName))
                .findFirst()
                .orElseThrow(() -> new AssertionError(itemDescription + " not found: " + itemName));
    }

    protected void shouldDisplayItemDetails(
            WebElementFacade item, String quantityLocator, String expectedQuantity,
            String descriptionLocator, String expectedDescription,
            String priceLocator, String expectedPrice) {
        if (expectedQuantity != null) {
            Assertions.assertThat(item.then(quantityLocator).waitUntilVisible().getText()).isEqualTo(expectedQuantity);
        }
        if (expectedDescription != null) {
            Assertions.assertThat(item.then(descriptionLocator).waitUntilVisible().getText()).isEqualTo(expectedDescription);
        }
        if (expectedPrice != null) {
            Assertions.assertThat(item.then(priceLocator).waitUntilVisible().getText()).isEqualTo(expectedPrice);
        }
    }

    protected String sauceDemoDataTestButton(String action, String productName) {
        return "[data-test='" + action + "-" + toSauceDemoProductSlug(productName) + "']";
    }

    protected String toSauceDemoProductSlug(String productName) {
        return productName
                .toLowerCase()
                .replaceAll("\\s+", "-")
                .replaceAll("^-|-$", "");
    }

    protected void shouldDisplayExternalLink(
            String locator, String expectedText, String expectedHref, String expectedTarget, String expectedRel) {
        waitForElementVisible(locator);
        shouldHaveText(locator, expectedText);
        Assertions.assertThat(waitAndGetAttribute(locator, "href")).isEqualTo(expectedHref);
        Assertions.assertThat(waitAndGetAttribute(locator, "target")).isEqualTo(expectedTarget);
        Assertions.assertThat(waitAndGetAttribute(locator, "rel")).isEqualTo(expectedRel);
    }

    protected String getCurrentPageUrl() {
        LOGGER.info("Getting current page URL");
        return String.valueOf(evaluateJavascript("return window.location.href;"));
    }

    protected boolean isElementDisplayed(WebElementFacade element) {
        try {
            return element != null && element.isDisplayed();
        } catch (RuntimeException exception) {
            return false;
        }
    }

    private List<String> visibleHeaderTexts() {
        LOGGER.info("Getting visible current page header text");
        return findAll(PAGE_HEADER).stream()
                .filter(this::isElementDisplayed)
                .map(WebElementFacade::getText)
                .map(String::trim)
                .filter(header -> !header.isBlank())
                .toList();
    }


}
