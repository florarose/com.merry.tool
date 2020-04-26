package com.study.rabbitmq.service;



import com.study.rabbitmq.common.ServerResponse;
import com.study.rabbitmq.pojo.User;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    List<User> getAll();

    User getOne(Integer id);

    void add(User user);

    void update(User user);

    void delete(Integer id);

    User getByUsernameAndPassword(String username, String password);

    ServerResponse login(String username, String password);

    boolean getUserByLogin(int id, LocalDate date);

}
