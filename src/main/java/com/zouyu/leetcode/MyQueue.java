package com.zouyu.leetcode;

import java.util.Stack;

/**
 * 用堆栈实现队列
 */
class MyQueue {

    Stack<Integer> stack1;
    Stack<Integer> stack2;

    /** Initialize your data structure here. */
    public MyQueue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }
    
    /** Push element x to the back of queue. */
    public void push(int x) {
        stack1.push(x);
    }
    
    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        while(stack1.size()>0 && stack1.peek()!= null){
            stack2.push(stack1.pop());
        }
        int re = stack2.pop();
        while(stack2.size()>0 && stack2.peek()!= null){
            stack1.push(stack2.pop());
        }
        return re;
    }
    
    /** Get the front element. */
    public int peek() {

        while(stack1.size()>0&&stack1.peek()!= null){
            stack2.push(stack1.pop());
        }
        int re = stack2.peek();
        while(stack2.size()>0&&stack2.peek()!= null){
            stack1.push(stack2.pop());
        }
        return re;
    }
    
    /** Returns whether the queue is empty. */
    public boolean empty() {
        return stack1.isEmpty();
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */