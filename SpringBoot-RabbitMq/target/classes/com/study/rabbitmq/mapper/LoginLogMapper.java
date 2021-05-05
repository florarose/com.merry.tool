package com.study.rabbitmq.mapper;


import com.study.rabbitmq.pojo.LoginLog;

public interface LoginLogMapper {

    void insert(LoginLog loginLog);

    LoginLog selectByMsgId(String msgId);

}
