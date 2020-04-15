package com.java.common.LambdaDemo;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liudongting
 * @date 2019/8/12 11:44
 */
public class listLambda {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a","b","b");
        //foreach
        for (String  s : list
             ) {
            System.out.println(s);
        }
        System.out.println("-----------------");
        //lambda
        list.forEach((e) -> System.out.println(e));
        //
        System.out.println(list.stream().filter((e) -> "b".equals(e)).count());

        test9();
        test10();
    }
    //聚合
    public static void test6(){
        System.out.println("=====reduce:将流中元素反复结合起来，得到一个值==========");
        Stream<Integer> stream = Stream.iterate(1, x -> x+1).limit(200);
        //stream.forEach(System.out::println);
        Integer sum = stream.reduce(10,(x,y)-> x+y);
        System.out.println(sum);
    }
     public static  void test7(){
         System.out.println("-----------------");
         List<String> numbers = Arrays.asList("b","a","b");
         numbers.stream().limit(1).forEach(System.out::println);
         System.out.println("-----------------");
         numbers.stream().skip(1).forEach(System.out::println);
         System.out.println("-----------------");
         numbers.stream().sorted().forEach(System.out::println);
         System.out.println("-----------------");
         Arrays.asList(3, 2, 1, 4, 5, 8, 6).stream().sorted((x,y) -> y.compareTo(x)).forEach(System.out::println);
         System.out.println("-----------------");
         numbers.stream().forEach(System.out::println);
         System.out.println("-----------------");

     }
    /**
     *  流返回各种类型
     */
     public static void test8(){
         System.out.println("=====list");
        List<Integer> list = Stream.iterate(1, x -> x+1).limit(1000).collect(Collectors.toList());
        System.out.println(list);
        System.out.println("=====set");
        Set<Integer> set = Arrays.asList(10, 10, 21, 20, 31, 30, 3).stream().collect(Collectors.toSet());
        System.out.println(set);
        System.out.println("=====TreeSet");
        TreeSet<Integer> treeSet = Arrays.asList(71, 81, 26, 27, 35, 35, 36).stream().collect(Collectors.toCollection(TreeSet::new));
        System.out.println(treeSet);
        System.out.println("=====map");
       Map<Integer, Integer> map = Stream.iterate(3, x -> x+1).limit(1000).collect(Collectors.toMap(Integer::intValue, Integer::intValue));
        System.out.println(map);
        System.out.println("=====sum");
        Integer sum = Stream.iterate(3, x -> x+1).limit(1000).collect(Collectors.summingInt(Integer::intValue));
        System.out.println(sum);
        System.out.println("=====avg");
        Double avg = Stream.iterate(5, x -> x+1).limit(1002).collect(Collectors.averagingInt(Integer::intValue));
        System.out.println(avg);
         List<lambdaDemo> lambdaDemos = Arrays.asList(new lambdaDemo("zhang","黑人",1),
                 new lambdaDemo("li","黑人",2),
                 new lambdaDemo("liu","白人",3));
         Map<Integer,String> maps =   lambdaDemos.stream().collect(Collectors.toMap(lambdaDemo::getAge,lambdaDemo::getName));
         System.out.println(maps);
     }

    /**
     * 分组和分区
     */
    public static  void test9() {
        List<lambdaDemo> lambdaDemos = Arrays.asList(new lambdaDemo("zhang","黑人",65),
                new lambdaDemo("li","黑人",40),
                new lambdaDemo("liu","白人",40));
        System.out.println("=======根据人的肤色进行分组==========================");
        Map<String, List<lambdaDemo>> map = lambdaDemos.stream().collect(Collectors.groupingBy(lambdaDemo::getName));
        System.out.println(map);
        System.out.println("=======根据人的年龄范围多级分组==========================");
        Map<Integer, Map<String, List<lambdaDemo>>> map2 = lambdaDemos.stream().collect(Collectors.groupingBy(lambdaDemo::getAge,
                Collectors.groupingBy(
                   ( p ) -> {
                        if ( p.getAge() > 60 ) {
                            return "老年人";
                        } else {
                            return "年轻人";
                        }
                   }
                )
        ));
        System.out.println(map2);
    }
    public static void test10()  {
        List<lambdaDemo> lambdaDemos = Arrays.asList(new lambdaDemo("zhang","黑人",60),
                new lambdaDemo("li","黑人",2),
                new lambdaDemo("liu","白人",3));
        System.out.println("========根据人的年龄是否大于40进行分区========================");
        Map<Boolean, List<lambdaDemo>> map = lambdaDemos.stream().collect(Collectors.partitioningBy(p -> p.getAge() > 40));
        System.out.println(map);
    }

    public void stream(){
        List<lambdaDemo> lambdaDemos = Arrays.asList(new lambdaDemo("zhang","bing",1),
                new lambdaDemo("li","hua",2));
        Map<Integer,String> maps =   lambdaDemos.stream().collect(Collectors.toMap(lambdaDemo::getAge,lambdaDemo::getName));
        System.out.println(maps);
    }
    public static  Map<Integer,lambdaDemo> check(List<lambdaDemo> lambdaDemos){
        return   lambdaDemos.stream().collect(Collectors.toMap(lambdaDemo::getAge, Function.identity(),
                (existing, replacement) -> existing));
    }
}
