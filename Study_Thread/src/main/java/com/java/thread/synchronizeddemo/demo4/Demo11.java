package com.java.thread.synchronizeddemo.demo4;

/**
 * 采用wait()和notify()方法解决两个线程交替执行
 * 使用多线程实现输出的效果为: 1 -1 2 -2 3 -3 4 -4 …
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/26 18:40
 */
public class Demo11 {

    public static void main(String[] args) {
        // 创建锁对象
        final Object object = new Object();
        // 创建线程A,开启线程
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 1; i < 100; i++) {
                    synchronized (object) {
                        try {
                            System.out.println(Thread.currentThread().getName() + " " + i);
                            // 进入等待状态,并释放锁
                            object.wait();// 无线等待状态
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }
            }
        }, "A").start();

        // 创建线程B
        new Thread(new Runnable() {

            @Override
            public void run() {
                for (int i = 1; i < 100; i++) {
                    synchronized (object) {
                        System.out.println(Thread.currentThread().getName() + " " + (-1) * i);
                        // 唤醒线程A
                        object.notify();
                        /*
                         * try { Thread.sleep(1); } catch (InterruptedException
                         * e) { // TODO Auto-generated catch block
                         * e.printStackTrace(); }
                         */

                        /**
                         * 为什么不能放在这个位置:
                         * 因为静态代码块执行完毕,虽然等待了1毫秒,但是还不会释放锁(sleep()方法不会释放锁的),
                         * 只有等静态代码块执行完毕的时候才开始释放锁,释放完锁,线程A和线程B会去争夺锁不一定
                         * 是A获得到锁,所以不会出现 1 -1 2 -2 3 -3 ....的效果,所以将这段代码写在锁释放完后
                         * 面,让其失去机会获取锁,然A获取到锁,执行,然后等待,释放锁,等待B线程唤醒,B抢到锁执行
                         * 代码,唤醒A,这样交替执行
                         */
                    }

                    //线程A和线程B交换执行关键所在
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        }, "B").start();
    }
}
