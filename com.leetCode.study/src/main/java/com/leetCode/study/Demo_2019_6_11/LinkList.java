package com.leetCode.study.Demo_2019_6_11;

public class LinkList {

    static Node head;

    static  class  Node{
        //数据域
        public int data ;

        //指针，指向下一个节点
        public Node next ;

        public Node(int data){
            this.data = data;
        }
    }

    public static Node getHead() {
        return head;
    }

    public static void setHead(Node head) {
        LinkList.head = head;
    }

    public void addData(int date) {

        Node node = new Node(date);

        //如果头节点为空，添加到头节点
        if(head == null){
            head = node;
            return;
        }
        //创建临时节点
        Node temp = head;

        //寻找尾节点
        while (temp.next != null){
            temp = temp.next;
        }

        temp.next = node;
    }

    public void traverse(Node node){
        //创建临时节点
        Node temp = node;
        while (temp != null){
            System.out.println("节点 ：" +temp.data);
            temp =temp.next;
        }
    }
    public int  linkListLength() {

        int length = 0;

        //临时节点，从首节点开始
        Node temp = head;

        // 找到尾节点
        while (temp != null) {
            length++;
            temp = temp.next;
        }

        return length;
    }

    public  void printLink(Node head){
        if(head != null){
           printLink(head.next);
        }
    }
    public void traverse(){
        traverse(head);
    }

    /**
     * 通过递归从尾到头输出单链表
     *
     * @param head 头节点
     */
    public  void printListReversely(Node head) {
        if (head != null) {
            printListReversely(head.next);
            System.out.println("节点："+head.data);
        }
    }

    /**
     * 实现链表的反转
     *
     * @param node 链表的头节点
     */
    public static Node reverseLinkList(Node node) {

        Node prev;
        if (node == null || node.next == null) {
            prev = node;
        } else {
            Node tmp = reverseLinkList(node.next);
            node.next.next = node;
            node.next = null;
            prev = tmp;
        }
        return prev;
    }
    }
