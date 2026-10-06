package com.droplan.network;

import java.net.*;
import java.io.*;

public class Client {
    private final String hostAddress;
    private final int port;

    public Client(String hostAddress, int port){
        this.hostAddress = hostAddress;
        this.port = port;
    }

    public boolean join(String roomCode){
        Socket clientSocket = null;
        BufferedReader in = null;
        PrintWriter out = null;

        try{
            clientSocket = new Socket(hostAddress, port);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(roomCode);
            String status = in.readLine();

            if ("OK".equals(status)){
                System.out.println("Joined room successfully.");
                return true;
            }
            System.out.println("Could not join: " + status);
            return false;

        }catch (IOException e){
            e.printStackTrace();
            return false;
        }finally {
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
}
