package com.study.proxy.actionDemo;

import com.study.proxy.staticDemo.HelloInterface;

import java.lang.reflect.Proxy;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/17 15:40
 */
public class BaseProxy {

    private Object target;

    private HelloInterface helloInterface;

    public BaseProxy(Object target, HelloInterface helloInterface) {
        this.target = target;
        this.helloInterface = helloInterface;
    }

    public Object getProxy() {
        ClassLoader classLoader = target.getClass().getClassLoader();
        Class[] interfaces = target.getClass().getInterfaces();


        /**
         * 传入ClassLoader
         * 传入要实现的接口
         * 传入处理调用方法的InvocationHandler
         */
        Object proxy = Proxy.newProxyInstance(classLoader, interfaces, (proxy1, method, args) -> {
            Object result = method.invoke(target, args);
          return result;
        });

        return proxy;
    }

    /**
     *  InvocationHandler handler = new InvocationHandler() {
     *             @Override
     *             public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
     *                 System.out.println(method);
     *                 if (method.getName().equals("morning")) {
     *                     System.out.println("Good morning, " + args[0]);
     *                 }
     *                 return null;
     *             }
     *         };
     *         Hello hello = (Hello) Proxy.newProxyInstance(
     *             Hello.class.getClassLoader(), // 传入ClassLoader
     *             new Class[] { Hello.class }, // 传入要实现的接口
     *             handler); // 传入处理调用方法的InvocationHandler
     *         hello.morning("Bob");
     */
}
