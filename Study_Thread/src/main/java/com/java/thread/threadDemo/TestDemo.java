package com.java.thread.threadDemo;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2019/11/11 11:09
 */
public class TestDemo {
    public static void main(String[] args) {
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.start();
        System.out.println("运行结束");
    }
}
