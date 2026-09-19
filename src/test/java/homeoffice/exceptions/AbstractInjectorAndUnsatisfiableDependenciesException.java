package homeoffice.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractInjectorAndUnsatisfiableDependenciesException extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(AbstractInjectorAndUnsatisfiableDependenciesException.class);

    public AbstractInjectorAndUnsatisfiableDependenciesException() {
    }

    public AbstractInjectorAndUnsatisfiableDependenciesException(String message) {
        super(message);
    }

    public AbstractInjectorAndUnsatisfiableDependenciesException(String message, Throwable cause) {
        super(message, cause);
    }

    public AbstractInjectorAndUnsatisfiableDependenciesException(Throwable cause) {
        super(cause);
    }

    protected AbstractInjectorAndUnsatisfiableDependenciesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
