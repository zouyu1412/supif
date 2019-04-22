package com.zouyu.leetcode;

import java.util.HashMap;
import java.util.Map;

class LRUCache {

    Map<Integer, Node> keys;
    int cap, cnt;
    Node head, tail;
    public LRUCache(int capacity) {
        keys = new HashMap<Integer, Node>();
        cap = capacity; cnt = 0;
        head = new Node(0,0); tail = new Node(0,0);
        head.next = tail; tail.prev = head;
    }

    private void add(Node node, int key) {
        node.prev = head;
        node.next = head.next;
        head.next = node;
        node.next.prev = node;
        if (key!=0) {keys.put(key, node); cnt++;}
    }

    private void update(Node node, int val) {
        if (val!=0) node.val = val;
        node.prev.next = node.next;
        node.next.prev = node.prev;
        add(node, 0);
    }

    private void evict() {
        keys.remove(tail.prev.key);
        tail.prev = tail.prev.prev;
        tail.prev.next = tail;
    }

    public int get(int key) {
        if (!keys.containsKey(key)) return -1;
        update(keys.get(key), 0);
        return keys.get(key).val;
    }

    public void put(int key, int value) {
        if (!keys.containsKey(key)) {
            add(new Node(key, value), key);
            if (cnt > cap) evict();
        }
        else update(keys.get(key), value);
    }

    class Node {
        int key, val;
        Node prev, next;
        public Node(int key, int val) {this.key=key; this.val=val;}
    }

}