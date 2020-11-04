package com.leetCode.study.offer.二维数组中的查找;

import java.util.Arrays;
import java.util.Collections;

/**
 * @auther: 坎布里奇
 * @Version: 1.0.0V
 * @date: 2020/7/23 11:04
 * @Description: mm
 */
public class Demo {


    /**
     * 一、
     * 暴力解决
     * 1. 首先定义一个二维数组,筛入数据；
     *
     */
    public static void test1(int [][] arg){

          for (int i=0;i<arg.length;i++){
              Arrays.asList(arg);
              for(int j=0;j<arg[i].length;j++){
                  System.out.println( Collections.binarySearch(Arrays.asList(arg[i][j]),1));
//                  System.out.println(arg[i][j]+"\t");
              }
          }

    }

    public static void main(String[] args) {
        int [] [] intA ={{1,2,8,9},{2,4,9,12},{4,7,10,13},{6,8,11,15}};
        test1(intA);
    }

}
