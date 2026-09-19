package homeoffice.support;

import org.openqa.selenium.chrome.ChromeDriverService;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public final class QuietBrowserLogging {

    private QuietBrowserLogging() {
    }

    public static void install() {
        quietChromeDriverService();
        quietSeleniumJulLoggers();
    }

    private static void quietChromeDriverService() {
        System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
        System.setProperty(ChromeDriverService.CHROME_DRIVER_VERBOSE_LOG_PROPERTY, "false");
        System.setProperty(ChromeDriverService.CHROME_DRIVER_DISABLE_BUILD_CHECK, "true");
    }

    private static void quietSeleniumJulLoggers() {
        LogManager.getLogManager().getLogger("").setLevel(Level.WARNING);
        setLevel("org.openqa.selenium", Level.WARNING);
        setLevel("org.openqa.selenium.remote.ProtocolHandshake", Level.WARNING);
        setLevel("org.openqa.selenium.devtools.CdpVersionFinder", Level.OFF);
        setLevel("org.openqa.selenium.chromium.ChromiumDriver", Level.OFF);
        setLevel("org.openqa.selenium.chrome.ChromeDriverService", Level.OFF);
    }

    private static void setLevel(String loggerName, Level level) {
        Logger logger = Logger.getLogger(loggerName);
        logger.setLevel(level);
        logger.setUseParentHandlers(true);
    }
}
