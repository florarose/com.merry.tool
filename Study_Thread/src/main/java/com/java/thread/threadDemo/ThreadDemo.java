package com.java.thread.threadDemo;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/11 11:08
 */
public class ThreadDemo extends Thread {
//    @Override
//    public void run() {
//        super.run();
//        System.out.println("Mythread");
//    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 10; i++) {
                int time = (int) Math.random()+1000;
                Thread.sleep(time);
                System.out.println("run:"+Thread.currentThread().getName());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
