package com.zouyu.lintcode;

import java.util.*;

/**
 * Created by zouy-c on 2017/12/19.
 */
public class StringSolution {

    /* 字符大小写排序
     * @param chars: The letter array you should sort by Case
     * @return: nothing
     */
    public void sortLetters(char[] chars) {
        if(chars==null || chars.length==0){
            return;
        }
        int start =0 ;
        int end = chars.length-1;
        while(start<end){
            while(chars[start]>='a'&&chars[start]<='z'&&start<end){
                start++;
            }
            while(chars[end]>='A'&&chars[end]<='Z'&&start<end) {
                end--;
            }
            char tem = chars[start];
            chars[start] = chars[end];
            chars[end] = tem;
            start++;
            end--;
        }
    }

    //TODO 运行超时
    /* 单词接龙
     * @param start: a string
     * @param end: a string
     * @param dict: a set of string
     * @return: An integer
     */
    public int ladderLength(String start, String end, Set<String> dict) {
        List<List<String>> list = new ArrayList<>();
        List<String> li = new ArrayList<>();
        li.add(start);
        makeLadder(start,end,li,dict,list);
        if(list.size()==0){
            return 0;
        }
        int re = Integer.MAX_VALUE;
        for(List<String> x:list){
            re = Math.min(re,x.size());
        }
        return re;
    }
    private static void makeLadder(String start, String end, List<String> li, Set<String> dict, List<List<String>> list) {
        if(myCompare(start,end)==1){
            li.add(end);
            list.add(li);
            return;
        }
        if(dict.size()==0){
            return;
        }
        for(Iterator<String> it = dict.iterator(); it.hasNext();){
            String tem = it.next();
            if(myCompare(start,tem)==1){
                List<String> l = new ArrayList<>();
                l.addAll(li);
                Set<String> s = new HashSet<>();
                s.addAll(dict);
                s.remove(tem);
                l.add(tem);
                makeLadder(tem,end,l,s,list);
            }

        }
    }

    private static int myCompare(String str1, String str2) {
        if(str1.length()!=str2.length()){
            return Integer.MAX_VALUE;
        }
        int count=0;
        for(int i=0;i<str1.length();i++){
            if(str1.charAt(i)!=str2.charAt(i)){
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        String s1 = "hit";
        String s2 = "cog";
        Set<String> set = new HashSet<>();
        set.add("hot");
        set.add("dot");
        set.add("dog");
        set.add("lot");
        set.add("log");
        System.out.println(new StringSolution().ladderLength(s1,s2,set));
        LinkedList<String> queue = new LinkedList<String>();
        queue.offer("ds");
        queue.poll();
    }

    static void printSet(Set set){
        set.forEach(System.out::println);
    }
    static void printList(List list){
        list.forEach(System.out::println);
    }
}
