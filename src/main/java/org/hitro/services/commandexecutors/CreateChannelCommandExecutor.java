package org.hitro.services.commandexecutors;
import org.hitro.constants.Constants;
import org.hitro.publicinterfaces.ChannelType;
import org.hitro.publicinterfaces.HymQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateChannelCommandExecutor<V> implements CommandExecutor<V>{
    @Override
    public List<String> execute(List<V> command, HymQueue hymQueue) {
        try{
            System.out.println("<><><><><>< create channel");
            System.out.println((String) command.get(2));
            System.out.println((String) command.get(3));
            hymQueue.createChannel((String)command.get(2),((String) command.get(3)).equalsIgnoreCase(Constants.getPollChannelType()) ? ChannelType.POLL: ChannelType.PUBSUB);
            return new ArrayList<>(Arrays.asList(getCommandId(command),Constants.getSuccess()));
        }
        catch (Exception e){
            throw e;
        }


    }
}
