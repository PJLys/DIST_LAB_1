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

    public Client(int port) {
        try {
            this.address = InetAddress.getByName("localhost");
            this.port = port;
            this.socket = new DatagramSocket(port);

            System.out.println("Client Created!");

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
                line = this.reader.readLine();
                byte[] buf = line.getBytes(StandardCharsets.UTF_8);
                DatagramPacket packet = new DatagramPacket(buf, buf.length, this.address, 4096);
                socket.send(packet);
                System.out.println("Client sent: "+ packet);
            } catch (IOException e) {
                e.printStackTrace();
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
