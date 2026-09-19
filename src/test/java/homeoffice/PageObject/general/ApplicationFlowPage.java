package homeoffice.PageObject.general;



import homeoffice.enums.WebElements;
import homeoffice.exceptions.TableRowDoesNotExistException;
import homeoffice.model.FieldInputModel;

import java.util.List;
import java.util.Map;

public interface ApplicationFlowPage extends ElementPage, TitleAboveHeadingPage, ButtonPage, LinkPage,
        ToglePage, TextualContentPage, ExpandableLinkPage, SelectableElementPage, UserNamePage,
        FieldInputDetailsPage, EditablePage, BlankFieldPage, AcceptedUsernamesLoginPage, EmailPage,
        FieldDataInputPage, FieldInputPage, InteractableTablePage, MessageBelowHeadingPage,
        MultiFieldInputDetailsAssertablePage, OrderedPage, OrderIDPage, PersonalInformation,
        RadioButtonPage, TableBelowHeadingPage, UserDataInputPage {

    @Override
    void click(String elementName);

    void addProductToCart(String productName);

    void assertProductActionIsVisible(String actionName, String productName);

    void assertAcceptedUsersListContains(String username);

    void assertCartItemCount(int expectedCount);

    void assertCartBadgeIconIsNotVisible();

    void assertPageContainsContent(List<String> expectedContent);

    void assertOnAllInputFieldsBeingCorrectlyPopulated(FieldInputModel fieldInputModel);

    void assertTableHasElementInEveryRow(WebElements expectedElement);

    void assertCorrectTableContents(Map<String, String> expectedContents) throws TableRowDoesNotExistException;
}
