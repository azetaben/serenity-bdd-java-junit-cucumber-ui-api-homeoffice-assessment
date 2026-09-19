package homeoffice.PageObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FooterPage extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(FooterPage.class);

    private static final String FOOTER = "[data-test='footer']";
    private static final String TWITTER_LINK = "[data-test='social-twitter']";
    private static final String FACEBOOK_LINK = "[data-test='social-facebook']";
    private static final String LINKEDIN_LINK = "[data-test='social-linkedin']";
    private static final String FOOTER_COPY = "[data-test='footer-copy']";

    public void shouldDisplayFooter() {
        LOGGER.info("Validating SauceDemo footer is displayed");
        waitAndScrollToElement(FOOTER);
        waitForElementVisible(FOOTER);
    }

    public void shouldDisplaySocialLinks() {
        LOGGER.info("Validating SauceDemo footer social links");
        shouldDisplaySocialLink(TWITTER_LINK, "Twitter", "https://twitter.com/saucelabs");
        shouldDisplaySocialLink(FACEBOOK_LINK, "Facebook", "https://www.facebook.com/saucelabs");
        shouldDisplaySocialLink(LINKEDIN_LINK, "LinkedIn", "https://www.linkedin.com/company/sauce-labs/");
    }

    public void shouldDisplayFooterCopy(String expectedCopy) {
        LOGGER.info("Validating SauceDemo footer copyright text");
        shouldHaveText(FOOTER_COPY, expectedCopy);
    }

    private void shouldDisplaySocialLink(String locator, String expectedText, String expectedHref) {
        shouldDisplayExternalLink(locator, expectedText, expectedHref, "_blank", "noreferrer");
    }
}
