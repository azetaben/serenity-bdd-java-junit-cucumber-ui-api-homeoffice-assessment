package homeoffice.utilities;

import homeoffice.properties.FrameworkConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class PathUtil {
    private static final Logger log = LoggerFactory.getLogger(PathUtil.class);

    private PathUtil() {
    }

    public static String getProjectRootDir() {
        return System.getProperty("user.dir");
    }

    public static String getAbsolutePath(String... relativePathSegments) {
        Path path = Paths.get(getProjectRootDir(), relativePathSegments);
        return path.normalize().toString();
    }

    public static String getConfigPropertiesPath() {
        return getConfigFilePath("config.properties");
    }

    public static String getConfigFilePath(String fileName) {
        return getAbsolutePath("src", "test", "resources", "config", fileName);
    }

    public static String getTestDataExcelFilePath(String excelFileName) {
        return getAbsolutePath("src", "test", "resources", "data", "excelfiles", excelFileName);
    }

    public static String getTestDataJsonFilePath(String jsonFileName) {
        return getAbsolutePath("src", "test", "resources", "data", "jsonfiles", jsonFileName);
    }

    public static String getReportsDir() {
        return getAbsolutePath("accessibility-reports");
    }

    public static String getExcelFilePath(String s) {
        return getAbsolutePath("src", "test", "resources", "data", s);
    }

    public static String getDownloadsDir() {
        return getAbsolutePath(FrameworkConfig.getInstance().getString("download.dir", "file_downloads"));
    }

    public static String getUploadsDir() {
        return getAbsolutePath(FrameworkConfig.getInstance().getString("upload.dir", "file_uploads"));
    }

    public static String getReportingCsvDir() {
        return getAbsolutePath(FrameworkConfig.getInstance().getString("reporting.csv.dir", "reporting_csv_data"));
    }

    public static String getLighthouseReportPath() {
        String configuredPath = FrameworkConfig.getInstance().getString("lighthouse.report.path", "report/lighthouse-report.json");

        assert configuredPath != null;

        return getAbsolutePath(configuredPath.split("[/\\\\]"));
    }

    public static String getResourcesPath() {
        return getAbsolutePath("src", "test", "resources");
    }
}
