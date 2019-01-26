package com.gdw.nio;

import java.nio.ByteBuffer;

/**
 * ByteBuffer 类型化put与get方法
 */
public class NioTest5 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(64);

        buffer.putInt(15);
        buffer.putLong(1500000000000L);
        buffer.putDouble(15.23);
        buffer.putChar('年');
        buffer.putShort((short) 2);
        buffer.putChar('我');

        buffer.flip();


        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
        System.out.println(buffer.getChar());
    }
}