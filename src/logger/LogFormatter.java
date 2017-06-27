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
                .append(new SimpleDateFormat("HH:MM").format(new Time(record.getMillis())))
                .append("\n")
                .append("Level: ")
                .append(record.getLevel().getLocalizedName())
                .append("\n")
                .append("Class: ")
                .append(record.getSourceClassName())
                .append("\n");


        switch (record.getLevel().getName()) {
            case "INFO":
                sb = formatInfo(sb, record);
                break;
            case "SEVERE":
                sb = formatSevere(sb, record);
                break;

        }

        sb.append("-----------------------------------------------------------------------------------")
                .append("\n");

        return sb.toString();
    }

    private StringBuilder formatInfo(StringBuilder sb, LogRecord record) {
        sb.append("Log info: ")
                .append(record.getMessage())
                .append("\n");
        return sb;
    }

    private StringBuilder formatSevere(StringBuilder sb, LogRecord record) {

        if (record.getThrown() != null) {
            sb.append("Method: ")
                    .append(record.getSourceMethodName())
                    .append("\n")
                    .append("Cause: ")
                    .append(record.getThrown().toString())
                    .append("\n");
        }

        return sb;
    }

}
