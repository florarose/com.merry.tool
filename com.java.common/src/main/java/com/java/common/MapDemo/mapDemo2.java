package com.java.common.MapDemo;

import java.util.*;

public class mapDemo2 {


    /**
     * 一、 keySet遍历方式
     * @param map
     */
    private static  void testKeySet(Map<String, Object> map) {
        //原始的
        Set<String> keys = map.keySet();
        for (String key : keys) {
            String value = (String) map.get(key);
            System.out.println(key + "--------" + value );
        }
        //使用lambda
//        map.keySet().forEach(name->{
//            System.out.println(name+"--------------"+map.get(name));
//        });
    }
    /**
     * 二、entrySet  遍历方式 ，容量较大的使用推荐此方法
     * @param map
     */
    private static void testEntrySet(Map<String, Object> map) {
        Set<Map.Entry<String,Object>> entry = map.entrySet();
        for (Map.Entry<String, Object> stringEntry : entry) {
            System.out.println(stringEntry.getKey() + "------" + stringEntry.getValue());
        }
        //使用lambda
//        map.entrySet().forEach((key)->{
//            System.out.println(key.getKey()+"-------------"+key.getValue());
//        });
    }
    /**
     * 三、Iterator遍历方式
     *     需要使用移出功能，尽量用此方法
     * @param map
     */
    private static void testIterator(Map<String, Object> map) {
        //使用entrySet
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Object> entry1 = it.next();
            System.out.println(entry1.getKey() + " ------- " + entry1.getValue());
        }
        //使用keySet
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = (String) map.get(key);
            System.out.println(key+"--------------"+value);
        }
    }
    /**
     * 四、Values 遍历方式
     *    只能获取value值
     * @param map
     */
    private static void testValues(Map<String, Object> map) {
        Collection<Object> collection =  map.values();
        for (Object s : collection) {
            System.out.println("value: " + s);
        }
        //使用lambda
//        map.values().forEach(name->{
//            System.out.println("value --------" +name);
//        });
    }
    /**
     * lambda 遍历方式
     * @param map
     */
    private static void testLambda(Map<String, Object> map) {
//        map.forEach((key, value) -> {
//            System.out.println(key + "  value: " + value );
//        });
    }
    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("A","测试1");
        map.put("B","测试2");
        map.put("C","测试3");
        testKeySet(map);
        System.out.println("-----------------------------------------");
        testEntrySet(map);
        System.out.println("-----------------------------------------");
        testIterator(map);
        System.out.println("-----------------------------------------");
        testValues(map);
        System.out.println("-----------------------------------------");
        testLambda(map);
    }

    //
    // 一般情况下使用foreach,如果在遍历集合的时候需要对对象集合中元素进行删除操作,尽量使用iterator,其自带的remove删除方式不会报错。

}
