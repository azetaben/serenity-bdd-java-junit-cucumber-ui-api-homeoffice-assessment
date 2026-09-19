package homeoffice.api;

import io.restassured.config.HttpClientConfig;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintStream;

import static homeoffice.config.PostcodesApiConfiguration.*;


public final class PostcodesRequestSpecification {

    private static final Logger HTTP_LOGGER = LoggerFactory.getLogger("io.postcodes.http");
    private static final PrintStream LOG_STREAM =
            new PrintStream(new Slf4jLogStream(HTTP_LOGGER), true);

    private PostcodesRequestSpecification() {}

    public static RequestSpecification request() {
        int timeout = timeoutMilliseconds();
        RestAssuredConfig config =
                RestAssuredConfig.config()
                        .httpClient(
                                HttpClientConfig.httpClientConfig()
                                        .setParam("http.connection.timeout", timeout)
                                        .setParam("http.socket.timeout", timeout))
                        .logConfig(LogConfig.logConfig().defaultStream(LOG_STREAM));

        RequestSpecification specification =
                SerenityRest.given().config(config).baseUri(baseUrl()).urlEncodingEnabled(true);

        if (logHttpTraffic()) {
            return specification.log().all();
        }

        return specification;
    }
}
