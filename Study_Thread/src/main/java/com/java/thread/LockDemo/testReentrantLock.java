package com.java.thread.LockDemo;



/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/5/11 14:20
 */
public class testReentrantLock  extends Thread {

    private ReentankLock reentankLock;
    public testReentrantLock(ReentankLock reentankLock){
        super();
        this.reentankLock = reentankLock;
    }

    @Override
    public void run() {
        reentankLock.await();
    }
}
