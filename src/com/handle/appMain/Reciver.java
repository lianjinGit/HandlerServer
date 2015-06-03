package com.handle.appMain;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.handle.resoureManager.RescourceManager;

public class Reciver {
    /**
    * Logger for this class
    */
    private static final Logger logger = Logger.getLogger(Reciver.class);

    private int port;

    private Selector selector;

    public Reciver() {
        init();
    }

    private void init() {
        port = Integer.parseInt(RescourceManager.getInstance().getProperty("serverSocket.port"));
        try {
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            selector = Selector.open();
            serverChannel.socket().bind(new InetSocketAddress(port));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        }
        catch (IOException e) {
            e.printStackTrace();
            logger.error("", e);
        }

    }

    public void start() {
        while (true) {
            try {
                selector.select();
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey selectKey = it.next();
                    it.remove();
                    processKey(selectKey);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void processKey(SelectionKey selectKey) {

        ServerSocketChannel server = (ServerSocketChannel) selectKey.channel();
        try {
            SocketChannel channel = server.accept();
            new HandleThread(channel).start();;
        }
        catch (IOException e) {
            e.printStackTrace();

        }

    }

}
