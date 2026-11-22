package droplan.client;

import java.io.InputStreamReader;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class RoomClient {

    public static void main(String[] args) {
        RoomClient client = new RoomClient();
        client.start("localhost", 5050);
    }

    public void start(String host, int port) {
        try (Socket clientSocket = new Socket(host, port);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            System.out.println("Connected to server at " + host + ":" + port);
            Scanner sc = new Scanner(System.in);
            while (true) {
                String msg = sc.nextLine();
                out.println(msg);

                String response = in.readLine();
                if (response == null) {
                    break;
                }
                System.out.println("Server response: " + response);

                if (msg.equals("/quit")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
