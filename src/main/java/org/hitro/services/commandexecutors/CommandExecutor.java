package org.hitro.services.commandexecutors;

import org.hitro.publicinterfaces.HymQueue;

import java.net.Socket;
import java.util.List;

public interface CommandExecutor<V> {
    public List<String> execute(List<V> command, HymQueue hymQueue, Socket socket);
    default String getCommandId(List<V> command){
        return (String)command.get(1);
    }
}
