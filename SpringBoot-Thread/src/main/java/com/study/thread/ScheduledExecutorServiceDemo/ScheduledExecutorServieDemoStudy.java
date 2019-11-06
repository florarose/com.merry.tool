package com.study.thread.ScheduledExecutorServiceDemo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/6 11:21
 */
public class ScheduledExecutorServieDemoStudy {

    public static void main(String[] args) {
        // 重点是，此处的线程出，默认只有1个线程，其余任务需要等待该线程完成，可以设置多线程。
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        long startTime = System.currentTimeMillis();
        System.out.println("第一次执行");
        executorService.schedule(()->{
            System.out.println(System.currentTimeMillis() - startTime);
            try {
                // 此处休眠4秒
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 2, TimeUnit.SECONDS);
        System.out.println("第二次执行");
        executorService.schedule(()->{
            System.out.println(System.currentTimeMillis() - startTime);
        }, 2, TimeUnit.SECONDS);
    }
}
