package com.zouyu.leetcode;

public class ListNode {
    public int val;
    public ListNode next;

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