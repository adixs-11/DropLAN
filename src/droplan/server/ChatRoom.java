package droplan.server;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoom {
    private final Set<Participant> participants = ConcurrentHashMap.newKeySet();

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    public void removeParticipant(Participant participant) {
        participants.remove(participant);
    }

    public void broadcast(String message) {
        for (Participant participant : participants) {
            participant.getHandler().sendMessage(message);
        }
    }
}
