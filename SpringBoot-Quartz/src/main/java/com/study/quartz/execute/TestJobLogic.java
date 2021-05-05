package com.study.quartz.execute;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/8 16:40
 * Job 是 定时任务的具体执行逻辑
 * JobDetail 是 定时任务的定义
 */
@Slf4j
@DisallowConcurrentExecution
public class TestJobLogic implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //写你自己的逻辑
        log.info("SayHelloJob.execute , hello world  ! ");

    }
}
