package org.hitro.conn;

import org.hitro.constants.Constants;
import org.hitro.exceptions.HymQueueException;

import javax.sound.sampled.Line;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ConnectionHandler implements Runnable{

    private Socket socket;
    public ConnectionHandler(Socket socket){
        this.socket = socket;
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

    }
    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            List<Byte> inputCommandsByteList = new ArrayList<>();
            int byteRead;
            while((byteRead = inputStream.read())!=-1){
                inputCommandsByteList.add((byte)byteRead);
                if(inputCommandsByteList.size()>2 &&
                        inputCommandsByteList.get(inputCommandsByteList.size()-2)== Constants.getCommandSeperator()[0] &&
                        inputCommandsByteList.get(inputCommandsByteList.size()-1)==Constants.getCommandSeperator()[1]
                ){
                    byte[] newCommand = this.convertToByteArray(inputCommandsByteList);
                    inputCommandsByteList = new ArrayList<>();
                }
            }
        } catch (IOException e) {
            throw new HymQueueException("Exception with TCP streams",e);
        }

    }
}
