package com.gdw.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class NioTest11 {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress socketAddress = new InetSocketAddress(8899);
        serverSocketChannel.bind(socketAddress);

        int messageLength = 2 + 3 + 4;
        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();
        while(true){
            int byteRead = 0;
            while (byteRead < messageLength){
                long r = socketChannel.read(buffers);
                byteRead += r;

                System.out.println("byteRead:" + byteRead);
                Arrays.stream(buffers)
                        .map(buffer -> "position:" + buffer.position() + ",limit:" + buffer.limit())
                        .forEach(System.out::println);
            }

            Arrays.stream(buffers).forEach(ByteBuffer::flip);
            long bytesWritten = 0;
            while (bytesWritten < messageLength){
                long r = socketChannel.write(buffers);
                bytesWritten += r;
            }

            Arrays.stream(buffers).forEach(ByteBuffer::clear);
            System.out.println("byteRead:" + byteRead + ",bytesWritten:" + bytesWritten + ",messageLength:" + messageLength);
        }

    }
}