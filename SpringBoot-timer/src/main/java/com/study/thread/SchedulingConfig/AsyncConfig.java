//package com.study.thread.SchedulingConfig;
//
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.MDC;
//import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.task.TaskDecorator;
//import org.springframework.scheduling.annotation.AsyncConfigurer;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//
//import java.util.Map;
//import java.util.concurrent.Executor;
//
///**
// * @author 坎布里奇
// * @version 1.0
// * @date 2019/11/6 12:08
// */
//
//@Slf4j
//@EnableAsync
//@Configuration
//public class AsyncConfig  implements AsyncConfigurer {
//
//    @Bean
//    @Override
//    public Executor getAsyncExecutor() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setCorePoolSize(10);
//        executor.setMaxPoolSize(10);
//        executor.setQueueCapacity(100);
//        executor.setThreadNamePrefix("async-pool-");
//        executor.setTaskDecorator(new MdcTaskDecorator());
//        executor.setWaitForTasksToCompleteOnShutdown(true);
//        executor.initialize();
//        return executor;
//    }
//
//    class MdcTaskDecorator implements TaskDecorator {
//        @Override
//        public Runnable decorate(Runnable runnable) {
//            Map<String, String> contextMap = MDC.getCopyOfContextMap();
//            Runnable runnable1 = new Runnable() {
//                @Override
//                public void run() {
//                    if (contextMap != null) {
//                        MDC.setContextMap(contextMap);
//                    }
//                    runnable.run();
//                }
//            };
//            return runnable1;
//        }
//    }
//
//    @Override
//    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//        return (throwable, method, params) -> {
//            log.error("异步任务异常：方法：{} 参数：{}", method.getName(), JSON.toJSONString(params));
//            log.error(throwable.getMessage(), throwable);
//        };
//    }
//}
