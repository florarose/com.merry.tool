package com.study.proxy.actionDemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 利用反射机制在运行时创建代理类。
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/17 15:49
 */
public class ProxyHandler implements InvocationHandler {


    private Object object;
    public ProxyHandler(Object object){
        this.object = object;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before invoke "  + method.getName());
        method.invoke(object, args);
        System.out.println("After invoke " + method.getName());
        return null;
    }
}
