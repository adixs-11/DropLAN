package droplan.app;

import java.util.Scanner;

public class ConsoleMenu {
    public enum Choice {
        CREATE_ROOM,
        JOIN_ROOM,
        EXIT
    }

    public Choice promptChoice(Scanner input) {
        while (true) {
            System.out.println("\n=== DropLAN ===");
            System.out.println("1) Create a room");
            System.out.println("2) Join a room");
            System.out.println("3) Exit");
            System.out.print("Choose: ");

            String choice = input.nextLine().trim();

            switch (choice) {
                case "1":
                    return Choice.CREATE_ROOM;
                case "2":
                    return Choice.JOIN_ROOM;
                case "3":
                    return Choice.EXIT;
                default:
                    System.out.println("Enter 1, 2, or 3.");
            }
        }
    }

    public String promptHost(Scanner input) {
        while (true) {
            System.out.print("Host IP: ");
            String host = input.nextLine().trim();

            if (!host.isBlank()) {
                return host;
            }

            System.out.println("Host IP cannot be blank.");
        }
    }

    public int promptPort(Scanner input) {
        while (true) {
            System.out.print("Port [5050]: ");
            String value = input.nextLine().trim();

            if (value.isBlank()) {
                return 5050;
            }

            try {
                int port = Integer.parseInt(value);
                if (port >= 1 && port <= 65535) {
                    return port;
                }
            } catch (NumberFormatException ignored) {
                // Use the common validation message below.
            }

            System.out.println("Enter a port number from 1 to 65535.");
        }
    }
}
