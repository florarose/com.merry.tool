package com.java.thread.synchronizeddemo.stringDemo;


/**
 * @author ldt merry
 * @date 2020/4/15
 */
public class ThreadB extends Thread {

    private StringTest service;

    public ThreadB(StringTest service){
        super();
        this.service = service;
    }

    @Override
    public void run() {
        System.out.println("B:---------");
       service.print("AA");
    }
}
