package logger;

import config.R;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/*
  Created by mrpitty on 26.06.17.
 */

public class Log {

    public static Logger getLogger(Class clazz) {
        return useProperties(clazz);
    }

    private static Logger useProperties(Class clazz) {
        try {
            System.setProperty(R.LogConfig.CONFIG_FILE, R.LogConfig.LOG_PROPERTIES);
        } catch (Exception e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
        }

        Logger log = Logger.getLogger(clazz.getName());
        LogManager manager = LogManager.getLogManager();

        try {
            manager.readConfiguration();
            manager.addLogger(log);
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }

        return log;
    }


}
