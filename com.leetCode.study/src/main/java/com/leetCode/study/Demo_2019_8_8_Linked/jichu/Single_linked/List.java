package com.leetCode.study.Demo_2019_8_8_Linked.jichu.Single_linked;

/**
 * 单链表的增删
 * @author liudongting
 * @date 2019/8/8 10:56
 */
public class List {

    static SinglyListNode head;

    /**
     *
     * addAtTail(val)
     * 将值为 val 的节点追加到链表的最后一个元素。
     */
    public void addAtTail(int val){
        SinglyListNode singlyListNode = new SinglyListNode(val);
        //如果头节点为空，添加到头节点
        if(head == null){
            head = singlyListNode;
            return;
        }
        //从头节点开始
        SinglyListNode temp = head;
        //从头节点开始判断，是否有下一个节点，找到最后一个节点
        while (temp.next != null){
            temp = temp.next;
        }
        //最后一个节点的指针指向新加入的节点
        temp.next = singlyListNode;
    }

    /**
     * 获取链表中第 index 个节点的值。如果索引无效，则返回-1。
     * @param index
     */
    public int get(int index){

      int length =0 ;
      //从头节点开始
      SinglyListNode temp = head;
        //如果结点不为空，判断该节点的索引是否和要求的索引一致。相同则返回，不同继续；
        while (temp != null) {
            if(length==index){
                return temp.val;
            }
            length++;
            temp = temp.next;
        }
      return -1;
    }

    /**
     * 在链表的第一个元素之前添加一个值为 val 的节点。插入后，新节点将成为链表的第一个节点。
     * @param val
     */

    public void  addAtHead(int val){
        SinglyListNode singlyListNode = new SinglyListNode(val);
        //如果头节点为空，添加到头节点
        if(head == null){
            head = singlyListNode;
            return;
        }
        //创建临时节点，保存头节点的值
        SinglyListNode temp = head;
        //新节点的值，给头结点
        head=singlyListNode;
        //新的头结点的下一个元素指向原来的头结点、
         head.next = temp;
    }

    /**
     *  在链表中的第 index 个节点之前添加值为 val  的节点。
     *  如果 index 等于链表的长度，则该节点将附加到链表的末尾。
     *  如果 index 大于链表长度，则不会插入节点。如果index小于0，则在头部插入节点。
     *
     *
     */
    public void addAtIndex(int index,int val){
        if(index<0){
            addAtHead(val);
            return;
        }
        if(linkListLength()==index){
            addAtTail(val);
            return;
        }
        if(index>linkListLength()){
            return;
        }
        SinglyListNode singlyListNode = new SinglyListNode(val);
        int length =0 ;
        //从头节点开始
        SinglyListNode temp = head;
        //如果结点不为空，判断该节点的索引是否和要求的索引一致。相同则返回，不同继续；
        while (temp != null) {
            if((length+1)==index){
                //index 前一个节点的指针指向的节点，即index处的节点
                SinglyListNode preNode = temp.next;
                //index 前一个节点的指针 指向 新的节点
               temp.next = singlyListNode;
               //新节点的下一个指针指向 index节点
               singlyListNode.next= preNode;
               return;
            }
            length++;
            temp = temp.next;
        }
    }

    /**
     * 获取链表的长度
     * @return
     */
    public int  linkListLength() {

        int length = 0;

        //临时节点，从首节点开始
        SinglyListNode temp = head;

        // 找到尾节点
        while (temp != null) {
            length++;
            temp = temp.next;
        }

        return length;
    }

    /**
     * 如果索引 index 有效，则删除链表中的第 index 个节点。
     * @param index
     */
    public void deleteAtIndex(int index){

        int length = 0;

        //临时节点，从首节点开始
        SinglyListNode temp = head;
        if(index==length){
            head=head.next;
        }
        // 找到尾节点
        while (temp != null) {
            if((index-1)==length){
                SinglyListNode indexNode =temp.next;
                SinglyListNode nextNode = indexNode.next;
                temp.next = nextNode;
                return;
            }
            length++;
            temp = temp.next;
        }

    }
    public static void main(String[] args) {
        List list = new List();
        list.addAtTail(2);
        list.addAtHead(3);
        list.addAtIndex(1,4);
        list.deleteAtIndex(0);
        System.out.println("222");
       System.out.println(" 获取链表的长度 list.linkListLength() : " +list.linkListLength());
        System.out.println(" 获取链表的某个元素 list.get(1) : " + list.get(1));
    }
}
