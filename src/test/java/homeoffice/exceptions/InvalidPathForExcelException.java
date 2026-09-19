package homeoffice.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class InvalidPathForExcelException extends InvalidPathForFilesException {
    private static final Logger log = LoggerFactory.getLogger(InvalidPathForExcelException.class);
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidPathForExcelException(String message) {
        super(message);
    }

    public InvalidPathForExcelException(String message, Throwable cause) {
        super(message, cause);
    }
}
