package org.academiadecodigo.mandalorians;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;


public class ChatServer {
    private static int port=8000;
    private Set<String> userNames;
    private Set<UserThread> userThreads;
    public static final int DEFAULT_PORT = 8080;
    ServerSocket serverSocket=null;



    public ChatServer(int port) {
        userNames = new HashSet<>();
        userThreads = new HashSet<>();

    }

    public static void main(String[] args) throws IOException {

        // exit application if no port number is specified
        if (args.length == 0) {
            System.out.println("Usage: java ChatServer [port]");
            System.exit(1);
        }
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        ChatServer server = new ChatServer(port);
        server.execute(port);

    }

    public void execute(int port) throws IOException {


        try {
            //wait for request of clients
            serverSocket = new ServerSocket(port);
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
            System.out.println("The user " + userName + " has been removed");
        }
    }

    //returns true if there are others users connected
    public boolean hasUsers(){
        return !this.userNames.isEmpty();
    }

    public Set<String> getUserNames(){
        return this.userNames ;
    }

}
