//package com.study.thread.SchedulingConfig;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.TaskScheduler;
//import org.springframework.scheduling.annotation.AsyncConfigurer;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.SchedulingConfigurer;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//
//import java.lang.reflect.Method;
//import java.util.concurrent.Executor;
//import java.util.concurrent.ThreadPoolExecutor;
//
///**
// * @author 坎布里奇
// * @version 1.0
// * @date 2019/11/5 12:39
// */
//
//@Configuration
//@EnableAsync
//@EnableScheduling
//public class ScheduleConfig implements SchedulingConfigurer, AsyncConfigurer {
//
//    Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        taskRegistrar.setTaskScheduler(taskScheduler());
//    }
//
//    @Bean
//    public TaskScheduler taskScheduler() {
//        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
//        taskScheduler.setPoolSize(10);// 配置线程池大小，根据任务数量定制
//        taskScheduler.setThreadNamePrefix("spring-task-scheduler-thread-");// 线程名称前缀
//        taskScheduler.setAwaitTerminationSeconds(60);// 线程池关闭前最大等待时间，确保最后一定关闭
//        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);// 线程池关闭时等待所有任务完成
//        taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());// 任务丢弃策略
//        return taskScheduler;
//    }
//
//    @Override
//    public Executor getAsyncExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(20);// 配置核心线程数
//        executor.setMaxPoolSize(50);// 配置最大线程数
//        executor.setQueueCapacity(100);// 配置缓存队列大小
//        executor.setKeepAliveSeconds(15);// 空闲线程存活时间
//        executor.setThreadNamePrefix("spring-task-executor-thread-");
//        // 线程池对拒绝任务的处理策略：这里采用了CallerRunsPolicy策略，当线程池没有处理能力的时候，该策略会直接在execute方法的调用线程中运行被拒绝的任务；如果执行程序已关闭，则会丢弃该任务
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());// AbortPolicy()
//        // 等待所有任务结束后再关闭线程池
//        executor.setWaitForTasksToCompleteOnShutdown(true);
//        // 设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是被没有完成的任务阻塞
//        executor.setAwaitTerminationSeconds(60);
//        executor.initialize();
//        return executor;
//    }
//
//    /**
//     * 处理异步方法的异常
//     */
//    @Override
//    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//        return new SpringAsyncExceptionHandler();
//    }
//
//    class SpringAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {
//        @Override
//        public void handleUncaughtException(Throwable arg0, Method arg1, Object... arg2) {
//            logger.error("Exception occurs in async method", arg0);
//        }
//    }
//
//}
