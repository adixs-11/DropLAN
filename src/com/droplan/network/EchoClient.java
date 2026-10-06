package com.droplan.network;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) throws Exception {
        try (Socket clientSocket = new Socket("localhost", 5000)) {
            System.out.println("Client connected to the port 5000");

            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 Scanner sc = new Scanner(System.in)) {

                while (true) {
                    String message = sc.nextLine();

                    out.println(message);

                    if (message.equalsIgnoreCase("exit") || message.isBlank()) {
                        System.out.println("Closing connection.");
                        break;
                    }

                    System.out.println("Sent: " + message);

                    String reply = in.readLine();
                    System.out.println("Received: " + reply);
                }
            }
        }
    }
}