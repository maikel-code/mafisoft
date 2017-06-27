package logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;


/*
  Created by mrpitty on 26.06.17.
 */
public class HTMLFormatter extends java.util.logging.Formatter {
    @Override
    public String getHead(Handler handler) {
        return "<html>\n" +
                "<head>\n" +
                "\t<title>MaFiSoft logs</title>\n" +
                "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<table border=1>\n" +
                "\t<tr bgcolor=#e6e6e6>\n" +
                "\t\t<td>Time</td>\n" +
                "\t\t<td>Date</td>\n" +
                "\t\t<td>Level</td>\n" +
                "\t\t<td>Class</td>\n" +
                "\t\t<td>Method</td>\n" +
                "\t\t<td>Info</td>\n" +
                "\t\t<td>Cause</td>\n" +
                "\t\t<td>stacktrace</td>\n" +
                "\t</tr>";
    }

    @Override
    public String getTail(Handler handler) {
        return "\n</table>\n" +
                "</body>\n" +
                "</html>\n";
    }

    @Override
    public String format(LogRecord record) {
        StringBuilder result = new StringBuilder();
        Level level = record.getLevel();
        result.append("\n\t");

        if (level == Level.SEVERE) {
            result.append("<tr bgColor=Tomato>\n\t\t<td>");
        } else if (level == Level.INFO) {
            result.append("<tr bgColor=#34cb34>\n\t\t<td>");
        } else {
            result.append("<tr bgColor=WHITE>\n\t\t<td>");
        }

        result.append(new SimpleDateFormat("HH:MM").format(new Time(record.getMillis())))
                .append("</td>\n\t\t<td>")
                .append(new SimpleDateFormat("dd.MM.yyyy").format(new Date(record.getMillis())))
                .append("</td>\n\t\t<td>")
                .append(record.getLevel().toString())
                .append("</td>\n\t\t<td>")
                .append(record.getSourceClassName())
                .append("</td>\n\t\t<td>")
                .append(record.getSourceMethodName())
                .append("</td>\n\t\t<td>")
                .append(record.getMessage())
                .append("</td>\n\t\t<td>");


        Throwable thrown = record.getThrown();


        if (thrown != null) {
            result.append(record.getThrown().getMessage());
            result.append("</td>\n\t\t<td>");

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            record.getThrown().printStackTrace(pw);
            String stackTrace = sw.toString();

            result.append(stackTrace);
            result.append("\n\t\t</td>");
        } else {
            result.append("\n\t\t</td>\n\t\t<td>null");
            result.append("\n\t\t</td>");
        }

        result.append("\n\t</tr>\n");
        return result.toString();
    }
}
