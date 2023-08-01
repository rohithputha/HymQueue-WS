package org.hitro.services;

import org.hitro.binaryprotocol.publicinterfaces.IBinaryProtocol;
import org.hitro.exceptions.HymQueueException;

import java.util.List;

public class CommandOrchestrator {
    private
    private <T,V> List<T> decode(byte[] command){
        V commandList = (V) IBinaryProtocol.getInstance().decode(command);
        if(!(commandList instanceof List)){
            throw new HymQueueException("Input data is not a list");
        }
        return (List<T>) commandList;
    }

    public <T> byte[] orchestrate(byte[] command){
        List<T> commandList = this.decode(command);
        try{
            String commandId = (String) commandList.get(0);
            String commandName = (String) commandList.get(1);

        }
        catch (Exception e){
            throw new HymQueueException("Command could not be decoded.",e);
        }
    }

}
