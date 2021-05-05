package com.leetCode.study.offer.二分查找;

import java.util.Scanner;

/**
 * @auther: 坎布里奇
 * @Version: 1.0.0V
 * @date: 2021/1/21 11:51
 * @Description: mm
 */
public class demo {

    /**
     * 非递归
     * @param arr 有序的列表
     * @param number
     * @return
     */
    public static  int noRecursive(int [] arr,int number){
        int start = 0;
        int end = arr.length-1;
        while(start <= end){
            int mid = start + (end - start)>>>1;
            //int mid = (start + end)/2;
            if(arr[mid] == number) {
                return mid;
            }
            else if(arr[mid] > number){
                end = mid -1;
            }else if(arr[mid] < number){
                start = mid + 1;
            }
        }
        return -1;
    }

    /**
     * 递归
     * 1. 明确函数的功能是什么；
     * 2. 明确函数结束的条件；
     * 3. 找出函数的等价关系式
     * [left,right]左闭右闭
     * @param arr 有序的列表
     * @param number
     * @return
     */
    public static  int recursive(int left,int right,int [] arr,int number){
        int mid=left+((right-left)>>>1);
        if(left > right){
            return -1;
        }
        if(arr[mid] == number) {
            return mid;
        }
        else if(arr[mid] > number){
            return recursive(left,mid-1,arr,number);
        }else if(arr[mid] < number){
            return recursive(mid + 1,right,arr,number);
        }
        return -1;
    }

    /**
     * 左闭右开
     * @param left
     * @param right
     * @param arr
     * @param number
     * @return
     */
    public static int leftRecursive(int left,int right,int [] arr,int number){
        int mid=left+((right-left)>>>1);
        if(left >= right){
            return -1;
        }
        if(arr[mid] == number) {
            return mid;
        }
        else if(arr[mid] > number){
            return leftRecursive(left,mid,arr,number);
        }else if(arr[mid] < number){
            return leftRecursive(mid + 1,right,arr,number);
        }
        return -1;
    }
    /**
     * 右闭左开
     * @param left
     * @param right
     * @param arr
     * @param number
     * @return
     */
    public static int rightRecursive(int left,int right,int [] arr,int number){
        int mid=left+((right-left)>>>1);
        if(left >= right){
            return -1;
        }
        if(arr[mid] == number) {
            return mid;
        }
        else if(arr[mid] > number){
            return rightRecursive(left,mid,arr,number);
        }else if(arr[mid] < number){
            return rightRecursive(mid,right,arr,number);
        }
        return -1;
    }
    /**
     * 测试两个int 相加，溢出的问题
     */
    public static  void test(){
        Scanner scan=new Scanner(System.in);
        System.out.println("请输入两个int类型的数：");
        int a=scan.nextInt();
        int b=scan.nextInt();
//        int avg=(a+b)/2;
        int c = Math.abs((b-a))>>>1;
        System.out.println(c);
        int avg=a+c;
        System.out.println("平均数是："+avg);
    }

    public static void main(String[] args) {
        test();
    }
}
