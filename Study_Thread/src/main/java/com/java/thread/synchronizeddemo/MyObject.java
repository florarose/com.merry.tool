package com.java.thread.synchronizeddemo;

/**
 * @author ldt merry
 * @date 2020/4/14
 */
public class MyObject {

  synchronized   public void  methodA(){
        try {
            System.out.println("begin methodA threadName:"+Thread.currentThread().getName());
            Thread.sleep(5000);
            System.out.println("end endTime:"+System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    synchronized  public void  methodB(){
        try {
            System.out.println("begin methodB threadName:"+Thread.currentThread().getName()+"begin time:"+System.currentTimeMillis());
            Thread.sleep(5000);
            System.out.println("end");
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public final class ThreadA extends Thread{
        public MyObject myObject;
        public ThreadA(MyObject myObject){
            super();
            this.myObject = myObject;
        }

        @Override
        public void run() {
            super.run();
            myObject.methodA();
        }
    }

    public class ThreadB extends Thread{
        private MyObject myObject;
        public ThreadB(MyObject myObject){
            super();
            this.myObject = myObject;
        }

        @Override
        public void run() {
            super.run();
            myObject.methodB();
        }
    }

    /**
     *         MyObject myObject = new MyObject();
     *         MyObject.ThreadA a = myObject.new ThreadA(myObject);
     *         a.setName("A");
     *         MyObject.ThreadB b = myObject.new ThreadB(myObject);
     *         b.setName("B");
     *         a.start();
     *         b.start();
     *
     *         如果方法A和方法B同时添加synchronized关键字,谁先持有Myobject对象的Lock锁，先执行完另一个方法。因为synchronized关键字是对象锁，不是某个方法的锁。
     */

}
