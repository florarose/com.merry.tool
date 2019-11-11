//package com.study.thread.SchedulingConfig;
//
//import com.study.thread.mapper.TScheduledTaskMapper;
//import com.study.thread.model.TScheduledTask;
//import com.study.thread.service.TScheduledTaskService;
//import com.study.thread.service.impl.TScheduledTaskServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.*;
//import org.springframework.scheduling.config.ScheduledTaskRegistrar;
//import org.springframework.scheduling.support.CronTrigger;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import javax.print.attribute.HashAttributeSet;
//import java.time.LocalDateTime;
//import java.util.*;
//
///**
// * @author 坎布里奇
// * @version 1.0
// * @date 2019/11/4 11:01
// */
//@Component
//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
//@EnableScheduling   // 2.开启定时任务
//public class ScheduleTask  implements SchedulingConfigurer {
//
//
//    private TScheduledTask tScheduledTask = null;
//    private static final String DEFAULT_CRON = "0/5 * * * * ?";
//    private String cronss = DEFAULT_CRON;
//
////    @Mapper
////    public interface CronMapper {
//////        @Select("select cron from cron limit 1")
//////        public String getCron();
////    }
////
//    @Autowired      //注入mapper
//    @SuppressWarnings("all")
//    private TScheduledTaskMapper tScheduledTaskService;
//    @Autowired      //注入mapper
//    @SuppressWarnings("all")
//    private TScheduledTaskServiceImpl tScheduledTaskServiceImpl;
//
//
//
////  public ScheduleTask(){
////      new Thread(
////              () ->{
////                  try {
////                      Thread.sleep(10000);
////                  }catch (InterruptedException e){
////                      e.printStackTrace();
////                  }
////                  tScheduledTask= tScheduledTaskService.selectOneTask();
////              }
////      ).start();
////  }
////    @Scheduled(fixedDelay = 5000)        //fixedDelay = 5000表示当前方法执行完毕5000ms后，Spring scheduling会再次调用该方法
////    public void testFixDelay() {
////        System.out.println("第一个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
////    }
//
//    /**
//     * 执行定时任务.
//     */
//    @Override
//    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//
//        taskRegistrar.addTriggerTask(
//                //1.添加任务内容(Runnable)
//                () -> {
//                    System.out.println("刷新 " + LocalDateTime.now().toLocalTime());
//                    System.out.println("线程 " + Thread.currentThread().getName());
//                    try {
//                        Thread.sleep(10000);
//                    }catch (InterruptedException e){
//                        e.printStackTrace();
//                    }
//                  if(null != tScheduledTask){
//                      System.out.println("执行动态定时任务: " + LocalDateTime.now().toLocalTime());
//                      tScheduledTaskServiceImpl.updateTaskSchedule(tScheduledTask.getId());
//                      tScheduledTask=null;
//                  }else {
//                      System.out.println("再查找一个新的");
//                        tScheduledTask= tScheduledTaskService.selectOneTask();
//                    }
//                },
//                //2.设置执行周期(Trigger)
//                triggerContext -> {
//                    String cron = "";
//                    //2.1 从数据库获取执行周期
//                    if(null == tScheduledTask){
//                        cron = cronss;
//                    }else {
//                        cron = tScheduledTask.getStatusCron();
//                        int s =new CronTrigger(cron).nextExecutionTime(triggerContext).compareTo(new Date());
//                            if(s==-1){
//                                System.out.println("过期了，都");
//                                tScheduledTaskServiceImpl.updateTaskSchedule(tScheduledTask.getId());
//                                cron = cronss;
//                                tScheduledTask=null;
//                            }else  if(s == 1){
//                                tScheduledTask=null;
//                                cron = cronss;
//                            }
//                    }
//                    System.out.println("表达式"+cron);
//                    System.out.println("开始 ");
//                    //2.2 合法性校验.
//                    if (StringUtils.isEmpty(cron) || null == cron) {
//                        System.out.println("没有任务");
//                        // Omitted Code ..
//                    }
//                    //2.3 返回执行周期(Date)
//                    return new CronTrigger(cron).nextExecutionTime(triggerContext);
//                }
//        );
//        System.out.println("-------------");
//    }
////    @Override
////    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
////        scheduledTaskRegistrar.addTriggerTask(new Runnable() {
////            @Override
////            public void run() {
////                try {
////                    taskService.deleteInvalidCheckCode();  //异步定时操作
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
////            }
////        }, new Trigger() {
////            @Override
////            public Date nextExecutionTime(TriggerContext triggerContext) {
////                String cron =cronMapper.getCron();
////                if("".equals(cron)||cron==null)
////                    return null;
////                //定时任务触发,可修改定时任务的执行周期
////                CronTrigger trigger=new CronTrigger(cron);
////                Date nextExecDate= trigger.nextExecutionTime(triggerContext);
////                return nextExecDate;
////            }
////        });
////    }
//
//}
