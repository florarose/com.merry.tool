package com.leetCode.study.Demo_2019_8_8_Linked.jichu.Double_pointer_technique;


public class resolve_01 {

    static ListNode head;

    public boolean hasCycle(ListNode listNode){
        resolve_01 resolve_01 =new resolve_01();
        int i =resolve_01.linkListLength();
        ListNode mm = resolve_01.get(i-1);
        if(mm.pos==null){
            return false;
        }
        return true;
    }
    public int  linkListLength() {
        int length = 0;
        //临时节点，从首节点开始
        ListNode temp = head;
        // 找到尾节点
        while (temp != null) {
            length++;
            temp = temp.pos;
        }
        return length;
    }
    public ListNode get(int index){

        int length =0 ;
        //从头节点开始
        ListNode temp = head;
        //如果结点不为空，判断该节点的索引是否和要求的索引一致。相同则返回，不同继续；
        while (temp != null) {
            if(length==index){
                return temp.pos;
            }
            length++;
            temp = temp.pos;
        }
        return null;
    }
}
