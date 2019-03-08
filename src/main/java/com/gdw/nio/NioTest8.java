package com.gdw.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest8 {
    public static void main(String[] args) throws Exception {
        FileInputStream fin = new FileInputStream("input2.txt");
        FileOutputStream fout = new FileOutputStream("output2.txt");

        FileChannel finChannel = fin.getChannel();
        FileChannel foutChannel = fout.getChannel();

        ByteBuffer buffer = ByteBuffer.allocateDirect(512);


        while(true){
            buffer.clear();

            int read = finChannel.read(buffer);
            System.out.println("read:" + read);
            if(-1 == read){
                break;
            }

            buffer.flip();
            foutChannel.write(buffer);
        }

        fin.close();
        fout.close();

    }
}
