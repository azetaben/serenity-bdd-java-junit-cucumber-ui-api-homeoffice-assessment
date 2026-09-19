package homeoffice.webelementdata;

public class FieldData {
    private final String fieldName;
    private String caseType;
    private int numberOfChars;
    private String valueType;
    private String fieldValue;

    public FieldData(String fieldValue, String fieldName) {
        this.fieldValue = fieldValue;
        this.fieldName = fieldName;
    }

    public FieldData(String fieldName, String caseType, int numberOfChars, String valueType) {
        this.fieldName = fieldName;
        this.caseType = caseType;
        this.numberOfChars = numberOfChars;
        this.valueType = valueType;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getCaseType() {
        return this.caseType;
    }

    public int getNumberOfChars() {
        return this.numberOfChars;
    }

    public String getFieldValue() {
        return this.fieldValue;
    }

    public String getValueType() {
        return this.valueType;
    }
}
