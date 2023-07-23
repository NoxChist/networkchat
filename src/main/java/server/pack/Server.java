package server.pack;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private static String path = "src/main/resources/server";
    private static String settingsFileName = "settings.csv";
    ////////////////////////////////////////////////////////
    private ServerSettings settings;
    private ServerSocket server;
    private ServerLogger logger;
    private int cnt;
    private ConcurrentHashMap<Integer, ClientChannel> channelMap;

    public Server() {
        settings = ServerSettings.getSettingsFromCsv(path, settingsFileName);
        try {
            server = new ServerSocket(settings.getPort());
            cnt = 0;
            channelMap = new ConcurrentHashMap<>();
            ClientChannel.setChannelMap(channelMap);
            logger = ServerLogger.getInstance();
            System.out.println("Server started " + settings.getPort());
            logger.log("[SYSTEM]", new Date(), String.format("Server started on port %d", settings.getPort()));
            System.out.println("Waiting for a connection.");
            logger.log("[SYSTEM]", new Date(), "Waiting for a connection.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConcurrentHashMap<Integer, ClientChannel> getChannelMap() {
        return channelMap;
    }

    public void start() {
        while (true) {
            try {
                Socket clientSocket = server.accept();
                cnt++;
                ClientChannel channel = new ClientChannel(cnt, clientSocket);
                channel.start();
                channelMap.put(cnt, channel);
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Error");
                logger.log("[ERROR]", new Date(), ex.getMessage());
            }
        }
    }
}
