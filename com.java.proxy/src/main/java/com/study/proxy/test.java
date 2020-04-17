package com.study.proxy;

import com.study.proxy.actionDemo.BaseProxy;
import com.study.proxy.staticDemo.Hello;
import com.study.proxy.staticDemo.HelloInterface;


/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/17 15:25
 */
public class test {



    public static void main(String[] args) {
        /**
         * 在ProxyGenerator.generateProxyClass函数中 saveGeneratedFiles定义如下，其指代是否保存生成的代理类class文件，默认false不保存。设置为true,则保存代理类。
         * 保存在根目录下。
         */
        System.getProperties().setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        HelloInterface hello = new Hello();

//        InvocationHandler handler = new ProxyHandler(hello);
//
//        HelloInterface proxyHello = (HelloInterface) Proxy.newProxyInstance(hello.getClass().getClassLoader(), hello.getClass().getInterfaces(), handler);
//
//        proxyHello.sayHello();
        BaseProxy baseProxy = new BaseProxy(new Hello(),hello);
        HelloInterface proxyHellos =  (HelloInterface)baseProxy.getProxy();
        proxyHellos.sayHello();
    }
}
