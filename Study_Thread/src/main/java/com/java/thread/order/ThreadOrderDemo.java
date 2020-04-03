package com.java.thread.order;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/2 18:28
 */
public class ThreadOrderDemo extends Thread {

    private Object lock;
    private String showChar;
    private int showNumPosition;
    private int printCount = 0;  //统计打印了几个字母
    volatile private static int addNumber = 1;

    public ThreadOrderDemo( Object lock, String showChar, int showNumPosition) {
        this.lock = lock;
        this.showChar = showChar;
        this.showNumPosition = showNumPosition;
    }

    @Override
    public void run() {
        try{
            synchronized (lock){
                while(true){
                    if(addNumber % 3 == showNumPosition){
                        System.out.println("ThreadName="+Thread.currentThread().getName()+"runCount="+addNumber+""+showChar);
                        lock.notifyAll();
                        addNumber++;
                        printCount++;
                        if(printCount == 3){
                            break;
                        }
                    }else lock.wait();
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


}
