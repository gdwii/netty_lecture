package com.gdw.nio;

import java.nio.ByteBuffer;
import java.util.stream.IntStream;

/**
 * slice buffer与原有Buffer共享相同的底层数组
 */
public class NioTest6 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        IntStream.range(0, buffer.capacity()).forEach(i -> buffer.put((byte) i));

        buffer.position(2).limit(6);

        ByteBuffer sliceBuffer = buffer.slice();

        for(int  i = 0; i < sliceBuffer.capacity(); i ++){
            byte b = sliceBuffer.get(i);
            b *= 2;
            sliceBuffer.put(i, b);
        }

        buffer.position(0).limit(buffer.capacity());

        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }
}