package com.zouyu.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 队列实现堆栈
 */
class MyStack {

    Queue queue1;
    Queue queue2;

    /** Initialize your data structure here. */
    public MyStack() {
        queue1 = new LinkedList();
        queue2 = new LinkedList();
    }
    
    /** Push element x onto stack. */
    public void push(int x) {
        queue1.offer(x);
    }
    
    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        int tem;
        while(queue1.size()>0){
            tem = (int)queue1.poll();
            if(queue1.size()==0){
                queue1 = new LinkedList(queue2);
                queue2.clear();
                return tem;
            }
            queue2.offer(tem);
        }
        return 0;
    }
    
    /** Get the top element. */
    public int top() {
        int tem;
        while(queue1.size()>0){
            tem = (int)queue1.poll();
            queue2.offer(tem);
            if(queue1.size()==0){
                queue1 = new LinkedList(queue2);
                queue2.clear();
                return tem;
            }
        }
        return 0;
    }
    
    /** Returns whether the stack is empty. */
    public boolean empty() {
        return queue1.isEmpty();
    }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */