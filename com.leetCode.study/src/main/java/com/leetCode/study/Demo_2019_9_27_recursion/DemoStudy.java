package com.leetCode.study.Demo_2019_9_27_recursion;

/**
 * 递归
 * @author liudongting
 * @date 2019/8/27 19:02
 */
public class DemoStudy {

    public static void main(String[] args) {
        int a= rsSum(2);
        System.out.println(a);
        System.out.println("--------------");
        int b = rsChengji(7);
        System.out.println(b);
        System.out.println("--------------");
        int c = yazi(2,7);
        System.out.println(c);
        System.out.println("--------------");
        int d = jiaogu(4,0);
        System.out.println(d);


    }

    /**
     *  递归求和
     * @param n
     * @return
     */
    public static  int rsSum(int n){
        if(n>0){
            return  n+rsSum(n-1);
        }else {
            return 0;
        }
    }

    /**
     * 递归阶乘  n! = n*(n-1)*(n-2)*...*1
     * @param n
     * @return
     */
    public static int rsChengji(int n){
        if(n>=1){
            return n*rsChengji(n-1);
        }else {
            return 1;
        }
    }

    /**
     *   * 题意：一个人赶着鸭子去每个村庄卖，每经过一个村子卖去所赶鸭子的一半又一只。
     *   * 这样他经过了七个村子后还剩两只鸭子，问他出发时共赶多少只鸭子？经过每个村子卖出多少只鸭子？
     * @return
     */
    public static  int yazi(int n,int i){
        n = (n+1)*2;
        if(i>0){
            System.out.println("i:"+i+"---"+n);
            return yazi(n,--i);
        }else {
            return n;
        }
    }

    /**
     * 角谷定理。输入一个自然数，若为偶数，则把它除以2，若为奇数，则把它乘以3加1。经过如此有限次运算后，总可以得到自然数值1。
     * 求经过多少次可得到自然数1。
     * @return
     */
     public static  int jiaogu(int num,int count){
        if(num==1){
            return count;
        }else {
            if(num%2==0){
                return  jiaogu(num/2,++count);
            }else {
                return  jiaogu(num*3+1,++count);
            }
        }
     }
}
