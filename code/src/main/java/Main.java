import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Client c1 = new Client(2048, "Client 1");
        Client c2 = new Client(2049, "Client 2");
        Server s = new Server(4096);

        Thread clientThread1 = new Thread(c1);
        Thread clientThread2 = new Thread(c2);
        Thread serverThread = new Thread(s);

        clientThread1.start();
        clientThread2.start();
        serverThread.start();

    }
}
