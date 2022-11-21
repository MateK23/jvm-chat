package client;

public class Main {
    public static void main(String[] args) {
        new ChatUI("User 1");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new ChatUI("User 2");
    }
}
