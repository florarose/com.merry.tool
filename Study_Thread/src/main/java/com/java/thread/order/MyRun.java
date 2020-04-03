package com.java.thread.order;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/2 18:34
 */
public class MyRun {
    public static void main(String[] args) {
        Object lock = new Object();
        ThreadOrderDemo a = new ThreadOrderDemo(lock,"A",1);
        ThreadOrderDemo b = new ThreadOrderDemo(lock,"B",2);
        ThreadOrderDemo c= new ThreadOrderDemo(lock,"C",0);
        a.start();
        b.start();
        c.start();
    }
}
