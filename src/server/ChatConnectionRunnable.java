package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatConnectionRunnable implements Runnable{
    private int port;
    Socket clientSocket = null;
    ChatServer chatServer = null;

    @Override
    public void run() {
        activateServer(port);


    }

    private void activateServer(int port){
        try {
            ServerSocket serverSocket;
            serverSocket = new ServerSocket(port);
            System.out.println("Server is On...");
            clientSocket = serverSocket.accept();
            if (clientSocket != null && chatServer != null) {
                chatServer.addSocketToPool(clientSocket);
            }
            System.out.println("Connecting to: " + clientSocket.getRemoteSocketAddress());
            System.out.println("Connection accepted");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // activateServer(port);
    }

    public void setPort(int port){
        this.port = port;
    }

    public void setChatServer(ChatServer chatServer) {
        this.chatServer = chatServer;
    }
}
