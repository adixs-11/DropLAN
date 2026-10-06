package com.droplan.network;

import com.droplan.core.ClientInfo;
import com.droplan.core.Room;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class HostServer {
    private final int port;
    private final Room room;
    private ServerSocket serverSocket;

    public HostServer(int port, Room room){
        this.port = port;
        this.room = room;
    }

    public void start(){
        try{
            serverSocket = new ServerSocket(port);
            System.out.println("Hosting room " + room.getRoomCode() + " on port " + port);
            acceptClient();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void acceptClient(){
        Socket clientSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try{
            clientSocket = serverSocket.accept();

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            String incomingCode = in.readLine();

            if (incomingCode != null && incomingCode.equals(room.getRoomCode())){
                out.println("OK");
                ClientInfo client = new ClientInfo("client-"+System.currentTimeMillis(), "Unknown");
                room.addClient(client);
                System.out.println("Successful");
            }else{
                out.println("Invalid");
                System.out.println("Reject the Client, Invalid Code Entered");
            }

        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                if (in != null){
                    in.close();
                }
                if (out != null){
                    out.close();
                }
                if (clientSocket != null){
                    clientSocket.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    }

    public void stop() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}




