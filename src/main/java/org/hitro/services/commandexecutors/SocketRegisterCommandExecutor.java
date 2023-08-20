package org.hitro.services.commandexecutors;

import org.hitro.constants.Constants;
import org.hitro.publicinterfaces.HymQueue;
import org.hitro.services.SocketManager;

import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class SocketRegisterCommandExecutor implements CommandExecutor{
    @Override
    public List<String> execute(List command, HymQueue hymQueue, Socket socket) {
        SocketManager.getInstance().add((String)command.get(2),socket);
        return Arrays.asList(getCommandId(command), Constants.getSuccess());
    }
}
