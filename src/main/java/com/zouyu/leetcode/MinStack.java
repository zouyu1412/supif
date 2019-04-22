package com.zouyu.leetcode;

import java.util.LinkedList;
import java.util.PriorityQueue;

class MinStack {

    /**
     * initialize your data structure here.
     *
     *  push(x) -- Push element x onto stack.
     *  pop() -- Removes the element on top of the stack.
     *  top() -- Get the top element.
     *  getMin() -- Retrieve the minimum element in the stack.
     */
    private LinkedList<Integer> list;
    private PriorityQueue<Integer> queue;

    public MinStack() {
        list = new LinkedList<>();
        queue = new PriorityQueue<>((o1,o2)->o1.compareTo(o2));
    }
    
    public void push(int x) {
        list.addFirst(x);
        queue.offer(x);
    }
    
    public void pop() {
        Integer integer = list.removeFirst();
        if(integer.equals(queue.peek())){
            queue.poll();
        }
    }
    
    public int top() {
        return list.getFirst();
    }
    
    public int getMin() {
        return queue.peek();
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(512);
        minStack.push(-1024);
        minStack.push(-1024);
        minStack.push(512);
        minStack.pop();
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.getMin());
//        minStack.push(12124);
//        minStack.push(2);
//        System.out.println(minStack.queue.size());
//        minStack.pop();
//        System.out.println(minStack.queue.peek());
//        minStack.pop();
//        System.out.println(minStack.queue.peek());

    }
}