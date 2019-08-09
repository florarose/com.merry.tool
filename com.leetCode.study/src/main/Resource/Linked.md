## 链表
- 在大多数情况下，我们将使用头结点(第一个结点)来表示整个列表。
-  如果我们想要获得第 i 个元素，我们必须从头结点逐个遍历。 我们按索引来访问元素平均要花费 O(N) 时间，其中 N 是链表的长度。

## 实现如下功能 :

 - get(index)：获取链表中第 index 个节点的值。如果索引无效，则返回-1。
 - addAtHead(val)：在链表的第一个元素之前添加一个值为 val 的节点。插入后，新节点将成为链表的第一个节点。
 - addAtTail(val)：将值为 val 的节点追加到链表的最后一个元素。
 - addAtIndex(index,val)：在链表中的第 index 个节点之前添加值为 val  的节点。如果 index 等于链表的长度，则该节点将附加到链表的末尾。如果 index 大于链表长度，则不会插入节点。如果index小于0，则在头部插入节点。
 - deleteAtIndex(index)：如果索引 index 有效，则删除链表中的第 index 个节点。

## 代码如下：
### 一、定义单链表的节点
   ```
        package com.leetCode.study.Demo_2019_8_8_Linked;
             /**
              * 单链表的定义
              * @author liudongting
              * @date 2019/8/8 10:45
              */
             public class SinglyListNode {
             
             
                 //val 是当前节点的值
             
                 int val;
             
                 //next 是指向下一个节点的指针/引用
             
                 SinglyListNode next;
                 //带参构造函数
             
                 SinglyListNode(int x) { val = x; }
             
             }
    
   ```
### 二、链表
   ```
    package com.leetCode.study.Demo_2019_8_8_Linked;
    
    /**
     * @author liudongting
     * @date 2019/8/8 10:56
     */
    public class List {
    
        static  SinglyListNode head;
    
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
            // 添加元素
            list.addAtTail(2);
            //在头结点添加元素
            list.addAtHead(3);
            //在指定位置添加元素
            list.addAtIndex(1,4);
            //根据索引删除元素
            list.deleteAtIndex(2);
            //获取链表的长度
            list.linkListLength();
            System.out.println("222");
            System.out.println(" 获取链表的长度 list.linkListLength() : " +list.linkListLength());
            System.out.println(" 获取链表的某个元素 list.get(1) : " + list.get(2));
        }
    }

   ```