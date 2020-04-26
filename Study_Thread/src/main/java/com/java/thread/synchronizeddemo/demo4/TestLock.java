package com.java.thread.synchronizeddemo.demo4;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/26 18:40
 */
public class TestLock {

    public static void main(String[] args) {
        //创建对象
        LockDemo lockDemo = new LockDemo();
        //创建两个线程,启动两个线程
        new Thread(lockDemo,"线程1").start();
        new Thread(lockDemo,"线程2").start();
    }
}
