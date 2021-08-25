package org.academiadecodigo.mandalorians.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

//ChatServer class has two Set collections to keep track the
// names and threads of the connected clients.
// Set is used because it doesn’t allow duplication
// and the order of elements does not matter

//https://www.codejava.net/java-se/networking/how-to-create-a-chat-console-application-in-java-using-socket


public class ChatServer {
    private static int port;
    private Set<String> userNames;
    private Set<UserThread> userThreads;



    public ChatServer(int port) {
        userNames = new HashSet<>();
        userThreads = new HashSet<>();
        this.port = port;
    }

    public static void main(String[] args) throws IOException {

        int port = Integer.parseInt(args[0]);

        ChatServer server = new ChatServer(port);
        server.execute();

    }

    public void execute() throws IOException {

        //
        try {
            //wait for request of clients
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Chat Server is listening on port " + port);

            while (true) {
                //accept the requests
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");

                //creating a new user(Thread) and save inside of memory
                UserThread newUser = new UserThread(socket, this);
                userThreads.add(newUser);
                newUser.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   /* public static void main(String[] args) throws IOException {
        *//*if (args.length < 1){
            System.out.println("Syntax: java ChatServer <port-number>");
            System.exit(0);
        }*//*
       // int port = Integer.parseInt(args[0]);
        ChatServer server = new ChatServer(port);
        server.execute();
    }*/

    //delivers a message from one user to others (broadcasting)
    public void broadcast(String message, UserThread excludeUser){
        for (UserThread aUser : userThreads){
            if (aUser != excludeUser){
                aUser.sendMessage(message);
            }
        }
    }

    //Stores username of the newly connected client
    public void addUserName(String userName){
        userNames.add(userName);
    }

    public void removeUser(String userName, UserThread aUser){
        boolean removed = userNames.remove(userName);
        if (removed){
            userThreads.remove(aUser);
            System.out.println("The user " + userName + " went out jacking off");
        }
    }

    //returns true if there are others users connected
    public boolean hasUsers(){
        return !this.userNames.isEmpty();
    }

    public Set<String> getUserNames(){
        return this.userNames;
    }

}
