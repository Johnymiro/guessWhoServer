package org.academiadecodigo.stringrays;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerWorker extends Thread{

    private Server server;
    private Socket playerSocket;
    private PrintWriter printWriter;
    private String threadName = currentThread().getName();
    private String greetingMessage = "Welcome!\n";

    public ServerWorker(Server server, Socket playerSocket){
        this.server = server;
        this.playerSocket = playerSocket;
    }


    @Override
    public void run(){
        try{

            handlePlayerSocket();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    private void handlePlayerSocket() throws IOException {

        //Handle chat between players
        //Handle PLayer game board

        InputStream inputStream = playerSocket.getInputStream();
        this.printWriter = new PrintWriter(playerSocket.getOutputStream(), true);
        printWriter.println(greetingMessage);



        BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));


        String line;
        while ((line = bReader.readLine()) != null){

            System.out.println("now we have players no: " + server.getPlayerList().size());
            handleMessageToPlayers(line);
        }

        playerSocket.close();
    }


    private void handleNumberOfPlayers() throws IOException{
        while(server.getPlayerList().size() != 2){
            printWriter.println("Waiting for Another Player connection\n");
        }
    }



    private void handleMessageToPlayers(String line) throws IOException{
        List<ServerWorker> playerList = server.getPlayerList();

        String messageToSend = "Opponent:  " + line + "\n";


        for (ServerWorker player : playerList) {
            if(currentThread().getId() != player.getId()) {
                player.send(messageToSend);
                System.out.println("sending to players");
            }
        }

    }



    private void send(String message) throws IOException{
        printWriter.println(message);
    }


}
