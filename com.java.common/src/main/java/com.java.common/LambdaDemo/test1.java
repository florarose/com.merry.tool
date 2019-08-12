package com.java.common.LambdaDemo;

import java.util.Arrays;
import java.util.List;

/**
 * @author liudongting
 * @date 2019/8/12 17:07
 */
public class test1 {
    public static void main(String[] args) {
//        List<String> list = Arrays.asList("a","b","b");
//        //foreach
//        for (String  s : list
//                ) {
//            System.out.println(s);
//        }
//        System.out.println("-----------------");
//        //lambda  用Iterable.forEach()取代foreach loop
//        list.forEach((e) -> System.out.println(e));

        List<String> list = Arrays.asList ("a", "c", "A", "C");
        list.sort ((s1, s2) -> s1.compareToIgnoreCase (s2));
        list.sort (String::compareToIgnoreCase);
        System.out.println (list);
    }
}
