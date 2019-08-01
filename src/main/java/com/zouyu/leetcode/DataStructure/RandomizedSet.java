package com.zouyu.leetcode.DataStructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

class RandomizedSet {

    Map<Integer,Integer> valToLocMap;
    List<Integer> valList;

    /** Initialize your data structure here. */
    public RandomizedSet() {
        valList = new ArrayList<>();
        valToLocMap = new HashMap<>();
    }
    
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        if(valToLocMap.get(val) == null){
            valToLocMap.put(val,valList.size());
            valList.add(val);
        }else {
            return false;
        }
        return true;
    }
    
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        if(valToLocMap.containsKey(val)){
            int ind = valToLocMap.get(val);
            Integer lastVal = valList.get(valList.size() - 1);
            valList.set(ind, lastVal);
            valToLocMap.put(lastVal,ind);
            valList.remove(valList.size()-1);
            valToLocMap.remove(val);

        }else{
            return false;
        }
        return true;
    }
    
    /** Get a random element from the set. */
    public int getRandom() {
        int at = ThreadLocalRandom.current().nextInt(valList.size());
        return valList.get(at);
    }
}