package homeoffice.utils;

import homeoffice.api.PostcodesRequestSpecification;
import io.restassured.specification.RequestSpecification;


public final class ApiRequestFactory {
    private ApiRequestFactory() {
    }

    public static RequestSpecification request() {
        return PostcodesRequestSpecification.request();
    }
}
