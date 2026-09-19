package homeoffice.actions;

import homeoffice.PageObject.NavigationPage;
import homeoffice.data.SauceDemoTestData;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.steps.UIInteractionSteps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class CommonSteps extends UIInteractionSteps {

    @Step("Verify app logo is present and visible")
    public void verifyLogoIsPresentAndVisible() {
        waitFor(ExpectedConditions.visibilityOfElementLocated(By.id(NavigationPage.APP_LOGO)));

        WebElement logo = find(NavigationPage.APP_LOGO);
        assertTrue("Logo should be visible", logo.isDisplayed());
        assertFalse("Logo text should not be blank", logo.getText().isBlank());
    }

    @Step("Verify browser page title is {0}")
    public void verifyBrowserTitle(String expectedTitle) {
        assertEquals("Page title mismatch", expectedTitle, getDriver().getTitle());
    }

    @Step("Verify current URL contains {0}")
    public void verifyCurrentUrlContains(String expectedUrlPart) {
        assertTrue(
                "Expected current URL to contain "
                        + expectedUrlPart
                        + " but was "
                        + getDriver().getCurrentUrl(),
                Objects.requireNonNull(getDriver().getCurrentUrl()).contains(expectedUrlPart));
    }

    @Step("Click {0} button")
    public void clickButton(String buttonNameOrAlias) {
        clickMatchingElement(buttonNameOrAlias, "button,input[type='button'],input[type='submit']");
    }

    @Step("Verify {0} button is displayed")
    public void verifyTextOrAliasDisplayed(String textOrAlias) {
        String expectedText = displayTextFor(textOrAlias);
        By expectedVisibleText = visibleText(expectedText);
        waitFor(ExpectedConditions.visibilityOfElementLocated(expectedVisibleText));
       // assertTrue("Expected visible page text: " + expectedText, getDriver().findElements(expectedVisibleText).stream().anyMatch(WebElement::isDisplayed));
    }

    @Step("Click {0} link")
    public void clickLink(String linkNameOrAlias) {
        clickMatchingElement(linkNameOrAlias, "a,button,[role='link'],[role='button']");
    }

    @Step("Click {0} link on {1} page")
    public void clickLinkOnPage(String linkNameOrAlias, String ignoredPageAlias) {
        clickLink(linkNameOrAlias);
    }

    private void clickMatchingElement(String nameOrAlias, String cssSelector) {
        String expected = normalizedToken(nameOrAlias);
        matchingElements(nameOrAlias, cssSelector)
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElseThrow(() -> new AssertionError("No visible element matched: " + nameOrAlias))
                .click();
    }

    private Stream<WebElement> matchingElements(String nameOrAlias, String cssSelector) {
        String expected = normalizedToken(nameOrAlias);
        List<WebElement> candidates = getDriver().findElements(By.cssSelector(cssSelector));
        return candidates.stream()
                .filter(element -> elementTokens(element).anyMatch(token -> token.equals(expected)));
    }

    private Stream<String> elementTokens(WebElement element) {
        return Stream.of(
                        element.getText(),
                        element.getAttribute("value"),
                        element.getAttribute("aria-label"),
                        element.getAttribute("title"),
                        element.getAttribute("id"),
                        element.getAttribute("name"),
                        element.getAttribute("data-test"))
                .filter(Objects::nonNull)
                .filter(value -> !value.isBlank())
                .map(this::normalizedToken);
    }

    private String displayTextFor(String textOrAlias) {
        try {
            return SauceDemoTestData.product(textOrAlias).name();
        } catch (IllegalArgumentException ignored) {
            return textOrAlias;
        }
    }



    private By visibleText(String expectedText) {
        String expectedLiteral = xpathLiteral(expectedText);
        return By.xpath("//*[normalize-space()=" + expectedLiteral + " or contains(normalize-space(), " + expectedLiteral + ")]");
    }

    private String normalizedToken(String value) {
        return value.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]", "");
    }

    private String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }

        return "concat('" + value.replace("'", "',\"'\",'") + "')";
    }

    public void i_should_see_menu_links(List<String> menuList) {
        new NavigationPage().shouldDisplayMenuList(menuList);
    }
}
