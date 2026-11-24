package droplan.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final ChatRoom room;
    private volatile PrintWriter clientOut;

    public ClientHandler(Socket socket, ChatRoom room) {
        this.socket = socket;
        this.room = room;
    }

    @Override
    public void run() {
        Participant participant = null;

        try (Socket s = this.socket;
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(s.getInputStream()));
             PrintWriter out = new PrintWriter(
                     s.getOutputStream(), true)) {

            this.clientOut = out;

            System.out.println("New client connected: " + s.getRemoteSocketAddress());

            String participantName = s.getRemoteSocketAddress().toString();
            participant = new Participant(participantName, this);
            room.addParticipant(participant);
            room.broadcast("[Room] " + participantName + " joined.");

            String message;
            while ((message = in.readLine()) != null) {
                if (message.equalsIgnoreCase("/quit")) {
                    out.println("Disconnecting...");
                    break;
                }

                System.out.println(
                        "Received from " + participantName + ": " + message);
                room.broadcast(participantName + ": " + message);
            }

        } catch (IOException e) {
            System.err.println(
                    "Communication error with client: " + e.getMessage());
        } finally {
            if (participant != null) {
                room.removeParticipant(participant);
                room.broadcast(
                        "[Room] " + participant.getName() + " left.");
            }

            System.out.println("Client connection closed.");
        }
    }
    public void sendMessage(String message) {
        if (clientOut != null) {
            clientOut.println(message);
        }
    }
}