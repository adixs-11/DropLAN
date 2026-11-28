package droplan.client;

import droplan.protocol.ChatMessage;
import droplan.protocol.MessageType;
import droplan.protocol.ProtocolCodec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class RoomClient {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            new RoomClient().start("localhost", 5050, input);
        }
    }

    public void start(String host, int port, Scanner input) {
        try (Socket clientSocket = new Socket(host, port);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(clientSocket.getInputStream()))) {

            System.out.println("Connected to server at " + host + ":" + port);
            System.out.print("Your name: ");
            String name = input.nextLine().trim();
            if (name.isBlank()) {
                name = "Guest";
            }

            Thread receiverHandler = new Thread(new ClientReceiver(in));
            receiverHandler.start();

            out.println(ProtocolCodec.encode(
                    new ChatMessage(MessageType.JOIN, name, "")));

            while (true) {
                String text = input.nextLine();
                if (text.equalsIgnoreCase("/quit")) {
                    out.println(ProtocolCodec.encode(
                            new ChatMessage(MessageType.QUIT, name, "")));
                    break;
                }

                if (!text.isBlank()) {
                    out.println(ProtocolCodec.encode(
                            new ChatMessage(MessageType.CHAT, name, text)));
                }
            }

            try {
                receiverHandler.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }
}
