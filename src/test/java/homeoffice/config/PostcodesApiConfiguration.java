package homeoffice.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import net.serenitybdd.model.environment.ConfiguredEnvironment;
import net.thucydides.model.util.EnvironmentVariables;

import java.io.File;
import java.util.List;

public final class PostcodesApiConfiguration {

    private static final Config SERENITY_CONFIG =
            ConfigFactory.parseFile(new File("serenity.conf"))
                    .withFallback(ConfigFactory.parseResourcesAnySyntax("serenity"))
                    .withFallback(ConfigFactory.load())
                    .resolve();
    private static final String BASE_URL = "postcodes.api.base-url";
    private static final String LOOKUP_ENDPOINT = "postcodes.api.lookup-endpoint";
    private static final String NEAREST_ENDPOINT = "postcodes.api.nearest-endpoint";
    private static final String OUTCODE_NEAREST_ENDPOINT = "postcodes.api.outcode-nearest-endpoint";
    private static final String POSTCODE_PATH_PARAMETER = "postcodes.api.postcode-path-parameter";
    private static final String OUTCODE_PATH_PARAMETER = "postcodes.api.outcode-path-parameter";
    private static final String LONGITUDE_QUERY_PARAMETER =
            "postcodes.api.longitude-query-parameter";
    private static final String LATITUDE_QUERY_PARAMETER = "postcodes.api.latitude-query-parameter";
    private static final String TIMEOUT_MILLISECONDS = "postcodes.api.timeout-milliseconds";
    private static final String LOG_HTTP = "postcodes.api.log-http";
    private static final String SUCCESS_STATUS_CODE = "postcodes.api.success-status-code";
    private static final String NOT_FOUND_STATUS_CODE = "postcodes.api.not-found-status-code";
    private static final String LOOKUP_POSTCODE_JSON_PATH =
            "postcodes.api.json-paths.lookup-postcode";
    private static final String LOOKUP_LONGITUDE_JSON_PATH =
            "postcodes.api.json-paths.lookup-longitude";
    private static final String LOOKUP_LATITUDE_JSON_PATH =
            "postcodes.api.json-paths.lookup-latitude";
    private static final String NEAREST_FIRST_POSTCODE_JSON_PATH =
            "postcodes.api.json-paths.nearest-first-postcode";
    private static final String NEAREST_POSTCODES_JSON_PATH =
            "postcodes.api.json-paths.nearest-postcodes";
    private static final String NEAREST_DISTANCES_JSON_PATH =
            "postcodes.api.json-paths.nearest-distances";
    private static final String RESULT_JSON_PATH = "postcodes.api.json-paths.result";
    private static final String STUB_MAPPINGS_FILE = "postcodes.stub.mappings-file";
    private static final String STUB_RESPONSES_DIRECTORY = "postcodes.stub.responses-directory";

    private PostcodesApiConfiguration() {}

    public static String baseUrl() {
        String overriddenBaseUrl = System.getProperty(BASE_URL);
        if (overriddenBaseUrl != null && !overriddenBaseUrl.isBlank()) {
            return overriddenBaseUrl.trim();
        }
        return requiredProperty(BASE_URL);
    }

    public static String lookupPath() {
        return requiredProperty(LOOKUP_ENDPOINT);
    }

    public static String nearestPath() {
        return requiredProperty(NEAREST_ENDPOINT);
    }

    public static String outcodeNearestPath() {
        return requiredProperty(OUTCODE_NEAREST_ENDPOINT);
    }

    public static String postcodePathParameter() {
        return requiredProperty(POSTCODE_PATH_PARAMETER);
    }

    public static String outcodePathParameter() {
        return requiredProperty(OUTCODE_PATH_PARAMETER);
    }

    public static String longitudeQueryParameter() {
        return requiredProperty(LONGITUDE_QUERY_PARAMETER);
    }

    public static String latitudeQueryParameter() {
        return requiredProperty(LATITUDE_QUERY_PARAMETER);
    }

    public static int timeoutMilliseconds() {
        String propertyValue = property(TIMEOUT_MILLISECONDS);
        return propertyValue == null ? 10000 : Integer.parseInt(propertyValue);
    }

    public static boolean logHttpTraffic() {
        String propertyValue = property(LOG_HTTP);
        return propertyValue == null ? false : Boolean.parseBoolean(propertyValue);
    }

    public static int successStatusCode() {
        String propertyValue = property(SUCCESS_STATUS_CODE);
        return propertyValue == null ? 200 : Integer.parseInt(propertyValue);
    }

    public static int notFoundStatusCode() {
        String propertyValue = property(NOT_FOUND_STATUS_CODE);
        return propertyValue == null ? 404 : Integer.parseInt(propertyValue);
    }

    public static String lookupPostcodeJsonPath() {
        return requiredProperty(LOOKUP_POSTCODE_JSON_PATH);
    }

    public static String lookupLongitudeJsonPath() {
        return requiredProperty(LOOKUP_LONGITUDE_JSON_PATH);
    }

    public static String lookupLatitudeJsonPath() {
        return requiredProperty(LOOKUP_LATITUDE_JSON_PATH);
    }

    public static String nearestFirstPostcodeJsonPath() {
        return requiredProperty(NEAREST_FIRST_POSTCODE_JSON_PATH);
    }

    public static String nearestPostcodesJsonPath() {
        return requiredProperty(NEAREST_POSTCODES_JSON_PATH);
    }

    public static String nearestDistancesJsonPath() {
        return requiredProperty(NEAREST_DISTANCES_JSON_PATH);
    }

    public static String resultJsonPath() {
        return requiredProperty(RESULT_JSON_PATH);
    }

    public static String stubMappingsFile() {
        return requiredProperty(STUB_MAPPINGS_FILE);
    }

    public static String stubResponsesDirectory() {
        return requiredProperty(STUB_RESPONSES_DIRECTORY);
    }

    public static void validate() {
        List.of(
                        BASE_URL,
                        LOOKUP_ENDPOINT,
                        NEAREST_ENDPOINT,
                        OUTCODE_NEAREST_ENDPOINT,
                        POSTCODE_PATH_PARAMETER,
                        OUTCODE_PATH_PARAMETER,
                        LONGITUDE_QUERY_PARAMETER,
                        LATITUDE_QUERY_PARAMETER,
                        TIMEOUT_MILLISECONDS,
                        SUCCESS_STATUS_CODE,
                        NOT_FOUND_STATUS_CODE,
                        LOOKUP_POSTCODE_JSON_PATH,
                        LOOKUP_LONGITUDE_JSON_PATH,
                        LOOKUP_LATITUDE_JSON_PATH,
                        NEAREST_FIRST_POSTCODE_JSON_PATH,
                        NEAREST_POSTCODES_JSON_PATH,
                        NEAREST_DISTANCES_JSON_PATH,
                        RESULT_JSON_PATH,
                        STUB_MAPPINGS_FILE,
                        STUB_RESPONSES_DIRECTORY)
                .forEach(PostcodesApiConfiguration::requiredProperty);
    }

    private static String requiredProperty(String propertyName) {
        String propertyValue = property(propertyName);
        if (propertyValue == null || propertyValue.isBlank()) {
            throw new IllegalStateException(
                    "Required configuration property is missing: " + propertyName);
        }
        return propertyValue;
    }

    private static String property(String propertyName) {
        String systemPropertyValue = System.getProperty(propertyName);
        if (systemPropertyValue != null && !systemPropertyValue.isBlank()) {
            return systemPropertyValue.trim();
        }

        EnvironmentVariables environmentVariables = ConfiguredEnvironment.getEnvironmentVariables();
        String serenityPropertyValue = environmentVariables.getProperty(propertyName);
        if (serenityPropertyValue != null && !serenityPropertyValue.isBlank()) {
            return serenityPropertyValue.trim();
        }

        if (SERENITY_CONFIG.hasPath(propertyName)) {
            return SERENITY_CONFIG.getString(propertyName).trim();
        }

        String serenityNestedPropertyName = "serenity." + propertyName;
        if (SERENITY_CONFIG.hasPath(serenityNestedPropertyName)) {
            return SERENITY_CONFIG.getString(serenityNestedPropertyName).trim();
        }

        return null;
    }
}
