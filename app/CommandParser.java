package droplan.app;

public class CommandParser {
    public enum Kind {
        CHAT,
        HELP,
        QUIT,
        UNKNOWN,
        EMPTY
    }

    public record ParsedCommand(Kind kind, String text) {
    }

    public ParsedCommand parse(String input) {
        if (input == null || input.isBlank()) {
            return new ParsedCommand(Kind.EMPTY, "");
        }

        String text = input.trim();

        if (text.equalsIgnoreCase("/help")) {
            return new ParsedCommand(Kind.HELP, "");
        }

        if (text.equalsIgnoreCase("/quit")) {
            return new ParsedCommand(Kind.QUIT, "");
        }

        if (text.startsWith("/")) {
            return new ParsedCommand(Kind.UNKNOWN, text);
        }

        return new ParsedCommand(Kind.CHAT, text);
    }
}
