package homeoffice.assertions;

import homeoffice.model.Coordinates;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static homeoffice.config.PostcodesApiConfiguration.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

public final class PostcodesAssertions {

    private static final double COORDINATE_TOLERANCE = 0.001;
    private static final double EARTH_RADIUS_KILOMETRES = 6371.0;
    private static final long RESPONSE_TIME_LOWER_BOUND_TOLERANCE_MILLISECONDS = 50;
    private static final long RESPONSE_TIME_UPPER_BOUND_TOLERANCE_MILLISECONDS = 60;
    private static final List<String> SENSITIVE_IMPLEMENTATION_MARKERS =
            List.of(
                    "exception",
                    "stacktrace",
                    "stack trace",
                    "java.",
                    "org.",
                    "wiremock",
                    "sql syntax",
                    "select *",
                    "password",
                    "secret",
                    "token");

    private PostcodesAssertions() {}

    public static void assertStatus(Response response, int expectedStatusCode) {
        assertThat(response.statusCode()).isEqualTo(expectedStatusCode);
    }

    public static void assertResponseTimeIsLessThan(
            Response response, long maximumResponseTimeMilliseconds) {
        assertThat(response.time())
                .as("Response time in milliseconds")
                .isLessThan(
                        maximumResponseTimeMilliseconds
                                + RESPONSE_TIME_UPPER_BOUND_TOLERANCE_MILLISECONDS);
    }

    public static void assertResponseTimeIsBetween(
            Response response,
            long minimumResponseTimeMilliseconds,
            long maximumResponseTimeMilliseconds) {
        assertThat(response.time())
                .as("Response time in milliseconds")
                .isBetween(
                        minimumResponseTimeMilliseconds
                                - RESPONSE_TIME_LOWER_BOUND_TOLERANCE_MILLISECONDS,
                        maximumResponseTimeMilliseconds
                                + RESPONSE_TIME_UPPER_BOUND_TOLERANCE_MILLISECONDS);
    }

    public static void assertLookupPostcode(Response response, String expectedPostcode) {
        assertThat(normalise(response.jsonPath().getString(lookupPostcodeJsonPath())))
                .isEqualTo(normalise(expectedPostcode));
    }

    public static void assertPostcodesMatch(String actualPostcode, String expectedPostcode) {
        assertThat(normalise(actualPostcode)).isEqualTo(normalise(expectedPostcode));
    }

    public static void assertLongitudeIsNegative(Coordinates coordinates) {
        assertThat(coordinates.longitude()).isNegative();
    }

    public static void assertLatitudeIsPositive(Coordinates coordinates) {
        assertThat(coordinates.latitude()).isPositive();
    }

    public static void assertLookupCoordinatesAreAbsent(Response response) {
        Double longitude = response.jsonPath().getObject(lookupLongitudeJsonPath(), Double.class);
        Double latitude = response.jsonPath().getObject(lookupLatitudeJsonPath(), Double.class);

        assertThat(longitude).as("Lookup longitude").isNull();
        assertThat(latitude).as("Lookup latitude").isNull();
    }

    public static void assertFirstNearestPostcode(Response response, String expectedPostcode) {
        assertThat(normalise(response.jsonPath().getString(nearestFirstPostcodeJsonPath())))
                .isEqualTo(normalise(expectedPostcode));
    }

    public static void assertFirstNearestPostcodeIsNot(
            Response response, String unexpectedPostcode) {
        assertThat(normalise(response.jsonPath().getString(nearestFirstPostcodeJsonPath())))
                .isNotEqualTo(normalise(unexpectedPostcode));
    }

    public static void assertNearestPostcodesContain(Response response, String expectedPostcode) {
        List<String> postcodes =
                response.jsonPath().getList(nearestPostcodesJsonPath(), String.class);
        assertThat(postcodes)
                .map(PostcodesAssertions::normalise)
                .contains(normalise(expectedPostcode));
    }

    public static void assertResultIsNotAList(Response response) {
        Object result = response.jsonPath().get(resultJsonPath());
        assertThat(result instanceof List).isFalse();
    }

    public static void assertResultIsAbsent(Response response) {
        Object result = response.jsonPath().get(resultJsonPath());
        assertThat(result).isNull();
    }

    public static void assertResultIsPresent(Response response) {
        Object result = response.jsonPath().get(resultJsonPath());
        assertThat(result).as("Response result").isNotNull();
    }

    public static void assertResponseMatchesJsonResource(Response response, String resourcePath) {
        Map<String, Object> actualResponse = JsonPath.from(response.asString()).getMap("$");
        Map<String, Object> expectedResponse = JsonPath.from(resourceText(resourcePath)).getMap("$");

        assertThat(actualResponse)
                .as("Response should match JSON resource %s", resourcePath)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    public static void assertResponseMatchesJsonSchema(Response response, String schemaResource) {
        response.then().body(matchesJsonSchemaInClasspath(schemaResource));
    }

    public static void assertJsonPathValue(
            Response response, String jsonPath, String expectedType, String expectedValue) {
        Object actualValue = response.jsonPath().get(jsonPath);
        assertJsonType(actualValue, expectedType, jsonPath);

        if ("null".equalsIgnoreCase(expectedType)) {
            assertThat(actualValue).as(jsonPath).isNull();
            return;
        }

        if (expectedValue == null || expectedValue.isBlank()) {
            return;
        }

        assertThat(String.valueOf(actualValue)).as(jsonPath).isEqualTo(expectedValue);
    }

    public static void assertContentTypeIsJson(Response response) {
        assertThat(response.contentType()).as("Content-Type").startsWith("application/json");
    }

    public static void assertBodyDoesNotContain(Response response, String unexpectedValue) {
        assertThat(response.asString().toLowerCase(Locale.UK))
                .as("Response body")
                .doesNotContain(unexpectedValue.toLowerCase(Locale.UK));
    }

    public static void assertBodyDoesNotExposeSensitiveImplementationDetails(Response response) {
        String responseBody = response.asString().toLowerCase(Locale.UK);
        assertThat(responseBody)
                .as("Response body should not expose sensitive implementation details")
                .doesNotContain(SENSITIVE_IMPLEMENTATION_MARKERS.toArray(String[]::new));
    }

    public static void assertCoordinatesWereExtracted(Coordinates coordinates) {
        assertThat(coordinates).as("Coordinates should have been extracted").isNotNull();
    }

    public static void assertLongitudeMatches(Coordinates actual, Coordinates expected) {
        assertThat(actual.longitude()).as("Longitude").isEqualTo(expected.longitude());
    }

    public static void assertLatitudeMatches(Coordinates actual, Coordinates expected) {
        assertThat(actual.latitude()).as("Latitude").isEqualTo(expected.latitude());
    }

    public static void assertCoordinatesAreCloseTo(Coordinates actual, Coordinates expected) {
        assertThat(actual.latitude())
                .as("Latitude")
                .isCloseTo(expected.latitude(), offset(COORDINATE_TOLERANCE));
        assertThat(actual.longitude())
                .as("Longitude")
                .isCloseTo(expected.longitude(), offset(COORDINATE_TOLERANCE));
    }

    public static void assertCoordinatesAreWithinKilometres(
            Coordinates actual, Coordinates expected, double maximumDistanceKilometres) {
        double distanceKilometres = distanceInKilometres(actual, expected);

        assertThat(distanceKilometres)
                .as(
                        "Distance from longitude %s, latitude %s to longitude %s, latitude %s",
                        actual.longitude(),
                        actual.latitude(),
                        expected.longitude(),
                        expected.latitude())
                .isLessThanOrEqualTo(maximumDistanceKilometres);
    }

    public static void assertNearestDistanceIsMinimal(Response response) {
        List<Double> distances =
                response.jsonPath().getList(nearestDistancesJsonPath(), Double.class);

        assertThat(distances).as("Nearest postcode distances").isNotEmpty();
        assertThat(distances.get(0)).as("Nearest postcode distance").isNotNegative();
        assertThat(distances)
                .allSatisfy(
                        distance -> assertThat(distance).isGreaterThanOrEqualTo(distances.get(0)));
    }

    public static void assertFirstNearestCoordinatesAreCloseTo(
            Response response, Coordinates expectedCoordinates) {
        Coordinates nearestCoordinates =
                new Coordinates(
                        response.jsonPath().getObject("result[0].longitude", Double.class),
                        response.jsonPath().getObject("result[0].latitude", Double.class));

        assertCoordinatesAreCloseTo(nearestCoordinates, expectedCoordinates);
    }

    private static String normalise(String postcode) {
        return postcode == null ? null : postcode.replaceAll("\\s+", "").toUpperCase(Locale.UK);
    }

    private static double distanceInKilometres(Coordinates first, Coordinates second) {
        double latitudeDelta = Math.toRadians(second.latitude() - first.latitude());
        double longitudeDelta = Math.toRadians(second.longitude() - first.longitude());
        double firstLatitude = Math.toRadians(first.latitude());
        double secondLatitude = Math.toRadians(second.latitude());

        double haversine =
                Math.pow(Math.sin(latitudeDelta / 2), 2)
                        + Math.cos(firstLatitude)
                                * Math.cos(secondLatitude)
                                * Math.pow(Math.sin(longitudeDelta / 2), 2);

        return EARTH_RADIUS_KILOMETRES
                * 2
                * Math.atan2(Math.sqrt(haversine), Math.sqrt(1 - haversine));
    }

    private static void assertJsonType(Object actualValue, String expectedType, String jsonPath) {
        String normalisedType = expectedType.toLowerCase(Locale.UK);
        switch (normalisedType) {
            case "string" -> assertThat(actualValue).as(jsonPath).isInstanceOf(String.class);
            case "integer" -> assertThat(actualValue).as(jsonPath).isInstanceOf(Integer.class);
            case "number" -> assertThat(actualValue).as(jsonPath).isInstanceOf(Number.class);
            case "boolean" -> assertThat(actualValue).as(jsonPath).isInstanceOf(Boolean.class);
            case "object" -> assertThat(actualValue).as(jsonPath).isInstanceOf(Map.class);
            case "array" -> assertThat(actualValue).as(jsonPath).isInstanceOf(List.class);
            case "null" -> assertThat(actualValue).as(jsonPath).isNull();
            case "present" -> assertThat(actualValue).as(jsonPath).isNotNull();
            default ->
                    throw new IllegalArgumentException(
                            "Unsupported JSON type '"
                                    + Objects.toString(expectedType)
                                    + "' for path "
                                    + jsonPath);
        }
    }

    private static String resourceText(String resourcePath) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (var inputStream = classLoader.getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resourcePath);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            throw new UncheckedIOException("Could not read resource: " + resourcePath, exception);
        }
    }
}
