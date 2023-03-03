import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Server s = new Server(4096);
        Client c = new Client(2048);

        Thread serverThread = new Thread(s);
        Thread clientThread = new Thread(c);

        serverThread.start();
        clientThread.start();
    }
}
