package org.hitro.services;

import org.hitro.binaryprotocol.publicinterfaces.IBinaryProtocol;
import org.hitro.exceptions.HymQueueException;
import org.hitro.publicinterfaces.HymQueue;
import org.hitro.services.commandexecutors.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandOrchestrator {
    private Map<String, CommandExecutor> commandExecutorMap;

    private final HymQueue hymQueue;
    public CommandOrchestrator(HymQueue hymQueue){
        commandExecutorMap = new HashMap<>();
        this. hymQueue = hymQueue;
        commandExecutorMap.put("create_channel",new CreateChannelCommandExecutor());
        commandExecutorMap.put("add_data",new AddDataCommandExecutor());
        commandExecutorMap.put("add_subscriber", new AddSubscriberCommandExecutor());
        commandExecutorMap.put("get_data", new GetDataCommandExecutor());
    }
    private <T,V> List<T> decode(byte[] command){
        V commandList = (V) IBinaryProtocol.getInstance().decode(command);
        if(!(commandList instanceof List)){
            throw new HymQueueException("Input data is not a list");
        }
        return (List<T>) commandList;
    }

    private byte[] encode(List<String> result){
        byte[] resByte = IBinaryProtocol.getInstance().encode(result);
        return  resByte;
    }
    public <T> byte[] orchestrate(byte[] command){

        try{
            List<T> commandList = this.decode(command);
            String commandName = (String) commandList.get(1);
            List<String> res = commandExecutorMap.get(commandName).execute(commandList,this.hymQueue);
            return encode(res);
        }
        catch (Exception e){
            throw new HymQueueException("Command could not be decoded.",e);
        }
    }

}
