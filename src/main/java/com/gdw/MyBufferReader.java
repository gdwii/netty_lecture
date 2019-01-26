package com.gdw;

import java.io.Reader;

public class MyBufferReader {
    private Reader reader;

    public MyBufferReader(Reader reader) {
        this.reader = reader;
    }

    // 定义一个缓冲区
    char[] buf = new char[1024];
    // 定义两个变量用于记录角标
    int index = 0, count = 0;

}
