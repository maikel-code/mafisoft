package logger;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogRecord;

/*
  Created by mrpitty on 26.06.17.
 */

public class LogFormatter extends java.util.logging.Formatter {

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
                .append(record.getSourceClassName())
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
}
