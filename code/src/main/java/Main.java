import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Client c = new Client(2048);
        Server s = new Server(4096);

        Thread clientThread = new Thread(c);
        Thread serverThread = new Thread(s);

        clientThread.start();
        serverThread.start();

    }
}
