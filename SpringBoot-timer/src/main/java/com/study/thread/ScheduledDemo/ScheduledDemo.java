package com.study.thread.ScheduledDemo;

import com.study.thread.SchedulingConfig.ScheduleTaskApi;
import com.study.thread.mapper.TScheduledTaskMapper;
import com.study.thread.model.TScheduledTask;
import com.study.thread.service.impl.TScheduledTaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledFuture;

/**
 * 基于注解@Scheduled默认为单线程，开启多个任务时，任务的执行时机会受上一个任务执行时间的影响。
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/4 13:44
 */
//@Configuration
@Slf4j
public class ScheduledDemo {

    /**
     * 、静态：基于注解
     * Cron表达式参数分别表示：
     * 秒（0~59） 例如0/5表示每5秒
     * 分（0~59）
     * 时（0~23）
     * 日（0~31）的某天，需计算
     * 月（0~11）
     * 周几（ 可填1-7 或 SUN/MON/TUE/WED/THU/FRI/SAT）
     */
    //3.添加定时任务
//    @Scheduled(cron = "0/5 * * * * ?")
    private void configureTasks() throws Exception {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }
}
