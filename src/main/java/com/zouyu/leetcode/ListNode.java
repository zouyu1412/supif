package com.zouyu.leetcode;

public class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    public void pri(){
        System.out.print(val+" ");
        if(next != null){
            next.pri();
        }
    }
}