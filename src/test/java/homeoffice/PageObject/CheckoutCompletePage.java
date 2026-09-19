package homeoffice.PageObject;

import net.serenitybdd.core.pages.WebElementFacade;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CheckoutCompletePage extends BasePage {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutCompletePage.class);

    private static final String CHECKOUT_COMPLETE_CONTAINER = "[data-test='checkout-complete-container']";
    private static final String PONY_EXPRESS_IMAGE = "[data-test='pony-express']";
    private static final String COMPLETE_HEADER = "[data-test='complete-header']";
    private static final String COMPLETE_TEXT = "[data-test='complete-text']";
    private static final String BACK_TO_PRODUCTS_BUTTON = "[data-test='back-to-products']";
    private static final String GENERATE_PDF_ORDER_BUTTON = "[data-test='generate-pdf-order']";
    private static final long DOWNLOAD_TIMEOUT_IN_SECONDS = 10;

    public void shouldDisplayCheckoutCompletePage() {
        LOGGER.info("Validating checkout complete page is displayed");
        shouldDisplayPageWithClickableElements(
                "/checkout-complete.html",
                java.util.List.of(CHECKOUT_COMPLETE_CONTAINER, PONY_EXPRESS_IMAGE),
                java.util.List.of(BACK_TO_PRODUCTS_BUTTON));
    }

    public void shouldDisplayCompleteMessage(String expectedHeader, String expectedText) {
        LOGGER.info("Validating checkout complete message");
        shouldHaveText(COMPLETE_HEADER, expectedHeader);
        shouldHaveText(COMPLETE_TEXT, expectedText);
    }

    public void backToProducts() {
        LOGGER.info("Navigating back to products from checkout complete page");
        waitAndClick(BACK_TO_PRODUCTS_BUTTON);
        waitForUrlContaining("/inventory.html");
    }

    public void shouldDisplayGeneratePdfOrderButton() {
        LOGGER.info("Validating Generate PDF order button is displayed");
        WebElementFacade generatePdfOrderButton = waitForElementClickable(GENERATE_PDF_ORDER_BUTTON);
        Assertions.assertThat(generatePdfOrderButton.getText().trim()).isEqualTo("Generate PDF order");
    }

    public void downloadPdfOrderTo(String downloadDirectoryName) {
        LOGGER.info("Downloading PDF order to [{}]", downloadDirectoryName);
        Path downloadDirectory = prepareDownloadDirectory(downloadDirectoryName);
        Path browserDownloadDirectory = Path.of(System.getProperty("user.home"), "Downloads").toAbsolutePath().normalize();
        Set<Path> filesBeforeDownload = filesIn(downloadDirectory);
        Set<Path> browserFilesBeforeDownload = filesIn(browserDownloadDirectory);

        WebElementFacade generatePdfOrderButton = waitForElementClickable(GENERATE_PDF_ORDER_BUTTON);
        scrollToElement(generatePdfOrderButton);
        generatePdfOrderButton.click();

        Set<Path> downloadedFiles = waitForNewPdfFiles(downloadDirectory, filesBeforeDownload);
        if (downloadedFiles.isEmpty()) {
            downloadedFiles = copyNewBrowserDownloadedPdfTo(downloadDirectory, browserDownloadDirectory, browserFilesBeforeDownload);
        }

        Assertions.assertThat(downloadedFiles)
                .as("Downloaded PDF order files in " + downloadDirectory)
                .isNotEmpty();
    }

    private Path prepareDownloadDirectory(String downloadDirectoryName) {
        Path downloadDirectory = Path.of(downloadDirectoryName).toAbsolutePath().normalize();
        try {
            Files.createDirectories(downloadDirectory);
            return downloadDirectory;
        } catch (IOException exception) {
            throw new IllegalStateException("Could not create download directory: " + downloadDirectory, exception);
        }
    }

    private Set<Path> waitForNewPdfFiles(Path downloadDirectory, Set<Path> filesBeforeDownload) {
        long endTime = System.currentTimeMillis() + Duration.ofSeconds(DOWNLOAD_TIMEOUT_IN_SECONDS).toMillis();
        while (System.currentTimeMillis() < endTime) {
            Set<Path> downloadedFiles = filesIn(downloadDirectory).stream()
                    .filter(file -> !filesBeforeDownload.contains(file))
                    .filter(this::isCompletedDownloadFile)
                    .filter(this::isPdfFile)
                    .collect(Collectors.toSet());
            if (!downloadedFiles.isEmpty()) {
                return downloadedFiles;
            }
            waitABit(250);
        }
        return Set.of();
    }

    private Set<Path> copyNewBrowserDownloadedPdfTo(
            Path targetDownloadDirectory, Path browserDownloadDirectory, Set<Path> browserFilesBeforeDownload) {
        Set<Path> browserDownloadedFiles = waitForNewPdfFiles(browserDownloadDirectory, browserFilesBeforeDownload);
        return browserDownloadedFiles.stream()
                .map(browserDownloadedFile -> copyFileTo(browserDownloadedFile, targetDownloadDirectory))
                .collect(Collectors.toSet());
    }

    private Path copyFileTo(Path sourceFile, Path targetDownloadDirectory) {
        Path targetFile = targetDownloadDirectory.resolve(sourceFile.getFileName()).toAbsolutePath().normalize();
        try {
            Files.copy(sourceFile, targetFile, StandardCopyOption.REPLACE_EXISTING);
            return targetFile;
        } catch (IOException exception) {
            throw new IllegalStateException("Could not copy downloaded PDF to " + targetFile, exception);
        }
    }

    private Set<Path> filesIn(Path directory) {
        if (!Files.exists(directory)) {
            return Set.of();
        }
        try (Stream<Path> files = Files.list(directory)) {
            return files
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toSet());
        } catch (IOException exception) {
            throw new IllegalStateException("Could not list files in download directory: " + directory, exception);
        }
    }

    private boolean isCompletedDownloadFile(Path file) {
        String fileName = file.getFileName().toString();
        return !fileName.endsWith(".crdownload") && !fileName.endsWith(".tmp");
    }

    private boolean isPdfFile(Path file) {
        return file.getFileName().toString().toLowerCase().endsWith(".pdf");
    }
}
