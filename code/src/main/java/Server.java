import java.net.*;
import java.io.*;
import java.util.Arrays;

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
            System.out.println("-----------------");

            DatagramPacket packet
                    = new DatagramPacket(this.buf, this.buf.length);
            try {
                this.serverSocket.receive(packet);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            InetAddress address = packet.getAddress();
            int port = packet.getPort();

            packet = new DatagramPacket(this.buf, this.buf.length, address, port);
            line = new String(packet.getData(), 0, packet.getLength()).trim();

            Arrays.fill(buf, (byte) 0);

            if (line.equals("")) {
                System.out.println("Server state");
                System.out.println("\t"+this.state);
                System.out.println("Server Address");
                System.out.println("\t"+this.address.getHostAddress());
            } else
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
