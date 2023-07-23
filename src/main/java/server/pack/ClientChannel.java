package server.pack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class ClientChannel extends Thread {
    private static final String STOP = "/exit";
    private static SimpleDateFormat dataF = new SimpleDateFormat("HH:mm");
    private static ConcurrentHashMap<Integer, ClientChannel> channelMap;
    private int id;
    private String name;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientChannel(int id, Socket socket) throws IOException {
        this.id = id;
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static void setChannelMap(ConcurrentHashMap<Integer, ClientChannel> channelMap) {
        ClientChannel.channelMap = channelMap;
    }

    @Override
    public void run() {
        String inputMsg;
        Date date;
        try {
            ServerLogger logger = ServerLogger.getInstance();
            do {
                sendToClient("Задайте ник: ");
                inputMsg = in.readLine();
            } while (inputMsg == null || inputMsg.isEmpty());
            name = inputMsg;
            sendToClient(String.format("Привет, %s, добро пожаловать!", name));
            date = new Date();
            for (ClientChannel client : channelMap.values()) {
                client.sendToClient("***" + name + " присоединился к чату.***");
            }
            logger.log("[INFO]", date, name + " присоединился к чату.");

            while (true) {
                inputMsg = in.readLine();
                while (inputMsg == null) {
                    inputMsg = in.readLine();
                }

                if (inputMsg.equalsIgnoreCase(STOP)) {
                    date = new Date();
                    for (ClientChannel client : channelMap.values()) {
                        if (client != this) {
                            client.sendToClient("***" + name + " покинул чат.***");
                        }
                    }
                    logger.log("[INFO]", date, " " + name + " покинул чат.");
                    out.println(ServerCommand.STOP.toString());
                    channelMap.remove(id);
                    break;
                }

                date = new Date();
                for (ClientChannel client : channelMap.values()) {
                    if (client != this) {
                        client.sendToClient(dataF.format(date) + " " + name + ": " + inputMsg);
                    }
                }
                logger.log(date, name, inputMsg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendToClient(String msg) {
        out.println(msg);
    }
}
