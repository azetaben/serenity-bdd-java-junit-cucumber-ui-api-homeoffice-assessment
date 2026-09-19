package homeoffice.StepsDefinitions;

import homeoffice.actions.FooterAction;
import io.cucumber.java.en.Then;
import net.serenitybdd.annotations.Steps;

public class FooterSteps {

    @Steps
    FooterAction footerAction;

    @Then("the footer is displayed")
    public void the_sauce_demo_footer_is_displayed() {
        footerAction.i_should_see_footer();
    }

    @Then("the footer social links are displayed")
    public void the_sauce_demo_footer_social_links_are_displayed() {
        footerAction.i_should_see_social_links();
    }

    @Then("the footer copy should be {string}")
    public void the_sauce_demo_footer_copy_should_be(String expectedCopy) {
        footerAction.i_should_see_footer_copy(expectedCopy);
    }
}
