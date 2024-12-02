package main.java.com.sudokusolver.server;

public class ServerDriver {
    public static void main(String[] args) {
        Server server = new Server();
        new Thread(server::start).start();
    }
}
