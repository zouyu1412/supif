package com.zouyu.lintcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouy-c on 2017/12/19.
 */
public class permutation {
    /*
     * 带重复元素的全排列
     * @param :  A list of integers
     * @return: A list of unique permutations
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        //定义了两个用于递归的参数
        List<List<Integer>> ret = new ArrayList();
        List<Integer> temp = new ArrayList();
        //定义了一个标记是否访问过的数组
        int[] visited = new int[nums.length];
        //参数：(层数k,总层数n，ret，temp，数组候选解，访问标志）
        recursion(0, nums.length, ret, temp, nums, visited);

        return ret;
    }

    public static void recursion(int k, int n, List<List<Integer>> ret, List<Integer> temp, int[] nums, int[] visited) {
        //如果temp有n个元素，ret中没有
        if (k == n && !ret.contains(temp))
            ret.add(new ArrayList<Integer>(temp));//ret就添加

        if (k > n)//退出条件限制递归层数
            return;

        else {
            for (int i = 0; i < n; i++) {
                int candidate = nums[i];//取出一个候选解
                if (visited[i] == 0)//如果没有被访问过
                {
                    temp.add(candidate);//temp添加
                    visited[i] = 1;//标记访问
                    recursion(k + 1, n, ret, temp, nums, visited);//递归
                    visited[i] = 0;//撤销访问标记
                    temp.remove(temp.size() - 1);//撤销上次添加
                }
            }
        }
    }
}


