import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleServer {
    public static final int PORT = 8100;
    private final List<ClientThread> clientThreadList = new ArrayList<>();

    public SimpleServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            do {
                Socket socket = serverSocket.accept();
                ClientThread client = new ClientThread(socket, serverSocket);
                clientThreadList.add(client);
                client.start();
            } while (true);
            // Should somehow create a listener that checks the condition when a thread ends
        } catch (Exception e) {
            System.err.println("Error: " + e);
        }
    }

    private boolean shouldExit() {
        AtomicBoolean shouldExit = new AtomicBoolean(false);
        clientThreadList.forEach(clientThread -> {
            if(clientThread.getExit()) {
                shouldExit.set(true);
            }
        });
        return shouldExit.get();
    }

    public static void main(String[] args) {
        SimpleServer server = new SimpleServer();
    }
}