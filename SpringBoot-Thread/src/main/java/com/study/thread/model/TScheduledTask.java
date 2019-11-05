package com.study.thread.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * @author ldt merry
 * @date 2019/10/23
 */
@TableName("t_scheduled_task")
@Data
public class TScheduledTask  extends Model<TScheduledTask> {

    private static final long serialVersionUID = 1L;
    @Override
    protected Serializable pkVal() {  return this.id;  }

    private int id;
    @TableField("status_name")
    private String statusName;
    @TableField("status_cron")
    private String statusCron;



    private int status;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;
}
