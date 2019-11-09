package org.academiadecodigo.stringrays;

public class TestServer {

    public static void main(String[] args) {

        int portNumber = 11001;

        Server server = new Server(portNumber);

        server.start();
    }
}
