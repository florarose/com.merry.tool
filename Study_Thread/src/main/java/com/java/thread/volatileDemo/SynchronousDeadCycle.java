package com.java.thread.volatileDemo;

/**
 * 同步死循环
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/5/9 15:46
 */
public class SynchronousDeadCycle {

    private boolean isContinuePrint = true;
    public boolean isContinuePrint(){
        return isContinuePrint;
    }
    public void setContinuePrint(boolean isContinuePrint){
        this.isContinuePrint = isContinuePrint;
    }

    public void printStringMethod(){
        try {
            while (isContinuePrint == true){
                System.out.println("run="+Thread.currentThread().getName());
                Thread.sleep(1000);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


}
