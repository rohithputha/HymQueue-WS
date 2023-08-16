package org.hitro.services.commandexecutors;

import org.hitro.binaryprotocol.publicinterfaces.BinaryProtocol;
import org.hitro.model.subscribers.SubscriberFunction;
import org.hitro.publicinterfaces.HymOutput;

import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class SubscriberCallback implements SubscriberFunction {
    private final String ip;
    private final int port;
    private final String commandId;

    private final String channelName;
    private BinaryProtocol binaryProtocol;
    public SubscriberCallback(String ip, int port, String commandId,String channelname,  BinaryProtocol binaryProtocol){
        this.ip = ip;
        this.port = port;
        this.commandId = commandId;
        this.channelName = channelname;
        this.binaryProtocol = binaryProtocol;
    }

    @Override
    public void consume(HymOutput hymOutput) {
        try {
            Socket socket = new Socket(ip, port);
            OutputStream outputStream = socket.getOutputStream();
            ArrayList hOutputList = new ArrayList<>(Arrays.asList(this.commandId,"subscriberCallback",channelName,hymOutput.toString()));
            outputStream.write(binaryProtocol.encode(hOutputList));
        }
        catch (Exception e){

        }
    }
}
