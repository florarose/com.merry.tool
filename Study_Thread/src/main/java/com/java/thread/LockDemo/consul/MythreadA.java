package com.java.thread.LockDemo.consul;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/5/11 18:55
 */
public class MythreadA extends Thread {

    private consulTest consulTest;

    public MythreadA(consulTest consulTest){
        super();
        this.consulTest = consulTest;
    }

    @Override
    public void run() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            consulTest.set();
        }
    }
}
