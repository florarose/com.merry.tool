package com.study.rabbitmq.designpattern.staticproxy;

import java.math.BigDecimal;

public interface PayService {

    void pay(String username, BigDecimal money);

}
