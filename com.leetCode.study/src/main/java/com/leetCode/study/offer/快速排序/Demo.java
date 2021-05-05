package com.leetCode.study.offer.快速排序;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther: 坎布里奇
 * @description: xxxxx
 * @date: 2021/4/1 14:04
 * @version: 1.0.0
 */
public class Demo {

    /**
     *  快速排序的原理： 选择一个关键值作为基准值。 比基准值小的都在左边序列(一般是无序的),比基准值大的都在右边(一般是无序的)。一般选择序列的第一个元素。
     *
     *  一次循环： 从后往前比较，用基准值和最后一个值比较，如果比基准值小的交换位置，如果没有继续比较下一个，直到找到第一个比基准值小的值才交换。
     *            从前往后比较，如果有比基准值大的，交换位置，如果没有继续比较下一个，直到找到第一个比基准值大的值才交换。
     *            直到从前往后的比较索引 > 从后往前比较的索引，结束第一次循环，此时，对于基准值来说，左右两边就是有序的了。
     *
     *  快速排序是一个不稳定排序算法，那么到底什么才是排序的稳定性呢，我认为通俗的讲有两个相同的数A和B，在排序之前A在B的前面，而经过排序之后，B跑到了A的前面，对于这种情况的发生，我们管他叫做排序的不稳定性，而快速排序在对存在相同数进行排序时就有可能发生这种情况。
     *
     * 最优情况:每一次的flag刚好都可以平分整个数组，此时的时间复杂度为O(nlogn)
     *
     *      最坏情况:每一次的flag刚好都是最大或者最小的数，此时的时间复杂度为O(n2)
     *
     *      平均情况:经过推到平均情况为O(nlogn)
     */

    public static void main(String[] args) {

//          int [] b = {1,2,34,71,11,33};
//          sort(b,0,b.length - 1);
//            System.out.println("排序后:");
//            for (int i : b) {
//                System.out.println(i);
//            }
            Map<String,Object> map = new HashMap<>();
            map.put("A",111);
            map.put("B",222);
            testMap(map);
    }

    public static int partition(int [] array, int low, int high){
        int key;
        key = array[low];
        while (low < high){
            while (low < high && array[high] >= key) high--;
            array[low] = array[high];
            while (low < high && array[low] <= key) low++;
            array[high] = array[low];
        }
        array[low] = key;
        return low;
    }
    public static void sort(int [] a, int low,int high){
        if(low < high){
            int key = partition(a,low,high);
            sort(a,low,key -1);
            sort(a,key + 1,high);
        }
    }

    /**
     * 85.784957
     */
    private static void testMap(Map<String,Object> map){
        map.forEach((key,value) -> {
            System.out.println(key + "---------" + value);
        });

        map.entrySet().iterator();
    }

}
