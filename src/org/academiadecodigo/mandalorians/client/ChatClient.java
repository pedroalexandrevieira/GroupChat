package org.academiadecodigo.mandalorians.client;

//The ChatClient starts the client program,
//connects to a server specified by hostname/IP address and port number.

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

//type "dude, I´m out" in order to finish the program
public class ChatClient {

    private static String hostname = "localhost";
    private static int port=10;
    private String userName = "GandaVieira";

    public ChatClient(String hostname, int port){
        this.hostname = hostname;
        this.port = port;
    }

    private void execute(){
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the chat Server");

            new ReadThread(socket,this);
            new WriteThread(socket,this);


        } catch (UnknownHostException ex) {
            System.out.println("Server no found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }
    }

    public void setUserName(String userName){
        this.userName=userName;
    }

    public String getUserName(){
        return this.userName;
    }

    public static void main(String[] args) {
       /* if (args.length<2) return;

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);*/

        ChatClient client = new ChatClient(hostname, port);
        client.execute();
    }
}
