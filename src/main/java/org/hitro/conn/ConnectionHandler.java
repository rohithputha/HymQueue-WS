package org.hitro.conn;

import java.net.Socket;

public class ConnectionHandler implements Runnable{

    private Socket socket;
    public ConnectionHandler(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run() {

    }
}
