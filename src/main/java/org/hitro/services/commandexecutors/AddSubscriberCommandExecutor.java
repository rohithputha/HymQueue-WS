package org.hitro.services.commandexecutors;

import org.hitro.binaryprotocol.publicinterfaces.IBinaryProtocol;
import org.hitro.constants.Constants;
import org.hitro.publicinterfaces.HymQueue;
import org.hitro.services.SocketManager;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddSubscriberCommandExecutor<V> implements CommandExecutor<V> {
    @Override
    public List<String> execute(List<V> command, HymQueue hymQueue, Socket socket) {
        try{
            hymQueue.addSubscriber((String)command.get(2),(String)command.get(3),new SubscriberCallback((String)command.get(4),Integer.valueOf((String)command.get(5)), getCommandId(command), (String)command.get(3),IBinaryProtocol.getInstance()));
            return new ArrayList<>(Arrays.asList(getCommandId(command), Constants.getSuccess()));
        }
        catch (Exception e){
            throw e;
        }
    }
}
