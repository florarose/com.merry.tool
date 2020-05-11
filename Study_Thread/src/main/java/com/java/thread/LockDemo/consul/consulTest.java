package com.java.thread.LockDemo.consul;

import com.java.thread.LockDemo.ReentankLock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/5/11 18:52
 */
public class consulTest {

    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private boolean hashValue = false;

    public void set() {
        try {
            lock.lock();
            while (hashValue == true){
                condition.await();
            }
            System.out.println("@@@@@@@@");
            hashValue = true;
            condition.signal();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void get() {
        try {
            lock.lock();
            while (hashValue == false){
                condition.await();
            }
            System.out.println("##########");
            hashValue = false;
            condition.signal();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
