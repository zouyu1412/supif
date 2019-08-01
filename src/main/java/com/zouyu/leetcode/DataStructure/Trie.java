package com.zouyu.leetcode.DataStructure;

class Trie {

    TrieNode root;
    static int maxSize = 26;

    private class TrieNode{
        char val;
        TrieNode[] son;
        boolean isEnd;
        public TrieNode() {
            son = new TrieNode[maxSize];
        }
    }

    /** Initialize your data structure here. */
    public Trie() {
        root = new TrieNode();
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        TrieNode node = root;
        for(int i=0;i<word.length();i++){
            char c = word.charAt(i);
            if(node.son[c-'a'] == null){
                node.son[c-'a'] = new TrieNode();
            }
            node = node.son[c-'a'];
            node.val = c;
            if(i == word.length() - 1){
                node.isEnd = true;
            }
        }
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        TrieNode node = root;
        for(int i=0;i<word.length();i++){
            char c = word.charAt(i);
            if(node.son[c-'a'] == null){
                return false;
            }
            if(i == word.length()-1 && node.son[c-'a'].isEnd){
                return true;
            }
            node = node.son[c-'a'];
        }
        return false;
    }
    
    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for(int i=0;i<prefix.length();i++) {
            char c = prefix.charAt(i);
            if(node.son[c-'a'] != null){
                node = node.son[c-'a'];
            }else{
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("app");
        trie.insert("apple");
        boolean apple = trie.search("app");
        System.out.println(apple);
    }
}
