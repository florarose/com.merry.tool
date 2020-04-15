package com.java.thread.synchronizeddemo.demo2;

import com.java.thread.synchronizeddemo.demo1.ThreadDemo;

/**
 * @author ldt merry
 * @date 2020/4/15
 */
public class pub {
    public static void main(String[] args) {
       ObjectDemo2 objectDemo2 = new ObjectDemo2();
       ThreadDemo2 threadDemo2 = new ThreadDemo2(objectDemo2);
       threadDemo2.start();
       ThreadBDemo2 threadBDemo2 = new ThreadBDemo2(objectDemo2);
       threadBDemo2.start();
    }
}
