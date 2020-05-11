package com.java.thread.LockDemo.consul;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/5/11 18:57
 */
public class test1 {
    public static void main(String[] args) {
        consulTest consulTest = new consulTest();
        MythreadA mythreadA = new MythreadA(consulTest);
        mythreadA.start();
        MythreadB mythreadb = new MythreadB(consulTest);
        mythreadb.start();
    }
}
