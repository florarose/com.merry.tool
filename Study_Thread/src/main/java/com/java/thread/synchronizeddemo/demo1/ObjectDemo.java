package com.java.thread.synchronizeddemo.demo1;

/**
 * @author ldt merry
 * @date 2020/4/14
 */
public class ObjectDemo {

    public int i= 10;
  synchronized   public void  methodA(){
        try {
            i--;
            System.out.println("ObjectDemo i="+i);
            Thread.sleep(100);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public final class ThreadA extends Thread{
        public ObjectDemo myObject;
        public ThreadA(ObjectDemo myObject){
            super();
            this.myObject = myObject;
        }

        @Override
        public void run() {
            super.run();
            myObject.methodA();
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
