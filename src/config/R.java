package config;

/*
  Created by mrpitty on 20.06.17.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.*;

public class R {
    public static class Log {
        public static final String LOG_PROPERTIES_FILE = "config/logger.properties";
    }

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

    /*

    "/" the local pathname separator

    "%t" the system temporary directory

    "%h" the value of the "user.home" system property

    "%g" the generation number to distinguish rotated logs

    "%u" a unique number to resolve conflicts

    "%%" translates to a single percent sign "%"

     */

    public static class LogConfig {
        public static Logger getLogger(Class clazz) {
            Logger logger = null;
            Handler fileHandler;
            try {
                logger = Logger.getLogger(clazz.getName());
                logger.setLevel(Level.FINE);
                fileHandler = new FileHandler("./logs/logging%u.log");
                fileHandler.setFormatter(new Formatter() {
                    Date dat = new Date();
                    private final static String format = "{0,date} {0,time}";
                    private MessageFormat formatter;
                    private Object args[] = new Object[1];
                    private String lineSeparator = "\n";

                    @Override
                    public synchronized String format(LogRecord record) {

                        StringBuilder sb = new StringBuilder();

                        dat.setTime(record.getMillis());
                        args[0] = dat;

                        StringBuffer text = new StringBuffer();
                        if (formatter == null) {
                            formatter = new MessageFormat(format);
                        }
                        formatter.format(args, text, null);
                        sb.append(text);
                        sb.append(" ");


                        if (record.getSourceClassName() != null) {
                            sb.append(record.getSourceClassName());
                        } else {
                            sb.append(record.getLoggerName());
                        }

                        if (record.getSourceMethodName() != null) {
                            sb.append(" ");
                            sb.append(record.getSourceMethodName());
                        }
                        sb.append(" - ");


                        String message = formatMessage(record);

                        sb.append(record.getLevel().getLocalizedName());
                        sb.append(": ");

                        int iOffset = (1000 - record.getLevel().intValue()) / 100;
                        for (int i = 0; i < iOffset; i++) {
                            sb.append(" ");
                        }

                        sb.append(message);
                        sb.append(lineSeparator);
                        if (record.getThrown() != null) {
                            try {
                                StringWriter sw = new StringWriter();
                                PrintWriter pw = new PrintWriter(sw);
                                record.getThrown().printStackTrace(pw);
                                pw.close();
                                sb.append(sw.toString());
                            } catch (Exception ignored) {
                            }
                        }
                        return sb.toString();
                    }
                });
                logger.addHandler(fileHandler);

            } catch (IOException e) {
                e.printStackTrace();
            }

            logger.setLevel(Level.ALL);

            return logger;
        }
    }
}
