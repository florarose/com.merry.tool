package com.springboot.enmu.service;

import com.baomidou.mybatisplus.service.IService;
import com.springboot.enmu.entity.App;
import org.apache.ibatis.annotations.Param;

public interface appService extends IService<App> {
    App getApp(int id);
}
