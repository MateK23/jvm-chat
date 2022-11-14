package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatClient {
    private String username;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
    ChatUI clientUI;
    private final String host;
    private final int port;
    Socket clientSocket;


    private void openConnectionToServer() throws IOException {
        clientSocket = new Socket(host, port);
    }

    private void closeConnectionToServer() throws IOException {
        clientSocket.close();
    }


    public ChatClient(String host, int port, ChatUI clientUI) {
        this.clientUI = clientUI;
        this.host = host;
        this.port = port;

        try {
            openConnectionToServer();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void sendMessageToServer(String message) {
        String formattedMessage = formatMessage(message);

        try {
            if (clientSocket != null) {
                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                outputStream.writeObject(formattedMessage);
                outputStream.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveMessageFromServer() {
        try {
            if (clientSocket != null) {
                ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                String message = (String) objectInputStream.readObject();
                sendMessageToUserInterface(message);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessageToUserInterface(String message){
        clientUI.addStringToChat(message);
    }

    private String formatMessage(String message) {
        return "[" + getCurrentTime() + "] " + username + ": " + message;
    }

    private String getCurrentTime() {
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public void setUser(String username) {
        this.username = username;
    }
}
