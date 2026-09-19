package homeoffice.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.util.List;

public class TableRowDoesNotExistException extends FrameworkException {
    private static final Logger log = LoggerFactory.getLogger(TableRowDoesNotExistException.class);
    @Serial
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_MESSAGE = "The specified row does not exist within the table.";

    public TableRowDoesNotExistException(String message) {
        super(message);
    }

    public TableRowDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public static void checkIfTableRowExistsOrElseThrowException(String message, List<?> rows, int rowIndex) {
        if (rows != null && !rows.isEmpty()) {
            if (rowIndex >= 0 && rowIndex < rows.size()) {
                if (rows.get(rowIndex) == null) {
                    throw new TableRowDoesNotExistException(resolveMessage(message));
                }
            } else {
                throw new TableRowDoesNotExistException(resolveMessage(message));
            }
        } else {
            throw new TableRowDoesNotExistException(resolveMessage(message));
        }
    }

    private static String resolveMessage(String message) {
        return message != null && !message.trim().isEmpty() ? message : "The specified row does not exist within the table.";
    }
}
