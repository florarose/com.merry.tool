package com.study.thread.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.thread.model.TScheduledTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author ldt merry
 * @date 2019/10/23
 */
@Mapper
public interface TScheduledTaskMapper extends BaseMapper<TScheduledTask> {

    TScheduledTask selectOneTask();

    List<TScheduledTask> selectManyTask();
}
