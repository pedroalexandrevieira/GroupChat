package org.academiadecodigo.mandalorians.client;

import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

//This class is responsible for reading input from the user and sending
// it to the server, continuously until the user types ‘dude, I´m out’ to end the chat.

public class WriteThread {

    private PrintWriter writer;
    private Socket socket;
    private ChatClient client;

    public WriteThread(Socket socket, ChatClient client){
        this.socket = socket;
        this.client = client;

        try {
            //return an output stream for writing bytes to this socket.
            OutputStream output = socket.getOutputStream();
            // PrintWriter will convert characters into bytes
            writer = new PrintWriter(output, true);

        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run(){

        Console console = System.console();

        String userName = console.readLine("\nEnter your name: ");
        //puts the userName in the method setUserName of the superclass
        client.setUserName(userName);
        writer.println(userName);

        String text;

        do {
            text = console.readLine("[" + userName + "]: ");
            writer.println(text);
        } while (!text.equals("dude, I´m out"));
        try {
            socket.close();

        } catch (IOException e) {
            System.out.println("Error writing to server: " + e.getMessage()
            );
        }
    }
}
