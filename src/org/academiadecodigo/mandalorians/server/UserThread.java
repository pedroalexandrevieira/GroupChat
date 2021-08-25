package org.academiadecodigo.mandalorians.server;


import java.io.*;
import java.net.Socket;

//This class is responsible for reading messages sent from the client
// and broadcasting(deliver a message from one client to all others clients)
public class UserThread extends Thread {

    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;

    public UserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            //It reads bytes and decodes them into characters
            InputStream input = socket.getInputStream();
            //Reads text from a character-input stream
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            //return an output stream for writing bytes to this socket.
            OutputStream output = socket.getOutputStream();
            // PrintWriter will convert characters into bytes
            writer = new PrintWriter(output, true);

            //print all users
            printUsers();

            //Read text from the client then add a reader into the userName
            String userName = reader.readLine();
            server.addUserName(userName);

            //Send a message for users that have a new connection
            String serverMessage = "New user connection " + userName;
            server.broadcast(serverMessage,this);

            String clientMessage;

            //Send message to users until they out(Dude, I´m out)
            do {
                clientMessage = reader.readLine();
                serverMessage = "[" + userName + "]: " + clientMessage;
                server.broadcast(serverMessage,this);
            } while (!clientMessage.equals("I´m out dude"));

            server.removeUser(userName,this);
            socket.close();

            // warn that user is out
            serverMessage = userName + " went out jacking off";
            server.broadcast(serverMessage,this);

        } catch (IOException ex) {
            System.out.println("Error in UserThead: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    //sends a list of online users to the newly connected user.
    public void printUsers(){
            writer.println("Connected users: " + server.getUserNames());
    }

    //sends a message to the client
    public void sendMessage(String message){
        writer.println(message);
    }
}
