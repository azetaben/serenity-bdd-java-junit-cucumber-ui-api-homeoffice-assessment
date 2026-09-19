package homeoffice.utilities;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Random;

public class TestDataGeneration {

    //The following examples to create names, addresses, cities, random numbers, characters, and dates. It can be still modified as per project requirements.

    public static LocalDate currentDate = LocalDate.now(ZoneId.of("Europe/London"));
    public static LocalDate neoNatalDOB, neoNatalDOD, nonNeonatalDOB, nonNeonatalDOD;
    public static Period deceasedAgePeriod;
    public static int currentYear = currentDate.getYear();
    public static String userDeceasedFirstName, userDeceasedLastName, deceasedPersonBirthDay, deceasedPersonBirthMonthNumber, deceasedPersonBirthMonthText, deceasedPersonBirthYear;
    public static String deceasedPersonDeathDay, deceasedPersonDeathMonthNumber, deceasedPersonDeathMonthText, deceasedPersonDeathYear, consultantName;
    public static int randomDeathDayInPastTwelveMonths, randomDeathMonthInPastTwelveMonths, randomDeathYearInPastTwelveMonths, randomDeathDayInPastTwentyEightDays, randomDeathMonthInPastTwentyEightDays, randomDeathYearInPastTwentyEightDays;
    public static String deceasedPersonAge;
    public static int deceasedAgeYears;
    public static int deceasedAgeMonths;
    public static int deceasedAgeDays;
    public static String deceasedLastSeenAliveDay, deceasedLastSeenAliveMonthNumber, deceasedLastSeenAliveMonthText, deceasedLastSeenAliveYear;
    public static int randomLastSeenAliveYear, randomLastSeenAliveMonth, randomLastSeenAliveDay;
    public static String deceasedPastLastSeenAliveDay, deceasedPastLastSeenAliveMonthNumber, deceasedPastLastSeenAliveMonthText, deceasedPastLastSeenAliveYear, deathLocationAddressLine1;
    public static String deathLocationAddressLine2, deathLocationAddressTown, deathLocationAddressCounty, deathLocationAddressPostCode;
    public static String userAttendingPractitionerFirstName, userAttendingPractitionerLastName;
    public static String apGmcNumber, apQualification, apWorkAddressLine1, apWorkAddressLine2, apWorkAddressTown, apWorkAddressCounty, apWorkAddressPostCode;
    public static String outputsFirstName, outputsLastName, outputsEmailAddress;
    public static String randomSingleChar, randomMoreThanThirtyFiveChar, randomMoreThanSeventyFiveChar, fourBlankSpaces;
    public static String pastDayMinusOne, pastMonthMinusOne, pastYearMinusOne, futureDayPlusOne, futureMonthPlusOne, futureYearPlusOne;
    public static String twoRandomChars;
    public static String randomPassword;
    public static String deceasedPersonAgeDays, deceasedPersonAgeHours;
    public static String userFirstName;
    public static String userLastName;
    static Faker faker = new Faker(new Locale("en-GB"));

    public static String createOrganisationName() {
        return faker.company().name();
    }

    public static void createUserName() {
        userFirstName = faker.name().firstName();
        userLastName = faker.name().lastName();
    }

    public static void createFullNameForUser() {
        createUserName();
    }

    public static String createPhoneNumber() {
        return faker.phoneNumber().subscriberNumber(10);
    }

    public static String createFullName() {
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static int createLine1() {
        return faker.number().numberBetween(1, 999);
    }

    public static void createFullNameForDeceased() {
        userDeceasedFirstName = faker.name().firstName().replaceAll("\\W", "");
        userDeceasedLastName = faker.name().lastName().replaceAll("\\W", "");
    }

    public static String createFullNameForConsultant() {
        consultantName = createFullName();
        return consultantName;
    }

    public static void createNonNeonatalDeceasedPersonDetails(String deceasedPersonType) {
        createFullNameForDeceased();
        createDeceasedPersonDOB(deceasedPersonType);
        createDeceasedPersonDOD(deceasedPersonType);
        calculateDeceasedPersonAge(deceasedPersonType);
        createDeceasedPersonLSD();
    }

    public static int createDeceasedRandomBirthDay() {
        return faker.number().numberBetween(1, 31);
    }

    public static int createDeceasedRandomBirthMonth() {
        return faker.number().numberBetween(1, 12);
    }

    public static int createNonNeonatalRandomBirthYear() {
        return faker.number().numberBetween(currentYear - 200, currentYear);
    }

    public static int createNeonatalRandomBirthStartYear() {
        return faker.number().numberBetween(currentYear - 10, currentYear);
    }

    public static void createNonNeonatalRandomDeathOfDeath() {
        int randomNonNeonatalDeathMonth = faker.number().numberBetween(0, 12);
        randomDeathDayInPastTwelveMonths = currentDate.minusMonths(randomNonNeonatalDeathMonth).getDayOfMonth();
        randomDeathMonthInPastTwelveMonths = currentDate.minusMonths(randomNonNeonatalDeathMonth).getMonth().getValue();
        randomDeathYearInPastTwelveMonths = currentDate.minusMonths(randomNonNeonatalDeathMonth).getYear();
    }

    private static void createNeonatalRandomDeathOfDeath() {
        int randomNeonatalDeathDays = faker.number().numberBetween(0, 27);
        randomDeathDayInPastTwentyEightDays = neoNatalDOB.plusDays(randomNeonatalDeathDays).getDayOfMonth();
        randomDeathMonthInPastTwentyEightDays = neoNatalDOB.plusDays(randomNeonatalDeathDays).getMonth().getValue();
        randomDeathYearInPastTwentyEightDays = neoNatalDOB.plusDays(randomNeonatalDeathDays).getYear();
    }

    public static void createDeceasedPersonDOB(String deceasedPersonType) {
        if (deceasedPersonType.equalsIgnoreCase("non-neonatal") || deceasedPersonType.equalsIgnoreCase("non neonatal") || deceasedPersonType.equalsIgnoreCase("over 28")) {
            nonNeonatalDOB = LocalDate.of(createNonNeonatalRandomBirthYear(), createDeceasedRandomBirthMonth(), createDeceasedRandomBirthDay());
            deceasedPersonBirthDay = String.valueOf(nonNeonatalDOB.getDayOfMonth());
            deceasedPersonBirthMonthText = String.valueOf(nonNeonatalDOB.getMonth());
            deceasedPersonBirthMonthNumber = String.valueOf(nonNeonatalDOB.getMonth().getValue());
            deceasedPersonBirthYear = String.valueOf(nonNeonatalDOB.getYear());
        } else if (deceasedPersonType.equalsIgnoreCase("neonatal") || deceasedPersonType.equalsIgnoreCase("under 28")) {
            neoNatalDOB = LocalDate.of(createNeonatalRandomBirthStartYear(), createDeceasedRandomBirthMonth(), createDeceasedRandomBirthDay());
            deceasedPersonBirthDay = String.valueOf(neoNatalDOB.getDayOfMonth());
            deceasedPersonBirthMonthNumber = String.valueOf(neoNatalDOB.getMonth().getValue());
            deceasedPersonBirthYear = String.valueOf(neoNatalDOB.getYear());
        }
    }

    public static void createDeceasedPersonDOD(String deceasedPersonType) {
        if (deceasedPersonType.equalsIgnoreCase("non-neonatal") || deceasedPersonType.equalsIgnoreCase("non neonatal") || deceasedPersonType.equalsIgnoreCase("over 28")) {
            createNonNeonatalRandomDeathOfDeath();
            nonNeonatalDOD = LocalDate.of(randomDeathYearInPastTwelveMonths, randomDeathMonthInPastTwelveMonths, randomDeathDayInPastTwelveMonths);
            deceasedPersonDeathDay = String.valueOf(nonNeonatalDOD.getDayOfMonth());
            deceasedPersonDeathMonthText = String.valueOf(nonNeonatalDOD.getMonth());
            deceasedPersonDeathMonthNumber = String.valueOf(nonNeonatalDOD.getMonth().getValue());
            deceasedPersonDeathYear = String.valueOf(nonNeonatalDOD.getYear());
        } else if (deceasedPersonType.equalsIgnoreCase("neonatal") || deceasedPersonType.equalsIgnoreCase("under 28")) {
            createNeonatalRandomDeathOfDeath();
            neoNatalDOD = LocalDate.of(randomDeathYearInPastTwentyEightDays, randomDeathMonthInPastTwentyEightDays, randomDeathDayInPastTwentyEightDays);
            deceasedPersonDeathDay = String.valueOf(neoNatalDOD.getDayOfMonth());
            deceasedPersonDeathMonthText = String.valueOf(neoNatalDOD.getMonth());
            deceasedPersonDeathMonthNumber = String.valueOf(neoNatalDOD.getMonth().getValue());
            deceasedPersonDeathYear = String.valueOf(neoNatalDOD.getYear());
        }
    }

    public static void calculateDeceasedPersonAge(String deceasedPersonType) {
        if (deceasedPersonType.equalsIgnoreCase("non-neonatal") || deceasedPersonType.equalsIgnoreCase("non neonatal") || deceasedPersonType.equalsIgnoreCase("over 28")) {
            deceasedAgePeriod = Period.between(nonNeonatalDOB, nonNeonatalDOD);
        } else if (deceasedPersonType.equalsIgnoreCase("neonatal") || deceasedPersonType.equalsIgnoreCase("under 28")) {
            deceasedAgePeriod = Period.between(neoNatalDOB, neoNatalDOD);
        }

        deceasedAgeYears = deceasedAgePeriod.getYears();
        deceasedAgeMonths = deceasedAgePeriod.getMonths();
        deceasedAgeDays = deceasedAgePeriod.getDays();

        if (deceasedAgeYears == 0 && deceasedAgeMonths == 0 && deceasedAgeDays > 0) {
            if (deceasedAgeDays == 1) {
                deceasedPersonAge = deceasedAgeDays + " day";
            } else deceasedPersonAge = deceasedAgeDays + " days";
        } else if (deceasedAgeYears == 0 && deceasedAgeMonths > 0 && deceasedAgeDays > 0) {
            if (deceasedAgeMonths == 1 && deceasedAgeDays == 1) {
                deceasedPersonAge = deceasedAgeMonths + " month and " + deceasedAgeDays + " day";
            } else if (deceasedAgeMonths == 1) {
                deceasedPersonAge = deceasedAgeMonths + " month and " + deceasedAgeDays + " days";
            } else if (deceasedAgeDays == 1) {
                deceasedPersonAge = deceasedAgeMonths + " months and " + deceasedAgeDays + " day";
            } else deceasedPersonAge = deceasedAgeMonths + " months and " + deceasedAgeDays + " days";
        } else if (deceasedAgeYears > 0 && deceasedAgeMonths > 0 && deceasedAgeDays > 0) {
            if (deceasedAgeYears == 1 && deceasedAgeMonths == 1 && deceasedAgeDays == 1) {
                deceasedPersonAge = deceasedAgeYears + " year " + deceasedAgeMonths + " month and " + deceasedAgeDays + " day";
            } else if (deceasedAgeYears == 1) {
                deceasedPersonAge = deceasedAgeYears + " year " + deceasedAgeMonths + " months and " + deceasedAgeDays + " days";
            } else if (deceasedAgeMonths == 1) {
                deceasedPersonAge = deceasedAgeYears + " years " + deceasedAgeMonths + " month and " + deceasedAgeDays + " days";
            } else if (deceasedAgeDays == 1) {
                deceasedPersonAge = deceasedAgeYears + " years " + deceasedAgeMonths + " months and " + deceasedAgeDays + " day";
            } else
                deceasedPersonAge = deceasedAgeYears + " years " + deceasedAgeMonths + " months and " + deceasedAgeDays + " days";
        } else if (deceasedAgeYears > 0 && deceasedAgeMonths == 0 && deceasedAgeDays == 0) {
            if (deceasedAgeYears == 1) {
                deceasedPersonAge = deceasedAgeYears + " year";
            } else deceasedPersonAge = deceasedAgeYears + " years";
        } else if (deceasedAgeYears > 0 && deceasedAgeMonths > 0 && deceasedAgeDays == 0) {
            if (deceasedAgeYears == 1 && deceasedAgeMonths == 1) {
                deceasedPersonAge = deceasedAgeYears + " year and " + deceasedAgeMonths + " month";
            } else if (deceasedAgeMonths == 1) {
                deceasedPersonAge = deceasedAgeYears + " years and " + deceasedAgeMonths + " month";
            } else if (deceasedAgeYears == 1) {
                deceasedPersonAge = deceasedAgeYears + " year and " + deceasedAgeMonths + " months";
            } else deceasedPersonAge = deceasedAgeYears + " years and " + deceasedAgeMonths + " months";
        }
    }

    public static void createDeceasedPersonLSD() {
        int randomLastSeenDayBetweenTwoDates = faker.number().numberBetween(1, 28);
        int deceasedPersonDeathDayLSA = Integer.parseInt(deceasedPersonDeathDay);
        int deceasedPersonDeathMonthLSA = Integer.parseInt(deceasedPersonDeathMonthNumber);
        int deceasedPersonDeathYearLSA = Integer.parseInt(deceasedPersonDeathYear);
        LocalDate deceasedLSDStartDate = LocalDate.of(deceasedPersonDeathYearLSA, deceasedPersonDeathMonthLSA, deceasedPersonDeathDayLSA);
        randomLastSeenAliveDay = deceasedLSDStartDate.minusDays(randomLastSeenDayBetweenTwoDates).getDayOfMonth();
        randomLastSeenAliveMonth = deceasedLSDStartDate.minusDays(randomLastSeenDayBetweenTwoDates).getMonthValue();
        randomLastSeenAliveYear = deceasedLSDStartDate.minusDays(randomLastSeenDayBetweenTwoDates).getYear();
        LocalDate deceasedLastSeenAliveDate = LocalDate.of(randomLastSeenAliveYear, randomLastSeenAliveMonth, randomLastSeenAliveDay);
        deceasedLastSeenAliveDay = String.valueOf(deceasedLastSeenAliveDate.getDayOfMonth());
        deceasedLastSeenAliveMonthText = String.valueOf(deceasedLastSeenAliveDate.getMonth());
        deceasedLastSeenAliveMonthNumber = String.valueOf(deceasedLastSeenAliveDate.getMonth().getValue());
        deceasedLastSeenAliveYear = String.valueOf(deceasedLastSeenAliveDate.getYear());
    }

    public static void changeDeceasedPersonDOD() {
        LocalDate nonNeonatalDOD = LocalDate.of(randomLastSeenAliveYear, randomLastSeenAliveMonth, randomLastSeenAliveDay);
        deceasedPersonDeathDay = String.valueOf(nonNeonatalDOD.plusDays(1).getDayOfMonth());
        deceasedPersonDeathMonthText = String.valueOf(nonNeonatalDOD.plusDays(1).getMonth());
        deceasedPersonDeathMonthNumber = String.valueOf(nonNeonatalDOD.plusDays(1).getMonth().getValue());
        deceasedPersonDeathYear = String.valueOf(nonNeonatalDOD.getYear());
    }

    public static void pastDateOfDodMoreThan28Days() {
        int randomLastSeenDayBetweenTwoDates = faker.number().numberBetween(29, 31);
        int deceasedPersonDeathDayLSA = Integer.parseInt(deceasedPersonDeathDay);
        int deceasedPersonDeathMonthLSA = Integer.parseInt(deceasedPersonDeathMonthNumber);
        int deceasedPersonDeathYearLSA = Integer.parseInt(deceasedPersonDeathYear);
        LocalDate deceasedLSDStartDate = LocalDate.of(deceasedPersonDeathYearLSA, deceasedPersonDeathMonthLSA, deceasedPersonDeathDayLSA);
        int randomLastSeenAliveDay = deceasedLSDStartDate.minusDays(randomLastSeenDayBetweenTwoDates).getDayOfMonth();
        int randomLastSeenAliveMonth = deceasedLSDStartDate.minusDays(randomLastSeenDayBetweenTwoDates).getMonthValue();
        int randomLastSeenAliveYear = deceasedLSDStartDate.minusDays(randomLastSeenDayBetweenTwoDates).getYear();
        LocalDate deceasedLastSeenAliveDate = LocalDate.of(randomLastSeenAliveYear, randomLastSeenAliveMonth, randomLastSeenAliveDay);
        deceasedPastLastSeenAliveDay = String.valueOf(deceasedLastSeenAliveDate.getDayOfMonth());
        deceasedPastLastSeenAliveMonthText = String.valueOf(deceasedLastSeenAliveDate.getMonth());
        deceasedPastLastSeenAliveMonthNumber = String.valueOf(deceasedLastSeenAliveDate.getMonth().getValue());
        deceasedPastLastSeenAliveYear = String.valueOf(deceasedLastSeenAliveDate.getYear());
    }

    public static void deathLocationAddress() {
        deathLocationAddressLine1 = String.valueOf(createLine1());
        deathLocationAddressLine2 = createLine2();
        deathLocationAddressTown = createTown();
        deathLocationAddressCounty = createCounty();
        deathLocationAddressPostCode = createPostCode();
    }

    public static String createLine2() {
        return faker.address().streetName();
    }

    public static String createTown() {
        return faker.address().cityName();
    }

    public static String createCounty() {
        String[] countiesInGB = {"Co Durham", "Tyne & Wear", "Northumberland", "North Yorkshire", "Cumbria", "Mid Glamorgan", "Gwynedd"};
        int selectCounty = new Random().nextInt(countiesInGB.length);
        return countiesInGB[selectCounty];
    }

    public static String createPostCode() {
        String[] postcodesInGB = {"M1 1AA", "M60 1NW", "CR2 6XH", "DN55 1PT", "W1A 1HQ", "EC1A 1BB", "NE15 8NY", "NE6 1JS"};
        int selectPostcode = new Random().nextInt(postcodesInGB.length);
        return postcodesInGB[selectPostcode];
    }

    public static String createDisease() {
        return faker.medical().diseaseName();
    }

    public static String createCondition() {
        return faker.medical().symptoms();
    }

    public static void createAttendingPractitionerDetails() {
        createFullNameForAttendingPractitioner();
        createGMCNumber();
        createQualification();
    }

    public static void createFullNameForAttendingPractitioner() {
        userAttendingPractitionerFirstName = faker.name().firstName().replaceAll("\\W", "");
        userAttendingPractitionerLastName = faker.name().lastName().replaceAll("\\W", "");
    }

    public static void createGMCNumber() {
        apGmcNumber = faker.number().digits(7);
    }

    public static void createQualification() {
        String[] doctorsQualificationInGB = {"BMBS", "BM", "MBBCh", "MBChB", "MBChirB", "MBBS", "MFFM", "MRCGP", "MRCOG", "MRCP", "MRCS"};
        int selectDoctorsQualification = new Random().nextInt(doctorsQualificationInGB.length);
        apQualification = doctorsQualificationInGB[selectDoctorsQualification];
    }

    public static void apWorkAddress() {
        apWorkAddressLine1 = String.valueOf(createLine1());
        apWorkAddressLine2 = createLine2();
        apWorkAddressTown = createTown();
        apWorkAddressCounty = createCounty();
        apWorkAddressPostCode = createPostCode();
    }

    public static void pastDateMinusOne() {
        pastDayMinusOne = String.valueOf(currentDate.minusDays(1).getDayOfMonth());
        pastMonthMinusOne = String.valueOf(currentDate.minusMonths(1).getMonth().getValue());
        pastYearMinusOne = String.valueOf(currentDate.minusYears(1).getYear());
    }

    public static void futureDatePlusOne() {
        futureDayPlusOne = String.valueOf(currentDate.plusDays(1).getDayOfMonth());
        futureMonthPlusOne = String.valueOf(currentDate.plusMonths(1).getMonth().getValue());
        futureYearPlusOne = String.valueOf(currentDate.plusYears(1).getYear());
    }

    public static void createEmailIdForOutputs() {
        outputsFirstName = faker.name().firstName().replaceAll("\\W", "");
        outputsLastName = faker.name().lastName().replaceAll("\\W", "");
        outputsEmailAddress = outputsFirstName.toLowerCase() + "." + outputsLastName.toLowerCase() + "@email.com";
    }

    public static void createInvalidNames() {
        randomSingleChar = faker.letterify("?");
        randomMoreThanThirtyFiveChar = faker.letterify("????????????????????????????????????");
    }

    public static void createInvalidAge() {
        randomSingleChar = faker.letterify("?");
        randomMoreThanSeventyFiveChar = faker.letterify("????????????????????????????????????????????????????????????????????????????????");
        fourBlankSpaces = "    ";
    }

    public static void createCharsForDates() {
        twoRandomChars = faker.letterify("??");
    }

    public static void createRandomPassword() {
        randomPassword = faker.lorem().characters(15);
    }

    public static void createDeceasedAgeDays() {
        deceasedPersonAgeDays = String.valueOf(faker.number().numberBetween(0, 27));
    }

    public static void createDeceasedAgeHours() {
        deceasedPersonAgeHours = String.valueOf(faker.number().numberBetween(0, 24));
    }
}