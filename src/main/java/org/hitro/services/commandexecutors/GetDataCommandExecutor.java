package org.hitro.services.commandexecutors;

import org.hitro.publicinterfaces.HymOutput;
import org.hitro.publicinterfaces.HymQueue;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetDataCommandExecutor<V> implements CommandExecutor<V>{
    @Override
    public List<String> execute(List<V> command, HymQueue hymQueue, Socket socket) {
        try{
            HymOutput data = hymQueue.get((String)command.get(2));
            return new ArrayList<>(Arrays.asList(getCommandId(command),data.toString()));
        }
        catch (Exception e){
            throw e;
        }
    }
}
