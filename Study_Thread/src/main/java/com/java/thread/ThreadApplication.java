package com.java.thread;

import com.java.thread.extendDemos.A;
import com.java.thread.extendDemos.B;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/11 11:05
 */
public class ThreadApplication {

    public static void main(String[] args) {
        A ab = new B();
        ab = new B();
    }
}

