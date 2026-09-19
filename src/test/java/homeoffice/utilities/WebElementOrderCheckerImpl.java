package homeoffice.utilities;

import net.serenitybdd.core.pages.WebElementFacade;

import java.util.ArrayList;
import java.util.List;

public class WebElementOrderCheckerImpl implements WebElementOrderChecker {
    List<? extends WebElementFacade> webElements;
    List<String> elementIds = new ArrayList<>();

    public WebElementOrderCheckerImpl(List<? extends WebElementFacade> webElements) {
        this.webElements = webElements;
    }

    public boolean areWebElementsOrderedLikeSpecifiedListByElementId(List<String> expectedElementIds) {
        this.populateAllIdsFromActualWebElementsThatAreAlsoPresentInExpectedElementIdList(expectedElementIds);

        for (int i = 0; i < this.elementIds.size(); ++i) {
            if (!((String) this.elementIds.get(i)).equals(expectedElementIds.get(i))) {
                return false;
            }
        }

        return true;
    }

    private void populateAllIdsFromActualWebElementsThatAreAlsoPresentInExpectedElementIdList(List<String> expectedElementIds) {
        for (WebElementFacade webElement : this.webElements) {
            if (expectedElementIds.contains(((WebElementFacade) webElement).getAttribute("id"))) {
                this.elementIds.add(((WebElementFacade) webElement).getAttribute("id"));
            }
        }

    }
}
