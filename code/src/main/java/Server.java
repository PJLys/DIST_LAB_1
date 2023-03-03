import java.net.*;
import java.io.*;

public class Server implements Runnable {

    private final DatagramSocket serverSocket;
    private final InetAddress address;
    private final int port;
    private enum states {IDLE, RUNNING, CLOSED}
    private states state;
    private byte[] buf = new byte[512];

    public Server(int port) throws IOException {
        this.serverSocket = new DatagramSocket(port);
        this.address = InetAddress.getByName("localhost");
        this.port = port;
        System.out.println("Server Created at port "+port);
        this.state = states.IDLE;
    }

    private void close() throws IOException {
        this.serverSocket.close();
        this.state = states.CLOSED;
        System.out.println("Server Closed");
    }

    public void run() {
        System.out.println("Server Running");
        this.state = states.RUNNING;
        String line = "";
        while (!line.equals("exit")) {
            DatagramPacket packet
                    = new DatagramPacket(buf, buf.length);
            try {
                this.serverSocket.receive(packet);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            InetAddress address = packet.getAddress();
            int port = packet.getPort();

            packet = new DatagramPacket(buf, buf.length, address, port);
            line = new String(packet.getData(), 0, packet.getLength()).trim();

            System.out.println("Server Received: "+line);
        }

        try {
            System.out.println("Closing server");
            this.close();
        } catch (IOException e) {
            System.out.println("ERR");
            System.out.println(e.getMessage());
        }
    }
}
