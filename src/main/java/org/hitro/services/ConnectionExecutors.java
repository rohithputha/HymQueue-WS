package org.hitro.services;

import org.hitro.conn.ConnectionHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionExecutors {
    private ExecutorService executors;

    private ConnectionExecutors(){
        executors = Executors.newFixedThreadPool(20);
    }

    public void addConnection(ConnectionHandler connectionHandler){
        executors.submit(connectionHandler);
    }

    public static ConnectionExecutors connectionExecutors;
    public static ConnectionExecutors getInstance(){
        if(connectionExecutors==null){
            synchronized (ConnectionExecutors.class){
                if(connectionExecutors==null) {
                    connectionExecutors = new ConnectionExecutors();
                }
            }
        }
        return connectionExecutors;
    }
}
