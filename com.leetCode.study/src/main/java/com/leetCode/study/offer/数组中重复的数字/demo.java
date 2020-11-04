package com.leetCode.study.offer.数组中重复的数字;

import java.util.Collections;

/**
 * @auther: 坎布里奇
 * @Version: 1.0.0V
 * @date: 2020/9/27 8:52
 * @Description: mm
 */
public class demo {

    /**
     * 方法一
     * @param arg
     * @return
     */
    public static Integer [] distrctArray(Integer [] arg){
        Integer [] res = new Integer[arg.length];
        for (int x=0;x<arg.length;x++){
            Integer tmp=arg[x];
            for (int i=x+1;i<arg.length;i++){
                if(arg[i] == tmp){
                    res[x]=arg[i];
                }
            }
        }
        return res;
    }

    /**
     *  方法二 biredbee@163.com
     *  方法一，不满足 时间复杂度 O(N)，空间复杂度 O(1)，。因此不能使用排序的方法，也不能使用额外的标记数组。
     *  思路:
     * 对于这种数组元素在 [0, n-1] 范围内的问题，可以将值为 i 的元素调整到第 i 个位置上进行求解。本题要求找出重复的数字，因此在调整过程中，如果第 i 位置上已经有一个值为 i 的元素，就可以知道 i 值重复。
     * 以 (2, 3, 1, 0, 2, 5) 为例，遍历到位置 4 时，该位置上的数为 2，但是第 2 个位置上已经有一个 2 的值了，因此可以知道 2 重复：
     * @param nums
     * @param length
     * @param duplication
     * @return
     */
    public boolean duplicate(int[] nums, int length, int[] duplication) {
        if (nums == null || length <= 0)
            return false;
        for (int i = 0; i < length; i++) {
            while (nums[i] != i) {
                if (nums[i] == nums[nums[i]]) {
                    duplication[0] = nums[i];
                    return true;
                }
                swap(nums, i, nums[i]);
            }
        }
        return false;
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    public static void main(String[] args) {
        Integer [] param={2,3,1,0,2,5,3};
        Integer [] res = distrctArray(param);
        System.out.println("重复的数字为:");
        for (Integer s: res) {
            System.out.println(s);
        }
    }

    /**
     * mount /mnt/Ainance/
     * (base) root@ainance-one:/home/ainance# more /etc/fstab
     */
}
