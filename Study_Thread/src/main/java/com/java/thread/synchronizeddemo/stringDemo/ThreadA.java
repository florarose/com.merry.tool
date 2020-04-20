package com.java.thread.synchronizeddemo.stringDemo;

import com.java.thread.synchronizeddemo.demo1.SonDemo;
import com.java.thread.synchronizeddemo.demo3.Service;

/**
 * @author ldt merry
 * @date 2020/4/15
 */
public class ThreadA extends Thread {

    private StringTest stringTest;

    public ThreadA(StringTest stringTest){
        super();
        this.stringTest = stringTest;
    }

    @Override
    public void run() {
        System.out.println("A:---------");
        stringTest.print("AA");
    }
}
