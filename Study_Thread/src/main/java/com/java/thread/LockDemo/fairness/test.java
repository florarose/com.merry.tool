package com.java.thread.LockDemo.fairness;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/5/12 11:53
 */
public class test {

    public static void main(String[] args) {
        final fairnessTest fairnessTest = new fairnessTest(true);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("线程 :" + Thread.currentThread().getName()+"开始执行");
                fairnessTest.fairMethod();
            }
        };
        Thread [] threadArray = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threadArray[i] = new Thread(runnable);
        }
        for (int i = 0; i < 10; i++) {
            threadArray[i].start();
        }
    }
}
