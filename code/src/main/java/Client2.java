import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Client2 implements Runnable {
    private Socket socket = null;
    private DataOutputStream os = null;
    private String name = null;
    private InetAddress address;
    private int port;

    public Client2(int port, String s) {
        try {
            this.name = s;
            this.address = InetAddress.getLocalHost();
            this.port = port;
            this.socket = new Socket(this.address, port);
            this.os = new DataOutputStream(socket.getOutputStream());
            System.out.println("Client2 Created");
        } catch (IOException e) {
            System.out.println("Failed Creating Client 2: ");
            System.out.println("\t"+e.getMessage());
        }
    }

    public void run() {
        System.out.println(this.name+" Running");
        while (true) {
            try {
                this.os.writeUTF("Hello from " + this.name + "!");
                this.os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Closing Client2");
                try {
                    this.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    void close() throws IOException {
        this.socket.close();
        this.os.close();
        System.out.println("Client2 Closed");
    }
}
