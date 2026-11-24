package droplan.client;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientReceiver implements Runnable {
    private final BufferedReader in;

    ClientReceiver(BufferedReader in){
        this.in = in;
    }

    @Override
    public void run() {
        try{
            String msg;
            while ((msg = in.readLine()) != null) {
                System.out.println(msg);
            }
        }catch (IOException e){
            System.err.println("Connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
