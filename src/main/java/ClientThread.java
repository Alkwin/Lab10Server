import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

class ClientThread extends Thread {
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private boolean loggedIn = false;

    public ClientThread(Socket socket, ServerSocket serverSocket) {
        this.socket = socket;
        this.serverSocket = serverSocket;
    }

    public void run() {
        try {
            boolean connected = true;
            while(connected) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                String request = in.readLine();
                System.out.println("Request " + request + " reached the server");
                String answer = "";
                if (request != null) {
                    answer = interpretRequest(request);
                    out.println(answer);
                    if(!loggedIn) {
                        connected = false;
                    }
                } else {
                    connected = false;
                }
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

    private String interpretRequest(String request) throws IOException {
        // Here we will handle each command and provide back an answer

        List<String> splitRequest = Arrays.asList(request.split(" ").clone());

        switch (splitRequest.get(0)) {
            case "register" -> {
                return "Server received the register request: " + request;
            }
            case "login" -> {
                loggedIn = true;
                return "Server received the login request: " + request;
            }
            case "friend" -> {
                return "Server received the friend request: " + request;
            }
            case "send" -> {
                return "Server received the send request: " + request;
            }
            case "read" -> {
                return "Server received the read request: " + request;
            }
            case "stop" -> {
                SimpleServer.serverState = false;
                serverSocket.close();
                return "Server received the stop request " + request;
            }
            case "logout" -> {
                loggedIn = false;
                return "Server received the logout request " + request;
            }
            default -> {
                return "Server received unknown request: " + request;
            }
        }
    }
}