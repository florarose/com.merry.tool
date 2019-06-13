package com.leetCode.study.Demo_2019_6_11;

public class SumTwoNmbers {

    public static void main(String[] args) {
         Node2 head = new Node2(2);
        System.out.println("head1:"+head.getData());
        Node2 head1 = new Node2(3);
         head.addNode(head1);
        System.out.println("head : "+head.getData()+" _______head1_________"+head.getNext().getData());
        /*-----------------------------------*/
        LinkList linkList = new LinkList();
        linkList.addData(4);
        linkList.addData(3);
        linkList.addData(6);
        System.out.println("length :" + linkList.linkListLength());
        int [] arr = new int[linkList.linkListLength()+1];
        for (int i = 1 ; i <arr.length; i ++) {
            if(LinkList.head != null){
                arr[i]=LinkList.head.data;
                System.out.println("ggg: "+ LinkList.head.data + "--"+(i));
                LinkList.head = LinkList.head.next;
            }
        }
        LinkList linkList2 = new LinkList();
        linkList2.addData(5);
        linkList2.addData(9);
        linkList2.addData(0);
        System.out.println("length :" + linkList2.linkListLength());
        int [] arrs = new int[linkList2.linkListLength()+1];
        for (int i =1 ; i <arrs.length; i++) {
            if(LinkList.head != null){
                arrs[i]=LinkList.head.data;
                System.out.println("ggg: "+ LinkList.head.data + "--"+(i));
                LinkList.head = LinkList.head.next;
            }
        }
        int a = 0;
        for (int i = 1; i < arr.length; i++) {
            for (int j = i ; j < arrs.length ;) {
                if(arr[i] + arrs[j]+a>9){
                    arr[i-1] = (arr[i] + arrs[j]+a) % 10;
                    a =  ( arr[i] + arrs[j]+a)/10;
                }else {
                    arr[i-1] = arr[i] + arrs[j]+a;
                    if(arr[i-1]>9){
                        arr[i-1] = arr[i-1]%10;
                        a = arr[i-1]/10;
                        if(i+1== arr.length){
                            arr[i]=a;
                        }
                    }else {
                        arr[i]=0;
                    }
                }
                break;
            }
        }
        System.out.println("-----------------------------------");
        /*-------------------------------------------------*/
        for (int i = 0; i <arr.length ; i++) {
            System.out.println("arr : " + arr[i] +"--"+i);
        }
    }

}
