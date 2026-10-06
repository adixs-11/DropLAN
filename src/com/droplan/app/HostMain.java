package com.droplan.app;
import com.droplan.core.Room;
import com.droplan.network.HostServer;
import com.droplan.util.RoomCodeGenerator;

public class HostMain {
    private static final int PORT = 5000;

    public static void main(String[] args) {
        String roomCode = RoomCodeGenerator.generate();
        Room room = new Room(roomCode);
        HostServer hostServer = new HostServer(PORT, room);
        System.out.println(roomCode);

        hostServer.start();
        hostServer.stop();
    }
}
