package com.gdw;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputStreamTest {
    public static void main(String[] args) throws IOException {
        try (
                // 创建字节输入流
                FileInputStream fis = new FileInputStream("FileOutputStreamTest.java");
                // 创建字节输出流
                FileOutputStream fos = new FileOutputStream("newFile.txt")){
            byte[] bbuf = new byte[32];
            int hasRead = 0;
            // 循环从输入流中取出数据
            while((hasRead = fis.read(bbuf)) > 0){
                // 每读取一次,即写入文件输出流,读了多少,就写多少
                fos.write(bbuf, 0, hasRead);
            }
        }
    }
}
