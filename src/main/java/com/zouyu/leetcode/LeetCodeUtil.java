package com.zouyu.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouy-c on 2018/3/21.
 */
public class LeetCodeUtil {

    public static void printIntArr(int[] A){
        for(int i=0;i<A.length;i++){
            System.out.print(A[i]+" ");
        }
        System.out.println();
    }

    public static void printListNode(ListNode l){
        if(l == null) return;
        while(l!=null){
            if(l.next!=null) {
                System.out.print(l.val + "->");
            }else{
                System.out.print(l.val);
                break;
            }
            l = l.next;
        }
        System.out.println();
    }

    public static ListNode intArrayToListNode(int[] A){
        if(A.length == 0) return null;
        ListNode re = new ListNode(A[0]);
        ListNode point = re;
        for(int i=1;i<A.length;i++){
            point.next = new ListNode(A[i]);
            point = point.next;
        }
        return re;
    }

    private static void printArr(boolean[][] arr){
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("=================================");
    }

    //HardProblemSolution.<T>printAllArr();
    private static <T> void printAllArr(T[][] arr){
        for(int i=0;i<arr.length;i++){
            for(int j=0;j<arr[0].length;j++){
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("=================================");
    }

    public static void main(String[] args) {
        List<Integer> list =new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        List<Integer> integers = list.subList(1, 3);
        System.out.println(integers.size());
    }
}
