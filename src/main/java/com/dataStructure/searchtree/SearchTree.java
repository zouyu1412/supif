package com.dataStructure.searchtree;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;

public class SearchTree implements Serializable {

    private STreeNode root;

    public SearchTree() {
        root = new STreeNode();
    }

    /**
     * 插入节点
     *
     * @param keyWord
     * @param url
     */
    public void insert(String keyWord, String url) {
        if (StringUtils.isEmpty(keyWord)) {
            return;
        }
        String wordUrl = getWordUrl(keyWord);
        if (wordUrl != null) {
            if (wordUrl.equals(url)) {
                //已存在相同的关键字及其url，直接返回
                return;
            } else {
                //进行更新，先删除之前的keyWord
                delete(keyWord);
            }
        }
        root.setCount(root.getCount() + 1);
        STreeNode node = root;
        StringBuilder temPath = new StringBuilder();
        for (int i = 0; i < keyWord.length(); i++) {
            char curValue = keyWord.charAt(i);
            STreeNode son = node.getSonByValue(curValue);
            if (son == null) {
                STreeNode curNode = new STreeNode(temPath.toString(), curValue);
                node.getSon().add(curNode);
                node = curNode;
            } else {
                son.setCount(son.getCount() + 1);
                node = son;
            }
            temPath.append(curValue);
        }
        node.setEnd(true);
        node.setUrl(url);
    }

    /**
     * 删除节点
     *
     * @param keyWord
     */
    private boolean delete(String keyWord) {
        if (StringUtils.isEmpty(keyWord)) {
            return false;
        }
        String wordUrl = getWordUrl(keyWord);
        if (StringUtils.isEmpty(wordUrl)) {
            return false;
        }
        //到这说明 一定存在keyWord关键字
        int len = keyWord.length();
        STreeNode node = root;
        for (int i = 0; i < len; i++) {
            char curVal = keyWord.charAt(i);
            STreeNode son = node.getSonByValue(curVal);
            if (son == null) {
                return false;
            } else {
                int curCount = son.getCount() - 1;
                if (curCount == 0) {
                    node.getSon().remove(son);
                    root.setCount(root.getCount() - 1);
                    return true;
                } else {
                    son.setCount(curCount);
                    node = son;
                }
            }
        }
        node.setEnd(false);
        node.setUrl(null);
        root.setCount(root.getCount() - 1);
        return true;
    }

    private boolean recursion(STreeNode curNode, String keyWord, int ind) {
        return true;
    }

    public boolean containsChar(char startChar) {
        return root.getSonByValue(startChar) == null ? false : true;
    }

    public String getWordUrl(String word) {
        if (StringUtils.isEmpty(word)) {
            return null;
        }
        STreeNode node = root;
        for (int i = 0; i < word.length(); i++) {
            char curValue = word.charAt(i);
            STreeNode son = node.getSonByValue(curValue);
            if (son == null) {
                return null;
            } else {
                node = son;
            }
        }
        if (node.isEnd()) {
            return node.getUrl();
        }
        return null;
    }

    /**
     * 从content中找count个关键字 最短优先匹配原则
     *
     * @param content
     * @param count
     * @return
     */
    public List<KeyWordLocation> analyzeFromString(String content, int count) {
        if (StringUtils.isEmpty(content) || count == 0) {
            return Collections.emptyList();
        }
        List<KeyWordLocation> result = new ArrayList<>();
        for (int i = 0; i < content.length() && count > 0; i++) {
            char temChar = content.charAt(i);
            //过滤html标签
            if (temChar == '<') {
                char newTem = content.charAt(++i);
                while (newTem != '>') {
                    newTem = content.charAt(++i);
                }
                continue;
            }
            STreeNode node = root.getSonByValue(temChar);
            if (node != null) {
                if (node.isEnd()) {   //单字 成关键词
                    result.add(new KeyWordLocation(i, i, content.substring(i, i + 1), node.getUrl()));
                    count--;
                    continue;
                }
                for (int j = i + 1; j < content.length(); j++) {
                    char inTem = content.charAt(j);
                    node = node.getSonByValue(inTem);
                    if (node == null) {
                        break;
                    } else {
                        if (node.isEnd()) {
                            result.add(new KeyWordLocation(i, j, content.substring(i, j + 1), node.getUrl()));
                            count--;
                            i = j;
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }


    /**
     * 从content中找count个关键字 最长优先匹配原则
     *
     * @param content
     * @param count
     * @return
     */
    public List<KeyWordLocation> analyzeFromStringPro(String content, int count) {
        if (StringUtils.isEmpty(content) || count == 0) {
            return Collections.emptyList();
        }
        List<KeyWordLocation> result = new ArrayList<>();
        //判断是否已经命中关键词
        Boolean hasWord = false;
        //已经命中关键词的临时变量
        KeyWordLocation temWord = null;

        for (int i = 0; i < content.length() && count > 0; i++) {
            char temChar = content.charAt(i);
            //过滤html标签
            if (temChar == '<') {
                while (content.charAt(++i) != '>' && i < content.length()) {}
                continue;
            }
            STreeNode node = root.getSonByValue(temChar);
            if (node != null) {
                if (node.isEnd()) {   //单字 成关键词
                    hasWord = true;
                    temWord = new KeyWordLocation(i, i, content.substring(i, i + 1), node.getUrl());
                }
                for (int j = i + 1; j < content.length(); j++) {
                    char inTem = content.charAt(j);
                    node = node.getSonByValue(inTem);
                    if (node == null) {
                        if (hasWord && temWord != null) {
                            result.add(temWord);
                            i = j - 1;
                            count--;
                            hasWord = false;
                            temWord = null;
                        }
                        break;
                    } else {
                        if (node.isEnd()) {
                            hasWord = true;
                            temWord = new KeyWordLocation(i, j, content.substring(i, j + 1), node.getUrl());
                        }
                    }
                }
                if (hasWord && temWord != null) {
                    result.add(temWord);
                    i = temWord.getEnd();
                    count--;
                    hasWord = false;
                    temWord = null;
                }
            }
        }
        return result;
    }

    /**
     *  获取固定前缀的词典集合 key,value -> word,url
     * @param prefix
     * @return
     */
    public Map<String,String> listCurPrefix(String prefix){
        STreeNode node = root;
        if(!StringUtils.isEmpty(prefix)){
            for(int i=0;i<prefix.length();i++){
                char tem = prefix.charAt(i);
                node = node.getSonByValue(tem);
                if(node == null){
                    return Collections.emptyMap();
                }
            }
        }
        Map<String,String> resultMap = new HashMap<>();
        if(node.isEnd()){
            resultMap.put(node.getWord(),node.getUrl());
        }
        handleNodeToMap(node,resultMap);
        return resultMap;
    }

    private void handleNodeToMap(STreeNode node, Map<String, String> map) {
        if(node != null && node.getSon() != null){
            List<STreeNode> sons = node.getSon();
            for(STreeNode son:sons){
                if(son.isEnd()){
                    map.put(son.getWord(),son.getUrl());
                }
                handleNodeToMap(son,map);
            }
        }
    }

    public STreeNode getRoot() {
        return root;
    }

    public void setRoot(STreeNode root) {
        this.root = root;
    }

    public static void main(String[] args) {
        SearchTree searchTree = new SearchTree();
        String[] str = new String[]{"我们", "a路", "我们的", "好", "很快就好", "我","a","路","hah a","hah   bfds","我的哈哈"};

        Arrays.sort(str, (o1, o2) -> o1.length() - o2.length());
        //别名特殊处理 长的别名包含了短别名 过滤掉短别名
        List<String> toInsertAlias = new ArrayList<>();
        boolean flag = true;
        for(String ali:str){
            if(!CollectionUtils.isEmpty(toInsertAlias)){
                for(int i = 0;i<toInsertAlias.size();i++){
                    String tem = toInsertAlias.get(i);
                    if(ali.contains(tem)){
                        flag = false;
                        toInsertAlias.set(i,ali);
//                        break;
                    }
                }
            }
            if(flag){
                toInsertAlias.add(ali);
            }
            flag = true;
        }
        for (String val : toInsertAlias) {
            searchTree.insert(val, val + "url");
        }

        Map<String, String> map = searchTree.listCurPrefix("");
        map.forEach((x,y)-> System.out.println(x+": "+y));

        List<KeyWordLocation> list = searchTree.analyzeFromStringPro("我们们们们们们饿哦的爱爱哈哈我们", 10);
        System.out.println(list.size());
    }

}
