import java.net.*;
import java.io.*;

public class Server implements Runnable {

    private final ServerSocket serverSocket;
    private Socket socket;
    private DataInputStream is;
    private String name;
    private enum states {IDLE, CONNECTED, RUNNING, CLOSED}
    private states state;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        System.out.println("Server Running at port "+port);
        this.state = states.IDLE;
    }

    public void connect() throws IOException {
        this.socket = this.serverSocket.accept();
        this.is = new DataInputStream(this.socket.getInputStream());
        this.state = states.CONNECTED;
        System.out.println("Server Connected");
    }

    private void close() throws IOException {
        this.socket.close();
        this.serverSocket.close();
        this.is.close();
        this.state = states.CLOSED;
        System.out.println("Server Closed");
    }

    public void run() {
        System.out.println("Server Running");
        this.state = states.RUNNING;
        String line = "";
        while (!line.equals("exit")) {
            try {
                try {
                    line = is.readUTF();
                } catch (EOFException e) {
                    break;
                }

                System.out.println("Server Received: " + line);
                System.out.println("__________________");
            } catch (IOException e) {
                System.out.println("ERR");
                System.out.println(e.getMessage());
                e.printStackTrace();
                break;
            }
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
