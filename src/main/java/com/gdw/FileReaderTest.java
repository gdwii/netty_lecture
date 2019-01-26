package com.gdw;

import java.io.FileReader;
import java.io.IOException;

public class FileReaderTest {
    public static void main(String[] args) throws IOException {
        // 创建字符输入流
        try(FileReader fr = new FileReader("FileInputStreamTest.java")){
            // 创建一个长度为32的竹筒
            char[] cbuf = new char[32];
            // 用于保存实际读取的字节数
            int hasRead = 0;
            // 使用循环来重复取水过程
            while((hasRead = fr.read(cbuf)) > 0){
                // 取出竹筒中的水滴（字符），将字符数组转换成字符串输入
                System.out.println(new String(cbuf, 0, hasRead));
            }
        }
    }
}
