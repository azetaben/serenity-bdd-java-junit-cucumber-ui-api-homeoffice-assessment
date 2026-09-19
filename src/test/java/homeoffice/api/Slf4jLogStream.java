package homeoffice.api;

import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

final class Slf4jLogStream extends OutputStream {

    private final Logger logger;
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    Slf4jLogStream(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void write(int value) throws IOException {
        if (value == '\n') {
            flush();
            return;
        }

        if (value != '\r') {
            buffer.write(value);
        }
    }

    @Override
    public void flush() {
        if (buffer.size() == 0) {
            return;
        }

        logger.info(buffer.toString(StandardCharsets.UTF_8));
        buffer.reset();
    }
}
