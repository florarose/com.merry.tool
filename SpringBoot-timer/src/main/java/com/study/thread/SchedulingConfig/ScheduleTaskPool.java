package com.study.thread.SchedulingConfig;

import com.study.thread.mapper.TScheduledTaskMapper;
import com.study.thread.model.TScheduledTask;
import com.study.thread.service.impl.TScheduledTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/4 11:01
 */
      //1.主要用于标记配置类，兼备Component的效果。

public class ScheduleTaskPool implements SchedulingConfigurer {


    private static TScheduledTask tScheduledTask = null;
    private static final String DEFAULT_CRON = "0/1 * * * * ?";
    private String cronss = DEFAULT_CRON;

    @Autowired      //注入mapper
    @SuppressWarnings("all")
    private TScheduledTaskMapper tScheduledTaskService;
    @Autowired      //注入mapper
    @SuppressWarnings("all")
    private TScheduledTaskServiceImpl tScheduledTaskServiceImpl;


//    @Bean
//    public ThreadPoolTaskScheduler trPoolTaskScheduler(){
//        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
//        threadPoolTaskScheduler.setPoolSize(10); // 配置线程池大小
//        threadPoolTaskScheduler.setThreadNamePrefix("spring-task-scheduler-thread-");// 线程名称前缀，//设置好了之后可以方便我们定位处理任务所在的线程池
//        threadPoolTaskScheduler.setAwaitTerminationSeconds(60);// 线程池中任务的关闭前最大等待时间，确保最后一定关闭
//        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);// 线程池关闭时等待所有任务完成
//        threadPoolTaskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());// 任务丢弃策略
//        return threadPoolTaskScheduler;
//    }

    /**
     * 执行定时任务.
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setTaskScheduler(taskScheduler());
        taskRegistrar.getScheduler().schedule(
                //1.添加任务内容(Runnable)
                () -> {
                    System.out.println("刷新 " + LocalDateTime.now().toLocalTime());
                    System.out.println("线程 " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(8000);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                  if(null != tScheduledTask){
                      System.out.println("执行动态定时任务: " + LocalDateTime.now().toLocalTime());
//                      tScheduledTaskServiceImpl.updateTaskSchedule(tScheduledTask.getId());
                  }else {
                      System.out.println("再查找一个新的");
                      tScheduledTask= tScheduledTaskService.selectOneTask();
                    }
                },
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    String cron = "";
                    //2.1 从数据库获取执行周期
                    if(null == tScheduledTask){
                        cron = cronss;
                    }else {
                        cron = tScheduledTask.getStatusCron();
                    }
                    System.out.println("表达式"+cron);
                    System.out.println("开始 ");
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron) || null == cron) {
                        System.out.println("没有任务");
                        // Omitted Code ..
                    }
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
//        try {
//            System.out.println("执行结果："+future.get());
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }catch (ExecutionException e){
//            e.printStackTrace();
//        }

        System.out.println("-------------");
    }
//    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.setThreadNamePrefix("spring-task-scheduler-thread-");
        taskScheduler.setAwaitTerminationSeconds(60);
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return taskScheduler;
    }

}
