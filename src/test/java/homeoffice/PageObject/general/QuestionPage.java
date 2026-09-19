package homeoffice.PageObject.general;

public interface QuestionPage extends UserDataInputPage, RadioButtonPage, ApplicationFlowPage {
    void assertRadioButtonsBelowHeading();
}
