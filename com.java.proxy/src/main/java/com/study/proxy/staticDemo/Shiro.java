package com.study.proxy.staticDemo;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/17 16:06
 */
public class Shiro implements HelloInterface {

    @Override
    public void sayHello() {
        System.out.println("shiro say next meet you !! ");
    }
}
