package org.hitro.conn;

import lombok.Getter;
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

    @Getter
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
        return commandOrchestrator.orchestrate(commnand, this.socket);
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
            OutputStream outputStream = socket.getOutputStream();
            List<Byte> inputCommandsByteList = new ArrayList<>();
            int byteRead;
            while((byteRead = socket.getInputStream().read())!=-1){
                inputCommandsByteList.add((byte)byteRead);
                if(inputCommandsByteList.size()>2 &&
                        inputCommandsByteList.get(inputCommandsByteList.size()-2)== Constants.getCommandSeperator()[0] &&
                        inputCommandsByteList.get(inputCommandsByteList.size()-1)==Constants.getCommandSeperator()[1]
                ){
                    updateTime();
                    byte[] newCommand = this.convertToByteArray(inputCommandsByteList);
                    inputCommandsByteList = new ArrayList<>();
                    byte[] res = decodeAndExecuteCommand(newCommand);
                    int bytesToSend = res.length;
                    int offset=0;
                    while (bytesToSend > 0) {
                        int bytesToWrite = Math.min(21, bytesToSend);
                        synchronized (outputStream){
                            outputStream.write(res, offset, bytesToWrite);
                            outputStream.flush();
                        }
                        offset += bytesToWrite;
                        bytesToSend -= bytesToWrite;
                    }
                    //                    outputStream.write(res);
                }
            }
        } catch (IOException e) {
            System.out.println("lost TCP connection");
        }

    }
}
