package homeoffice.config;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Properties;

public final class SauceDemoConfiguration {

    private static final Properties PROPERTIES = loadProperties();

    private SauceDemoConfiguration() {
    }

    public static String property(String key) {
        String systemPropertyValue = System.getProperty(key);
        if (systemPropertyValue != null && !systemPropertyValue.isBlank()) {
            return systemPropertyValue.trim();
        }

        String propertyValue = PROPERTIES.getProperty(key);
        if (propertyValue == null) {
            throw new IllegalStateException("Required SauceDemo property is missing: " + key);
        }
        return propertyValue;
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (var inputStream = classLoader.getResourceAsStream("config/saucedemo.properties")) {
            if (inputStream == null) {
                throw new IllegalStateException("Resource not found: config/saucedemo.properties");
            }
            properties.load(inputStream);
            return properties;
        } catch (IOException exception) {
            throw new UncheckedIOException(
                    "Could not read resource: config/saucedemo.properties", exception);
        }
    }
}
