package com.gdw.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class NioTest3 {
    public static void main(String[] args) throws IOException {
        FileOutputStream fout = new FileOutputStream("NioTest3.txt");
        FileChannel foutChannel = fout.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);

        byte[] messages = "hello world welcome, nihao".getBytes();
        for(int i = 0; i < messages.length; i ++){
            buffer.put(messages[i]);
        }

        buffer.flip();
        foutChannel.write(buffer);
        fout.close();
    }
}
