package com.java.common;

/**
 * @author 坎布里奇
 * @version 1.0
 * @date 2020/4/3 18:24
 */
public class dd {

    public static void main(String[] args) {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                return super.loadClass(name);
            }
        };
    }
}
