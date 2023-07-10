package server;

import tools.Logger;

import java.io.IOException;
import java.util.Date;

public class ServerLogger extends Logger {
    protected static String defaultPath = "logs/file.log";
    protected static String defaultDataFormatPattern = "dd.MM.yy HH:mm";
    private static ServerLogger instance = null;

    protected ServerLogger() throws IOException {
        super(defaultPath, defaultDataFormatPattern);
    }

    public static ServerLogger getInstance() throws IOException {
        if (instance == null) {
            synchronized (ServerLogger.class) {
                if (instance == null) {
                    instance = new ServerLogger();
                }
            }
        }
        return instance;
    }

    synchronized public void log(String level, Date data, String msg) {
        try {
            writer.write(String.format("%s%s#%s\n", dataF.format(data), level, msg));
            writer.flush();
        } catch (Exception e) {
            System.out.println("Пичалька:(");
        }
    }

    public static void setDefaultServerLoggerSettings(String path, String dataFormatPattern) {
        if (instance == null) {
            defaultPath = path;
            defaultDataFormatPattern = dataFormatPattern;
        }
    }
}
