package com.droplan.util;
import java.util.Random;

public class RoomCodeGenerator {
    private static final Random RANDOM = new Random();

    private RoomCodeGenerator(){}

    public static String generate(){
        int number = RANDOM.nextInt(1_000_000);
        return String.format("%06d", number);
    }
}