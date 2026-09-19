package homeoffice.constants;


import homeoffice.properties.FrameworkConfig;

/**
 * Framework constants - now using externalized configuration from framework.properties
 * to avoid hardcoding values.
 */
public final class FrameworkConstants {
    private static final FrameworkConfig CONFIG = FrameworkConfig.getInstance();

    private FrameworkConstants() {
    }

    /**
     * Get explicit wait timeout from framework.properties (in milliseconds)
     */
    public static int getExplicitWait() {
        return CONFIG.getInt("framework.explicit.wait", 15000);
    }
}
