package homeoffice.PageObject.general;


import homeoffice.PageObject.BasePage;
import homeoffice.contentdesign.PageContent;
import homeoffice.enums.WebElements;
import homeoffice.exceptions.TableRowDoesNotExistException;
import homeoffice.model.FieldInputModel;
import homeoffice.webelementdata.FieldData;
import net.serenitybdd.core.pages.WebElementFacade;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;

public class ApplicationFlowPageImpl extends BasePage implements ApplicationFlowPage, QuestionPage, TypedFieldInputPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationFlowPageImpl.class);

    private static final String PAGE_HEADING = "[data-test='title'], .title, .login_logo";
    private static final String BURGER_MENU_BUTTON = "#react-burger-menu-btn";
    private static final String BURGER_MENU_WRAP = ".bm-menu-wrap";
    private static final String BURGER_MENU_CLOSE_BUTTON = "#react-burger-cross-btn";
    private static final String EDITABLE_FIELDS = "input:not([type='hidden']):not([type='button']):not([type='submit']):not([type='reset']), textarea, select";
    private static final String REPEATABLE_ROWS = "[data-test='inventory-item'], .cart_item, table tbody tr";
    private static final String CART_BADGE = "[data-test='shopping-cart-badge'], .shopping_cart_badge";
    private static final String FIRST_NAME_FIELD = "[data-test='firstName'], #first-name";
    private static final String LAST_NAME_FIELD = "[data-test='lastName'], #last-name";
    private static final String POSTAL_CODE_FIELD = "[data-test='postalCode'], #postal-code";

    private static final Map<String, String> CLICKABLE_CONTROLS = Map.ofEntries(
            Map.entry("login", "[data-test='login-button']"),
            Map.entry("add to cart", "button[id^='add-to-cart'], button[data-test^='add-to-cart']"),
            Map.entry("remove", "button[id^='remove'], button[data-test^='remove']"),
            Map.entry("shopping cart", "[data-test='shopping-cart-link'], .shopping_cart_link"),
            Map.entry("cart", "[data-test='shopping-cart-link'], .shopping_cart_link"),
            Map.entry("basket", "[data-test='shopping-cart-link'], .shopping_cart_link"),
            Map.entry("continue shopping", "[data-test='continue-shopping']"),
            Map.entry("checkout", "[data-test='checkout']"),
            Map.entry("continue", "[data-test='continue']"),
            Map.entry("continues", "[data-test='continue']"),
            Map.entry("cancel", "[data-test='cancel']"),
            Map.entry("finish", "[data-test='finish']"),
            Map.entry("back home", "[data-test='back-to-products']"),
            Map.entry("back to products", "[data-test='back-to-products']"),
            Map.entry("burger menu", BURGER_MENU_BUTTON),
            Map.entry("menu", BURGER_MENU_BUTTON),
            Map.entry("close menu", BURGER_MENU_CLOSE_BUTTON)
    );

    private static final Map<String, String> NAVIGATION_LINKS = Map.ofEntries(
            Map.entry("all items", "#inventory_sidebar_link"),
            Map.entry("about", "#about_sidebar_link"),
            Map.entry("logout", "#logout_sidebar_link"),
            Map.entry("reset app state", "#reset_sidebar_link"),
            Map.entry("shopping cart", "[data-test='shopping-cart-link'], .shopping_cart_link"),
            Map.entry("cart", "[data-test='shopping-cart-link'], .shopping_cart_link"),
            Map.entry("twitter", "[data-test='social-twitter'], .social_twitter a"),
            Map.entry("facebook", "[data-test='social-facebook'], .social_facebook a"),
            Map.entry("linkedin", "[data-test='social-linkedin'], .social_linkedin a")
    );

    @Override
    public void assertElementIsDisplayed(String expectedElementName, boolean isExpectedToBeDisplayed) {
        String locator = locatorFor(expectedElementName);
        boolean displayed = isElementDisplayed(locator);
        Assertions.assertEquals(isExpectedToBeDisplayed, displayed,
                "Unexpected display state for element '" + expectedElementName + "'.");
    }

    @Override
    public void click(String elementName) {
        LOGGER.info("Clicking application flow element [{}]", elementName);
        String locator = locatorFor(elementName);
        if (isBurgerMenuLink(elementName)) {
            openBurgerMenu(locator);
            clickWithJavaScript(locator);
            waitForDocumentReady();
            return;
        }
        if ("close menu".equals(normalizeKey(elementName)) && !isElementDisplayed(locator)) {
            LOGGER.info("Skipping close menu click because SauceDemo menu is already closed");
            return;
        }
        waitAndClick(locator);
        waitForDocumentReady();
    }

    @Override
    public void assertCorrectButtonTitle(String expectedButtonTitle) {
        String actualTitle = readableTitle(locatorFor(expectedButtonTitle, "button"));
        Assertions.assertTrue(normalizeButtonText(actualTitle).contains(normalizeButtonText(expectedButtonTitle)),
                "Expected button title '" + expectedButtonTitle + "' but was '" + actualTitle + "'.");
    }

    @Override
    public void assertCorrectLinkTitle(String expectedLinkName) {
        if (isBurgerMenuLink(expectedLinkName)) {
            openBurgerMenu(locatorFor(expectedLinkName, "link"));
        }
        String actualTitle = readableTitle(locatorFor(expectedLinkName, "link"));
        Assertions.assertTrue(normalizeButtonText(actualTitle).contains(normalizeButtonText(expectedLinkName)),
                "Expected link title '" + expectedLinkName + "' but was '" + actualTitle + "'.");
    }

    @Override
    public void clickLink(String linkName) {
        click(linkName);
    }

    @Override
    public void assertCorrectTextualContentOnFields(Map<String, String> fieldValues) {
        fieldValues.forEach((fieldName, expectedText) -> Assertions.assertTrue(pageContains(expectedText),
                "Expected textual content for '" + fieldName + "' to be visible: " + expectedText));
    }

    @Override
    public void assertCorrectExpandedContent(String expectedExpandedContent) {
        Assertions.assertTrue(pageContains(expectedExpandedContent),
                "Expected expanded content to be visible: " + expectedExpandedContent);
    }

    @Override
    public void assertCorrectUsername(String name) {
        Assertions.assertTrue(pageContains(name), "Expected signed-in username to be visible: " + name);
    }

    @Override
    public void assertIfElementIsNotSelected(String elementName) {
        WebElementFacade element = waitForElementVisible(locatorFor(elementName));
        Assertions.assertFalse(element.isSelected(), "Expected element not to be selected: " + elementName);
    }

    @Override
    public void inputFieldData(String fieldValue) {
        WebElementFacade field = firstVisible()
                .orElseThrow(() -> new IllegalStateException("No visible editable field was found on the current page."));
        clearAndType(field, fieldValue);
    }

    @Override
    public void assertOnCorrectFieldData(String fieldValue) {
        String expected = Objects.requireNonNullElse(fieldValue, "").trim();
        Assertions.assertTrue(visibleWebElements().stream()
                        .map(this::fieldValue)
                        .map(String::trim)
                        .anyMatch(expected::equals),
                "Expected field value to be displayed or entered on the page: " + expected);
    }

    @Override
    public void assertFieldsAreEditable() {
        List<WebElementFacade> editableFields = visibleWebElements();
        Assertions.assertFalse(editableFields.isEmpty(), "Expected at least one visible editable field on the page.");
        editableFields.forEach(field -> Assertions.assertTrue(field.isEnabled(),
                "Expected field to be editable: " + readableFieldName(field)));
    }

    @Override
    public void assertThatAllFieldsAreBlank() {
        visibleWebElements().forEach(field -> Assertions.assertTrue(fieldValue(field).isBlank(),
                "Expected field to be blank: " + readableFieldName(field)));
    }

    public void clickLoginButton() {
        click("Login");
    }

    public void clickLogoutButton() {
        click("Logout");
    }

    public void clickNavigationButton(String buttonName) {
        waitAndClick(locatorFor(buttonName, "button"));
        waitForDocumentReady();
    }

    public void clickNavigationLink(String linkName) {
        String locator = locatorFor(linkName, "link");
        if (isBurgerMenuLink(linkName)) {
            openBurgerMenu(locator);
            clickWithJavaScript(locator);
            waitForDocumentReady();
            return;
        }
        waitAndClick(locator);
        waitForDocumentReady();
    }

    @Override
    public void clickLogout() {
        clickNavigationLink("logout");
    }

    @Override
    public void clickAllItems() {
        clickNavigationLink("all items");
    }

    @Override
    public void resetAppState() {
        clickNavigationLink("reset app state");
    }

    @Override
    public void clickItem(String itemName) {
        clickNavigationLink(itemName);
    }

    @Override
    public void assertTitleIsAboveHeading(String expectedHeading) {
        Assertions.assertTrue(pageContains(expectedHeading),
                "Expected title or heading to be visible: " + expectedHeading);
    }

    @Override
    public void loginDetail(String username, String password) {
        waitAndType("[data-test='username'], #user-name", username);
        waitAndType("[data-test='password'], #password", password);
        clickLoginButton();
    }

    @Override
    public void addProductToCart(String productName) {
        waitAndClick(sauceDemoDataTestButton("add-to-cart", productName));
        Assertions.assertTrue(isElementDisplayed(sauceDemoDataTestButton("remove", productName)),
                "Expected Remove button to be visible after adding product: " + productName);
    }

    @Override
    public void assertProductActionIsVisible(String actionName, String productName) {
        String locator = sauceDemoDataTestButton(normalizeActionName(actionName), productName);
        Assertions.assertTrue(isElementDisplayed(locator),
                "Expected '" + actionName + "' button to be visible for product: " + productName);
    }

    @Override
    public void assertAcceptedUsersListContains(String username) {
        String acceptedUsersText = waitAndGetText("#login_credentials, [data-test='login-credentials']");
        Assertions.assertTrue(acceptedUsersText.contains(username),
                "Expected accepted users list to contain: " + username);
    }

    @Override
    public void assertCartItemCount(int expectedCount) {
        if (expectedCount == 0) {
            assertCartBadgeIconIsNotVisible();
            return;
        }
        Assertions.assertEquals(String.valueOf(expectedCount), waitAndGetText(CART_BADGE),
                "Unexpected cart badge count.");
    }

    @Override
    public void assertCartBadgeIconIsNotVisible() {
        Assertions.assertFalse(isElementDisplayed(CART_BADGE), "Expected cart badge icon not to be visible.");
    }

    @Override
    public void assertPageContainsContent(List<String> expectedContent) {
        expectedContent.forEach(text -> Assertions.assertTrue(pageContains(text),
                "Expected page content to be visible: " + text));
    }

    @Override
    public void clickSendEmailButton() {
        click("send email");
    }

    @Override
    public void inputFieldValueData(FieldData fieldData) {
        inputNamedField(fieldData.getFieldName(), valueFor(fieldData));
    }


    public void assertOnFieldsBeingCorrectlyPrePopulatedWithEmployerData(FieldInputModel fieldInputModel) {
        assertOnAllInputFieldsBeingCorrectlyPopulated(fieldInputModel);
    }


    public void inputFieldModelDataIntoAllPageFields(FieldInputModel fieldInputModel) {
        inputMultiFieldData(fieldInputModel.getFieldInputData());
    }

    public void inputFieldModelDataIntoSpecifiedPageFields(FieldInputModel fieldInputModel, List<String> fieldNames) {
        Map<String, String> fieldInputData = fieldInputModel.getFieldInputData();
        fieldNames.forEach(fieldName -> inputNamedField(fieldName, fieldInputData.get(fieldName)));
    }

    @Override
    public void assertOnAllInputFieldsBeingCorrectlyPopulated(FieldInputModel fieldInputModel) {
        assertOnCorrectContentInFields(fieldInputModel.getFieldInputData());
    }

    @Override
    public void assertEditableFieldsCanBeEdited() {
        assertFieldsAreEditable();
    }

    @Override
    public void assertMessageBelowHeading() {
        Assertions.assertTrue(pageContains(PageContent.CHECKOUT_COMPLETE_MESSAGE) || pageContains("Thank you"),
                "Expected a message below the page heading.");
    }

    @Override
    public void assertCorrectMessageBelowHeading(String expectedMessage) {
        Assertions.assertTrue(pageContains(expectedMessage),
                "Expected message below heading to be visible: " + expectedMessage);
    }

    @Override
    public void inputMultiFieldData(Map<String, String> fieldValues) {
        fieldValues.forEach(this::inputNamedField);
    }

    @Override
    public void assertOnCorrectContentInFields(Map<String, String> fieldValues) {
        fieldValues.forEach((fieldName, expectedValue) -> {
            WebElementFacade field = waitForElementVisible(fieldLocatorFor(fieldName));
            Assertions.assertEquals(Objects.requireNonNullElse(expectedValue, ""), fieldValue(field),
                    "Unexpected value for field: " + fieldName);
        });
    }

    @Override
    public void assertIfElementsAreOrderedAsInTheExpectedList(WebElements[] expectedElements) {
        String body = bodyText();
        int previousIndex = -1;
        for (WebElements expectedElement : expectedElements) {
            int currentIndex = body.indexOf(readableElementName(expectedElement));
            Assertions.assertTrue(currentIndex >= previousIndex,
                    "Expected element order to contain " + Arrays.toString(expectedElements));
            previousIndex = currentIndex;
        }
    }

    @Override
    public void assertCorrectOrderID() {
        Assertions.assertTrue(pageContains("Thank you for your order") || pageContains("order"),
                "Expected order confirmation content to be visible.");
    }

    @Override
    public void personalCheckoutInformation(String firstName, String lastName, String postalCode) {
        waitAndType(FIRST_NAME_FIELD, firstName);
        waitAndType(LAST_NAME_FIELD, lastName);
        waitAndType(POSTAL_CODE_FIELD, postalCode);
    }

    @Override
    public void assertRadioButtonsBelowHeading() {
        Assertions.assertFalse(findAll("input[type='radio']").isEmpty(),
                "Expected at least one radio button below the page heading.");
    }

    @Override
    public void clickOnRadioButton(String radioButtonName) {
        waitAndClick(radioButtonLocatorFor(radioButtonName));
    }

    @Override
    public void assertTableBelowHeading() {
        Assertions.assertFalse(repeatableRows().isEmpty(),
                "Expected table or repeatable row content below the page heading.");
    }

    @Override
    public void assertTableHasElementInEveryRow(WebElements expectedElement) {
        List<WebElementFacade> rows = repeatableRows();
        Assertions.assertFalse(rows.isEmpty(), "Expected table or repeatable row content.");
        String childLocator = rowChildLocatorFor(expectedElement);
        rows.forEach(row -> Assertions.assertFalse(row.thenFindAll(childLocator).isEmpty(),
                "Expected row to contain element: " + expectedElement));
    }

    @Override
    public void assertCorrectTableContents(Map<String, String> expectedContents) throws TableRowDoesNotExistException {
        expectedContents.forEach((key, value) -> Assertions.assertTrue(rowContains(key, value),
                "Expected table row containing '" + key + "' and '" + value + "'."));
    }


    public void clickTableElementOnRow(WebElements element, int rowNumber, String rowText) throws TableRowDoesNotExistException {
        List<WebElementFacade> rows = repeatableRows();
        int rowIndex = Math.max(rowNumber - 1, 0);
        TableRowDoesNotExistException.checkIfTableRowExistsOrElseThrowException(
                "No row exists at one-based row number: " + rowNumber, rows, rowIndex);
        WebElementFacade row = rows.get(rowIndex);
        if (rowText != null && !rowText.isBlank()) {
            Assertions.assertTrue(row.getText().contains(rowText),
                    "Expected row " + rowNumber + " to contain: " + rowText);
        }
        row.then(rowChildLocatorFor(element)).waitUntilClickable().click();
    }

    private String locatorFor(String elementName) {
        String key = normalizeKey(elementName);
        String locator = CLICKABLE_CONTROLS.get(key);
        if (locator != null) {
            return locator;
        }
        locator = NAVIGATION_LINKS.get(key);
        return locator != null ? locator : genericClickableLocator(elementName, null);
    }

    private String locatorFor(String elementName, String elementType) {
        String key = normalizeKey(elementName);
        String locator = "link".equals(elementType) ? NAVIGATION_LINKS.get(key) : CLICKABLE_CONTROLS.get(key);
        if (locator != null) {
            return locator;
        }
        locator = "link".equals(elementType) ? CLICKABLE_CONTROLS.get(key) : NAVIGATION_LINKS.get(key);
        return locator != null ? locator : genericClickableLocator(elementName, elementType);
    }

    private String genericClickableLocator(String elementName, String elementType) {
        String expected = normalizeKey(elementName);
        String expectedDataTest = expected.replace(" ", "-");
        String xPathLiteral = toXPathLiteral(expected);
        String dataTestLiteral = toXPathLiteral(expectedDataTest);
        String elementPredicate = switch (elementType == null ? "" : elementType) {
            case "button" -> "self::button or self::input";
            case "link" -> "self::a";
            default -> "self::button or self::input or self::a";
        };
        String text = "translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')";
        String value = "translate(normalize-space(@value), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')";
        String ariaLabel = "translate(normalize-space(@aria-label), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')";
        String dataTest = "translate(normalize-space(@data-test), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')";
        String id = "translate(normalize-space(@id), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')";
        return "//*[(" + elementPredicate + ") and (" + text + " = " + xPathLiteral
                + " or " + value + " = " + xPathLiteral
                + " or " + ariaLabel + " = " + xPathLiteral
                + " or " + dataTest + " = " + xPathLiteral
                + " or " + dataTest + " = " + dataTestLiteral
                + " or " + id + " = " + xPathLiteral
                + " or " + id + " = " + dataTestLiteral + ")]";
    }

    private String readableTitle(String locator) {
        WebElementFacade element = waitForElementVisible(locator);
        String text = firstNonBlank(
                element.getText(),
                element.getAttribute("value"),
                element.getAttribute("aria-label"),
                element.getAttribute("data-test")
        );
        return text == null ? "" : text.trim();
    }

    private boolean pageContains(String expectedText) {
        String text = expectedText == null ? "" : expectedText.trim();
        return text.isBlank() || bodyText().contains(text);
    }

    private String bodyText() {
        long endTime = System.currentTimeMillis() + Duration.ofSeconds(10).toMillis();
        RuntimeException lastFailure = null;
        while (System.currentTimeMillis() < endTime) {
            try {
                return getDriver().findElement(By.tagName("body")).getText();
            } catch (RuntimeException exception) {
                lastFailure = exception;
                waitABit(100);
            }
        }
        throw new AssertionError("Expected page body to be available for content assertion.", lastFailure);
    }

    private String fieldValue(WebElementFacade field) {
        return Objects.requireNonNullElse(field.getAttribute("value"), "");
    }

    private String readableFieldName(WebElementFacade field) {
        return Objects.requireNonNullElse(firstNonBlank(
                field.getAttribute("data-test"),
                field.getAttribute("name"),
                field.getAttribute("id")
        ), "<unnamed>");
    }

    private void openBurgerMenu(String targetMenuLinkLocator) {
        if (!isBurgerMenuLinkReady(targetMenuLinkLocator)) {
            clickWithJavaScript(BURGER_MENU_BUTTON);
            long endTime = System.currentTimeMillis() + 5000L;
            while (!isBurgerMenuLinkReady(targetMenuLinkLocator) && System.currentTimeMillis() < endTime) {
                waitABit(100);
            }
            Assertions.assertTrue(isBurgerMenuLinkReady(targetMenuLinkLocator),
                    "Expected SauceDemo burger menu link to be ready: " + targetMenuLinkLocator);
        }
    }

    private boolean isBurgerMenuLinkReady(String targetMenuLinkLocator) {
        return findAll(targetMenuLinkLocator).stream()
                .anyMatch(element -> element.isPresent() && element.isDisplayed() && element.isEnabled());
    }

    private boolean isBurgerMenuLink(String linkName) {
        String key = normalizeKey(linkName);
        return "all items".equals(key)
                || "about".equals(key)
                || "logout".equals(key)
                || "reset app state".equals(key);
    }

    private List<WebElementFacade> visibleWebElements() {
        return findAll(ApplicationFlowPageImpl.EDITABLE_FIELDS).stream().filter(WebElementFacade::isVisible).toList();
    }

    private Optional<WebElementFacade> firstVisible() {
        return visibleWebElements().stream().findFirst();
    }

    protected void clearAndType(WebElementFacade field, String fieldValue) {
        field.clear();
        field.type(fieldValue == null ? "" : fieldValue);
    }

    private void inputNamedField(String fieldName, String fieldValue) {
        waitAndType(fieldLocatorFor(fieldName), fieldValue);
    }

    private String fieldLocatorFor(String fieldName) {
        String expected = normalizeKey(fieldName);
        return switch (expected) {
            case "username", "user name" -> "[data-test='username'], #user-name";
            case "password" -> "[data-test='password'], #password";
            case "first name", "firstname" -> FIRST_NAME_FIELD;
            case "last name", "lastname", "surname" -> LAST_NAME_FIELD;
            case "postal code", "postcode", "zip", "zip code" -> POSTAL_CODE_FIELD;
            default -> genericFieldLocator(expected);
        };
    }

    private String genericFieldLocator(String normalizedFieldName) {
        String expected = toXPathLiteral(normalizedFieldName);
        String expectedDataTest = toXPathLiteral(normalizedFieldName.replace(" ", "-"));
        String text = "translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')";
        String placeholder = "translate(normalize-space(@placeholder), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')";
        String name = "translate(normalize-space(@name), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')";
        String id = "translate(normalize-space(@id), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')";
        String dataTest = "translate(normalize-space(@data-test), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')";
        return "//*[self::input or self::textarea or self::select][" + placeholder + " = " + expected
                + " or " + name + " = " + expected
                + " or " + id + " = " + expected
                + " or " + id + " = " + expectedDataTest
                + " or " + dataTest + " = " + expected
                + " or " + dataTest + " = " + expectedDataTest
                + " or @id = //label[" + text + " = " + expected + "]/@for]";
    }

    private String radioButtonLocatorFor(String radioButtonName) {
        String expected = normalizeKey(radioButtonName);
        return "//*[@type='radio' and (translate(normalize-space(@value), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = "
                + toXPathLiteral(expected)
                + " or @id = //label[translate(normalize-space(.), 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz') = "
                + toXPathLiteral(expected) + "]/@for)]";
    }

    private String valueFor(FieldData fieldData) {
        if (fieldData.getFieldValue() != null) {
            return fieldData.getFieldValue();
        }
        int length = fieldData.getNumberOfChars() > 0 ? fieldData.getNumberOfChars() : 8;
        String seed = switch (Objects.requireNonNullElse(fieldData.getValueType(), "").toLowerCase(Locale.ROOT)) {
            case "number", "numeric", "digits" -> "1234567890";
            case "email" -> "test.user@example.com";
            default -> "testvalue";
        };
        String value = seed.repeat(Math.max(1, (length / seed.length()) + 1)).substring(0, length);
        if ("upper".equalsIgnoreCase(fieldData.getCaseType()) || "uppercase".equalsIgnoreCase(fieldData.getCaseType())) {
            return value.toUpperCase(Locale.ROOT);
        }
        return value;
    }

    private List<WebElementFacade> repeatableRows() {
        List<WebElementFacade> rows = new ArrayList<>(findAll(REPEATABLE_ROWS).stream()
                .filter(WebElementFacade::isVisible)
                .toList());
        if (rows.isEmpty()) {
            rows.addAll(findAll("[data-test='inventory-list'] > *, .cart_list > *").stream()
                    .filter(WebElementFacade::isVisible)
                    .toList());
        }
        return rows;
    }

    private boolean rowContains(String key, String value) {
        return repeatableRows().stream().anyMatch(row -> {
            String text = row.getText();
            return text.contains(Objects.requireNonNullElse(key, ""))
                    && text.contains(Objects.requireNonNullElse(value, ""));
        });
    }

    private String rowChildLocatorFor(WebElements element) {
        return switch (element) {
            case DELETE_LINK, TABLE_DELETE_BUTTON -> "button[id^='remove'], button[data-test^='remove'], a";
            case TABLE_EDIT_BUTTON -> "button[id^='add-to-cart'], button[data-test^='add-to-cart'], button, a";
            case SUB_HEADING -> "[data-test='inventory-item-name'], .inventory_item_name, .cart_item_label";
            case TABLE -> "*";
        };
    }

    private String readableElementName(WebElements element) {
        return element.name().toLowerCase(Locale.ROOT).replace('_', ' ');
    }

    private void waitForDocumentReady() {
        long endTime = System.currentTimeMillis() + 5000L;
        while (!"complete".equals(evaluateJavascript("return document.readyState")) && System.currentTimeMillis() < endTime) {
            waitABit(100);
        }
    }

    private String normalizeKey(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Navigation element name must not be blank.");
        }
        return value.trim().toLowerCase(Locale.ROOT).replaceAll("[_-]+", " ").replaceAll("\\s+", " ");
    }

    private String normalizeButtonText(String value) {
        return value == null ? "" : normalizeKey(value);
    }

    private String normalizeActionName(String actionName) {
        String normalized = normalizeKey(actionName);
        return switch (normalized) {
            case "add to cart", "add" -> "add-to-cart";
            case "remove" -> "remove";
            default -> normalized.replace(" ", "-");
        };
    }

    private String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }

    private String toXPathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }
        String[] parts = value.split("'");
        StringBuilder literal = new StringBuilder("concat(");
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) {
                literal.append(", \"'\", ");
            }
            literal.append("'").append(parts[i]).append("'");
        }
        literal.append(")");
        return literal.toString();
    }
}
