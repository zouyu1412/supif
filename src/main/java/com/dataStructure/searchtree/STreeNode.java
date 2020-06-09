package com.dataStructure.searchtree;

import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class STreeNode implements Serializable {

    private int count;
    private List<STreeNode> son;
    private String path;
    private char value;
    private boolean isEnd;
    private boolean isForbid;
    private String url;

    STreeNode(String path, char value) {
        this();
        this.path = path;
        this.value = value;
    }

    STreeNode() {
        count = 1;
        son = new ArrayList<>();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<STreeNode> getSon() {
        return son;
    }

    public void setSon(List<STreeNode> son) {
        this.son = son;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public boolean isForbid() {
        return isForbid;
    }

    public void setForbid(boolean forbid) {
        isForbid = forbid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWord(){
        return path+value;
    }

    public STreeNode getSonByValue(char value) {
        if (CollectionUtils.isEmpty(son)) {
            return null;
        }
        for (int i = 0; i < son.size(); i++) {
            if (son.get(i).value == value) {
                return son.get(i);
            }
        }
        return null;
    }
}