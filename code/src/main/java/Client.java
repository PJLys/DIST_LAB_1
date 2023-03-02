import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client implements Runnable {
    private Socket socket = null;
    private BufferedReader reader = null;
    private DataOutputStream os = null;
    private String name = "Client";

    public Client(String address, int port) {
        try {
            this.socket = new Socket(address, port);
            System.out.println("Client Connected!");

            this.reader = new BufferedReader(new InputStreamReader(System.in));
            this.os = new DataOutputStream(socket.getOutputStream());

            this.name = "Client@"+address;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
        System.out.println("Client Running");
        String line = this.name;
        while (!line.equals("exit")) {
            try {
                this.os.writeUTF(line);
                this.os.flush();
                line = reader.readLine();
            } catch (IOException e) {
                System.out.println(e.getMessage());
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
