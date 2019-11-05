package com.study.thread.SchedulingConfig;

import com.study.thread.mapper.TScheduledTaskMapper;
import com.study.thread.model.DateDifferentExample;
import com.study.thread.model.TScheduledTask;
import com.study.thread.service.impl.TScheduledTaskServiceImpl;
import com.study.thread.yanshi.RingBufferWheel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

/**
 * 基于注解@Scheduled默认为单线程，开启多个任务时，任务的执行时机会受上一个任务执行时间的影响。
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/4 13:44
 */
@Configuration
@Slf4j
public class ScheduleTask_A {

    @Autowired
    private ScheduleTaskApi scheduleTaskApi;
    @Autowired      //注入mapper
    @SuppressWarnings("all")
    private TScheduledTaskMapper tScheduledTaskService;
    @Autowired      //注入mapper
    @SuppressWarnings("all")
    private TScheduledTaskServiceImpl tScheduledTaskServiceImpl;

        //接受任务的返回结果
    private ScheduledFuture<?> future;

    /**
     * 、静态：基于注解
     * 二、动态：基于接口
     * 三、多线程定时任务
     * Cron表达式参数分别表示：
     *
     * 秒（0~59） 例如0/5表示每5秒
     * 分（0~59）
     * 时（0~23）
     * 日（0~31）的某天，需计算
     * 月（0~11）
     * 周几（ 可填1-7 或 SUN/MON/TUE/WED/THU/FRI/SAT）
     * @Scheduled：除了支持灵活的参数表达式cron之外，还支持简单的延时操作，例如 fixedDelay ，fixedRate 填写相应的毫秒数即可。
     *
     *
     */
    //3.添加定时任务
    @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() throws Exception {
        TScheduledTask tScheduledTask = tScheduledTaskService.selectOneTask();
        if(null != tScheduledTask){
            future= scheduleTaskApi.configureTasks(tScheduledTask.getStatusCron(),tScheduledTask.getId());
            if(null == future){
                System.out.println("任务启动失败");
            }else {
                boolean cancle = future.cancel(true);
                if(cancle){
                    System.out.println("停止该定时任务");
                }
                System.out.println(future);
            }
        }
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }
    //3.添加定时任务
//    @Scheduled(cron = "0/5 * * * * ?")
//    //或直接指定时间间隔，例如：5秒
//    //@Scheduled(fixedRate=5000)
//    private void configureTaskss() throws Exception {
//        ExecutorService executorService = Executors.newFixedThreadPool(2);
//        RingBufferWheel wheel = new RingBufferWheel(executorService,512);
//        List<TScheduledTask> tScheduledTask = tScheduledTaskService.selectManyTask();
////        for(TScheduledTask tScheduledTask1 : tScheduledTask){
////            int id= DateDifferentExample.getTime(tScheduledTask1.getCreateTime(),new Date());
////            RingBufferWheel.Task task = new Job(id);
////            task.setKey(id);
////            wheel.addTask(task);
////        }
//        for(int i=0;i<tScheduledTask.size();i++){
//            int id= DateDifferentExample.getTime(tScheduledTask.get(i).getCreateTime(),new Date());
//            RingBufferWheel.Task task = new Job(id);
//            task.setKey(id);
//            wheel.addTask(task);
//        }
//
//        wheel.start();
//        log.info("task size",wheel.taskSize());
//        wheel.stop(false);
//    }
//
//    private static class Job extends RingBufferWheel.Task{
//        private int num;
//        public Job(int num){
//            this.num = num;
//        }
//
//        @Override
//        public void run(){
//            log.info("哈哈哈={}",num);
//        }
//    }
}
