package logger;

import java.io.IOException;
import java.util.logging.FileHandler;

/*
  Created by mrpitty on 26.06.17.
 */
public class HTMLHandler extends FileHandler {
    public HTMLHandler() throws IOException, SecurityException {
    }

    public HTMLHandler(String pattern) throws IOException, SecurityException {
        super(pattern);
    }

    public HTMLHandler(String pattern, boolean append) throws IOException, SecurityException {
        super(pattern, append);
    }

    public HTMLHandler(String pattern, int limit, int count) throws IOException, SecurityException {
        super(pattern, limit, count);
    }

    public HTMLHandler(String pattern, int limit, int count, boolean append) throws IOException, SecurityException {
        super(pattern, limit, count, append);
    }
}
