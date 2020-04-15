package com.java.thread.synchronizeddemo.demo2;

/**
 * @author ldt merry
 * @date 2020/4/15
 */
public class ThreadBDemo2 extends Thread {

    ObjectDemo2 objectDemo2;

    public ThreadBDemo2(ObjectDemo2 objectDemo2) {
        super();
        this.objectDemo2 = objectDemo2;
    }

    @Override
    public void run() {
        super.run();
        objectDemo2.methodA();
    }
}
