package droplan.client;

import droplan.protocol.ChatMessage;
import droplan.protocol.ProtocolCodec;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientReceiver implements Runnable {
    private final BufferedReader in;

    public ClientReceiver(BufferedReader in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                try {
                    ChatMessage message = ProtocolCodec.decode(line);
                    System.out.println(message);
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid message from server: " + e.getMessage());
                }
            }
            System.out.println("Disconnected from server.");
        } catch (IOException e) {
            System.err.println("Connection error: " + e.getMessage());
        }
    }
}
