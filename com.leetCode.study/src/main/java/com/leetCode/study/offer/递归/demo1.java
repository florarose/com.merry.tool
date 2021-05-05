package com.leetCode.study.offer.递归;

/**
 * 递归
 * 递归三要素：
 *  1、定义函数功能；
 *  2、递归结束条件;
 *  3、通项公式；即共有规律
 * @auther: 坎布里奇
 * @Version: 1.0.0V
 * @date: 2021/1/25 7:49
 * @Description: mm
 */
public class demo1 {

    /**
     * 斐波那契数列(Fibonacci sequence),又称黄金分割数列,也称为兔子数列。
     * 最高支持 n = 92 ，否则超出 Long.MAX_VALUE
     * 斐波那契数列的是这样一个数列：1、1、2、3、5、8、13、21、34....，
     * 即第一项 f(1) = 1,第二项 f(2) = 1.....,第 n 项目为 f(n) = f(n-1) + f(n-2)。求第 n 项的值是多少。
     * @param n
     * @return
     */
    public static long test1(int n){
        if(n <= 0){
            return 0;
        }
        if(n <= 2){
            return 1;
        }
        return test1(n-1)+test1(n-2);
    }

    /**
     * 小青蛙跳台阶
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
     * @param n
     * @return
     */
    public static int test2(int n){
        if(n == 1){
            return 1;
        }
        if(n==2){
            return 2;
        }
        return test2(n-1)+test2(n-2);
    }

    /**
     * 反转单链表
     * 反转单链表。例如链表为：1->2->3->4。反转后为 4->3->2->1
     */
    public static void test3(){

    }
    class Node{
        int data;
        Node next;
    }
    // 题目 https://www.zhihu.com/question/31412436列表
    // 需查询问题:
    //  单链表和内部类的使用
    public static void main(String[] args) {
//        System.out.println(test1(50));
        System.out.println(test2(4));
    }
}
