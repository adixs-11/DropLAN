package droplan.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class RoomServer {

    public static void main(String[] args) {
        RoomServer roomServer = new RoomServer();
        roomServer.start(5050);
    }

    public void start(int port) {
        // ServerSocket listens for clients on this port.
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Room server listening on port " + port);
            System.out.println("Waiting for one client...");

            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Accepted: " + client.getRemoteSocketAddress());

                Thread handlerThread = new Thread(new ClientHandler(client));
                handlerThread.start();
            }

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}