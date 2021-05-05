package com.study.rabbitmq.service;


import com.study.rabbitmq.common.ServerResponse;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {

    ServerResponse createToken();

    void checkToken(HttpServletRequest request);

}
