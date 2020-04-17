package com.study.proxy.staticDemo;

/**
 * 静态代理类
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/17 15:35
 */
public class HelloProxy implements HelloInterface{

    private HelloInterface helloInterface = new Hello();

    @Override
    public void sayHello() {
        System.out.println("Before invoke sayHello" );
        helloInterface.sayHello();
        System.out.println("After invoke sayHello");
    }
}
