package droplan.app;

import droplan.client.RoomClient;
import droplan.server.RoomServer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConsoleMenu menu = new ConsoleMenu();

        try (Scanner input = new Scanner(System.in)) {
            while (true) {
                ConsoleMenu.Choice choice = menu.promptChoice(input);

                switch (choice) {
                    case CREATE_ROOM -> new RoomServer().start(menu.promptPort(input));
                    case JOIN_ROOM -> {
                        String host = menu.promptHost(input);
                        int port = menu.promptPort(input);
                        new RoomClient().start(host, port, input);
                    }
                    case EXIT -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                }
            }
        }
    }
}
