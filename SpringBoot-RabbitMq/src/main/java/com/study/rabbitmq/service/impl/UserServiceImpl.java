package com.study.rabbitmq.service.impl;


import com.study.rabbitmq.common.Constant;
import com.study.rabbitmq.common.ResponseCode;
import com.study.rabbitmq.common.ServerResponse;
import com.study.rabbitmq.config.RabbitConfig;
import com.study.rabbitmq.mapper.MsgLogMapper;
import com.study.rabbitmq.mapper.UserMapper;
import com.study.rabbitmq.mq.MessageHelper;
import com.study.rabbitmq.pojo.LoginLog;
import com.study.rabbitmq.pojo.MsgLog;
import com.study.rabbitmq.pojo.User;
import com.study.rabbitmq.service.UserService;
import com.study.rabbitmq.util.DateUtil;
import com.study.rabbitmq.util.JedisUtil;
import com.study.rabbitmq.util.JodaTimeUtil;
import com.study.rabbitmq.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MsgLogMapper msgLogMapper;

    @Autowired
    private JedisUtil jedisUtil;


    private static final LocalDate beginDate=LocalDate.of(2018,1,1);
    @Override
    public List<User> getAll() {
        return userMapper.selectAll();
    }

    @Override
    public User getOne(Integer id) {
        return userMapper.selectOne(id);
    }

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }

    @Override
    public User getByUsernameAndPassword(String username, String password) {
        return userMapper.selectByUsernameAndPassword(username, password);
    }

    @Override
    public ServerResponse login(String username, String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ServerResponse.error(ResponseCode.USERNAME_OR_PASSWORD_EMPTY.getMsg());
        }

        User user = userMapper.selectByUsernameAndPassword(username, password);
        if (null == user) {
            return ServerResponse.error(ResponseCode.USERNAME_OR_PASSWORD_WRONG.getMsg());
        }

        saveAndSendMsg(user);

        jedisUtil.setBit(String.valueOf(user.getId()), DateUtil.getDateDuration(beginDate,LocalDate.now()),true);
        return ServerResponse.success();
    }

    @Override
    public boolean getUserByLogin(int id, LocalDate date) {
        return jedisUtil.getBit(String.valueOf(id),DateUtil.getDateDuration(beginDate,LocalDate.now()));
    }

    /**
     * 保存并发送消息
     * @param user
     */
    private void saveAndSendMsg(User user) {
        String msgId = RandomUtil.UUID32();

        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(user.getId());
        loginLog.setType(Constant.LogType.LOGIN);
        Date date = new Date();
        loginLog.setDescription(user.getUsername() + "在" + JodaTimeUtil.dateToStr(date) + "登录系统");
        loginLog.setCreateTime(date);
        loginLog.setUpdateTime(date);
        loginLog.setMsgId(msgId);

        CorrelationData correlationData = new CorrelationData(msgId);
        rabbitTemplate.convertAndSend(RabbitConfig.LOGIN_LOG_EXCHANGE_NAME, RabbitConfig.LOGIN_LOG_ROUTING_KEY_NAME, MessageHelper.objToMsg(loginLog), correlationData);

        MsgLog msgLog = new MsgLog(msgId, loginLog, RabbitConfig.LOGIN_LOG_EXCHANGE_NAME, RabbitConfig.LOGIN_LOG_ROUTING_KEY_NAME);
        msgLogMapper.insert(msgLog);
    }
}
