import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    public static final int PORT = 8100;
    public static boolean serverState = true;

    public SimpleServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while(serverState) {
                System.out.println ("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                if(serverState) {
                    new ClientThread(socket, serverSocket).start();
                }
            }
            // Should somehow create a listener that checks the condition when a thread ends
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }

    public static void main(String[] args) {
        SimpleServer server = new SimpleServer();
    }
}