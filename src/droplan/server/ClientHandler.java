package droplan.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (Socket s = this.socket;
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {

            System.out.println("New client connected: " + s.getRemoteSocketAddress());

            String message;
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("/quit")) {
                    out.println("Disconnecting...");
                    break;
                }

                // Per-client logic: Echo the message back (or process it)
                System.out.println("Received: " + message);
                out.println("Server: " + message);
            }
        } catch (IOException e) {
            System.err.println("Communication error with client: " + e.getMessage());
        } finally {
            System.out.println("Client connection closed.");
        }
    }
}
