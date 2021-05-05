package com.study.rabbitmq.mq.consumer;

import com.rabbitmq.client.Channel;
import com.study.rabbitmq.common.Constant;
import com.study.rabbitmq.config.RabbitConfig;
import com.study.rabbitmq.mq.MessageHelper;
import com.study.rabbitmq.pojo.Mail;
import com.study.rabbitmq.pojo.MsgLog;
import com.study.rabbitmq.service.MsgLogService;
import com.study.rabbitmq.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class SimpleMailConsumer {

    @Autowired
    private MsgLogService msgLogService;

    @Autowired
    private MailUtil mailUtil;

    /**
     *  1 保证消费的幂等性  2 发送邮件  3  更新消息状态 ，手动ack
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = RabbitConfig.MAIL_QUEUE_NAME)
    public void consume(Message message, Channel channel) throws IOException {
        Mail mail = MessageHelper.msgToObj(message, Mail.class);
        log.info("收到消息: {}", mail.toString());

        String msgId = mail.getMsgId();

        MsgLog msgLog = msgLogService.selectByMsgId(msgId);
        if (null == msgLog || msgLog.getStatus().equals(Constant.MsgLogStatus.CONSUMED_SUCCESS)) {// 消费幂等性
            log.info("重复消费, msgId: {}", msgId);
            return;
        }

        MessageProperties properties = message.getMessageProperties();
        long tag = properties.getDeliveryTag();

        boolean success = mailUtil.send(mail);
        if (success) {
            msgLogService.updateStatus(msgId, Constant.MsgLogStatus.CONSUMED_SUCCESS);
            //在手动ack模式下, 消费端必须进行手动确认(ack), 否则消息会一直保存在队列中 ;;; 虽然消息确实被消费了, 但是由于是手动确认模式, 而最后又没手动确认, 所以, 消息仍被rabbitmq保存
            // 手动ack能够保证消息一定被消费, 但一定要记得basicAck
            channel.basicAck(tag, false);// 消费确认
        } else {
            channel.basicNack(tag, false, true);
        }
    }

}
