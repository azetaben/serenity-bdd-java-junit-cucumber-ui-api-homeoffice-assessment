package homeoffice;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static homeoffice.constants.RunnerConstants.CUCUMBER_ENGINE;
import static homeoffice.constants.RunnerConstants.DEFAULT_PLUGINS;
import static homeoffice.constants.RunnerConstants.FEATURE_ROOT;
import static homeoffice.constants.RunnerConstants.GLUE_STEPS;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

@Suite
@IncludeEngines(CUCUMBER_ENGINE)
@SelectClasspathResource(FEATURE_ROOT)
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = GLUE_STEPS)
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = DEFAULT_PLUGINS)

public class CucumberTestSuite {

}
