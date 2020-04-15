package com.java.thread.synchronizeddemo;

/**
 * @author ldt merry
 * @date 2020/4/14
 */
public class Run {

    public static void main(String[] args) {
        MyObject myObject = new MyObject();
//        MyObject.ThreadA a = new MyObject.ThreadA();
        MyObject.ThreadA a = myObject.new ThreadA(myObject);
        a.setName("A");
        MyObject.ThreadB b = myObject.new ThreadB(myObject);
        b.setName("B");
        a.start();
        b.start();

    }
}
