package homeoffice.actions;

import homeoffice.PageObject.CheckoutCompletePage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckoutCompleteAction {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutCompleteAction.class);

    CheckoutCompletePage checkoutCompletePage;

    public void i_should_see_checkout_complete_page() {
        LOGGER.info("Checking checkout complete page is displayed");
        checkoutCompletePage.shouldDisplayCheckoutCompletePage();
    }

    public void i_should_see_checkout_complete_message(String expectedHeader, String expectedText) {
        LOGGER.info("Checking checkout complete message");
        checkoutCompletePage.shouldDisplayCompleteMessage(expectedHeader, expectedText);
    }

    public void i_go_back_to_products() {
        LOGGER.info("Going back to products from checkout complete page");
        checkoutCompletePage.backToProducts();
    }

    public void i_should_see_generate_pdf_order_button() {
        LOGGER.info("Checking Generate PDF order button is displayed");
        checkoutCompletePage.shouldDisplayGeneratePdfOrderButton();
    }

    public void i_download_pdf_order_to(String downloadDirectoryName) {
        LOGGER.info("Downloading PDF order to [{}]", downloadDirectoryName);
        checkoutCompletePage.downloadPdfOrderTo(downloadDirectoryName);
    }

}
