package com.study.rabbitmq.service;


import com.study.rabbitmq.pojo.LoginLog;

public interface LoginLogService {

    void insert(LoginLog loginLog);

    LoginLog selectByMsgId(String msgId);

}
