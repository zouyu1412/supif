package com.kwai.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zouy-c on 2017/12/18.
 */
public class NQueens1 {
    /*
         * @param n: The number of queens
         * @return: All distinct solutions
         */
    public List<List<String>> solveNQueens(int n) {
        int[][] tem = new int[n][n];
        List<List<String>> re = new ArrayList<List<String>>();
        for(int i=0;i<n;i++) {
            makeNQueue(tem, 0, i, re);
        }
        return re;
    }

    private static void makeNQueue(int[][] tem, int raw, int col, List<List<String>> re) {
        int len = tem.length;
        int[][] temp = new int[len][len];
        copyArr(tem,temp);
        if(canBeQueue(temp,raw,col)){
            temp[raw][col] = 1;
            if(raw == len-1){
//                printArr(temp);
                fill(temp,re);
            }else {
                for (int i = 0; i < len; i++) {
                    makeNQueue(temp, raw + 1, i, re);
                }
            }
        }
    }

    private static void copyArr(int[][] tem, int[][] temp) {
        for(int i=0;i<tem.length;i++) {
            for (int j = 0; j < tem[0].length; j++) {
                temp[i][j] = tem[i][j];
            }
        }
    }

    private static void fill(int[][] tem, List<List<String>> re) {
        List<String> list = new ArrayList<String>();
        for(int i=0;i<tem.length;i++){
            StringBuilder sb = new StringBuilder();
            for(int j=0;j<tem[0].length;j++){
                if(tem[i][j]==1){
                    sb.append('Q');
                }else{
                    sb.append('.');
                }
            }
            list.add(sb.toString());
        }
        re.add(list);
    }

    private static boolean canBeQueue(int[][] tem, int raw, int col) {
        int len = tem.length;
        int count = 0;
        //行不能同为皇后(1)
//        while(count < len){
//            if(1 == tem[raw][count]){
//                return false;
//            }
//            count++;
//        }
        //列不能同为皇后(1)
        count = 0;
        while(count < len){
            if(1 == tem[count][col]){
                return false;
            }
            count++;
        }
        //斜线不能同为皇后(1)
        for(int i=0;i<len;i++){
            int leftUpRaw = raw - i;
            int leftUpCol = col - i;
            int leftDownRaw = raw + i;
            int leftDownCol = col - i;
            int rightUpRaw = raw - i;
            int rightUpCol = col + i;
            int rightDownRaw = raw + i;
            int rightDownCol = col + i;
            if(leftUpRaw>=0 && leftUpRaw<len && leftUpCol>=0 && leftUpCol<len){
                if(tem[leftUpRaw][leftUpCol]==1){
                    return false;
                }
            }
            if(leftDownRaw>=0 && leftDownRaw<len && leftDownCol>=0 && leftDownCol<len){
                if(tem[leftDownRaw][leftDownCol]==1){
                    return false;
                }
            }
            if(rightUpRaw>=0 && rightUpRaw<len && rightUpCol>=0 && rightUpCol<len){
                if(tem[rightUpRaw][rightUpCol]==1){
                    return false;
                }
            }
            if(rightDownRaw>=0 && rightDownRaw<len && rightDownCol>=0 && rightDownCol<len){
                if(tem[rightDownRaw][rightDownCol]==1){
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(new NQueens1().solveNQueens(8).size());
    }

    private static void printArr(int[][] x){
        for(int i=0;i<x.length;i++){
            for(int j=0;j<x[0].length;j++){
                System.out.print(x[i][j]+"  ");
            }
            System.out.println();
        }
    }
}
