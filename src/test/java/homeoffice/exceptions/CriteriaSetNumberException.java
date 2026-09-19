package homeoffice.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class CriteriaSetNumberException extends FrameworkException {
    private static final Logger log = LoggerFactory.getLogger(CriteriaSetNumberException.class);
    @Serial
    private static final long serialVersionUID = 1L;

    public CriteriaSetNumberException(String message) {
        super(message);
    }

    public CriteriaSetNumberException(String message, Throwable cause) {
        super(message, cause);
    }
}
