package droplan.protocol;

public final class ProtocolCodec {
    private static final String DELIMITER = "|";

    private ProtocolCodec() {
    }

    public static String encode(ChatMessage message) {
        if (message == null) {
            throw new IllegalArgumentException("message is required");
        }

        String sender = message.getSender();
        String text = message.getText();

        if (sender.contains(DELIMITER)) {
            throw new IllegalArgumentException("sender cannot contain '|'");
        }
        if (containsLineBreak(sender) || containsLineBreak(text)) {
            throw new IllegalArgumentException("messages must fit on one line");
        }

        return message.getType().name()
                + DELIMITER + sender
                + DELIMITER + text;
    }

    public static ChatMessage decode(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("line is required");
        }

        String[] parts = line.split("\\|", 3);
        if (parts.length != 3) {
            throw new IllegalArgumentException("message must contain type, sender, and text");
        }

        MessageType type = MessageType.valueOf(parts[0]);
        return new ChatMessage(type, parts[1], parts[2]);
    }

    private static boolean containsLineBreak(String value) {
        return value.contains("\n") || value.contains("\r");
    }
}
