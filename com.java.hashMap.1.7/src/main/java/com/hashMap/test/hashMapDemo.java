package com.hashMap.test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class hashMapDemo {
    static final int MAXIMUM_CAPACITY = 1 << 30;
    private static void map1(){
        ConcurrentHashMap<String,Object> map = new ConcurrentHashMap<>();
         map.put("A","111");
    }
    static final int tableSizeFor(int cap) {

        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
    public static void main(String[] args) {
        System.out.println(Integer.parseInt("0011111", 2) & 15);
        System.out.println(Integer.parseInt("0111111", 2) & 15);
        System.out.println(Integer.parseInt("1111111", 2) & 15);
    }
}
