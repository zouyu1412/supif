package com.zouyu.examination;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:zouyu
 * @Date:2020/5/15 10:44
 */
public class MyExamLruCache {

    class Node{
        int key;
        Object value;
        Node pre;
        Node next;
        public void Node(int key, Object val){
            this.key = key;
            this.value = val;
        }
    }

    private Map<Integer, Object> cache;
    private Node head;
    private Node tail;
    private Integer capacity;
    private static Integer DEFAULT_CAPACITY = 1<<4;
    private Integer usedCapacity;

    public void MyExamLruCache(Integer capacity){
        this.capacity = capacity;
        this.usedCapacity = 0;
        cache = new HashMap<>();
    }

    public boolean insert(int key, Object val){
        return true;
    }

    public static void main(String[] args) {
        System.out.println(DEFAULT_CAPACITY);
    }


}
