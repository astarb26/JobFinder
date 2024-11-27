package server;

public class ServerDriver extends Thread{
    public static void main(String[] args) {
        Server server = new Server(12346);
        new Thread(server).start();
    }
}
