package com.leetCode.study.Demo_2019_6_11;

public class Node2 {

    private int data;
    private Node2 next;

    public Node2(){

    }
    public Node2(int data){
        this.data = data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node2 getNext() {
        return next;
    }

    public void setNext(Node2 next) {
        this.next = next;
    }

    public boolean hasNext(){
        return this.next == null ? false : true;
    }
    //链表增加
    public  void addNode(Node2 node){
        Node2 temp = this;
        while (temp.hasNext()){
            temp = temp.next;
        }
        temp.next = node;
    }
}
