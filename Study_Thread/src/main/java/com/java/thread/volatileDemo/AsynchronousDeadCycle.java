package com.java.thread.volatileDemo;

/**
 * 异步死循环
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/5/9 15:46
 */
public class AsynchronousDeadCycle extends Thread{

   volatile private boolean isContinuePrint = true;
    public boolean isContinuePrint(){
        return isContinuePrint;
    }
    public void setContinuePrint(boolean isContinuePrint){
        this.isContinuePrint = isContinuePrint;
    }

    @Override
    public void run() {
        System.out.println("开始执行");
        while (isContinuePrint == true){

        }
        System.out.println("线程停止了");
    }
}
