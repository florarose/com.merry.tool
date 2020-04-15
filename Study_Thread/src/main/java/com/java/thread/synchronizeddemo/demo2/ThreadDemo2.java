package com.java.thread.synchronizeddemo.demo2;

import com.java.thread.synchronizeddemo.demo1.SonDemo;

/**
 * @author ldt merry
 * @date 2020/4/15
 */
public class ThreadDemo2 extends Thread {

    ObjectDemo2 objectDemo2;

    public ThreadDemo2(ObjectDemo2 objectDemo2) {
        super();
        this.objectDemo2 = objectDemo2;
    }

    @Override
    public void run() {
        super.run();
        objectDemo2.methodA();
    }
}
