package com.study.rabbitmq.mq.listener;

import com.rabbitmq.client.Channel;
import com.study.rabbitmq.config.RabbitConfig;
import com.study.rabbitmq.mq.BaseConsumer;
import com.study.rabbitmq.mq.BaseConsumerProxy;
import com.study.rabbitmq.mq.consumer.LoginLogConsumer;
import com.study.rabbitmq.service.MsgLogService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginLogListener {

    @Autowired
    private LoginLogConsumer loginLogConsumer;

    @Autowired
    private MsgLogService msgLogService;

    @RabbitListener(queues = RabbitConfig.LOGIN_LOG_QUEUE_NAME)
    public void consume(Message message, Channel channel) throws IOException {
        BaseConsumerProxy baseConsumerProxy = new BaseConsumerProxy(loginLogConsumer, msgLogService);
        BaseConsumer proxy = (BaseConsumer) baseConsumerProxy.getProxy();
        if (null != proxy) {
            proxy.consume(message, channel);
        }
    }

}
