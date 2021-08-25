package org.academiadecodigo.mandalorians.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

// This class is responsible for reading input from the server and printing it
// to the console repeatedly, until the client disconnects.

public class ReadThread extends Thread {

    private BufferedReader reader;
    private Socket socket;
    private ChatClient client;

    public ReadThread(Socket socket, ChatClient client) {
        this.socket = socket;
        this.client = client;

        try {
            //It reads bytes and decodes them into characters
            InputStream input = socket.getInputStream();
            //Reads text from a character-input stream
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run(){
        while (true){
            try {
                String response = reader.readLine();
                System.out.println("\n" + response);

                //prints the username after displaying the server´s message
                if (client.getUserName() != null) {
                    System.out.println("[" + client.getUserName() + "]: ");
                }
            } catch (IOException e) {
                System.out.println("Error reading from server: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }

}
