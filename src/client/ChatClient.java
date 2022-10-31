package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatClient {
    public String username;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    ChatUI clientUI;
    boolean chatSyncLoop = true;
    Socket clientSocket;

    public ChatClient(String host, int port, ChatUI clientUI) {
        this.clientUI = clientUI;

        try {
            clientSocket = new Socket(host, port);
            sendMessageToServer("Test");
            receiveMessageFromServerAndSendItToUI();

            clientSocket.close();
            clientSocket = null;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUser(String username){
        this.username = username;
    }

    public void sendMessageToServer(String message){
        String formattedMessage = formatMessage(message);

        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            outputStream.writeObject(formattedMessage);
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void receiveMessageFromServerAndSendItToUI(){
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            String response = (String) objectInputStream.readObject();

            clientUI.addStringToChat(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String formatMessage(String message){
        return getCurrentTime() + " " + username + ": " + message;
    }

    private String getCurrentTime(){
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
