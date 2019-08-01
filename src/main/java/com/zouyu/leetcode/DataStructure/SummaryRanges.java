package com.zouyu.leetcode.DataStructure;

import java.util.TreeMap;

/**
 * Given a data stream input of non-negative integers a1, a2, ..., an, ..., summarize the numbers seen so far as a list of disjoint intervals.
 * For example, suppose the integers from the data stream are 1, 3, 7, 2, 6, ..., then the summary will be:
 *
 * [1, 1]
 * [1, 1], [3, 3]
 * [1, 1], [3, 3], [7, 7]
 * [1, 3], [7, 7]
 * [1, 3], [6, 7]
 */
class SummaryRanges {

    /**
     * 数值 -> 数值边界
     * 1   ->   1
     */
    TreeMap<Integer,Integer> zoneMap;

    /** Initialize your data structure here. */
    public SummaryRanges() {
        zoneMap = new TreeMap<>();
    }
    
    public void addNum(int val) {
        Integer floorKey = zoneMap.floorKey(val);
        Integer floorValue = floorKey == null? null:zoneMap.get(floorKey);
        Integer ceilingKey = zoneMap.ceilingKey(val);
        Integer ceilingValue = ceilingKey == null?null:zoneMap.get(ceilingKey);
        if(floorKey != null && val <= floorValue){
            return;
        }
        int newKey;
        int newValue;
        if(floorKey != null && val - floorValue == 1){
            newKey = floorKey;
        }else {
            newKey = val;
        }
        if(ceilingKey != null && ceilingKey-val == 1){
            newValue = ceilingValue;
            zoneMap.remove(ceilingKey);
        }else{
            newValue = val;
        }
        zoneMap.put(newKey,newValue);

    }
    
    public int[][] getIntervals() {
        int[][] res = new int[zoneMap.size()][2];
        int ind = 0;
        for(int val:zoneMap.keySet()){
            res[ind][0] = val;
            res[ind][1] = zoneMap.get(val);
            ind++;
        }
        return res;
    }

}