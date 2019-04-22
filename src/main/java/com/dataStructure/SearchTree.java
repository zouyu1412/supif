package com.dataStructure;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchTree {

    private STreeNode root;

    SearchTree(){
        root = new STreeNode();
    }

    private class STreeNode{

        private int count;
        private List<STreeNode> son;
        private String path;
        private char value;
        private boolean isEnd;
        private String url;
        STreeNode(String path,char value){
            this();
            this.path = path;
            this.value = value;
        }
        STreeNode(){
            count = 1;
            son = new ArrayList<>();
        }

        private STreeNode getSonByValue(char value){
            if(CollectionUtils.isEmpty(son)){
                return null;
            }
            for(int i=0;i<son.size();i++){
                if(son.get(i).value == value){
                    return son.get(i);
                }
            }
            return null;
        }
    }

    public void insert(String keyWord,String url){
        if(StringUtils.isEmpty(keyWord)){
            return;
        }
        root.count++;
        STreeNode node = root;
        StringBuilder temPath = new StringBuilder();
        for(int i = 0;i<keyWord.length();i++){
            char curValue = keyWord.charAt(i);
            STreeNode son = node.getSonByValue(curValue);
            if(son==null){
                STreeNode curNode = new STreeNode(temPath.toString(), curValue);
                node.son.add(curNode);
                node = curNode;
            }else{
                son.count++;
                node = son;
            }
            temPath.append(curValue);
        }
        node.isEnd = true;
        node.url = url;
    }

    public boolean containsChar(char startChar){
        return root.getSonByValue(startChar) == null ? false : true;
    }

    public String getWordUrl(String word){
        if(StringUtils.isEmpty(word)){
            return null;
        }
        STreeNode node = root;
        for(int i=0;i<word.length();i++){
            char curValue = word.charAt(i);
            STreeNode son = node.getSonByValue(curValue);
            if(son==null){
                return null;
            }else{
                node = son;
            }
        }
        if(node.isEnd){
            return node.url;
        }
        return null;
    }

    /**
     * 从文章content中，找到count个关键字的位置
     * @param content
     * @param count
     * @return
     */
    public List<KeyWordLocation> analyzeFromString(String content,int count){
        if(StringUtils.isEmpty(content) || count == 0){
            return null;
        }
        List<KeyWordLocation> result = new ArrayList<>();
        for(int i=0;i<content.length() && count>0;i++){
            char temChar = content.charAt(i);
            STreeNode node = root.getSonByValue(temChar);
            if(node != null){
                if(node.isEnd){   //单字 成关键词
                    result.add(new KeyWordLocation(i,i,content.substring(i,i+1)));
                    count--;
                    continue;
                }
                for(int j=i+1;j<content.length();j++){
                    char inTem = content.charAt(j);
                    node = node.getSonByValue(inTem);
                    if(node == null){
                        break;
                    }else{
                        if(node.isEnd){
                            result.add(new KeyWordLocation(i,j,content.substring(i,j+1)));
                            count--;
                            i=j;
                            continue;
                        }
                    }
                }
            }
        }
        return result;
    }

    static class KeyWordLocation{
        int start;
        int end;
        String word;

        KeyWordLocation(int start,int end,String word){
            this.start = start;
            this.end = end;
            this.word = word;
        }
    }

    public static void main(String[] args) {
//        SearchTree searchTree = new SearchTree();
//        String[] strs = {"魅力", "好魅力","好","大哥","大哥好哒"};
//        String[] prefix = {"ba", "b", "band", "abc",};
//        for (String str: strs) {
//            searchTree.insert(str,"url"+str);
//        }
//        String bee = searchTree.getWordUrl("魅力");
//        String bee1 = searchTree.getWordUrl("好");
//        System.out.println(bee1);

        List<obj> list = new ArrayList<>();
        obj obj = new obj(1, 2);
        list.add(obj);
//        obj.setI(999);
        obj = null;
        System.out.println(list.get(0).toString());
    }

    static class obj{
        int i=0;
        int j=1;

        @Override
        public String toString() {
            return "obj{" +
                    "i=" + i +
                    ", j=" + j +
                    '}';
        }

        public obj(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public int getI() {
            return i;
        }

        public void setI(int i) {
            this.i = i;
        }

        public int getJ() {
            return j;
        }

        public void setJ(int j) {
            this.j = j;
        }
    }
}
