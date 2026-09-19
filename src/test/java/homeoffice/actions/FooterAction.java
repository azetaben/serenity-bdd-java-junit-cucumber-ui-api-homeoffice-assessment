package homeoffice.actions;

import homeoffice.PageObject.FooterPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FooterAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(FooterAction.class);

    FooterPage footerPage;

    public void i_should_see_footer() {
        LOGGER.info("Checking SauceDemo footer");
        footerPage.shouldDisplayFooter();
    }

    public void i_should_see_social_links() {
        LOGGER.info("Checking SauceDemo footer social links");
        footerPage.shouldDisplaySocialLinks();
    }

    public void i_should_see_footer_copy(String expectedCopy) {
        LOGGER.info("Checking SauceDemo footer copyright text");
        footerPage.shouldDisplayFooterCopy(expectedCopy);
    }
}
