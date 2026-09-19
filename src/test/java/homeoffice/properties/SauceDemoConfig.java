package homeoffice.properties;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Properties;

public final class SauceDemoConfig {
    private static final String SAUCE_DEMO_URL_PROPERTY = "webdriver.saucedemo.url";
    private static final String BASE_URL_PROPERTY = "webdriver.base.url";
    private static final String DEFAULT_BASE_URL = "https://www.saucedemo.com";
    private static String cachedBaseUrl;

    private SauceDemoConfig() {
    }

    public static String baseUrl() {
        if (cachedBaseUrl == null) {
            cachedBaseUrl = resolveConfiguredUrl();
        }

        return cachedBaseUrl;
    }

    public static String baseOrigin() {
        URI uri = URI.create(baseUrl());
        if (uri.getScheme() != null && uri.getAuthority() != null) {
            String var10000 = uri.getScheme();
            return var10000 + "://" + uri.getAuthority();
        } else {
            return baseUrl().replaceAll("/+$", "");
        }
    }

    public static String absoluteUrlFor(String path) {
        String normalizedPath = path == null ? "" : path.trim();
        if (!normalizedPath.isBlank() && !"/".equals(normalizedPath)) {
            if (!normalizedPath.startsWith("http://") && !normalizedPath.startsWith("https://")) {
                String var10000 = baseOrigin().replaceAll("/+$", "");
                return var10000 + "/" + normalizedPath.replaceAll("^/+", "");
            } else {
                return normalizedPath;
            }
        } else {
            return baseOrigin();
        }
    }

    private static String resolveConfiguredUrl() {
        String systemSauceDemoUrl = System.getProperty("webdriver.saucedemo.url");
        if (systemSauceDemoUrl != null && !systemSauceDemoUrl.isBlank()) {
            return systemSauceDemoUrl.trim();
        } else {
            String systemBaseUrl = System.getProperty("webdriver.base.url");
            if (systemBaseUrl != null && !systemBaseUrl.isBlank()) {
                return systemBaseUrl.trim();
            } else {
                Properties properties = serenityProperties();
                String propertyUrl = properties.getProperty("webdriver.saucedemo.url");
                if (propertyUrl == null || propertyUrl.isBlank()) {
                    propertyUrl = properties.getProperty("webdriver.base.url");
                }

                return propertyUrl != null && !propertyUrl.isBlank() ? propertyUrl.trim() : "https://www.saucedemo.com";
            }
        }
    }

    private static Properties serenityProperties() {
        Properties properties = new Properties();
        loadClasspathProperties(properties);
        loadProjectRootProperties(properties);
        return properties;
    }

    private static void loadClasspathProperties(Properties properties) {
        try {
            InputStream inputStream = SauceDemoConfig.class.getClassLoader().getResourceAsStream("serenity.properties");

            try {
                if (inputStream != null) {
                    properties.load(inputStream);
                }
            } catch (Throwable var5) {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Throwable var4) {
                        var5.addSuppressed(var4);
                    }
                }

                throw var5;
            }

            if (inputStream != null) {
                inputStream.close();
            }

        } catch (IOException exception) {
            throw new IllegalStateException("Unable to read serenity.properties from classpath", exception);
        }
    }

    private static void loadProjectRootProperties(Properties properties) {
        Path rootSerenityProperties = Path.of("serenity.properties");
        if (Files.exists(rootSerenityProperties, new LinkOption[0])) {
            try {
                InputStream inputStream = Files.newInputStream(rootSerenityProperties);

                try {
                    properties.load(inputStream);
                } catch (Throwable var6) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable var5) {
                            var6.addSuppressed(var5);
                        }
                    }

                    throw var6;
                }

                if (inputStream != null) {
                    inputStream.close();
                }

            } catch (IOException exception) {
                throw new IllegalStateException("Unable to read serenity.properties from project root", exception);
            }
        }
    }
}
