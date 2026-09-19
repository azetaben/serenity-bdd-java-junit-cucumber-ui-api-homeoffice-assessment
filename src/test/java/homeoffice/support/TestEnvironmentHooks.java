package homeoffice.support;

import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static homeoffice.config.PostcodesApiConfiguration.validate;


public class TestEnvironmentHooks {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestEnvironmentHooks.class);

    @BeforeAll
    public static void validateConfiguration() {
        LOGGER.info("Validating test environment configuration");
        validate();
    }

    @Before("@stub")
    public void startPostcodesApiStub() {
        LOGGER.info("Starting Postcodes API stub");
        PostcodesApiStub.start();
    }

    @Before("@live")
    public void useLivePostcodesApi() {
        if (!PostcodesApiStub.isRunning()) {
            System.clearProperty("postcodes.api.base-url");
            return;
        }

        LOGGER.info("Stopping Postcodes API stub before live scenario");
        PostcodesApiStub.stop();
    }

    @AfterAll
    public static void stopPostcodesApiStub() {
        if (!PostcodesApiStub.isRunning()) {
            return;
        }

        LOGGER.info("Stopping Postcodes API stub");
        PostcodesApiStub.stop();
    }
}
