package com.springboot.enmu.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.springboot.enmu.dao.AppMapper;
import com.springboot.enmu.entity.App;
import com.springboot.enmu.service.appService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *  @author liudongting
 *  @date2019/08/23 16:14:09
*/
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements appService {

    @Autowired
    private AppMapper appMapper;

    @Override
    public App getApp(int id) {
        return appMapper.getApp(id);
    }
}
