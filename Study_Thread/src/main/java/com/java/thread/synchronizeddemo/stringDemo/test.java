package com.java.thread.synchronizeddemo.stringDemo;

/**
 * @author ldt merry
 * @date 2020/4/19
 */
public class test {
    public static void main(String[] args) {
        StringTest stringTest = new StringTest();
        Thread a = new ThreadA(stringTest);
        a.setName("A");
        a.start();
        ThreadB b = new ThreadB(stringTest);
        b.setName("B");
        b.start();
    }
}
