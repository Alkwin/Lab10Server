import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SimpleServer {
    public static final int PORT = 8100;
    public static boolean serverState = true;
    public static ArrayList<Person> connections = new ArrayList<Person>();

    public SimpleServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while(serverState) {
                System.out.println ("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                if(serverState) {
                    new ClientThread(socket, serverSocket).start();
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }

    public static void main(String[] args) {
        SimpleServer server = new SimpleServer();
    }
}