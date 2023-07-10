package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static String path = "src/main/resources/server";
    private static String settingsFileName = "settings.csv";

    private static ServerSettings settings;
    private static int cnt;
    private static ConcurrentHashMap<Integer, ClientChannel> channelMap;

    public static ConcurrentHashMap<Integer, ClientChannel> getChannelMap() {
        return channelMap;
    }

    public static void run() {
        settings = ServerSettings.getSettingsFromCsv(path, settingsFileName);
        try (ServerSocket server = new ServerSocket(settings.getPort())) {
            cnt = 0;
            channelMap = new ConcurrentHashMap<>();
            ServerLogger logger = ServerLogger.getInstance();
            System.out.println("Server started " + settings.getPort());
            logger.log("[SYSTEM]", new Date(), String.format("Server started on port %d", settings.getPort()));
            System.out.println("Waiting for a connection.");
            logger.log("[SYSTEM]", new Date(), "Waiting for a connection.");

            while (true) {
                Socket clientSocket = server.accept();
                try {
                    cnt++;
                    channelMap.put(cnt, new ClientChannel(cnt, clientSocket));

                } catch (IOException ex) {
                    System.out.println("Error");
                    logger.log("[ERROR]", new Date(), String.format("Client socket(%d) will be closed", cnt));
                    clientSocket.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
