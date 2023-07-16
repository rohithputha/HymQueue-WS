package org.hitro.server;

import org.hitro.conn.ConnectionHandler;
import org.hitro.exceptions.HymQueueException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    private ServerSocket serverSocket;
    private TcpServer(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }

    public void listen(){
        while(true){
            try{
                Socket socket =  this.serverSocket.accept();
                Thread newConnectionHandlerThread = new Thread(new ConnectionHandler(socket));
                newConnectionHandlerThread.start();
            }
            catch (IOException ioe){
                throw new HymQueueException("Failed to accept socket connection",ioe);
            }
            catch (Exception e){
                throw new HymQueueException(e);
            }
        }
    }

    private static TcpServer tcpServer;
    public static TcpServer getInstance(){
        if(tcpServer == null){
            synchronized (tcpServer){
                if(tcpServer== null){
                    try {
                        tcpServer = new TcpServer(new ServerSocket(3456));
                    } catch (IOException e) {
                        throw new HymQueueException("Failed to acquire port for the HymQueue-WS",e);
                    }
                }
            }
        }
        return tcpServer;
    }

}
