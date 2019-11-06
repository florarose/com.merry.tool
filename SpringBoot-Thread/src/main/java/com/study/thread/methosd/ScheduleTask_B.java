package com.study.thread.methosd;

import com.study.thread.SchedulingConfig.ScheduleTaskApi;
import com.study.thread.mapper.TScheduledTaskMapper;
import com.study.thread.model.TScheduledTask;
import com.study.thread.service.impl.TScheduledTaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * 基于注解@Scheduled默认为单线程，开启多个任务时，任务的执行时机会受上一个任务执行时间的影响。
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/4 13:44
 */
@Configuration
@Slf4j
public class ScheduleTask_B {


    @Autowired      //注入mapper
    @SuppressWarnings("all")
    private TScheduledTaskMapper tScheduledTaskService;
    @Autowired      //注入mapper
    @SuppressWarnings("all")
    private TScheduledTaskServiceImpl tScheduledTaskServiceImpl;

        //接受任务的返回结果
    private ScheduledFuture future;

    @Autowired
    CronTaskRegistrar cronTaskRegistrar;

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
    public void configureTasks() throws Exception {
        List<TScheduledTask> tScheduledTasks = tScheduledTaskService.selectManyTask();
        if(tScheduledTasks.size()>0){
            for(TScheduledTask tScheduledTask : tScheduledTasks){
                System.out.println("哈哈哈，进来了"+tScheduledTask.getId());
                if(null != tScheduledTask){
                    SchedulingRunnable task = new SchedulingRunnable("demoTask", "taskWithParams", "null",tScheduledTask.getId());
                    System.out.println(tScheduledTask.getStatusCron());
                    cronTaskRegistrar.addCronTask(task, tScheduledTask.getStatusCron());
                    tScheduledTaskServiceImpl.updateTaskSchedule(tScheduledTask.getId(),2);
                }
            }
        }else {
            System.out.println("执行结束了");
        }

    }
}
