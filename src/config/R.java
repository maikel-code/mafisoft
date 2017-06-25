package config;

/*
  Created by mrpitty on 20.06.17.
 */

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;

public class R {
    private static Handler fileHandler = LogConfig.HandlerSingleton.getInstance().getFileHandler();

    public static class DB {
        public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    }

    public static class Pages {
        public static final String PATH_TO_MAIN_WINDOW = "gui/Homepage.fxml";
        public static final String PATH_TO_ADD_COURSE_WINDOW = "gui/AddCourse.fxml";
        public static final String PATH_TO_CHANGE_COURSE_WINDOW = "gui/CourseList.fxml";
        public static final String PATH_TO_ADD_CUSTOMER_WINDOW = "gui/AddCustomer.fxml";
        public static final String PATH_TO_CHANGE_CUSTOMER_WINDOW = "gui/ChangeCustomerData.fxml";

    }

    public static class LogConfig {

        public static Logger getLogger(Class clazz) {
            Logger logger = Logger.getLogger(clazz.getName());

            logger.setLevel(Level.FINE);
            logger.addHandler(fileHandler);
            fileHandler.setFormatter(new Formatter() {
                @Override
                public String format(LogRecord record) {
                    StringBuilder sb = new StringBuilder();

                    sb.append("Date: ")
                            .append(new SimpleDateFormat("dd.MM.yyyy").format(new Date(record.getMillis())))
                            .append("\n")
                            .append("Time: ")
                            .append(new SimpleDateFormat("HH:MM").format(new Time(System.currentTimeMillis())))
                            .append("\n")
                            .append("Level: ")
                            .append(record.getLevel().getLocalizedName())
                            .append("\n")
                            .append("Class: ")
                            .append(clazz.getName())
                            .append("\n");

                    if (record.getThrown() != null) {
                        sb.append("Method: ")
                                .append(record.getSourceMethodName())
                                .append("\n")
                                .append("Cause: ")
                                .append(record.getThrown().toString())
                                .append("\n")
                                .append("-----------------------------------------------------------------------------------")
                                .append("\n");
                    }

                    return sb.toString();
                }
            });

            return logger;
        }

        private static class HandlerSingleton {
            private static HandlerSingleton instance;

            private HandlerSingleton() {

            }

            private synchronized static HandlerSingleton getInstance() {
                if (instance == null) {
                    instance = new HandlerSingleton();
                }

                return instance;
            }

            private synchronized FileHandler getFileHandler() {
                try {
                    return new FileHandler("./logs/logging.%u.%g.log", 10_485_760, 50, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }

    }

}
