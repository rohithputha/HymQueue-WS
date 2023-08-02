package org.hitro.services.commandexecutors;

import org.hitro.publicinterfaces.HymQueue;

import java.util.List;

public interface CommandExecutor<V> {
    public List<String> execute(List<V> command, HymQueue hymQueue);
    default String getCommandId(List<V> command){
        return (String)command.get(1);
    }
}
