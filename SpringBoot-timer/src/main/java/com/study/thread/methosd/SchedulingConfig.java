package com.study.thread.methosd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/6 15:03
 */
@Slf4j
@Configuration
@EnableScheduling
public class SchedulingConfig {
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        // 定时任务执行线程池核心线程数
        taskScheduler.setPoolSize(20);
        taskScheduler.setAwaitTerminationSeconds(60);
        taskScheduler.setRemoveOnCancelPolicy(true);
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        taskScheduler.setThreadNamePrefix("TaskSchedulerThreadPool-");
        taskScheduler.setErrorHandler(throwable -> log.error("调度任务发生异常", throwable));
        return taskScheduler;
    }
}