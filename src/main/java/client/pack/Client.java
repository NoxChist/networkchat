package client.pack;

import server.pack.ServerCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {
    private static final String STOP = "/exit";
    private static String path = "src/main/resources/client";
    private static String settingsFileName = "settings.json";
    ////////////////////////////////////////////////////////
    private ClientLogger logger;
    private ClientSettings settings;
    private Socket clientSocket;
    private Scanner scanner;
    private PrintWriter out;
    private BufferedReader in;
    private String name = null;
    AtomicBoolean onLine;

    public Client() throws IOException {
        settings = ClientSettings.getSettingsFromJson(path, settingsFileName);
        scanner = new Scanner(System.in);
        clientSocket = new Socket(settings.getHost(), settings.getPort());
        onLine = new AtomicBoolean(true);
        logger = new ClientLogger();

    }

    public void start() throws IOException {
        System.out.print("Задайте ник: ");
        while (name == null || name.isEmpty()) {
            name = scanner.nextLine();
        }
        new WriteMsg().start();
        new ReadMsg().start();
    }

    private class ReadMsg extends Thread {
        @Override
        public void run() {
            String msg;
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                while (onLine.get()) {
                    msg = in.readLine();
                    if (msg != null && msg.equals(ServerCommand.STOP.toString())) {
                        onLine.getAndSet(false);
                        System.out.println("Вы покинули чат.");
                        logger.log(new Date(), "Вы покинули чат");
                        break;
                    }
                    System.out.println(msg);
                    logger.log(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Тут херня какая-то");
            } finally {
                try {
                    onLine.getAndSet(false);
                    in.close();
                    out.close();
                    clientSocket.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public class WriteMsg extends Thread {
        @Override
        public void run() {
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(name);
                while (onLine.get()) {
                    String userMsg;
                    do {
                        userMsg = scanner.nextLine();
                    } while (userMsg == null || userMsg.isEmpty());
                    out.println(userMsg);

                    logger.log(new Date(), "Вы", userMsg);
                    if (userMsg.equals(STOP)) {
                        onLine.set(false);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
