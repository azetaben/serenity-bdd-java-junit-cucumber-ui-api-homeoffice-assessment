package homeoffice.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public final class TextUtil {
    private static final Logger log = LoggerFactory.getLogger(TextUtil.class);

    private TextUtil() {
    }

    public static String nullToBlank(String value) {
        return value == null ? "" : value;
    }

    public static String normalizeWhitespace(String value) {
        return nullToBlank(value).replace(' ', ' ').replaceAll("\\s+", " ").trim();
    }

    public static String normalizeToken(String value) {
        return normalizeWhitespace(value).toLowerCase(Locale.ROOT).replace("-", " ");
    }

    public static String normalizeAlias(String value) {
        String normalized = normalizeWhitespace(value).toLowerCase(Locale.ROOT);
        int queryIndex = normalized.indexOf(63);
        if (queryIndex >= 0) {
            normalized = normalized.substring(0, queryIndex);
        }

        int hashIndex = normalized.indexOf(35);
        if (hashIndex >= 0) {
            normalized = normalized.substring(0, hashIndex);
        }

        normalized = normalized.replaceAll("^https?://[^/]+/+", "");
        return normalized.replaceAll("^/+", "").replaceAll("/+$", "");
    }

    public static String normalizePrice(String price) {
        String normalizedPrice = normalizeWhitespace(price);
        return normalizedPrice.startsWith("$") ? normalizedPrice : "$" + normalizedPrice;
    }

    public static double parsePrice(String price) {
        String numericPrice = normalizeWhitespace(price).replaceAll("[^0-9.\\-]", "");
        if (numericPrice.isBlank()) {
            throw new IllegalArgumentException("Price value is blank or does not contain a number: " + price);
        } else {
            return Double.parseDouble(numericPrice);
        }
    }
}
