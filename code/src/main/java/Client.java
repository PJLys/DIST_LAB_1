import java.net.*;
import java.io.*;

public class Client implements Runnable {
    private Socket socket = null;
    private BufferedReader reader = null;
    private DataOutputStream os = null;
    private String name = "Client";
    private InetAddress address;
    private int port;

    public Client(int port, String clientName) {
        try {
            this.address = InetAddress.getLocalHost();
            this.port = port;
            this.name = clientName;
            System.out.println(this.name + " Created");

            this.reader = new BufferedReader(new InputStreamReader(System.in));

            this.name = "Client@"+this.address;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        try {
            this.socket = new Socket(this.address.getHostAddress(), port);
            this.os = new DataOutputStream(this.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(this.name+" Running");
        String line = this.name;

        while (!line.equals("exit")) {
            try {
                line = reader.readLine();
                this.os.writeUTF(line);
                this.os.flush();
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

    void close() throws IOException {
        this.reader.close();
        this.socket.close();
        this.os.close();
        System.out.println("Client Closed");
    }
}
