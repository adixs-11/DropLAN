package com.droplan.network;

import java.io.*;
import java.net.*;

public class EchoServer {
    public static void main(String[] args) throws Exception {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server is waiting for a connection on port 5000...");

            try (Socket clientSocket = serverSocket.accept();
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                System.out.println("Client connected!");

                while (true) {
                    String message = in.readLine();

                    if (message == null || message.equalsIgnoreCase("exit") || message.isBlank()) {
                        System.out.println("Client disconnected.");
                        break;
                    }

                    System.out.println("Received: " + message);
                    out.println("Echo: " + message);
                    System.out.println("Sent echo back.");
                }
            }
        }
    }
}