package homeoffice.constants;

public final class RunnerConstants {

    public static final String CUCUMBER_ENGINE = "cucumber";

    public static final String FEATURE_ROOT = "/features";
    public static final String UI_FEATURE_ROOT = FEATURE_ROOT + "/ui_tests";
    public static final String API_FEATURE_ROOT = FEATURE_ROOT + "/api_tests";
    public static final String UI_FLOWS_FEATURE_ROOT = UI_FEATURE_ROOT + "/flows";
    public static final String UI_E2E_FEATURE_ROOT = UI_FEATURE_ROOT + "/e2e";

    public static final String GLUE_STEPS = "homeoffice";

    public static final String SERENITY_REPORTER_PLUGIN =
            "io.cucumber.core.plugin.SerenityReporterParallel";
    public static final String EXTENT_REPORTS_PLUGIN =
            "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:";
    public static final String PRETTY_PLUGIN = "pretty";

    public static final String TIMELINE_REPORT = "timeline:build/test-results/timeline";
    public static final String UI_HTML_REPORT = "html:target/cucumber-reports/ui.html";
    public static final String UI_JSON_REPORT = "json:target/cucumber-reports/ui.json";
    public static final String UI_JUNIT_REPORT = "junit:target/cucumber-reports/ui.xml";
    public static final String UI_TIMELINE_REPORT = TIMELINE_REPORT + "/ui";

    public static final String POSTCODES_HTML_REPORT = "html:target/cucumber-reports/postcodes.html";
    public static final String POSTCODES_JSON_REPORT = "json:target/cucumber-reports/postcodes.json";
    public static final String POSTCODES_JUNIT_REPORT = "junit:target/cucumber-reports/postcodes.xml";
    public static final String POSTCODES_TIMELINE_REPORT =
            TIMELINE_REPORT + "/postcodes";

    public static final String DEFAULT_PLUGINS =
            SERENITY_REPORTER_PLUGIN
                    + ","
                    + EXTENT_REPORTS_PLUGIN
                    + ","
                    + PRETTY_PLUGIN
                    + ","
                    + TIMELINE_REPORT;

    public static final String UI_PLUGINS =
            SERENITY_REPORTER_PLUGIN
                    + ","
                    + EXTENT_REPORTS_PLUGIN
                    + ","
                    + PRETTY_PLUGIN
                    + ","
                    + UI_HTML_REPORT
                    + ","
                    + UI_JSON_REPORT
                    + ","
                    + UI_JUNIT_REPORT
                    + ","
                    + UI_TIMELINE_REPORT;

    public static final String POSTCODES_PLUGINS =
            SERENITY_REPORTER_PLUGIN
                    + ","
                    + EXTENT_REPORTS_PLUGIN
                    + ","
                    + PRETTY_PLUGIN
                    + ","
                    + POSTCODES_HTML_REPORT
                    + ","
                    + POSTCODES_JSON_REPORT
                    + ","
                    + POSTCODES_JUNIT_REPORT
                    + ","
                    + POSTCODES_TIMELINE_REPORT;

    private RunnerConstants() {}
}
