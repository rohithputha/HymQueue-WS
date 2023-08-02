package org.hitro.server;

import org.hitro.conn.ConnectionHandler;
import org.hitro.exceptions.HymQueueException;
import org.hitro.publicinterfaces.HymQueue;
import org.hitro.services.ConnectionExecutors;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    private final ServerSocket serverSocket;
    private final HymQueue hymQueue;
    private TcpServer(ServerSocket serverSocket, HymQueue hymQueue)  {
        this.serverSocket = serverSocket;
        this.hymQueue = hymQueue;
    }

    public void listen(){
        while(true){
            try{
                Socket socket =  this.serverSocket.accept();
                ConnectionExecutors.getInstance().addConnection(new ConnectionHandler(socket, this.hymQueue));
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
                        tcpServer = new TcpServer(new ServerSocket(3456), new HymQueue());
                    } catch (IOException e) {
                        throw new HymQueueException("Failed to acquire port for the HymQueue-WS",e);
                    }
                }
            }
        }
        return tcpServer;
    }

}
