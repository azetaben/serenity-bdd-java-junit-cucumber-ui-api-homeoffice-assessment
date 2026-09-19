package homeoffice.properties;



import homeoffice.utilities.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

public final class FrameworkConfig {
    private static final Logger log = LoggerFactory.getLogger(FrameworkConfig.class);
    private static final FrameworkConfig INSTANCE = new FrameworkConfig();
    private static final String CLASSPATH_CONFIG = "config/config.properties";
    private static final String DEFAULT_ENV = "prod";
    private final Properties properties = new Properties();

    private FrameworkConfig() {
        if (this.loadFromClasspath()) {
            log.info("Loaded default config from classpath: {}", "config/config.properties");
        }

        this.loadEnvironmentConfig();
    }

    public static FrameworkConfig getInstance() {
        return INSTANCE;
    }

    private boolean loadFromClasspath() {
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/config.properties");

            boolean var7;
            label48:
            {
                try {
                    if (inputStream == null) {
                        var7 = false;
                        break label48;
                    }

                    this.properties.load(inputStream);
                    var7 = true;
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

                return var7;
            }

            if (inputStream != null) {
                inputStream.close();
            }

            return var7;
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load framework configuration from classpath:config/config.properties", exception);
        }
    }

    private void loadEnvironmentConfig() {
        String env = (String) Optional.ofNullable(System.getProperty("env")).or(() -> Optional.ofNullable(System.getenv("ENV"))).orElse("prod");
        String envConfigFileName = env + ".config.properties";
        Path envConfigPath = Paths.get(PathUtil.getResourcesPath(), "config", envConfigFileName);
        if (Files.exists(envConfigPath, new LinkOption[0])) {
            try {
                InputStream inputStream = Files.newInputStream(envConfigPath);

                try {
                    this.properties.load(inputStream);
                    log.info("Loaded environment-specific config: {}", envConfigPath);
                } catch (Throwable var8) {
                    try {
                        inputStream.close();
                    } catch (Throwable var7) {
                        var8.addSuppressed(var7);
                    }

                    throw var8;
                }

                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException exception) {
                throw new IllegalStateException("Unable to load environment configuration from " + envConfigPath, exception);
            }
        } else if (!"prod".equalsIgnoreCase(env) || System.getProperty("env") != null || System.getenv("ENV") != null) {
            log.warn("Environment-specific config file not found for env '{}' at {}. Using default/other loaded properties.", env, envConfigPath);
        }

    }

    public String getString(String key) {
        return this.getString(key, (String) null);
    }

    public String getString(String key, String defaultValue) {
        String value = this.firstNonBlank(System.getProperty(key), System.getenv(this.toEnvironmentKey(key)), this.properties.getProperty(key), defaultValue);
        return value == null ? null : value.trim();
    }

    public int getInt(String key, int defaultValue) {
        String value = this.getString(key);
        return value != null && !value.isBlank() ? Integer.parseInt(value) : defaultValue;
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String value = this.getString(key);
        return value != null && !value.isBlank() ? Boolean.parseBoolean(value) : defaultValue;
    }

    public Properties asProperties() {
        Properties copy = new Properties();
        copy.putAll(this.properties);
        return copy;
    }

    private String toEnvironmentKey(String key) {
        return key.replace('.', '_').replace('-', '_').replace(' ', '_').toUpperCase();
    }

    private String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }

        return null;
    }
}
