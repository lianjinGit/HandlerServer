
package com.handle;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Test {

    public static void main(String[] args) throws Exception, IOException {

        Socket socket = new Socket("127.0.0.1", 7777);
        PrintWriter pw = new PrintWriter(socket.getOutputStream());;

        pw.println("1111");
        pw.flush();



        Thread.sleep(10000000);
    }

}
