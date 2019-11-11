package org.academiadecodigo.stringrays;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {

    private int serverPort;
    private ArrayList<ServerWorker> playerList = new ArrayList<>();

    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    @Override
    public void run(){
        try{
            ServerSocket serverSocket = new ServerSocket(serverPort);

            while(true){

                System.out.println("Waiting to accept player connection");

                Socket playerSocket = serverSocket.accept();

                System.out.println("Accepted connection from " + playerSocket);

                ServerWorker worker = new ServerWorker(this, playerSocket);
                playerList.add(worker);
                worker.start();
            }

        }catch (IOException e){
            System.out.println("Something went wrong in Server...");
            e.printStackTrace();
        }
    }

    public void removeWorker(ServerWorker worker){
        playerList.remove(worker);
    }

    public List<ServerWorker> getPlayerList(){
        return playerList;
    }
}
