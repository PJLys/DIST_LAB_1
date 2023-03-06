import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client implements Runnable {
    private DatagramSocket socket = null;
    private BufferedReader reader = null;
    private DataOutputStream os = null;
    private String name = "Client";
    private InetAddress address;
    private int port;

    private String clientName;

    public Client(int port, String clientName) {
        try {
            this.address = InetAddress.getByName("localhost");
            this.port = port;
            this.socket = new DatagramSocket(port);
            this.clientName = clientName;

            System.out.println(this.clientName + " Created");

            this.reader = new BufferedReader(new InputStreamReader(System.in));
            this.name = "UDP Client";
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        System.out.println("Client Running");
        String line = this.name;
        while (!line.equals("exit")) {
            try {
                line = "Test message from " + this.clientName;
                byte[] buf = line.getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(buf, buf.length, this.address, 4096);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Closing Client");

        try {
            this.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void close() throws IOException {
        this.reader.close();
        this.socket.close();
        System.out.println("Client Closed");
    }
}
