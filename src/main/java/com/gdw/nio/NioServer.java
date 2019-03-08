package com.gdw.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class NioServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8899));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        Map<String, SocketChannel> socketChannelMap = new HashMap<>();

        while(true){
            int number = selector.select();
            if(number <= 0){
                continue;
            }

            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            for(SelectionKey selectedKey : selectedKeys){
                if(selectedKey.isAcceptable()){
                    ServerSocketChannel selectedServerSocketChannel = (ServerSocketChannel) selectedKey.channel();
                    SocketChannel socketChannel = selectedServerSocketChannel.accept();
                    socketChannel.configureBlocking(false);

                    System.out.println("收到连接:" + socketChannel);
                    socketChannelMap.put(UUID.randomUUID().toString(), socketChannel);

                    socketChannel.register(selector, SelectionKey.OP_READ);

                    continue;
                }

                if(selectedKey.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) selectedKey.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int readNum = socketChannel.read(readBuffer);
                    if(readNum <= 0){
                        continue;
                    }

                    String uuid = socketChannelMap.entrySet()
                            .stream()
                            .filter(entry -> entry.getValue() == socketChannel)
                            .map(Map.Entry::getKey)
                            .findFirst()
                            .get();

                    String msg = "[" + uuid + "]:" + new String(readBuffer.array());
                    ByteBuffer writeBuffer = ByteBuffer.wrap(msg.getBytes());

                    for(Map.Entry<String, SocketChannel> entry : socketChannelMap.entrySet()){
                        writeBuffer.rewind();
                        entry.getValue().write(writeBuffer);
                    }
                }
            }

            selectedKeys.clear();
        }
    }
}
