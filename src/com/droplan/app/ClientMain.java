package com.droplan.app;

import com.droplan.network.Client;

import java.io.IOException;
import java.util.Scanner;

public class ClientMain {
    private static final int PORT = 5000;
    private static final String ADDRESS = "localhost";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the room code: ");
        String code = sc.nextLine().trim();

        Client client = new Client(ADDRESS, PORT);
        try {
            if (client.join(code)) {
                System.out.println("Joined Successfully");
            } else {
                System.out.println("Invalid Roomcode");
            }
        } catch (IOException e) {
            System.out.println("Couldn't reach to the host");
        }
    }
}