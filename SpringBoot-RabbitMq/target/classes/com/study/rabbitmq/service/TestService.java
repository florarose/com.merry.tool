package com.study.rabbitmq.service;


import com.study.rabbitmq.common.ServerResponse;
import com.study.rabbitmq.pojo.Mail;

public interface TestService {

    ServerResponse testIdempotence();

    ServerResponse accessLimit();

    ServerResponse send(Mail mail);
}
