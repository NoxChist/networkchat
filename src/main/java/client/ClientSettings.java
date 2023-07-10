package client;

import tools.CsvParser;

import java.io.File;
import java.util.List;

public class ClientSettings {
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 8085;
    private static final String[] COLUMN_MAPPING = {"host", "port"};

    private String host;
    private int port;

    public ClientSettings() {
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static ClientSettings getSettingsFromCsv(String path, String fileName) {
        File file = new File(path, fileName);
        CsvParser<ClientSettings> parser = new CsvParser<>(file);
        List<ClientSettings> tempList = parser.parse(ClientSettings.class, COLUMN_MAPPING);
        if (!tempList.isEmpty() && tempList != null) {
            return tempList.get(0);
        }
        return null;
    }
}
