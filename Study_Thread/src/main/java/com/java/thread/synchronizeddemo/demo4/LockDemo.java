package com.java.thread.synchronizeddemo.demo4;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁解决两个线程交替执行
 * 使用多线程实现输出的效果为: 1 -1 2 -2 3 -3 4 -4 …
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/26 18:39
 */
public class LockDemo implements Runnable {

    // 定义一个数用来交替更改数字的正负号
    private int j = 1;
    // 定义公平锁,用来是线程1和线程2交替执行
    ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        for(int i = 1; i < 100; i++){
            //获取锁
            lock.lock();
            try{
                System.out.println(Thread.currentThread().getName()+" "+i*j);
                j=-j;
            }finally{
                //释放锁
                lock.unlock();
            }
        }
    }

}
