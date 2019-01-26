package com.gdw.nio;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.util.stream.IntStream;

/**
 * 只读buffer我们可以随时将一个普通buffer调用asReadOnlyBuffer方法返回一个只读buffer
 * 但不能将一个只读buffer转为读写buffer
 */
public class NioTest7 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(buffer.getClass());

        IntStream.range(0, buffer.capacity()).forEach(i -> buffer.put((byte) i));

        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        readOnlyBuffer.position(0);

        readOnlyBuffer.put((byte)2);
    }
}
