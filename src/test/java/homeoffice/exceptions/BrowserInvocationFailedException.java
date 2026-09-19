package homeoffice.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class BrowserInvocationFailedException extends FrameworkException {
    private static final Logger log = LoggerFactory.getLogger(BrowserInvocationFailedException.class);
    @Serial
    private static final long serialVersionUID = 1L;

    public BrowserInvocationFailedException(String message) {
        super(message);
    }

    public BrowserInvocationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
