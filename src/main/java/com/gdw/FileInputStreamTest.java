package com.gdw;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileInputStreamTest {
    public static void main(String[] args) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter("employee.txt"), true); // autoflush
//        writer.write();
    }
}
