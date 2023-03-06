import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server implements Runnable {

    private final ServerSocket serverSocket;
    private Thread connectionThread;
    private final InetAddress address;
    private enum states {IDLE, RUNNING, CLOSED}
    private states state;
    private ArrayList<Socket> clients;

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port,0,InetAddress.getLocalHost());
        this.serverSocket.setReuseAddress(true);
        this.address = InetAddress.getByName("localhost");
        System.out.println("Server Created at port "+port);
        this.state = states.IDLE;
    }

    public void checkForClients() throws IOException {
        Socket clientSocket = serverSocket.accept();
        DataInputStream is = new DataInputStream(clientSocket.getInputStream());
        System.out.println("New client added!");
        new Thread(() -> {
            String line = "";
            System.out.println("__________________");
            while (true) {
                try {
                    line = is.readUTF();
                    System.out.println("Server Received: " + line);
                    System.out.println("__________________");
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }).start();
    }

    public void run() {
        System.out.println("Server Running");
        this.state = states.RUNNING;
        while (this.state != states.CLOSED) {
            try {
                this.checkForClients();
                // wait for 5 seconds before checking for new clients again
            } catch (SocketTimeoutException e) {
                // this exception is thrown when the accept() method times out
                // it's safe to ignore it and continue the loop
            } catch (Exception e) {
                System.out.println(e.getMessage());
                break;
            }
        }
        System.out.println("Server Closed");
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(12345);
        Thread serverThread = new Thread(server);
        serverThread.start();
    }
}
