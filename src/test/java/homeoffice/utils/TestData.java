package homeoffice.utils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class TestData {
    private TestData() {
    }

    public static Map<String, Object> postcodePayload(String... p) {
        return Map.of("postcodes", List.of(p));
    }

    public static Map<String, Object> geolocation(double lon, double lat, Integer radius, Integer limit) {
        Map<String, Object> g = new LinkedHashMap<>();
        g.put("longitude", lon);
        g.put("latitude", lat);
        if (radius != null) g.put("radius", radius);
        if (limit != null) g.put("limit", limit);
        return g;
    }

    @SafeVarargs
    public static Map<String, Object> geolocationPayload(Map<String, Object>... g) {
        return Map.of("geolocations", List.of(g));
    }
}