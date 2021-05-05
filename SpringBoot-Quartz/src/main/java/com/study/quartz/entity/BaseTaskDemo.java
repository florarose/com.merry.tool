package com.study.quartz.entity;

import lombok.Builder;
import lombok.Data;
import org.quartz.Job;
import org.quartz.JobKey;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/8 16:40
 * Quartz 里面 一个最简单,最基本的定时任务 应该包含以下必要基本属性
 */
@Data
@Builder
public class BaseTaskDemo {

    /**
     * 定时任务 的名字和分组名 JobKey,{@link JobKey}
     */
    @NotNull(message = "定时任务的 名字 和 组名 不能为空")
    private JobKey jobKey;
    /**
     * 定时任务 的描述(定时任务本身的描述 或者 触发器)
     */
    private String description;
    /**
     * 定时任务 的执行cron,表达式 (Trigger的CronScheduleBuilder 的cronExpression)
     */
    @NotEmpty(message = "定时任务的执行cron 不能为空")
    private String cronExpression;
    /**
     * 定时任务 的元数据
     * {@link org.quartz.JobDataMap}
     */
    private Map<?, ?> jobDataMap;
    /**
     * 定时任务 的 具体执行逻辑类
     * {@link Job}
     */
    @NotNull(message = "定时任务的具体执行逻辑类")
    private Class<? extends Job> jobClass;
}
