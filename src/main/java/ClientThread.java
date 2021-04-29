import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class ClientThread extends Thread {
    private Socket socket = null;
    private ServerSocket serverSocket = null;

    private Boolean exit = false;

    public ClientThread(Socket socket, ServerSocket serverSocket) {
        this.socket = socket;
        this.serverSocket = serverSocket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            String request = in.readLine();
            System.out.println("Request " + request + " reached the server");
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String answer = "";
            if(request!=null) {
                if (request.toLowerCase().contains("stop")) {
                    answer = "Server stopped for request " + request;
                    exit = true;
                    serverSocket.close();
                } else {
                    answer = interpretRequest(request);
                }
                out.println(answer);
            }
        } catch (IOException e) {
            System.err.println("Communication error... " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }

    private String interpretRequest(String request) {
        // Here we will handle each command and provide back an answer
        return "Server received the request " + request;
    }

    public Boolean getExit() {
        return exit;
    }
}