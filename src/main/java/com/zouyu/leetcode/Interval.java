package com.zouyu.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zouy-c on 2018/4/20.
 */
public class Interval {
    int start;
    int end;

    Interval() {
        start = 0;
        end = 0;
    }

    Interval(int s, int e) {
        start = s;
        end = e;
    }

    public static void main(String[] args) {
        List<Integer> list =new ArrayList(){{add(1);add(2);add(3);}};
        Integer[] integers = list.stream().toArray(Integer[]::new);
        String s = Arrays.toString(integers);
        System.out.println(s);
        list.remove(1);
        System.out.println(list.size());
        System.out.printf(Arrays.toString(integers));
        System.out.printf(s);
    }
}
