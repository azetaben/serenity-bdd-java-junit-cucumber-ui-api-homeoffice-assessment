package homeoffice.utilities.tableutils;

public class TestDataTableEntryAppender {
    public static String getEntryNumberAsStringOrSetToEmptyStringIfZero(int entryNumber) {
        String entryNo = Integer.toString(entryNumber);
        if (entryNo.equals("0")) {
            entryNo = "";
        }

        return entryNo;
    }
}
