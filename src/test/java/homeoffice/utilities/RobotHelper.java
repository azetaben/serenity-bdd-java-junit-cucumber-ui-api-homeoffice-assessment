package homeoffice.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class RobotHelper {
    private static final Logger log = LoggerFactory.getLogger(RobotHelper.class);
    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            log.error("Failed to initialize java.awt.Robot. Brutal force dismissal of prompts may not work.", e);
        }

    }

    public static void dismissPasswordSavePrompt() {
        if (robot == null) {
            log.warn("Robot not initialized. Skipping brutal force prompt dismissal.");
        } else {
            try {
                log.debug("Attempting to dismiss browser prompt (e.g., password save) with ESCAPE key...");
                pressEscape();
                pressEscape();
                log.debug("ESCAPE key pressed to dismiss prompt successfully.");
            } catch (Exception e) {
                log.error("An unexpected error occurred during Robot prompt dismissal: {}", e.getMessage(), e);
            }

        }
    }

    private static void pressEscape() {
        robot.keyPress(27);
        robot.keyRelease(27);
    }
}
