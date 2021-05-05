package com.study.quartz.api;

import com.study.quartz.execute.TestJobLogic;
import com.study.quartz.service.QuartzJobService;
import com.study.quartz.entity.*;

import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/8 16:40
 */
@RestController
public class JobApi {


    @Resource
    private QuartzJobService quartzJobService;


    //定义一个定时任务，名字是Test, 组名为 ainan
    private final JobKey jobKey = JobKey.jobKey("Test", "ainan");


    /**
     * 启动 hello world
     */
    @GetMapping("/startHelloWorldJob")
    public String startHelloWorldJob() throws SchedulerException {

        //创建一个定时任务
        BaseTaskDemo task = BaseTaskDemo.builder()
                .jobKey(jobKey)
                .cronExpression("0/2 * * * * ? ")   //定时任务 的cron表达式
                .jobClass(TestJobLogic.class)   //定时任务 的具体执行逻辑
                .description("这是一个测试的 任务")    //定时任务 的描述
                .build();

        quartzJobService.scheduleJob(task);
        return "start HelloWorld Job success";
    }

    /**
     * 暂停 hello world
     */
    @GetMapping("/pauseHelloWorldJob")
    public String pauseHelloWorldJob() throws SchedulerException {
        quartzJobService.pauseJob(jobKey);
        return "pause HelloWorld Job success";
    }


    /**
     * 恢复 hello world
     */
    @GetMapping("/resumeHelloWorldJob")
    public String resumeHelloWorldJob() throws SchedulerException {
        quartzJobService.resumeJob(jobKey);
        return "resume HelloWorld Job success";
    }

    /**
     * 删除 hello world
     */
    @GetMapping("/deleteHelloWorldJob")
    public String deleteHelloWorldJob() throws SchedulerException {
        quartzJobService.deleteJob(jobKey);
        return "delete HelloWorld Job success";
    }

    /**
     * 修改 hello world 的cron表达式
     */
    @GetMapping("/modifyHelloWorldJobCron")
    public String modifyHelloWorldJobCron() {

        //这是即将要修改cron的定时任务
        BaseTaskDemo modifyCronTask = BaseTaskDemo.builder()
                .jobKey(jobKey)
                .cronExpression("0/5 * * * * ? ")   //定时任务 的cron表达式
                .jobClass(TestJobLogic.class)   //定时任务 的具体执行逻辑
                .description("这是一个测试的 任务")    //定时任务 的描述
                .build();
        if (quartzJobService.modifyJobCron(modifyCronTask))
            return "modify HelloWorld Job Cron success";
        else return "modify HelloWorld Job Cron fail";
    }


}
