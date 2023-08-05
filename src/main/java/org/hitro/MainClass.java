package org.hitro;

import org.hitro.server.TcpServer;

import java.io.IOException;

public class MainClass {
    public static void main(String[] args) throws IOException, InterruptedException {
        TcpServer tcpServer  = TcpServer.getInstance();
        Thread thread = new Thread(()->tcpServer.listen());
        thread.start();
    }
}