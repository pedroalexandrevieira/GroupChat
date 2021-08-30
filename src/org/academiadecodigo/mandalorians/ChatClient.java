package org.academiadecodigo.mandalorians;

//The ChatClient starts the client program,
//connects to a server specified by hostname/IP address and port number.

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

//type "I´m out dude" to finish the program
public class ChatClient {

    private static String hostname = "localhost";
    private static int port = 8080;
    private String userName = "GandaVieira";

    public ChatClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    private void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the chat Server");

            new ReadThread(socket, this);
            new WriteThread(socket, this);


        } catch (UnknownHostException ex) {
            System.out.println("Server no found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public static void main(String[] args) {

        ChatClient client = new ChatClient(hostname, port);
        client.execute();
    }
}
