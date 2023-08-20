package org.hitro.services;

import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SocketManager {
    private Map<String, Socket> ipAddressToSocketMapping;

    private SocketManager(){
        ipAddressToSocketMapping = new ConcurrentHashMap<>();
    }

    public void add(String ip, Socket socket){
        ipAddressToSocketMapping.put(ip, socket);
    }
    public Socket get(String ip){
        return ipAddressToSocketMapping.get(ip);
    }

    private static SocketManager socketManager;
    public static SocketManager getInstance(){
        if(socketManager ==null){
            synchronized (SocketManager.class){
                if(socketManager ==null){
                    socketManager = new SocketManager();
                }
            }
        }
        return socketManager;
    }
}
