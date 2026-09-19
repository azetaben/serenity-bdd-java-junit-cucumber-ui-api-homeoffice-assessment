package homeoffice.exceptions;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.util.Objects;

public class NotOnExpectedPageException extends FrameworkException {
    private static final Logger log = LoggerFactory.getLogger(NotOnExpectedPageException.class);
    @Serial
    private static final long serialVersionUID = 1L;
    private static WebDriver driver;

    public NotOnExpectedPageException(String message) {
        super(message);
    }

    public NotOnExpectedPageException(String message, Throwable cause) {
        super(message, cause);
    }

    public static void setDriver(WebDriver webDriver) {
        driver = webDriver;
    }

    public static void checkIfOnTheCorrectPageOtherwiseThrowException(String pageUrl, String exceptionMessage) throws NotOnExpectedPageException, PageException {
        if (driver == null) {
            throw new PageException("WebDriver is not initialized for page validation.");
        } else {
            checkIfOnTheCorrectPageOtherwiseThrowException(driver, pageUrl, exceptionMessage);
        }
    }

    public static void checkIfOnTheCorrectPageOtherwiseThrowException(WebDriver webDriver, String pageUrl, String exceptionMessage) throws NotOnExpectedPageException, PageException {
        if (webDriver == null) {
            throw new PageException("Provided WebDriver is null.");
        } else if (!isCurrentPageTheExpectedPage(webDriver, pageUrl)) {
            throw new NotOnExpectedPageException(resolveMessage(exceptionMessage, pageUrl, webDriver.getCurrentUrl()));
        }
    }

    private static boolean isCurrentPageTheExpectedPage(WebDriver webDriver, String pageUrl) {
        return Objects.equals(webDriver.getCurrentUrl(), pageUrl);
    }

    private static String resolveMessage(String exceptionMessage, String expectedUrl, String actualUrl) {
        return exceptionMessage != null && !exceptionMessage.trim().isEmpty() ? exceptionMessage : "Expected URL '" + expectedUrl + "' but found '" + actualUrl + "'.";
    }
}
