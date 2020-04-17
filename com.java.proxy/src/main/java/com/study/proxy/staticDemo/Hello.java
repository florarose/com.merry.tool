package com.study.proxy.staticDemo;

/**
 * 被代理类
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/17 15:26
 */
public class Hello implements HelloInterface{
    @Override
    public void sayHello() {
        System.out.println("Hello zhanghao!");
    }
}
