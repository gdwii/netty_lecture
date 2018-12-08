package com.gdw.netty.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class ProtoBufTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        StudentOne.Student student = StudentOne.Student.newBuilder()
                .setName("gdw")
                .setAge(28)
                .setAddress("beijin")
                .build();

        byte[] dataInfo = student.toByteArray();

        StudentOne.Student student2 = StudentOne.Student.parseFrom(dataInfo);
        System.out.println(student2.getName());
        System.out.println(student2.getAge());
        System.out.println(student2.getAddress());
    }
}