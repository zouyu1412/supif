package com.zouyu.leetcode.DataStructure;

import java.util.PriorityQueue;

/**
 * addNum(1)
 * addNum(2)
 * findMedian() -> 1.5
 * addNum(3)
 * findMedian() -> 2
 * 快速中位数 查找
 */
public class MedianFinder {

    private PriorityQueue<Integer> asq;
    private PriorityQueue<Integer> desq;

    /** initialize your data structure here. */
    public MedianFinder() {
        asq = new PriorityQueue<>((a,b)->a-b);
        desq = new PriorityQueue<>((a,b)->b-a);
    }
    
    public void addNum(int num) {
        asq.offer(num);
        desq.offer(asq.poll());
        if(desq.size()>asq.size()){
            asq.offer(desq.poll());
        }
    }

    public double findMedian() {
        if((asq.size()+desq.size())%2 == 1){
            return asq.peek();
        }else {
            return (asq.peek()+desq.peek())/2.0;
        }
    }
}