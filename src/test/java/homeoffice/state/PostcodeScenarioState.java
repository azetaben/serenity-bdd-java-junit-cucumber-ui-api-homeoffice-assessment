package homeoffice.state;

import homeoffice.model.Coordinates;
import io.restassured.response.Response;

import java.util.List;

public class PostcodeScenarioState {
    private Response lookupResponse;
    private Response nearestResponse;
    private Response currentResponse;
    private Coordinates coordinates;
    private Coordinates originalCoordinates;
    private String postcodeLookoutFor;
    private String nearestPostcode;
    private List<String> bulkPostcodesLookoutFor;
    private String lookupOutwardCode;

    public Response getLookupResponse() {
        return lookupResponse;
    }

    public void setLookupResponse(Response lookupResponse) {
        this.lookupResponse = lookupResponse;
        this.currentResponse = lookupResponse;
    }

    public Response getNearestResponse() {
        return nearestResponse;
    }

    public void setNearestResponse(Response nearestResponse) {
        this.nearestResponse = nearestResponse;
        this.currentResponse = nearestResponse;
    }

    public Response getCurrentResponse() {
        return currentResponse;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getPostcodeLookoutFor() {
        return postcodeLookoutFor;
    }

    public void setPostcodeLookoutFor(String postcodeLookoutFor) {
        this.postcodeLookoutFor = postcodeLookoutFor;
    }

    public String getNearestPostcode() {
        return nearestPostcode;
    }

    public void setNearestPostcode(String nearestPostcode) {
        this.nearestPostcode = nearestPostcode;
    }

    public List<String> getBulkPostcodesLookoutFor() {
        return bulkPostcodesLookoutFor;
    }

    public void setBulkPostcodesLookoutFor(List<String> bulkPostcodesLookoutFor) {
        this.bulkPostcodesLookoutFor = bulkPostcodesLookoutFor;
    }

    public String getLookupOutwardCode() {
        return lookupOutwardCode;
    }

    public void setLookupOutwardCode(String lookupOutwardCode) {
        this.lookupOutwardCode = lookupOutwardCode;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getOriginalCoordinates() {
        return originalCoordinates;
    }

    public void setOriginalCoordinates(Coordinates originalCoordinates) {
        this.originalCoordinates = originalCoordinates;
    }
}
