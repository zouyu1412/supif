package com.zouyu.leetcode.DataStructure;

import java.util.*;

class PickRamIndSolution {

    Map<Integer, List<Integer>> map = new HashMap<>();

    public PickRamIndSolution(int[] nums) {
        for(int i=0;i<nums.length;i++){
            List<Integer> temList = map.get(nums[i]);
            if(temList == null){
                temList = new ArrayList<>();
                temList.add(i);
                map.put(nums[i],temList);
            }else{
                temList.add(i);
            }
        }
    }
    
    public int pick(int target) {
        List<Integer> integers = map.get(target);
        return integers.get(new Random().nextInt(integers.size()));
    }
}