package org.hitro.services.commandexecutors;

import org.hitro.constants.Constants;
import org.hitro.publicinterfaces.HymQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddDataCommandExecutor<V> implements CommandExecutor<List<String>,V>{
    @Override
    public List<String> execute(List<V> command, HymQueue hymQueue) {
        try{
            hymQueue.add(command.get(2),(String) command.get(3),(String)command.get(4));
            return new ArrayList<>(Arrays.asList(getCommandId(command), Constants.getSuccess()));
        }
        catch (Exception e){
            throw e;
        }
    }
}
