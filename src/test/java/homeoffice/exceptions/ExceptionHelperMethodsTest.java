package homeoffice.exceptions;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.List;

public class ExceptionHelperMethodsTest {
    private static final Logger log = LoggerFactory.getLogger(ExceptionHelperMethodsTest.class);

    private static Object anyElement() {
        return new Object();
    }

    private static WebDriver webDriverWithCurrentUrl(String currentUrl) {
        return (WebDriver) Proxy.newProxyInstance(WebDriver.class.getClassLoader(), new Class[]{WebDriver.class}, (proxy, method, args) -> {
            if ("getCurrentUrl".equals(method.getName())) {
                return currentUrl;
            } else {
                if (method.getDeclaringClass() == Object.class) {
                    if ("toString".equals(method.getName())) {
                        return "ProxyWebDriver";
                    }

                    if ("hashCode".equals(method.getName())) {
                        return System.identityHashCode(proxy);
                    }

                    if ("equals".equals(method.getName())) {
                        return args != null && args.length > 0 && proxy == args[0];
                    }
                }

                return defaultValue(method.getReturnType());
            }
        });
    }

    private static Object defaultValue(Class<?> returnType) {
        if (!returnType.isPrimitive()) {
            return null;
        } else if (Boolean.TYPE.equals(returnType)) {
            return false;
        } else if (Character.TYPE.equals(returnType)) {
            return '\u0000';
        } else if (Byte.TYPE.equals(returnType)) {
            return 0;
        } else if (Short.TYPE.equals(returnType)) {
            return Short.valueOf((short) 0);
        } else if (Integer.TYPE.equals(returnType)) {
            return 0;
        } else if (Long.TYPE.equals(returnType)) {
            return 0L;
        } else if (Float.TYPE.equals(returnType)) {
            return 0.0F;
        } else {
            return Double.TYPE.equals(returnType) ? (double) 0.0F : null;
        }
    }

    @After
    public void resetStaticDriver() {
        NotOnExpectedPageException.setDriver((WebDriver) null);
    }

    @Test
    public void checkIfCriteriaSetIsOverOne_allowsValuesGreaterThanOne() {
        CriteriaSetNotMoreThanOneException.checkIfCriteriaSetIsOverOneOrElseThrowException("ignored", 2);
    }

    @Test
    public void checkIfCriteriaSetIsOverOne_throwsWithDefaultMessageWhenMessageIsBlank() {
        try {
            CriteriaSetNotMoreThanOneException.checkIfCriteriaSetIsOverOneOrElseThrowException("  ", 1);
            Assert.fail("Expected CriteriaSetNotMoreThanOneException");
        } catch (CriteriaSetNotMoreThanOneException ex) {
            Assert.assertEquals("Criteria set number must be greater than one.", ex.getMessage());
        }

    }

    @Test
    public void checkIfCriteriaSetIsOverOne_throwsWithCustomMessage() {
        try {
            CriteriaSetNotMoreThanOneException.checkIfCriteriaSetIsOverOneOrElseThrowException("custom criteria error", 0);
            Assert.fail("Expected CriteriaSetNotMoreThanOneException");
        } catch (CriteriaSetNotMoreThanOneException ex) {
            Assert.assertEquals("custom criteria error", ex.getMessage());
        }

    }

    @Test
    public void checkIfWebElementListContainsElement_allowsValidElementAtIndex() {
        List<Object> webElements = Collections.singletonList(anyElement());
        NoSuchElementsException.checkIfWebElementListContainsElementOrElseThrowNoSuchElementException(webElements, 0);
    }

    @Test
    public void checkIfWebElementListContainsElement_throwsWhenListIsNull() {
        try {
            NoSuchElementsException.checkIfWebElementListContainsElementOrElseThrowNoSuchElementException((List) null, 0);
            Assert.fail("Expected NoSuchElementsException");
        } catch (NoSuchElementsException ex) {
            Assert.assertEquals("No web elements were found in the provided list.", ex.getMessage());
        }

    }

    @Test
    public void checkIfWebElementListContainsElement_throwsWhenIndexIsInvalid() {
        List<Object> webElements = Collections.singletonList(anyElement());

        try {
            NoSuchElementsException.checkIfWebElementListContainsElementOrElseThrowNoSuchElementException(webElements, 2);
            Assert.fail("Expected NoSuchElementsException");
        } catch (NoSuchElementsException ex) {
            Assert.assertEquals("No web element exists at index: 2", ex.getMessage());
        }

    }

    @Test
    public void checkIfWebElementListContainsElement_throwsWhenElementAtIndexIsNull() {
        List<Object> webElements = Collections.singletonList((Object) null);

        try {
            NoSuchElementsException.checkIfWebElementListContainsElementOrElseThrowNoSuchElementException(webElements, 0);
            Assert.fail("Expected NoSuchElementsException");
        } catch (NoSuchElementsException ex) {
            Assert.assertEquals("Web element at index 0 is null.", ex.getMessage());
        }

    }

    @Test
    public void checkIfTableRowExists_allowsValidRowAtIndex() {
        List<Object> rows = Collections.singletonList(anyElement());
        TableRowDoesNotExistException.checkIfTableRowExistsOrElseThrowException("ignored", rows, 0);
    }

    @Test
    public void checkIfTableRowExists_throwsDefaultMessageWhenRowsAreMissing() {
        try {
            TableRowDoesNotExistException.checkIfTableRowExistsOrElseThrowException((String) null, Collections.emptyList(), 0);
            Assert.fail("Expected TableRowDoesNotExistException");
        } catch (TableRowDoesNotExistException ex) {
            Assert.assertEquals("The specified row does not exist within the table.", ex.getMessage());
        }

    }

    @Test
    public void checkIfTableRowExists_throwsCustomMessageWhenIndexIsInvalid() {
        List<Object> rows = Collections.singletonList(anyElement());

        try {
            TableRowDoesNotExistException.checkIfTableRowExistsOrElseThrowException("custom row error", rows, 9);
            Assert.fail("Expected TableRowDoesNotExistException");
        } catch (TableRowDoesNotExistException ex) {
            Assert.assertEquals("custom row error", ex.getMessage());
        }

    }

    @Test
    public void checkIfTableRowExists_throwsWhenRowIsNull() {
        List<Object> rows = Collections.singletonList((Object) null);

        try {
            TableRowDoesNotExistException.checkIfTableRowExistsOrElseThrowException("row is null", rows, 0);
            Assert.fail("Expected TableRowDoesNotExistException");
        } catch (TableRowDoesNotExistException ex) {
            Assert.assertEquals("row is null", ex.getMessage());
        }

    }

    @Test
    public void checkIfOnTheCorrectPage_allowsMatchingUrlWithDriverParameter() throws PageException {
        WebDriver webDriver = webDriverWithCurrentUrl("https://expected/page");
        NotOnExpectedPageException.checkIfOnTheCorrectPageOtherwiseThrowException(webDriver, "https://expected/page", "ignored");
    }

    @Test
    public void checkIfOnTheCorrectPage_throwsWithResolvedDefaultMessageWhenUrlMismatches() {
        WebDriver webDriver = webDriverWithCurrentUrl("https://actual/page");

        try {
            NotOnExpectedPageException.checkIfOnTheCorrectPageOtherwiseThrowException(webDriver, "https://expected/page", (String) null);
            Assert.fail("Expected NotOnExpectedPageException");
        } catch (NotOnExpectedPageException ex) {
            Assert.assertEquals("Expected URL 'https://expected/page' but found 'https://actual/page'.", ex.getMessage());
        } catch (PageException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void checkIfOnTheCorrectPage_throwsPageExceptionWhenProvidedDriverIsNull() {
        PageException exception = Assert.assertThrows(PageException.class, () -> NotOnExpectedPageException.checkIfOnTheCorrectPageOtherwiseThrowException((WebDriver) null, "https://expected/page", "ignored"));
        Assert.assertEquals("Provided WebDriver is null.", exception.getMessage());
    }

    @Test
    public void checkIfOnTheCorrectPage_staticOverloadThrowsPageExceptionWhenDriverNotInitialized() {
        PageException exception = Assert.assertThrows(PageException.class, () -> NotOnExpectedPageException.checkIfOnTheCorrectPageOtherwiseThrowException("https://expected/page", "ignored"));
        Assert.assertEquals("WebDriver is not initialized for page validation.", exception.getMessage());
    }

    @Test
    public void checkIfOnTheCorrectPage_staticOverloadUsesConfiguredDriver() throws PageException {
        WebDriver webDriver = webDriverWithCurrentUrl("https://expected/page");
        NotOnExpectedPageException.setDriver(webDriver);
        NotOnExpectedPageException.checkIfOnTheCorrectPageOtherwiseThrowException("https://expected/page", "ignored");
    }
}
