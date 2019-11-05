//package com.java.common.MapDemo;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.function.Predicate;
//import java.io.FileFilter;
//
///**
// * @author liudongting
// * @date 2019/8/15 11:56
// */
//public class demo_01 {
//
//   /**
//    *  lambda 在 java 的基本使用方式
//    */
//   private static void mapList(){
//
//      //在map集合下的使用
//      Map<String,Object> map =new HashMap<String, Object>();
//      map.put("A",1);
//      map.put("B",2);
//      //需要添加判断条件的
//      map.keySet().forEach(name ->{
//          if(name.startsWith("A")){
//             System.out.println(map.get(name));
//          }
//      });
//      System.out.println("-----------------------------------------");
//      //不需要判断条件
//      map.keySet().forEach(name ->{
//            System.out.println(map.get(name));
//      });
//      //方法引用由::双冒号操作符标示
//      System.out.println("-----------------------------------------");
//      map.keySet().forEach( System.out::println);
//
//      // list
//      List future = Arrays.asList("Great works are accomplished not by strength but by perseverance.","Keep its original intention and remain unchanged");
//      //lambda
//      future.forEach(n -> System.out.println(n));
//      //lambda 结合 方法引用
//      future.forEach(System.out::println);
//
//   }
//   public static void eval(List<Integer> list, Predicate<Integer> predicate) {
//      for(Integer n: list) {
//         if(predicate.test(n)) {
//            System.out.println(n + " ");
//         }
//      }
//      System.out.println("-----------------------------------------");
//      list.stream().filter((name) -> (predicate.test(name))).forEach((name) ->{
//         System.out.println(name);
//      });
//      System.out.println("-----------------------------------------");
//      list.stream().filter((name) -> (predicate.test(name))).forEach(System.out::println);
//
//   }
//
//   public static void main(String[] args) {
//      List<Integer> list = Arrays.asList(1, 12, 13, 14, 15, 6, 7, 8, 9);
//      // 传递参数 n
//      eval(list, n->true);
//      mapList();
//
//      List<String> lists = Arrays.asList("Great works are accomplished not by strength but by perseverance.","Keep its original intention and remain unchanged");
//
//      Predicate<String> starts = (name) -> name.startsWith("1");
//      Predicate<String> lengths = (name) -> name.length() == 4;
//      lists.stream().filter(starts.and(lengths)).forEach(System.out::println);
//
//
//      List<Integer> listss = Arrays.asList(new Integer[]{3,6,53,73});
//       int  a = 66;
//      listss.forEach(element -> { System.out.println(a);});
//
//   }
//}
