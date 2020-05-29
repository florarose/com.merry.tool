package com.java.thread.LockDemo.fairness;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/5/12 11:51
 */
public class fairnessTest {


    private ReentrantLock lock;
    public fairnessTest(boolean isFair){
        super();
        lock =  new ReentrantLock(isFair);
    }

    public void fairMethod(){
        try {
            lock.lock();
            System.out.println("Threadname= " + Thread.currentThread().getName());
        }finally {
            lock.unlock();
        }
    }
}
