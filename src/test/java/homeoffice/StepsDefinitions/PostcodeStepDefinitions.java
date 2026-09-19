package homeoffice.StepsDefinitions;

import homeoffice.api.PostcodesApi;
import homeoffice.model.Coordinates;
import homeoffice.state.PostcodeScenarioState;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static homeoffice.assertions.PostcodesAssertions.*;
import static homeoffice.config.PostcodesApiConfiguration.*;

public class PostcodeStepDefinitions {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostcodeStepDefinitions.class);

    @Steps
    PostcodesApi postcodesApi;

    private PostcodeScenarioState state;

    @Before
    public void initialiseScenarioState() {
        state = new PostcodeScenarioState();
        LOGGER.info("Initialised postcode scenario state");
    }

    @Given("the Postcodes API is available")
    public void thePostcodesApiIsAvailable() {
        LOGGER.info("Validating Postcodes API configuration");
        validate();
    }

    @Given("the postcode {string} to search")
    @Given("the {string} to search")
    public void thePostcodeToSearch(String postcode) {
        LOGGER.info("Setting postcode under test to {}", postcode);
        state.setPostcodeLookoutFor(postcode);
    }

    @Given("the bulk postcode lookup request contains")
    public void theBulkPostcodeLookupRequestContains(DataTable postcodes) {
        List<String> postcodeValues =
                postcodes.asMaps().stream()
                        .map(row -> requiredColumn(row, "postcode"))
                        .toList();
        LOGGER.info("Setting bulk postcode lookup request to {}", postcodeValues);
        state.setBulkPostcodesLookoutFor(postcodeValues);
    }

    @Given("the outward code is {string}")
    public void theOutwardCodeIs(String outcode) {
        LOGGER.info("Setting outward code under test to {}", outcode);
        state.setLookupOutwardCode(outcode);
    }

    @When("I search postcode for {string}")
    public void iSearchForThePostcode(String postcode) {
        state.setPostcodeLookoutFor(postcode);
        iRequestThePostcodeDetails();
    }

    @When("I request the postcode details")
    public void iRequestThePostcodeDetails() {
        LOGGER.info("Requesting postcode details for {}", state.getPostcodeLookoutFor());
        state.setLookupResponse(postcodesApi.lookupPostcode(state.getPostcodeLookoutFor()));
        LOGGER.info("Postcode lookup returned status {}", state.getLookupResponse().statusCode());
    }

    @When("I request the bulk postcode lookup")
    public void iRequestTheBulkPostcodeLookup() {
        LOGGER.info("Requesting bulk postcode lookup for {}", state.getBulkPostcodesLookoutFor());
        state.setNearestResponse(postcodesApi.bulkLookupPostcodes(state.getBulkPostcodesLookoutFor()));
        LOGGER.info("Bulk postcode lookup returned status {}", state.getCurrentResponse().statusCode());
    }

    @When("I search for the nearest outward codes")
    public void iSearchForTheNearestOutwardCodes() {
        LOGGER.info("Searching nearest outward codes for {}", state.getLookupOutwardCode());
        state.setNearestResponse(postcodesApi.nearestOutcodes(state.getLookupOutwardCode()));
        LOGGER.info(
                "Nearest outward-code search returned status {}",
                state.getCurrentResponse().statusCode());
    }

    @When("I search for the nearest postcodes using the returned coordinates")
    @When("I search for the nearest postcodes using the stored coordinates")
    @When("I search for the nearest postcodes using the latest stored coordinates")
    public void iSearchForTheNearestPostcodesUsingTheCurrentCoordinates() {
        Coordinates coordinates = currentCoordinates();
        LOGGER.info(
                "Searching nearest postcodes using longitude {} and latitude {}",
                coordinates.longitude(),
                coordinates.latitude());
        state.setNearestResponse(
                postcodesApi.nearestPostcodes(coordinates.longitude(), coordinates.latitude()));
        LOGGER.info(
                "Nearest-postcode search returned status {}",
                state.getNearestResponse().statusCode());
    }

    @When("I search for the nearest {string} using the returned coordinates")
    @When("I search for the nearest {string} using the stored coordinates")
    @When("I search for the nearest {string} using the latest stored coordinates")
    public void iSearchForTheNearestPostcodeTypeUsingTheCurrentCoordinates(String ignoredType) {
        LOGGER.info("Searching nearest {} using current coordinates", ignoredType);
        iSearchForTheNearestPostcodesUsingTheCurrentCoordinates();
    }

    @When("I search for the nearest postcodes using the adjusted coordinates")
    public void iSearchForTheNearestPostcodesUsingTheAdjustedCoordinates(DataTable adjustments) {
        searchForNearestPostcodesUsingAdjustedCoordinates(adjustments);
    }

    @When("I search for the nearest {string} using the adjusted coordinates")
    public void iSearchForTheNearestPostcodeTypeUsingTheAdjustedCoordinates(
            String ignoredType, DataTable adjustments) {
        LOGGER.info("Searching nearest {} using adjusted coordinates", ignoredType);
        searchForNearestPostcodesUsingAdjustedCoordinates(adjustments);
    }

    private void searchForNearestPostcodesUsingAdjustedCoordinates(DataTable adjustments) {
        Map<String, String> adjustment = adjustments.asMaps().get(0);
        double latitudeAdjustment =
                Double.parseDouble(requiredAdjustment(adjustment, "latitude_adjustment"));
        double longitudeAdjustment =
                Double.parseDouble(requiredAdjustment(adjustment, "longitude_adjustment"));
        Coordinates coordinates = currentCoordinates();

        LOGGER.info(
                "Adjusting coordinates by longitude {} and latitude {}",
                longitudeAdjustment,
                latitudeAdjustment);
        state.setCoordinates(
                new Coordinates(
                        coordinates.longitude() + longitudeAdjustment,
                        coordinates.latitude() + latitudeAdjustment));

        iSearchForTheNearestPostcodesUsingTheCurrentCoordinates();
    }

    @When("I perform a nearest {string} search using the extracted coordinates")
    public void iPerformANearestPostcodeSearchUsingTheExtractedCoordinates(String postcode) {
        LOGGER.info("Searching nearest postcodes for {} using extracted coordinates", postcode);
        iSearchForTheNearestPostcodesUsingTheCurrentCoordinates();
    }

    @When("I request details for the closest postcode returned")
    public void iRequestDetailsForTheClosestPostcodeReturned() {
        state.setPostcodeLookoutFor(
                state.getNearestResponse().jsonPath().getString(nearestFirstPostcodeJsonPath()));
        LOGGER.info("Requesting details for closest postcode {}", state.getPostcodeLookoutFor());
        iRequestThePostcodeDetails();
    }
    @When("I lookup postcode for {string}")
    @When("I request details for {string}")
    @When("I request the {string} details")
    public void iRequestDetailsFor(String postcode) {
        LOGGER.info("Setting postcode under test to {}", postcode);
        state.setPostcodeLookoutFor(postcode);
        iRequestThePostcodeDetails();
    }

    @When("I move the returned latitude by {double} degrees")
    public void iMoveTheReturnedLatitudeByDegrees(double latitudeDelta) {
        Coordinates coordinates = currentCoordinates();
        LOGGER.info("Moving returned latitude by {} degrees", latitudeDelta);
        state.setCoordinates(
                new Coordinates(coordinates.longitude(), coordinates.latitude() + latitudeDelta));
    }

    @When("I move the returned longitude by {double} degrees")
    public void iMoveTheReturnedLongitudeByDegrees(double longitudeDelta) {
        Coordinates coordinates = currentCoordinates();
        LOGGER.info("Moving returned longitude by {} degrees", longitudeDelta);
        state.setCoordinates(
                new Coordinates(coordinates.longitude() + longitudeDelta, coordinates.latitude()));
    }

    @When("I search for the nearest postcodes using longitude {double} and latitude {double}")
    public void iSearchForTheNearestPostcodesUsingCoordinates(double longitude, double latitude) {
        LOGGER.info(
                "Searching nearest postcodes using explicit longitude {} and latitude {}",
                longitude,
                latitude);
        state.setNearestResponse(postcodesApi.nearestPostcodes(longitude, latitude));
        LOGGER.info(
                "Nearest-postcode search returned status {}",
                state.getNearestResponse().statusCode());
    }

    @Then("the postcode lookup should be successful")
    public void thePostcodeLookupShouldBeSuccessful() {
        LOGGER.info("Asserting postcode lookup status is {}", successStatusCode());
        assertStatus(state.getLookupResponse(), successStatusCode());
    }

    @Then("the {string} lookup should be successful")
    public void theLookupForPostcodeTypeShouldBeSuccessful(String ignoredPostcodeType) {
        LOGGER.info("Asserting {} lookup was successful", ignoredPostcodeType);
        thePostcodeLookupShouldBeSuccessful();
    }

    @Then("the postcode lookup for {string} should be successful")
    public void thePostcodeLookupForPostcodeShouldBeSuccessful(String expectedPostcode) {
        thePostcodeLookupShouldBeSuccessful();
        assertLookupPostcode(state.getLookupResponse(), expectedPostcode);
    }

    @Then("the response status should be {string}")
    public void theResponseStatusShouldBe(String expectedStatusCode) {
        int statusCode = Integer.parseInt(expectedStatusCode);
        LOGGER.info("Asserting latest response status is {}", statusCode);
        assertStatus(state.getCurrentResponse(), statusCode);
    }

    @Then("the response time should be less than {long} milliseconds")
    public void theResponseTimeShouldBeLessThanMilliseconds(long maximumResponseTimeMilliseconds) {
        LOGGER.info(
                "Asserting latest response time is less than {} milliseconds",
                maximumResponseTimeMilliseconds);
        assertResponseTimeIsLessThan(state.getCurrentResponse(), maximumResponseTimeMilliseconds);
    }

    @Then("the response time should be between {long} to {long} milliseconds")
    public void theResponseTimeShouldBeBetweenMilliseconds(
            long minimumResponseTimeMilliseconds, long maximumResponseTimeMilliseconds) {
        LOGGER.info(
                "Asserting latest response time is between {} and {} milliseconds",
                minimumResponseTimeMilliseconds,
                maximumResponseTimeMilliseconds);
        assertResponseTimeIsBetween(
                state.getCurrentResponse(),
                minimumResponseTimeMilliseconds,
                maximumResponseTimeMilliseconds);
    }

    @Then("the postcode lookup should report that the postcode was not found")
    public void thePostcodeLookupShouldReportThatThePostcodeWasNotFound() {
        LOGGER.info("Asserting postcode lookup status is {}", notFoundStatusCode());
        assertStatus(state.getLookupResponse(), notFoundStatusCode());
    }

    @Then("the {string} lookup should report that the {string} was not found")
    public void theLookupForPostcodeTypeShouldReportThatThePostcodeWasNotFound(
            String ignoredPostcodeType, String ignoredPostcode) {
        LOGGER.info(
                "Asserting {} lookup reported {} was not found",
                ignoredPostcodeType,
                ignoredPostcode);
        thePostcodeLookupShouldReportThatThePostcodeWasNotFound();
    }

    @Then("the returned postcode should be {string}")
    public void theReturnedPostcodeShouldBe(String expectedPostcode) {
        LOGGER.info("Asserting returned postcode is {}", expectedPostcode);
        assertLookupPostcode(state.getLookupResponse(), expectedPostcode);
    }

    @Then("the returned {string} should be {string}")
    public void theReturnedPostcodeTypeShouldBe(String ignoredPostcodeType, String expectedPostcode) {
        LOGGER.info("Asserting returned {} is {}", ignoredPostcodeType, expectedPostcode);
        assertLookupPostcode(state.getLookupResponse(), expectedPostcode);
    }

    @Then("the returned longitude should be negative")
    public void theReturnedLongitudeShouldBeNegative() {
        LOGGER.info("Asserting returned longitude is negative");
        assertLongitudeIsNegative(currentCoordinates());
    }

    @Then("the returned latitude should be positive")
    public void theReturnedLatitudeShouldBePositive() {
        LOGGER.info("Asserting returned latitude is positive");
        assertLatitudeIsPositive(currentCoordinates());
    }

    @Then("I store the returned longitude and latitude")
    @Then("I should extract the latitude and longitude from the response")
    public void iStoreTheReturnedLongitudeAndLatitude() {
        Coordinates returnedCoordinates = returnedCoordinates();
        LOGGER.info(
                "Storing returned longitude {} and latitude {}",
                returnedCoordinates.longitude(),
                returnedCoordinates.latitude());
        state.setCoordinates(returnedCoordinates);

        if (state.getOriginalCoordinates() == null) {
            state.setOriginalCoordinates(returnedCoordinates);
        }
    }

    @Then("the returned longitude should equal the stored longitude")
    public void theReturnedLongitudeShouldEqualTheStoredLongitude() {
        LOGGER.info("Asserting returned longitude equals stored longitude");
        assertLongitudeMatches(returnedCoordinates(), currentCoordinates());
    }

    @Then("the returned latitude should equal the stored latitude")
    public void theReturnedLatitudeShouldEqualTheStoredLatitude() {
        LOGGER.info("Asserting returned latitude equals stored latitude");
        assertLatitudeMatches(returnedCoordinates(), currentCoordinates());
    }

    @Then("the returned longitude should equal the original longitude")
    public void theReturnedLongitudeShouldEqualTheOriginalLongitude() {
        LOGGER.info("Asserting returned longitude equals original longitude");
        assertLongitudeMatches(returnedCoordinates(), state.getOriginalCoordinates());
    }

    @Then("the returned latitude should equal the original latitude")
    public void theReturnedLatitudeShouldEqualTheOriginalLatitude() {
        LOGGER.info("Asserting returned latitude equals original latitude");
        assertLatitudeMatches(returnedCoordinates(), state.getOriginalCoordinates());
    }

    @Then("the extracted coordinates should be close to longitude {double} and latitude {double}")
    public void theExtractedCoordinatesShouldBeCloseToLongitudeAndLatitude(
            double longitude, double latitude) {
        Coordinates expectedCoordinates = new Coordinates(longitude, latitude);
        LOGGER.info(
                "Asserting extracted coordinates are close to longitude {} and latitude {}",
                longitude,
                latitude);
        assertCoordinatesAreCloseTo(currentCoordinates(), expectedCoordinates);
    }

    @Then(
            "the extracted coordinates should be within {double} kilometres of longitude {double} and latitude {double}")
    public void theExtractedCoordinatesShouldBeWithinKilometresOfLongitudeAndLatitude(
            double maximumDistanceKilometres, double longitude, double latitude) {
        Coordinates expectedCoordinates = new Coordinates(longitude, latitude);
        LOGGER.info(
                "Asserting extracted coordinates are within {} kilometres of longitude {} and latitude {}",
                maximumDistanceKilometres,
                longitude,
                latitude);
        assertCoordinatesAreWithinKilometres(
                currentCoordinates(), expectedCoordinates, maximumDistanceKilometres);
    }

    @Then("no longitude or latitude should be available")
    public void noLongitudeOrLatitudeShouldBeAvailable() {
        LOGGER.info("Asserting lookup coordinates are absent");
        assertLookupCoordinatesAreAbsent(state.getLookupResponse());
    }

    @Then("the response should contain a valid result")
    public void theResponseShouldContainAValidResult() {
        LOGGER.info("Asserting latest response contains a valid result");
        assertResultIsPresent(state.getCurrentResponse());
    }

    @Then("the response content type should be JSON")
    public void theResponseContentTypeShouldBeJson() {
        LOGGER.info("Asserting latest response content type is JSON");
        assertContentTypeIsJson(state.getCurrentResponse());
    }

    @Then("the response should not echo the submitted postcode")
    public void theResponseShouldNotEchoTheSubmittedPostcode() {
        LOGGER.info("Asserting latest response does not echo submitted postcode");
        assertBodyDoesNotContain(state.getCurrentResponse(), state.getPostcodeLookoutFor());
    }

    @Then("the response body should not expose sensitive implementation details")
    public void theResponseBodyShouldNotExposeSensitiveImplementationDetails() {
        LOGGER.info("Asserting latest response body does not expose sensitive details");
        assertBodyDoesNotExposeSensitiveImplementationDetails(state.getCurrentResponse());
    }

    @Then("the response should exactly match {string}")
    public void theResponseShouldExactlyMatch(String expectedJsonResource) {
        LOGGER.info("Asserting latest response exactly matches {}", expectedJsonResource);
        assertResponseMatchesJsonResource(state.getCurrentResponse(), expectedJsonResource);
    }

    @Then("the response should match the JSON schema {string}")
    public void theResponseShouldMatchTheJsonSchema(String schemaResource) {
        LOGGER.info("Asserting latest response matches JSON schema {}", schemaResource);
        assertResponseMatchesJsonSchema(state.getCurrentResponse(), schemaResource);
    }

    @Then("the response should contain these JSON path values")
    public void theResponseShouldContainTheseJsonPathValues(DataTable expectedValues) {
        expectedValues.asMaps().forEach(this::assertExpectedJsonPathValue);
    }

    @Then("the nearest-postcode search should be successful")
    public void theNearestPostcodeSearchShouldBeSuccessful() {
        LOGGER.info("Asserting nearest-postcode search status is {}", successStatusCode());
        assertStatus(state.getNearestResponse(), successStatusCode());
    }

    @Then("the nearest-postcode search should return no results for the coordinates")
    public void theNearestPostcodeSearchShouldReturnNoResultsForTheCoordinates() {
        LOGGER.info(
                "Asserting nearest-postcode search returned no results with status {}",
                successStatusCode());
        assertStatus(state.getNearestResponse(), successStatusCode());
    }

    @Then("the closest postcode should be {string}")
    public void theClosestPostcodeShouldBe(String expectedPostcode) {
        LOGGER.info("Asserting closest postcode is {}", expectedPostcode);
        assertFirstNearestPostcode(state.getNearestResponse(), expectedPostcode);
    }

    @Then("the {string} should be the nearest {string} to its extracted coordinates")
    public void thePostcodeShouldBeTheNearestPostcodeToItsExtractedCoordinates(
            String expectedPostcode, String ignoredPostcodeType) {
        LOGGER.info("Asserting {} is nearest to its extracted coordinates", expectedPostcode);
        assertFirstNearestPostcode(state.getNearestResponse(), expectedPostcode);
    }

    @Then("I should extract the nearest {string} from the results")
    public void iShouldExtractTheNearestPostcodeFromTheResults(String postcode) {
        String nearestPostcode =
                state.getNearestResponse().jsonPath().getString(nearestFirstPostcodeJsonPath());

        LOGGER.info("Extracted nearest postcode {} from results", nearestPostcode);
        state.setNearestPostcode(nearestPostcode);
        assertPostcodesMatch(nearestPostcode, postcode);
    }

    @Then(
            "I verify with the extracted coordinates {string} and {string} is the nearest to {string}")
    public void iVerifyWithTheExtractedCoordinatesIsTheNearestTo(
            String latitude, String longitude, String postcode) {
        Coordinates expectedCoordinates =
                new Coordinates(Double.parseDouble(longitude), Double.parseDouble(latitude));

        LOGGER.info(
                "Verifying extracted coordinates latitude {} and longitude {} are nearest to {}",
                latitude,
                longitude,
                postcode);
        assertCoordinatesAreCloseTo(currentCoordinates(), expectedCoordinates);
        assertPostcodesMatch(state.getNearestPostcode(), postcode);
        assertFirstNearestPostcode(state.getNearestResponse(), postcode);
    }

    @Then(
            "I verify with the extracted coordinates latitude and longitude that the nearest {string} is indeed the closest to the original {string}")
    public void iVerifyWithTheExtractedCoordinatesThatTheNearestIsClosestToTheOriginal(
            String nearestPostcode, String originalPostcode) {
        LOGGER.info(
                "Verifying nearest postcode {} is closest to original postcode {} using extracted coordinates",
                nearestPostcode,
                originalPostcode);
        assertCoordinatesWereExtracted(currentCoordinates());
        assertPostcodesMatch(state.getNearestPostcode(), nearestPostcode);
        assertPostcodesMatch(nearestPostcode, originalPostcode);
        assertFirstNearestPostcode(state.getNearestResponse(), nearestPostcode);
        assertNearestDistanceIsMinimal(state.getNearestResponse());
    }

    @Then("the closest postcode should not be {string}")
    public void theClosestPostcodeShouldNotBe(String unexpectedPostcode) {
        LOGGER.info("Asserting closest postcode is not {}", unexpectedPostcode);
        assertFirstNearestPostcodeIsNot(state.getNearestResponse(), unexpectedPostcode);
    }

    @Then("the closest {string} should not be {string}")
    public void theClosestPostcodeTypeShouldNotBe(
            String ignoredPostcodeType, String unexpectedPostcode) {
        LOGGER.info("Asserting closest {} is not {}", ignoredPostcodeType, unexpectedPostcode);
        assertFirstNearestPostcodeIsNot(state.getNearestResponse(), unexpectedPostcode);
    }

    @Then("the nearest-postcode results should include {string}")
    @Then("the response should contain {string} results")
    public void theNearestPostcodeResultsShouldInclude(String expectedPostcode) {
        LOGGER.info("Asserting nearest-postcode results include {}", expectedPostcode);
        assertNearestPostcodesContain(state.getNearestResponse(), expectedPostcode);
    }

    @Then("the distance to the nearest {string} should be minimal")
    public void theDistanceToTheNearestPostcodeShouldBeMinimal(String postcode) {
        LOGGER.info("Asserting distance to nearest {} is minimal", postcode);
        assertNearestDistanceIsMinimal(state.getNearestResponse());
    }

    @Then("the first nearest postcode coordinates should match the stored coordinates")
    public void theFirstNearestPostcodeCoordinatesShouldMatchTheStoredCoordinates() {
        LOGGER.info("Asserting first nearest postcode coordinates match stored coordinates");
        assertFirstNearestCoordinatesAreCloseTo(state.getNearestResponse(), currentCoordinates());
    }

    @Then("no nearest-postcode results should be returned")
    public void noNearestPostcodeResultsShouldBeReturned() {
        LOGGER.info("Asserting nearest-postcode results are absent");
        assertResultIsAbsent(state.getNearestResponse());
    }

    private Coordinates currentCoordinates() {
        if (state.getCoordinates() == null) {
            LOGGER.info("No stored coordinates found; extracting coordinates from lookup response");
            state.setCoordinates(returnedCoordinates());
        }

        assertCoordinatesWereExtracted(state.getCoordinates());
        return state.getCoordinates();
    }

    private Coordinates returnedCoordinates() {
        return postcodesApi.extractCoordinates(state.getLookupResponse());
    }

    private String requiredAdjustment(Map<String, String> adjustment, String columnName) {
        String value = adjustment.get(columnName);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Adjustment table is missing column: " + columnName);
        }
        return value.trim();
    }

    private void assertExpectedJsonPathValue(Map<String, String> expectedValue) {
        String jsonPath = requiredColumn(expectedValue, "json_path");
        String type = requiredColumn(expectedValue, "type");
        String value = expectedValue.getOrDefault("value", "");

        LOGGER.info("Asserting JSON path {} has type {} and value {}", jsonPath, type, value);
        assertJsonPathValue(state.getCurrentResponse(), jsonPath, type, value);
    }

    private String requiredColumn(Map<String, String> row, String columnName) {
        String value = row.get(columnName);
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Data table is missing column: " + columnName);
        }
        return value.trim();
    }


    @Given("the API root endpoint is reachable")
    public void theApiRootEndpointIsAvailable() {
        assertStatus(postcodesApi.get("/"), successStatusCode());
    }

}
