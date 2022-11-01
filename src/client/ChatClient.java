package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatClient {
    private String username;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
    ChatUI clientUI;
    boolean chatSyncLoop = true;
    private String host;
    private int port;

    // sendMessageToServer("Test");
    // receiveMessageFromServerAndSendItToUI();


    public ChatClient(String host, int port, ChatUI clientUI) {
        this.clientUI = clientUI;
        this.host = host;
        this.port = port;

//        while (chatSyncLoop){
//            receiveMessageFromServerAndSendItToUI();
//        }

    }

    public void setUser(String username) {
        this.username = username;
    }

    public void sendMessageToServer(String message) {
        String formattedMessage = formatMessage(message);


        try {
            Socket clientSocket = new Socket(host, port);
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            outputStream.writeObject(formattedMessage);
            outputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveMessageFromServerAndSendItToUI() {
        try {
            Socket clientSocket = new Socket(host, port);
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            String response = (String) objectInputStream.readObject();
            clientUI.addStringToChat(response);
            clientSocket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String formatMessage(String message) {
        return "[" + getCurrentTime() + "] " + username + ": " + message;
    }

    private String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
