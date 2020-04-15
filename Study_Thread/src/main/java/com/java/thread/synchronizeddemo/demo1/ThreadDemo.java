package com.java.thread.synchronizeddemo.demo1;

/**
 * @author ldt merry
 * @date 2020/4/15
 */
public class ThreadDemo extends Thread {

    @Override
    public void run() {
        SonDemo sonDemo = new SonDemo();
        sonDemo.methodB();
    }
}
