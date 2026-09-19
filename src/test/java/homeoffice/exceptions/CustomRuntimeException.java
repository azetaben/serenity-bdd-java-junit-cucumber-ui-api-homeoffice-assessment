package homeoffice.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomRuntimeException extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(CustomRuntimeException.class);

    public CustomRuntimeException() {
    }

    public CustomRuntimeException(String message) {
        super(message);
    }

    public CustomRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomRuntimeException(Throwable cause) {
        super(cause);
    }

    protected CustomRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
