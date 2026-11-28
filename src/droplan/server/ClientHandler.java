package droplan.server;

import droplan.protocol.ChatMessage;
import droplan.protocol.MessageType;
import droplan.protocol.ProtocolCodec;

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
             PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {

            this.clientOut = out;
            System.out.println("New client connected: " + s.getRemoteSocketAddress());

            String joinLine = in.readLine();
            if (joinLine == null) {
                return;
            }

            ChatMessage joinMessage = ProtocolCodec.decode(joinLine);
            if (joinMessage.getType() != MessageType.JOIN) {
                System.out.println("Client did not send JOIN first.");
                return;
            }

            String participantName = joinMessage.getSender();
            participant = new Participant(participantName, this);
            room.addParticipant(participant);
            room.broadcast(ProtocolCodec.encode(
                    new ChatMessage(MessageType.JOIN, participantName, "")));

            String line;
            while ((line = in.readLine()) != null) {
                ChatMessage incoming = ProtocolCodec.decode(line);

                if (incoming.getType() == MessageType.QUIT) {
                    out.println(ProtocolCodec.encode(
                            new ChatMessage(MessageType.LEAVE, "SYSTEM", "Disconnecting...")));
                    break;
                }

                if (incoming.getType() == MessageType.CHAT) {
                    ChatMessage chatMessage = new ChatMessage(
                            MessageType.CHAT, participantName, incoming.getText());
                    room.broadcast(ProtocolCodec.encode(chatMessage));
                } else {
                    System.out.println("Ignoring unexpected message type: " + incoming.getType());
                }
            }
        } catch (IOException e) {
            System.err.println("Communication error with client: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid protocol message: " + e.getMessage());
        } finally {
            if (participant != null) {
                room.removeParticipant(participant);
                room.broadcast(ProtocolCodec.encode(
                        new ChatMessage(MessageType.LEAVE, participant.getName(), "")));
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
