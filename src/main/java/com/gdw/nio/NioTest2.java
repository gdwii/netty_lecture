package com.gdw.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest2 {
    public static void main(String[] args) throws IOException {
        FileInputStream fin = new FileInputStream("NioTest2.txt");
        FileChannel fChannel = fin.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);
        fChannel.read(buffer);

        buffer.flip();
        while (buffer.hasRemaining()){
            byte b = buffer.get();
            System.out.println("Character:" + (char)b);
        }

        fin.close();
    }
}
