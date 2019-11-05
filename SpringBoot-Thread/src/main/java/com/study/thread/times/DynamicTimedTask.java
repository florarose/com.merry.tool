//package com.study.thread.times;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.ScheduledFuture;
//
///**
// * @author 坎布里奇
// * @version 1.0
// * @date 2019/11/5 16:11
// */
//@Component
//public class DynamicTimedTask {
//
//    private static final Logger logger = LoggerFactory.getLogger(DynamicTimedTask.class);
//
//    //利用创建好的调度类统一管理
//    //@Autowired
//    //@Qualifier("myThreadPoolTaskScheduler")
//    //private ThreadPoolTaskScheduler myThreadPoolTaskScheduler;
//
//
//    //接受任务的返回结果
//    private ScheduledFuture<?> future;
//
//    @Autowired
//    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
//
//    //实例化一个线程池任务调度类,可以使用自定义的ThreadPoolTaskScheduler
//    @Bean
//    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
//        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
//        return new ThreadPoolTaskScheduler();
//    }
//
//
//    /**
//     * 启动定时任务
//     * @return
//     */
//    public boolean startCron() {
//        boolean flag = false;
//        //从数据库动态获取执行周期
//        String cron = "0/2 * * * * ? ";
//        future = threadPoolTaskScheduler.schedule(new CheckModelFile(),cron);
//        if (future!=null){
//            flag = true;
//            logger.info("定时check训练模型文件,任务启动成功！！！");
//        }else {
//            logger.info("定时check训练模型文件,任务启动失败！！！");
//        }
//        return flag;
//    }
//
//    /**
//     * 停止定时任务
//     * @return
//     */
//    public boolean stopCron() {
//        boolean flag = false;
//        if (future != null) {
//            boolean cancel = future.cancel(true);
//            if (cancel){
//                flag = true;
//                logger.info("定时check训练模型文件,任务停止成功！！！");
//            }else {
//                logger.info("定时check训练模型文件,任务停止失败！！！");
//            }
//        }else {
//            flag = true;
//            logger.info("定时check训练模型文件，任务已经停止！！！");
//        }
//        return flag;
//    }
//
//
//    class CheckModelFile implements Runnable{
//
//        @Override
//        public void run() {
//            //编写你自己的业务逻辑
//            System.out.print("模型文件检查完毕！！！");
//        }
//    }
//}
