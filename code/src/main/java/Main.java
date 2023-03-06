import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Client c1 = new Client(5000, "Client 1");
        Client2 c2 = new Client2(5000, "Client 2");
        Server s = new Server(5000);

        Thread clientThread1 = new Thread(c1);
        Thread clientThread2 = new Thread(c2);
        Thread serverThread = new Thread(s);

        clientThread1.start();
        clientThread2.start();
        serverThread.start();
    }
}
