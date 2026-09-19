package homeoffice.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.util.List;

public class NoSuchElementsException extends FrameworkException {
    private static final Logger log = LoggerFactory.getLogger(NoSuchElementsException.class);
    @Serial
    private static final long serialVersionUID = 1L;

    public NoSuchElementsException(String message) {
        super(message);
    }

    public NoSuchElementsException(String message, Throwable cause) {
        super(message, cause);
    }

    public static void checkIfWebElementListContainsElementOrElseThrowNoSuchElementException(List<?> webElements, int webElementIndexToBeRetrieved) {
        if (webElements != null && !webElements.isEmpty()) {
            if (webElementIndexToBeRetrieved >= 0 && webElementIndexToBeRetrieved < webElements.size()) {
                if (webElements.get(webElementIndexToBeRetrieved) == null) {
                    throw new NoSuchElementsException("Web element at index " + webElementIndexToBeRetrieved + " is null.");
                }
            } else {
                throw new NoSuchElementsException("No web element exists at index: " + webElementIndexToBeRetrieved);
            }
        } else {
            throw new NoSuchElementsException("No web elements were found in the provided list.");
        }
    }
}
