package homeoffice.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public final class DataTableUtil {
    private static final Logger log = LoggerFactory.getLogger(DataTableUtil.class);

    private DataTableUtil() {
    }

    public static String requiredValue(Map<String, String> row, String key) {
        return requiredValue(row, key, "DataTable missing required column or value for: '" + key + "'.");
    }

    public static String requiredValue(Map<String, String> row, String key, String message) {
        String value = (String) row.get(key);
        if (value != null && !value.isBlank()) {
            return value.trim();
        } else {
            throw new IllegalArgumentException(message);
        }
    }

    public static int requiredInt(Map<String, String> row, String key) {
        String value = requiredValue(row, key);

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("DataTable value for '" + key + "' must be an integer, but was: " + value, exception);
        }
    }
}
