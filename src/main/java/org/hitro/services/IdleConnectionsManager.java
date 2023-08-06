package org.hitro.services;

import org.hitro.conn.ConnectionHandler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class IdleConnectionsManager implements Runnable{
    private Map<ConnectionHandler, Thread> chSet;
    public IdleConnectionsManager(){
        chSet = new ConcurrentHashMap<>();
    }

    public synchronized void addCh(ConnectionHandler ch, Thread t){
        this.chSet.put(ch, t);
    }

    public synchronized void removeCh(ConnectionHandler ch){
        this.chSet.remove(ch);
    }

    public synchronized int getChSize(){
        return this.chSet.size();
    }
    private void checkAndRemoveIdleConnections(){
        Set<ConnectionHandler> removedSet = new HashSet<>();
        for(ConnectionHandler c: this.chSet.keySet()){
            if(c.getIdleTime() >= 900000){
                chSet.get(c).interrupt();
                removedSet.add(c);
            }
        }

        chSet.keySet().removeAll(removedSet);
    }
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(2000);
                checkAndRemoveIdleConnections();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
