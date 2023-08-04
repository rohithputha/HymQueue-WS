package org.hitro;

import org.hitro.binaryprotocol.publicinterfaces.BinaryProtocol;
import org.hitro.binaryprotocol.publicinterfaces.IBinaryProtocol;
import org.hitro.server.TcpServer;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
//        System.out.println("Hello world!");
        TcpServer tcpServer  = TcpServer.getInstance();
        Thread thread = new Thread(()->tcpServer.listen());
        thread.start();
//        Thread.sleep(5000);

//        BinaryProtocol binaryProtocol = IBinaryProtocol.getInstance();
//        byte[] command = binaryProtocol.encode(new ArrayList<>(Arrays.asList("create_channel","232423","testchannel","poll")));
//        byte[] changedCommand = new byte[command.length+2];
//        for (int i =0;i<command.length;i++){
//            changedCommand[i]= command[i];
//        }
//        changedCommand[changedCommand.length-2]= 92;
//        changedCommand[changedCommand.length-1]= 119;
//        Socket socket = new Socket("localhost",3456);
//        socket.getOutputStream().write(changedCommand);
//
//        Thread.sleep(3000);
//        byte[] out = new byte[1024];
//        System.out.println(socket.getInputStream().available());
//        socket.getInputStream().read(out);
//        System.out.println("<><>");
//        for(int i=0;i<out.length;i++){
//            System.out.print(out[i]+",");
//        }
    }
}