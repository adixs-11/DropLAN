package droplan.protocol;

public final class ProtocolCodec {
    private static final char DELIMITER = '|';

    private ProtocolCodec() {
    }

    public static String encode(ChatMessage message) {
        if (message == null) {
            throw new IllegalArgumentException("message is required");
        }
        return message.getType().name()
                + DELIMITER
                + escape(message.getSender())
                + DELIMITER
                + escape(message.getText());
    }

    public static ChatMessage decode(String line) {
        if (line == null || line.isBlank()) {
            throw new IllegalArgumentException("line is required");
        }

        String typeToken = null;
        String senderToken = null;
        String textToken = null;
        StringBuilder current = new StringBuilder();
        int field = 0;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '\\') {
                if (i + 1 >= line.length()) {
                    throw new IllegalArgumentException("Malformed message: dangling escape");
                }
                current.append(c);
                current.append(line.charAt(++i));
                continue;
            }
            if (c == DELIMITER && field < 2) {
                if (field == 0) {
                    typeToken = current.toString();
                } else {
                    senderToken = current.toString();
                }
                current.setLength(0);
                field++;
                continue;
            }
            current.append(c);
        }

        if (field != 2) {
            throw new IllegalArgumentException("Malformed message: " + line);
        }
        textToken = current.toString();

        MessageType type;
        try {
            type = MessageType.valueOf(typeToken);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown message type: " + typeToken, e);
        }

        return new ChatMessage(type, unescape(senderToken), unescape(textToken));
    }

    private static String escape(String value) {
        StringBuilder out = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            switch (c) {
                case '\\':
                    out.append("\\\\");
                    break;
                case '|':
                    out.append("\\|");
                    break;
                case '\n':
                    out.append("\\n");
                    break;
                case '\r':
                    out.append("\\r");
                    break;
                default:
                    out.append(c);
                    break;
            }
        }
        return out.toString();
    }

    private static String unescape(String value) {
        StringBuilder out = new StringBuilder(value.length());
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            if (c != '\\') {
                out.append(c);
                continue;
            }
            if (i + 1 >= value.length()) {
                throw new IllegalArgumentException("Malformed message: dangling escape");
            }
            char next = value.charAt(++i);
            switch (next) {
                case '\\':
                    out.append('\\');
                    break;
                case '|':
                    out.append('|');
                    break;
                case 'n':
                    out.append('\n');
                    break;
                case 'r':
                    out.append('\r');
                    break;
                default:
                    throw new IllegalArgumentException("Malformed message: unknown escape \\" + next);
            }
        }
        return out.toString();
    }
}
