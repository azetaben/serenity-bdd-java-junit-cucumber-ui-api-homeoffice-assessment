package homeoffice.PageObject.general;



import homeoffice.model.FieldInputModel;

import java.util.List;

public interface FieldInputPage {
    void inputFieldModelDataIntoAllPageFields(FieldInputModel var1);

    void inputFieldModelDataIntoSpecifiedPageFields(FieldInputModel var1, List<String> var2);

    void assertOnAllInputFieldsBeingCorrectlyPopulated(FieldInputModel var1);

    void assertEditableFieldsCanBeEdited();
}
