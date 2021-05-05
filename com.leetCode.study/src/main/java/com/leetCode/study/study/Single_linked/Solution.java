package com.leetCode.study.study.Single_linked;

/**
 * @author ldt merry
 * @date 2019/11/7
 */
public class Solution {

    static  ListNode head;

    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
        Solution getSD() {
                   System.out.println("getMyClass..");
                   return Solution.this;
                }
    }

    ListNode getListNode(int i){
        return new ListNode(i);
    }
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast.next != null && fast.next.next != null) {
            if (slow == fast) {
                return true;
            }
            slow= slow.next;
            fast =fast.next.next;
        }
        return slow == fast;
    }
    public static void main(String[] args) {
        Solution solution = new Solution();
        ListNode heads=  solution.getListNode(0);
        System.out.println(solution.hasCycle(heads));
    }
}
