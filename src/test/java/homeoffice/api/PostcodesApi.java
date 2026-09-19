package homeoffice.api;


import homeoffice.model.Coordinates;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;

import java.util.List;
import java.util.Map;

import static homeoffice.api.PostcodesRequestSpecification.request;
import static homeoffice.config.PostcodesApiConfiguration.*;
import static io.restassured.http.ContentType.JSON;

public class PostcodesApi {

    public static final String ABSENT_COORDINATES_MESSAGE =
            "Longitude or latitude is absent from the postcode lookup response";

    @Step("Look up postcode {0}")
    public Response lookupPostcode(String postcode) {
        ValidatableResponse response =
                request()
                        .pathParam(postcodePathParameter(), postcode)
                        .when()
                        .get(lookupPath())
                        .then();

        return logResponseIfEnabled(response).extract().response();
    }

    @Step("Find nearest postcodes for longitude {0} and latitude {1}")
    public Response nearestPostcodes(double longitude, double latitude) {
        ValidatableResponse response =
                request()
                        .queryParam(longitudeQueryParameter(), longitude)
                        .queryParam(latitudeQueryParameter(), latitude)
                        .when()
                        .get(nearestPath())
                        .then();

        return logResponseIfEnabled(response).extract().response();
    }

    @Step("Bulk look up postcodes {0}")
    public Response bulkLookupPostcodes(List<String> postcodes) {
        ValidatableResponse response =
                request()
                        .contentType(JSON)
                        .body(Map.of("postcodes", postcodes))
                        .when()
                        .post(nearestPath())
                        .then();

        return logResponseIfEnabled(response).extract().response();
    }

    @Step("Find nearest outward codes around outward code {0}")
    public Response nearestOutcodes(String outcode) {
        ValidatableResponse response =
                request()
                        .pathParam(outcodePathParameter(), outcode)
                        .when()
                        .get(outcodeNearestPath())
                        .then();

        return logResponseIfEnabled(response).extract().response();
    }

    @Step("Check API availability at {0}")
    public Response get(String path) {
        ValidatableResponse response = request().when().get(path).then();

        return logResponseIfEnabled(response).extract().response();
    }

    public Coordinates extractCoordinates(Response response) {
        Double longitude = response.jsonPath().getObject(lookupLongitudeJsonPath(), Double.class);
        Double latitude = response.jsonPath().getObject(lookupLatitudeJsonPath(), Double.class);
        if (longitude == null || latitude == null) {
            throw new IllegalStateException(ABSENT_COORDINATES_MESSAGE);
        }
        return new Coordinates(longitude, latitude);
    }

    private ValidatableResponse logResponseIfEnabled(ValidatableResponse response) {
        if (logHttpTraffic()) {
            return response.log().all();
        }

        return response;
    }
}
