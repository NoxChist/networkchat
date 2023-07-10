package client;

import server.ServerCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Client {
    private static final String STOP = "/exit";
    private static String path = "src/main/resources/client";
    private static String settingsFileName = "settings.csv";
    ////////////////////////////////////////////////////////
    private ClientLogger logger;
    private ClientSettings settings;
    private Socket clientSocket;
    private Scanner userInput;
    private PrintWriter out;
    private BufferedReader in;
    boolean onLine;

    public Client() throws IOException {
        settings = ClientSettings.getSettingsFromCsv(path, settingsFileName);
        userInput = new Scanner(System.in);
        clientSocket = new Socket(settings.getHost(), settings.getPort());
        onLine = true;
        logger = new ClientLogger();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        System.out.println(in.readLine());
        out.println(userInput.nextLine());
        System.out.println(in.readLine());
        new ReadMsg().start();
        new WriteMsg().start();
    }

    private class ReadMsg extends Thread {
        @Override
        public void run() {
            String msg;
            try {
                while (onLine) {
                    msg = in.readLine();
                    if (msg != null && msg.equals(ServerCommand.STOP.toString())) {
                        onLine = false;
                        System.out.println("Вы покинули чат.");
                        logger.log(new Date(), "Вы покинули чат.");
                        break;
                    }
                    System.out.println(msg);
                    logger.log(msg);
                }
            } catch (IOException e) {
                System.out.println("Тут херня какая-то");
            }
        }
    }

    public class WriteMsg extends Thread {
        @Override
        public void run() {
            while (onLine) {
                String userMsg;
                userMsg = userInput.nextLine();
                if (userMsg.equals(STOP)) {
                    out.println(userMsg);

                    break;
                } else {
                    out.println(userMsg);
                }
                logger.log(new Date(), "Вы", userMsg);
            }
        }
    }
}
