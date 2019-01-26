package com.gdw.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest4 {
    public static void main(String[] args) throws IOException {
        FileInputStream fin = new FileInputStream("NioTest4In.txt");
        FileOutputStream fout = new FileOutputStream("NioTest4Out.txt");

        FileChannel inChannel = fin.getChannel();
        FileChannel outChannel = fout.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);

        while (true){
            buffer.clear(); // 若注释掉改行代码会发生什么情况

            int read = inChannel.read(buffer);

            System.out.println("read:" + read);

            if(-1 == read){
                break;
            }

            buffer.flip();
            outChannel.write(buffer);
        }

        fin.close();
        fout.close();
    }
}
