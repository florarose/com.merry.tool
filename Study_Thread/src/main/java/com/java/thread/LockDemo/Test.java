package com.java.thread.LockDemo;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/5/11 14:22
 */
public class Test {
    public static void main(String[] args) throws InterruptedException{
        ReentankLock reentankLock = new ReentankLock();
        testReentrantLock testReentrantLock1=  new testReentrantLock(reentankLock);
        testReentrantLock1.start();
        Thread.sleep(5000);
        reentankLock.signal();
    }
}
