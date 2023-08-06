package org.hitro.conn;

import org.hitro.constants.Constants;
import org.hitro.exceptions.HymQueueException;
import org.hitro.publicinterfaces.HymQueue;
import org.hitro.services.CommandOrchestrator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnectionHandler implements Runnable{

    private final Socket socket;
    private final HymQueue hymQueue;
    private final CommandOrchestrator commandOrchestrator;

    private long lastActivityTime;
    public ConnectionHandler(Socket socket, HymQueue hymQueue){
        this.socket = socket;
        this.hymQueue = hymQueue;
        this.commandOrchestrator = new CommandOrchestrator(hymQueue);
        lastActivityTime =  System.currentTimeMillis();
    }

    public byte[] convertToByteArray(List<Byte> byteList){
        byte[] newByteArray = new byte[byteList.size()-2];
        for(int i=0;i< byteList.size()-2;i++){
            newByteArray[i]= byteList.get(i);
        }
        byteList.clear();
        return newByteArray;
    }


    private byte[] decodeAndExecuteCommand(byte[] commnand){
        return commandOrchestrator.orchestrate(commnand);
    }

    private synchronized void updateTime(){
       this.lastActivityTime = System.currentTimeMillis();
    }

    public synchronized long getIdleTime(){
        return System.currentTimeMillis() - this.lastActivityTime;
    }
    @Override
    public void run() {
        try {
            System.out.println("<><><><><><><> connection started");
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            List<Byte> inputCommandsByteList = new ArrayList<>();
            int byteRead;
            while((byteRead = inputStream.read())!=-1){
                inputCommandsByteList.add((byte)byteRead);
                if(inputCommandsByteList.size()>2 &&
                        inputCommandsByteList.get(inputCommandsByteList.size()-2)== Constants.getCommandSeperator()[0] &&
                        inputCommandsByteList.get(inputCommandsByteList.size()-1)==Constants.getCommandSeperator()[1]
                ){
                    updateTime();
                    byte[] newCommand = this.convertToByteArray(inputCommandsByteList);
                    inputCommandsByteList = new ArrayList<>();
                    byte[] res = decodeAndExecuteCommand(newCommand);
                    outputStream.write(res);
                }
            }
        } catch (IOException e) {
            throw new HymQueueException("Exception with TCP streams",e);
        }

    }
}
