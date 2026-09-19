package homeoffice.utilities.tableutils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableUtils {
    public static List<String> convertDatesIntoTableFormat(Map<String, String> expectedFieldDataFromPage) {
        String startMonth = (String) expectedFieldDataFromPage.get("Start Month");
        String startYear = (String) expectedFieldDataFromPage.get("Start Year");
        String finishMonth = (String) expectedFieldDataFromPage.get("Finish Month");
        String finishYear = (String) expectedFieldDataFromPage.get("Finish Year");
        String startDateInTableFormat = startMonth + "/" + startYear;
        String finishDateInTableFormat = finishMonth + "/" + finishYear;
        String dateInTableFormat = startDateInTableFormat + " - " + finishDateInTableFormat;
        List<String> fieldData = new ArrayList<>(expectedFieldDataFromPage.values());
        fieldData.remove(fieldData.indexOf(startMonth));
        fieldData.remove(fieldData.indexOf(startYear));
        fieldData.remove(fieldData.indexOf(finishMonth));
        fieldData.remove(fieldData.indexOf(finishYear));
        fieldData.add(dateInTableFormat);
        fieldData.add("Edit or");
        return fieldData;
    }
}
