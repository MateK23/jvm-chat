package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    boolean serverActiveLoop = true;
    public ChatServer(int port) {
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server is On...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connection accepted");


            while(serverActiveLoop){
                receiveMessages(serverSocket, clientSocket);
            }

            serverSocket.close();
        }catch (Exception e){
            System.out.println("Failed to init server: " + e.getMessage());
            System.out.println("Restarting...\n");
            new ChatServer(port);
        }

    }

    public void receiveMessages(ServerSocket serverSocket, Socket clientSocket){
        if (clientSocket != null) {
            try {

                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

                String response = (String) inputStream.readObject();
                System.out.println("Getting response");

                ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                System.out.println("Received from client: " + response);
                outputStream.writeObject(response);


                outputStream.close();
                inputStream.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
