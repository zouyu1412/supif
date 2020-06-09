package com.zouyu.examination;

import java.util.*;

/**
 * @Author:zouyu
 * @Date:2020/5/17 19:56
 */
public class GraphDemo {

    static class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }

    public static void main(String[] args) {

        Node node = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        List<Node> list = new ArrayList<>();
        list.add(node1);
        list.add(node2);
        list.add(node3);
        node.neighbors = list;

        Node node4 = new GraphDemo().cloneGraph(node);
        System.out.println(node4.val);

    }

    public Node cloneGraph(Node node) {
        Map<Node, Node> map = new HashMap<>();
        copy(map, node);
        Node res = null;
        Set<Node> visits = new HashSet();
        res = clone(node, map, visits);
        return res;
    }

    private Node clone(Node node, Map<Node, Node> map, Set<Node> visits) {
        if (node == null) {
            return null;
        }
        Node res = map.get(node);
        if(!visits.add(res)){
            return res;
        }
        if (res == null) {
            return null;
        }
        List<Node> newNodes = new ArrayList<>();
        if (node.neighbors != null && node.neighbors.size() > 0) {
            for (Node sub : node.neighbors) {
                Node tem = clone(sub, map, visits);
                newNodes.add(tem);
            }
        }
        res.neighbors = newNodes;
        return res;
    }

    private void copy(Map<Node, Node> map, Node node) {
        if (map.containsKey(node)) {
            return;
        }
        map.put(node, new Node(node.val));
        if (node.neighbors == null || node.neighbors.size() == 0) {
            return;
        }
        for (Node sub : node.neighbors) {
            copy(map, sub);
        }
    }
}
