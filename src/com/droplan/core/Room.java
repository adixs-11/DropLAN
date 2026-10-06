package com.droplan.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Room {
    private final String  roomCode;
    private final List<ClientInfo> connectedClients = new ArrayList<>();

    public Room(String roomCode){
        this.roomCode = roomCode;
    }

    public String getRoomCode(){
        return roomCode;
    }

    public void addClient(ClientInfo client){
        connectedClients.add(client);
    }

    public void removeClient(ClientInfo client){
        connectedClients.remove(client);
    }

    public List<ClientInfo> getConnectedClients(){
        return Collections.unmodifiableList(connectedClients);
    }
}
