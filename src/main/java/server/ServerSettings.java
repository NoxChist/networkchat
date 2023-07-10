package server;

import tools.CsvParser;

import java.io.File;
import java.util.List;

public class ServerSettings {
    public static final int DEFAULT_PORT = 8085;
    public static final String[] COLUMN_MAPPING = {"port"};
    private int port;

    public ServerSettings() {
    }

    public int getPort() {
        return port;
    }

    public static ServerSettings getSettingsFromCsv(String path, String fileName) {
        File file = new File(path, fileName);
        CsvParser<ServerSettings> parser = new CsvParser<>(file);
        List<ServerSettings> tempList = parser.parse(ServerSettings.class, COLUMN_MAPPING);
        if (!tempList.isEmpty() && tempList != null) {
            return tempList.get(0);
        }
        return null;
    }
}
