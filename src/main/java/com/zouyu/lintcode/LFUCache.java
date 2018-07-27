package com.zouyu.lintcode;

import java.util.HashMap;
import java.util.Map;

public class LFUCache {
    //TODO 需要进一步优化
    private int capacity;
    static final Map<Integer,CacheData> cache = new HashMap<>();

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    /*
     * @param key: An integer
     * @param value: An integer
     * @return: nothing
     */
    public void set(int key, int value){
        if(null != cache.get(key)){
            CacheData tem = cache.get(key);
            tem.setValue(value);
            tem.setAccessCount(tem.getAccessCount()+1);
            tem.setLastModifiedDate(System.nanoTime());
        }else if(cache.size()<capacity){
            cache.put(key,new CacheData(key,value,1,System.nanoTime()));
        }else{
            int curKey=0;
            int curMinAccessCount = Integer.MAX_VALUE;
            long curEarliestUpdateTime = System.nanoTime();
            int minCount;//最少访问计数
            for(Integer x:cache.keySet()) {
                CacheData tem = cache.get(x);
                if(curMinAccessCount>tem.getAccessCount()){
                    curKey = tem.getKey();
                    curMinAccessCount = tem.getAccessCount();
                    curEarliestUpdateTime = tem.getLastModifiedDate();
                }else if(curMinAccessCount == tem.getAccessCount()){
                    if(curEarliestUpdateTime>tem.getLastModifiedDate()) {
                        curKey = tem.getKey();
                        curEarliestUpdateTime = tem.getLastModifiedDate();
                    }
                }
            }
            cache.remove(curKey);
            cache.put(key,new CacheData(key,value,1,System.nanoTime()));
        }
    }

    /*
     * @param key: An integer
     * @return: An integer
     */
    public int get(int key) {
        int re;
        if(null != cache.get(key)){
            CacheData tem = cache.get(key);
            tem.setLastModifiedDate(System.nanoTime());
            tem.setAccessCount(tem.getAccessCount()+1);
            re = tem.getValue();
        }else{
            re = -1;
        }
        return re;
    }

    class CacheData{
        int key;
        int value;
        int accessCount;
        long lastModifiedDate;

        public CacheData(int key, int value, int accessCount, long lastModifiedDate) {
            this.key = key;
            this.value = value;
            this.accessCount = accessCount;
            this.lastModifiedDate = lastModifiedDate;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public int getAccessCount() {
            return accessCount;
        }

        public void setAccessCount(int accessCount) {
            this.accessCount = accessCount;
        }

        public long getLastModifiedDate() {
            return lastModifiedDate;
        }

        public void setLastModifiedDate(long lastModifiedDate) {
            this.lastModifiedDate = lastModifiedDate;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        LFUCache cache = new LFUCache(3);
        cache.set(1, 10);
        cache.set(2, 20);
        cache.set(3, 30);
        System.out.println(cache.get(1));
        cache.set(4, 40);
        printCache();
        System.out.println(cache.get(4));
        System.out.println(cache.get(3));
        System.out.println(cache.get(2));
        System.out.println(cache.get(1));
        cache.set(5, 50);
        printCache();
        System.out.println(cache.get(1));
        System.out.println(cache.get(2));
        System.out.println(cache.get(3));
        System.out.println(cache.get(4));
        System.out.println(cache.get(5));
    }

    public static void printCache(){
        for(Integer x:cache.keySet()){
            int tem = cache.get(x).getValue();
            System.out.println("key:"+x+"\tvalue:"+tem);
        }
    }
}