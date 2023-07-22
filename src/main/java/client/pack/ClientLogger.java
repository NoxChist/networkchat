package client.pack;

import tools.Logger;

import java.io.IOException;
import java.util.Date;

public class ClientLogger extends Logger {

    private static String path = "logs/cFile.log";
    private static String dataFormatPattern = "HH:mm";

    public ClientLogger() throws IOException {
        super(path, dataFormatPattern);
    }

    public ClientLogger(String path, String dataFormatPattern) throws IOException {
        super(path, dataFormatPattern);
    }

    synchronized public void log(Date date, String msg) {
        try {
            writer.write(String.format("***%s в %s***\n", msg, dataF.format(date)));
            writer.flush();
        } catch (Exception e) {
            System.out.println("Пичалька:(");
        }
    }

    synchronized public void log(String msg) {
        try {
            writer.write(msg + "\n");
            writer.flush();
        } catch (Exception e) {
            System.out.println("Пичалька:(");
        }
    }
}
