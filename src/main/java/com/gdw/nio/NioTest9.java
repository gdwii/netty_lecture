package com.gdw.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest9 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile accessFile = new RandomAccessFile("D:\\GeneratorTool.java", "rw");
        FileChannel fileChannel = accessFile.getChannel();

        MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 100);
        fileChannel.close();

        byteBuffer.put(20, (byte)'g');
        System.out.println("sds");
    }
}