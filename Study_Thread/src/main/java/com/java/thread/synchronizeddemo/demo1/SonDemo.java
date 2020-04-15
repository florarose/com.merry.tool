package com.java.thread.synchronizeddemo.demo1;

/**
 * @author ldt merry
 * @date 2020/4/15
 */
public class SonDemo extends ObjectDemo {

    synchronized  public void  methodB(){
        try {
            while (i >0){
                i--;
                System.out.println("son pring i=:"+i);
                Thread.sleep(100);
                this.methodA();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
