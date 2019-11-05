package com.study.thread.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.thread.mapper.TScheduledTaskMapper;
import com.study.thread.model.TScheduledTask;
import com.study.thread.service.TScheduledTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * @author ldt merry
 * @date 2019/10/23
 */
@Service
public class TScheduledTaskServiceImpl extends ServiceImpl<TScheduledTaskMapper, TScheduledTask> implements TScheduledTaskService {

    @Autowired
    private TScheduledTaskMapper tScheduledTaskMapper;

    @Override
    public String selectOneTask() {
        Map<String,Object> filters = new HashMap<>();
        return tScheduledTaskMapper.selectOneTask().getStatusCron();
    }

    @Override
    public void updateTaskSchedule(int id) {
        TScheduledTask scheduleTask = tScheduledTaskMapper.selectById(id);
        scheduleTask.setStatus(1);
        tScheduledTaskMapper.updateById(scheduleTask);
    }
}
