package com.java.thread.LockDemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/5/11 14:17
 */
public class ReentankLock {
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void await(){
        try {
            lock.lock();
            System.out.println("获取同步监视器");
            condition.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
            System.out.println("释放同步监视器");
        }
    }

    public void signal(){
        try {
            lock.lock();
            System.out.println("signal 时间：" +System.currentTimeMillis());
            condition.signal();
        }finally {
            lock.unlock();
        }
    }

    /**
     * 1
     */
    public void testMethod(){
        lock.lock();
        for (int i = 0; i < 5; i++) {
            System.out.println("ThreadName :" + Thread.currentThread().getName()+"----"+(i+1));
        }
        lock.unlock();
    }

}
