package org.academiadecodigo.stringrays;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerWorker extends Thread{

    private Server server;
    private Socket playerSocket;
    private OutputStream outputStream;
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
        this.outputStream = playerSocket.getOutputStream();



        outputStream.write(greetingMessage.getBytes());

        BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));


        String line;
        while ((line = bReader.readLine()) != null){

            handleMessageToPlayers(line);

            }



        playerSocket.close();
    }


    private void handleNumberOfPlayers() throws IOException{
        while(server.getPlayerList().size() != 2){
            outputStream.write("Waiting for Another Player connection\n".getBytes());
        }
    }



    private void handleMultiPlayer() {

    }



    private void handleMessageToPlayers(String line) throws IOException{
        List<ServerWorker> playerList = server.getPlayerList();
        String text = "";

        String messageToSend = "[message] " + line + "\n";

        for (ServerWorker player : playerList) {
            player.send(messageToSend);
        }
    }



    private void send(String message) throws IOException{
        outputStream.write(message.getBytes());
    }


}
