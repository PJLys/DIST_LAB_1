import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Server s = new Server(5000);
        Client c = new Client("127.0.0.1", 5000);

        s.connect();

        Thread serverThread = new Thread(s);
        Thread clientThread = new Thread(c);

        serverThread.start();
        clientThread.start();
    }
}
