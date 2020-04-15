package com.java.thread.synchronizeddemo.demo2;

/**
 * 同步代码块的使用
 * @author ldt merry
 * @date 2020/4/15
 */
public class ObjectDemo2 {


     public void  methodA(){
        try {
            synchronized(this){
                System.out.println("begin time:"+System.currentTimeMillis());
                Thread.sleep(2000);
                System.out.println("end endTime:"+System.currentTimeMillis());
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private String getData1;
     private String getData2;

    public void  methodB(){
        try {
            System.out.println("begin time:"+System.currentTimeMillis());
            Thread.sleep(3000);
            String a = ""+Thread.currentThread().getName();
            String b= ""+Thread.currentThread().getName();
            synchronized(this){
                 getData1 = a;
                 getData2 = b;
            }
            System.out.println(getData1);
            System.out.println(getData2);
            System.out.println("end endTime:"+System.currentTimeMillis());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


}
