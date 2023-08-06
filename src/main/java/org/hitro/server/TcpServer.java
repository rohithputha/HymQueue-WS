package org.hitro.server;

import org.hitro.conn.ConnectionHandler;
import org.hitro.publicinterfaces.HymQueue;
import org.hitro.exceptions.HymQueueException;
import org.hitro.services.ConnectionExecutors;
import org.hitro.services.IdleConnectionsManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {
    private final ServerSocket serverSocket;
    private final HymQueue hymQueue;

    private final IdleConnectionsManager idleConnectionsManager;
    private TcpServer(ServerSocket serverSocket, HymQueue hymQueue, IdleConnectionsManager idleConnectionsManager)  {
        this.serverSocket = serverSocket;
        this.hymQueue = hymQueue;
        this.idleConnectionsManager = idleConnectionsManager;
    }

    public void listen(){
        System.out.println("server listening");

        while(true){
            try{
                Socket socket =  this.serverSocket.accept();
                System.out.println("connect request received");
                ConnectionHandler connectionHandler = new ConnectionHandler(socket,this.hymQueue);
                Thread thread = new Thread(connectionHandler);
                idleConnectionsManager.addCh(connectionHandler,thread);
                System.out.println("Active Connection Number = "+idleConnectionsManager.getChSize());
                thread.start();
//                ConnectionExecutors.getInstance().addConnection(new ConnectionHandler(socket, this.hymQueue));
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
            synchronized (TcpServer.class){
                if(tcpServer == null){
                    try {
                        IdleConnectionsManager idleConnectionsManager1 = new IdleConnectionsManager();
                        Thread idleConnectionManagerThread = new Thread(idleConnectionsManager1);
                        idleConnectionManagerThread.start();
                        tcpServer = new TcpServer(new ServerSocket(3456), new HymQueue(), idleConnectionsManager1);
                    } catch (IOException e) {
                        throw new HymQueueException("Failed to acquire port for the HymQueue-WS",e);
                    }
                }
            }
        }
        return tcpServer;
    }

}
