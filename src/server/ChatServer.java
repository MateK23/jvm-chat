package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChatServer {
    ChatConnectionRunnable chatListenRunnable = new ChatConnectionRunnable();
    Thread chatListenThread = new Thread(chatListenRunnable);
    Socket[] clientSocketPool = new Socket[1];


    public ChatServer(int port) {
        chatListenRunnable.setPort(port);
        chatListenRunnable.setChatServer(this);
        chatListenThread.start();

        // new ChatServer(port);
    }

    public void receiveMessage(Socket clientSocket){
        if (clientSocket != null) {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

                String message = (String) inputStream.readObject();
                System.out.println("Getting response");
                System.out.println("Received from client: " + message);

                broadcastMessage(clientSocket, message);
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

    public void addSocketToPool(Socket clientSocket) {
        int newSize = clientSocketPool.length + 1;

        Socket[] newSocketPool = new Socket[newSize];

        for (int i = 0; i < newSize-1; i++){
            newSocketPool[i] = clientSocketPool[i];
        }
        newSocketPool[newSize] = clientSocket;
        clientSocketPool = newSocketPool;

        for (int i = 0; i <= newSize; i++){
            System.out.println(clientSocketPool[i].getRemoteSocketAddress());
        }
    }

    /*
    public static int[] addX(int n, int arr[], int x)
    {
        int i;

        // create a new array of size n+1
        int newarr[] = new int[n + 1];

        // insert the elements from
        // the old array into the new array
        // insert all elements till n
        // then insert x at n+1
        for (i = 0; i < n; i++)
            newarr[i] = arr[i];

        newarr[n] = x;

        return newarr;
    }

     */

}
