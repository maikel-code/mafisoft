package logger;

import config.R;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/*
  Created by mrpitty on 26.06.17.
 */

public class Log {
    private static Logger logger;

    public static Logger getLogger(Class clazz) {
        if (logger == null) {
            Log.useProperties(clazz);
        }
        return logger;
    }

    public static void useProperties(Class logName) {
        System.setProperty("java.util.logging.config.file", R.LogConfig.LOG_PROPERTIES);
        LogManager manager = LogManager.getLogManager();

        try {
            manager.readConfiguration();
            Logger log = Logger.getLogger(logName.getName());
            manager.addLogger(log);
            logger = log;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
