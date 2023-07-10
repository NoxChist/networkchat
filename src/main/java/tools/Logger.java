package tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Logger {
    protected String logStrPattern = "%s %s: %s\n";
    protected SimpleDateFormat dataF;
    protected File logs;
    protected FileWriter writer;

    protected Logger(String path, String dateFormatPattern) throws IOException {
        logs = new File(path);
        if (!logs.exists()) {
            PathBuilder.build(logs.getParent());
            logs.createNewFile();
        }
        writer = new FileWriter(logs, true);
        dataF = new SimpleDateFormat(dateFormatPattern);
    }

    public String getLogStrPattern() {
        return logStrPattern;
    }

    synchronized public void log(Date date, String name, String msg) {
        try {
            writer.write(String.format(logStrPattern, dataF.format(date), name, msg));
            writer.flush();
        } catch (Exception e) {
            System.out.println("Пичалька:(");
        }
    }

    synchronized public void close() {
        try {
            writer.close();
        } catch (Exception e) {
        }
    }
}
