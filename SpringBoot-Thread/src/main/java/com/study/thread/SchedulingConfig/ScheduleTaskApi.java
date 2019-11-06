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
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/4 11:01
 */
     //1.主要用于标记配置类，兼备Component的效果。
 @Component
public class ScheduleTaskApi  {


    private static TScheduledTask tScheduledTask = null;
    private static final String DEFAULT_CRON = "0/1 * * * * ?";

    @Autowired      //注入mapper
    @SuppressWarnings("all")
    private TScheduledTaskMapper tScheduledTaskService;
    @Autowired      //注入mapper
    @SuppressWarnings("all")
    private TScheduledTaskServiceImpl tScheduledTaskServiceImpl;

    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;


    /**
     * 执行定时任务.
     */
    public ScheduledFuture configureTasks(String cronss,int i) {

        ScheduledFuture future=taskScheduler.schedule(
                //1.添加任务内容(Runnable)
                () -> {
                    sout(i);
                },
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    String cron = "";
                    cron = cronss;
                    System.out.println("表达式"+cron);
                    System.out.println("开始 ");
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(cron) || null == cron) {
                        System.out.println("没有任务");
                        // Omitted Code ..
                    }
                    //2.3 返回执行周期(Date)
//                    if(new CronTrigger(cron).nextExecutionTime(triggerContext).before(new Date())){
//
//                    }
                    System.out.println("ScheduledExecutionTime"+triggerContext.lastScheduledExecutionTime());
                    System.out.println("CompletionTime"+triggerContext.lastCompletionTime());
                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
                }
        );
        System.out.println("-------------");
        return future;
    }
   public void sout(int i){
       System.out.println("刷新 " + LocalDateTime.now().toLocalTime()+"---------"+i);
       System.out.println("线程 " + Thread.currentThread().getName()+"---------"+i);
       System.out.println("执行动态定时任务: " + LocalDateTime.now().toLocalTime()+"---------"+i);
   }
}
