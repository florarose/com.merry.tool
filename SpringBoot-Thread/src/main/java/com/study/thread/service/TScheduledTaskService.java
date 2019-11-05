package com.study.thread.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.thread.model.TScheduledTask;

/**
 * @author ldt merry
 * @date 2019/10/23
 */
public interface TScheduledTaskService extends IService<TScheduledTask> {

    String selectOneTask();

    void updateTaskSchedule(int id);
}
