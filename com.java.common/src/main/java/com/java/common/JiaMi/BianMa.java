package com.java.common.JiaMi;

import java.io.IOException;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/3 18:26
 */
public class BianMa {


    public void StringToByte(String str) throws IOException {
        byte[] bytes=str.getBytes("UTF8");
        System.out.println(bytes);
    }

    public void ByteToString(byte[] bytes) throws IOException {
        String srt2=new String(bytes,"UTF-8");
        System.out.println(bytes);
    }
}
