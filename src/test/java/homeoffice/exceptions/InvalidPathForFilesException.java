package homeoffice.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class InvalidPathForFilesException extends FrameworkException {
    private static final Logger log = LoggerFactory.getLogger(InvalidPathForFilesException.class);
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidPathForFilesException(String message) {
        super(message);
    }

    public InvalidPathForFilesException(String message, Throwable cause) {
        super(message, cause);
    }
}
