package homeoffice.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class CriteriaSetNotMoreThanOneException extends CriteriaSetNumberException {
    private static final Logger log = LoggerFactory.getLogger(CriteriaSetNotMoreThanOneException.class);
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_MESSAGE = "Criteria set number must be greater than one.";

    public CriteriaSetNotMoreThanOneException(String message) {
        super(message);
    }

    public CriteriaSetNotMoreThanOneException(String message, Throwable cause) {
        super(message, cause);
    }

    public static void checkIfCriteriaSetIsOverOneOrElseThrowException(String message, int criteriaSetNumber) {
        if (criteriaSetNumber <= 1) {
            throw new CriteriaSetNotMoreThanOneException(resolveMessage(message));
        }
    }

    private static String resolveMessage(String message) {
        return message != null && !message.trim().isEmpty() ? message : "Criteria set number must be greater than one.";
    }
}
