package com.droplan.core;

public class ClientInfo {
    private final String clientId;
    private final String displayName;
    private final long connectedAt;

    public ClientInfo(String clientId, String displayName){
        this.clientId = clientId;
        this.displayName = displayName;
        this.connectedAt = System.currentTimeMillis();
    }

    public String getClientId() {
        return clientId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public long getConnectedAt() {
        return connectedAt;
    }

}
