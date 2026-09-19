package homeoffice.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;

public class PageException extends Exception {
    private static final Logger log = LoggerFactory.getLogger(PageException.class);
    @Serial
    private static final long serialVersionUID = 4699775993150102769L;

    public PageException(String message) {
        super(message);
    }
}
