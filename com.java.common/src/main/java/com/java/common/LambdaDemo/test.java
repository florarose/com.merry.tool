package com.java.common.LambdaDemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author liudongting
 * @date 2019/8/12 11:22
 */
public class test {



    interface  NameChecker{
        boolean check(lambdaDemo l);
    }
    interface  Executor{
        void execute(lambdaDemo l);
    }

//    public static void checkAndExecute(List<lambdaDemo> lambdaDemos,
//                                       NameChecker nameChecker,
//                                       Executor executor){
//        for(lambdaDemo lambdaDemo :lambdaDemos){
//            if(nameChecker.check(lambdaDemo)){
//                executor.execute(lambdaDemo);
//            }
//        }
//    }
//    public static void checkAndExecute2(List<lambdaDemo> lambdaDemos,
//                                       Predicate<lambdaDemo> predicate,
//                                       Consumer<lambdaDemo> consumer){
//        lambdaDemos.forEach(l->{if(predicate.test(l))consumer.accept(l);});
//    }
    public static void main(String[] args) {
//        List<lambdaDemo> lambdaDemoList = Arrays.asList(
//                new lambdaDemo("zhang","li",20),
//                new lambdaDemo("li","hua",33)
//        );
//        lambdaDemo l = new lambdaDemo();
//        checkAndExecute(lambdaDemoList,l->l.getName().startsWith("h"),
//                l->System.out.println(l.getFirstName()));
    }
}
