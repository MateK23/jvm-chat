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
                receiveMessage(clientSocket);
            }

            serverSocket.close();
        }catch (Exception e){

            new ChatServer(port);
        }

    }

    public void receiveMessage(Socket clientSocket){
        if (clientSocket != null) {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

                String message = (String) inputStream.readObject();
                System.out.println("Getting response");
                System.out.println("Received from client: " + message);




                broadcastMessage(clientSocket, message);
                inputStream.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void broadcastMessage(Socket clientSocket, String message){
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            outputStream.writeObject(message);

            outputStream.close();
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
