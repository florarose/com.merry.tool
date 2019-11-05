//package com.study.thread.SchedulingConfig;
//
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//
///**
// * @author 坎布里奇
// * @version 1.0
// * @date 2019/11/4 10:51
// */
//@Component
//@EnableScheduling   // 1.开启定时任务
//@EnableAsync        // 2.开启多线程
//public class ScheduleTasks {
//
//    @Async
//    @Scheduled(fixedDelay = 1000)  //间隔1秒
//    public void first() throws InterruptedException {
//        System.out.println("第一个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
//        System.out.println();
//        Thread.sleep(1000 * 10);
//    }
//
//    @Async
//    @Scheduled(fixedDelay = 2000)
//    public void second() {
//        System.out.println("第二个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
//        System.out.println();
//    }
//
//    public static void main(String[] args)  throws InterruptedException{
//        ScheduleTasks scheduleTask = new ScheduleTasks();
//        scheduleTask.first();
//        scheduleTask.second();
//    }
//}
