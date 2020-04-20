package com.java.thread.synchronizeddemo.stringDemo;

/**
 * @author ldt merry
 * @date 2020/4/19
 */
public class StringTest {
    public static void main(String[] args) {
        String a = "a";
        String b = "a";
        System.out.println(a== b);
    }

    public void print(String stringParam){
        int i= 8;
        try {
            synchronized (stringParam){
                while (i>1){
                    System.out.println(Thread.currentThread().getName());
                    Thread.sleep(1000);
                    i--;
                }
            }
        }catch (InterruptedException e){
           e.printStackTrace();
        }
    }
}
