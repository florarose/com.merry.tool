package com.java.thread.volatileDemo;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/5/9 15:56
 */
public class testDemo {


    public static void main(String [] args){
//        SynchronousDeadCycle synchronousDeadCycle = new SynchronousDeadCycle();
//        synchronousDeadCycle.printStringMethod();
//        System.out.println("我要停止它！"+Thread.currentThread().getName());
//        synchronousDeadCycle.setContinuePrint(false);
//        SynchronousDeadCycleDemo synchronousDeadCycleDemo = new SynchronousDeadCycleDemo();
//        new Thread(synchronousDeadCycleDemo).start();
//        System.out.println("我要停止它！"+Thread.currentThread().getName());
//        synchronousDeadCycleDemo.setContinuePrint(false);

        try {
            AsynchronousDeadCycle asynchronousDeadCycle = new AsynchronousDeadCycle();
            asynchronousDeadCycle.start();
            Thread.sleep(1000);
            asynchronousDeadCycle.setContinuePrint(false);
            System.out.println("赋值");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
