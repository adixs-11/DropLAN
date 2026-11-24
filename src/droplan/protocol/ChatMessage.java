package droplan.protocol;

public final class ChatMessage {
    private final MessageType type;
    private final String sender;
    private final String text;

    public ChatMessage(MessageType type, String sender, String text) {
        if (type == null) {
            throw new IllegalArgumentException("type is required");
        }
        if (sender == null || sender.isBlank()) {
            throw new IllegalArgumentException("sender is required");
        }
        this.type = type;
        this.sender = sender;
        this.text = text == null ? "" : text;
    }

    public MessageType getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return type + " from " + sender + ": " + text;
    }
}
