package com.java.thread.synchronizeddemo.demo3;

/**
 *
 * 全局锁
 * @author ldt merry
 * @date 2020/4/18
 */
public class Service {

    synchronized public static void test1(){
       try {
           System.out.println("来了啊1"+Thread.currentThread().getName());
           Thread.sleep(2000);
           System.out.println("慢走啊1"+Thread.currentThread().getName());
       }catch (InterruptedException e){
           e.printStackTrace();
       }
   }
    synchronized public static void test2(){
        try {
            System.out.println("来了啊2"+Thread.currentThread().getName());
            Thread.sleep(2000);
            System.out.println("慢走啊2"+Thread.currentThread().getName());
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
