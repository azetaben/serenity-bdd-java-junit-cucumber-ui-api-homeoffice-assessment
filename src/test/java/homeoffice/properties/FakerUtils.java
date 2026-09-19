package homeoffice.properties;

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Random;
import java.util.UUID;

public final class FakerUtils {
    private static final Logger log = LoggerFactory.getLogger(FakerUtils.class);
    private static final long FAKER_SEED = Long.getLong("faker.seed", 42L);
    private static final Faker FAKER;
    private static final String[] UK_POSTCODE_AREAS;
    private static final String UK_POSTCODE_LETTERS = "ABCDEFGHJKPSTUW";

    static {
        FAKER = new Faker(new Random(FAKER_SEED));
        UK_POSTCODE_AREAS = new String[]{"AB", "B", "BA", "BR", "BS", "CA", "CF", "CR", "CT", "DA", "E", "EC", "EH", "EX", "FK", "G", "GL", "GU", "HA", "HP", "IG", "IP", "KT", "L", "LE", "LS", "M", "MK", "N", "NE", "NG", "NN", "NW", "OX", "PE", "RG", "S", "SE", "SL", "SW", "TN", "W", "WC", "YO"};
    }

    private FakerUtils() {
    }

    public static String resolveToken(String token) {
        String var10000 = switch (token == null ? "" : token.trim().toLowerCase(Locale.ROOT)) {
            case "", "blank", "empty", "null" -> "";
            case "email", "random_email" -> generateRandomEmail();
            case "password", "random_password" -> generateRandomPassword();
            case "name", "random_name" -> generateRandomName();
            case "first_name", "firstname", "random_first_name" -> generateRandomFirstName();
            case "last_name", "lastname", "random_last_name" -> generateRandomLastName();
            case "postal_code", "postcode", "zip_code", "zipcode", "random_postal_code", "uk_postcode",
                 "uk_postal_code", "random_uk_postcode" -> generateRandomPostalCode();
            case "username", "random_username", "invalid_username" -> generateRandomUsername();
            case "user_name" -> generateUserName();
            case "invalid_password", "wrong_password" -> generateInvalidPasswordForSauceDemo();
            case "valid_password", "sauce_password", "secret_sauce" -> "secret_sauce";
            case "valid_username", "standard_user" -> "standard_user";
            default -> token;
        };

        return var10000;
    }

    public static long generateRandomNumber() {
        return FAKER.number().randomNumber(10, true);
    }

    public static String generateRandomEmail() {
        return FAKER.internet().emailAddress();
    }

    public static String generateRandomPassword() {
        return FAKER.internet().password(8, 14, true, true, true);
    }

    public static String generateRandomName() {
        return FAKER.name().fullName();
    }

    public static String generateRandomFirstName() {
        return FAKER.name().firstName();
    }

    public static String generateRandomLastName() {
        return FAKER.name().lastName();
    }

    public static String generateUserName() {
        String var10000 = FAKER.name().lastName();
        return var10000 + FAKER.name().firstName();
    }

    public static String generateRandomPostalCode() {
        return generateRandomUkPostcode();
    }

    public static String generateRandomUkPostcode() {
        String area = ((String) FAKER.options().option(UK_POSTCODE_AREAS)).replace(" ", "");
        int district = FAKER.number().numberBetween(1, 100);
        int sector = FAKER.number().numberBetween(0, 10);
        return "%s%d %d%s%s".formatted(area, district, sector, randomUkPostcodeLetter(), randomUkPostcodeLetter());
    }

    private static String randomUkPostcodeLetter() {
        int index = FAKER.number().numberBetween(0, "ABCDEFGHJKPSTUW".length());
        return String.valueOf("ABCDEFGHJKPSTUW".charAt(index));
    }

    public static String generateRandomUsername() {
        String var10000 = UUID.randomUUID().toString().replace("-", "");
        return "user_" + var10000.substring(0, 8);
    }

    public static String generateInvalidPasswordForSauceDemo() {
        String var10000 = UUID.randomUUID().toString().replace("-", "");
        return "wrong_" + var10000.substring(0, 8);
    }
}
