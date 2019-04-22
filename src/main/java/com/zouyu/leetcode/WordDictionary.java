package com.zouyu.leetcode;

/**
 * 字典 区别于字典树Trie
 * addWord("bad")
 * addWord("dad")
 * addWord("mad")
 * search("pad") -> false
 * search("bad") -> true
 * search(".ad") -> true
 * search("b..") -> true
 */
class WordDictionary {
    class TrieNode {
        private final int R = 26;
        private TrieNode[] links = new TrieNode[R];
        boolean isEnd = false;

        public void setEnd(boolean end) {
            isEnd = end;
        }

        public boolean isEnd() {
            return isEnd;
        }

        public boolean contain(char character) {
            return links[character - 'a'] != null;
        }

        public void put(char character, TrieNode node) {
            links[character - 'a'] = node;
        }

        public TrieNode get(char character) {
            return links[character - 'a'];
        }

        public TrieNode[] getLinks() {
            return links;
        }
    }

    private TrieNode root;

    /**
     * Initialize your data structure here.
     */
    public WordDictionary() {
        root = new TrieNode();
    }

    /**
     * Adds a word into the data structure.
     */
    public void addWord(String word) {
        if (word == null || word.length() == 0) {
            return;
        }
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char character = word.charAt(i);
            if (!node.contain(character)) {
                node.put(character, new TrieNode());
            }
            node = node.get(character);
        }
        node.setEnd(true);
    }

    /**
     * Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter.
     */
    public boolean search(String word) {
        if (word == null || word.length() == 0) {
            return true;
        }
        return search(root, word);
    }

    private boolean search(TrieNode node, String word) {
        if (word.length() == 0) {
            return node != null && node.isEnd();
        }
        if (word.charAt(0) != '.') {
            if (node.contain(word.charAt(0))) {
                return search(node.get(word.charAt(0)), word.substring(1));
            } else {
                return false;
            }
        } else {
            for (TrieNode childNode : node.getLinks()) {
                if (childNode != null) {
                    if (search(childNode, word.substring(1))) {
                        return true;
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            }
            return false;
        }
    }
}