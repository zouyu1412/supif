package com.zouyu.lintcode;

import java.util.*;

import static java.lang.Math.min;

/**
 * Created by zouy-c on 2018/1/3.
 */
public class arithmetic {
    /**
     * 筛子求和
     *
     * @param n an integer
     * @return a list of Map.Entry<sum, probability>
     */
    public List<Map.Entry<Integer, Double>> dicesSum(int n) {
        long[][] dp = new long[n + 1][6 * n + 1];
        dp[1][1] = 1;
        dp[1][2] = 1;
        dp[1][3] = 1;
        dp[1][4] = 1;
        dp[1][5] = 1;
        dp[1][6] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = i; j <= i * 6; j++) {
                long x1 = 0, x2 = 0, x3 = 0, x4 = 0, x5 = 0, x6 = 0;
                if (j - 1 > 0) {
                    x1 = dp[i - 1][j - 1];
                }
                if (j - 2 > 0) {
                    x2 = dp[i - 1][j - 2];
                }
                if (j - 3 > 0) {
                    x3 = dp[i - 1][j - 3];
                }
                if (j - 4 > 0) {
                    x4 = dp[i - 1][j - 4];
                }
                if (j - 5 > 0) {
                    x5 = dp[i - 1][j - 5];
                }
                if (j - 6 > 0) {
                    x6 = dp[i - 1][j - 6];
                }
                dp[i][j] = x1 + x2 + x3 + x4 + x5 + x6;
            }
        }
        List<Map.Entry<Integer, Double>> result = new ArrayList<Map.Entry<Integer, Double>>();
        for (int i = n; i <= 6 * n; i++) {
            AbstractMap.SimpleEntry<Integer, Double> entry = new AbstractMap.SimpleEntry<Integer, Double>(i, dp[n][i] / Math.pow(6, n));
            result.add(entry);
        }
        return result;
    }

    /* 两个排序数组的中位数
     * @param A: An integer array
     * @param B: An integer array
     * @return: a double whose format is *.5 or *.0
     */
    public double findMedianSortedArrays(int[] A, int[] B) {
        int lenA = A.length;
        int lenB = B.length;
        int len = lenA + lenB;
        if (len % 2 == 0) {  //总长为偶数，求第len/2个和len/2+1个最小数的平均
            double x = getCertainMinFromTwoArr(A, B, 0, 0, lenA, lenB, len / 2);
            double y = getCertainMinFromTwoArr(A, B, 0, 0, lenA, lenB, len / 2 + 1);
            return (x + y) / 2;
        } else {
            return getCertainMinFromTwoArr(A, B, 0, 0, lenA, lenB, len / 2 + 1);
        }
    }

    private static double getCertainMinFromTwoArr(int[] a, int[] b, int a_start, int b_start, int a_len, int b_len, int k) {
        if (a_len > b_len) {
            return getCertainMinFromTwoArr(b, a, b_start, a_start, b_len, a_len, k);
        }
        if (a_len == 0) {
            return b[b_start + k - 1];
        }
        if (k == 1) {
            return min(a[a_start], b[b_start]);
        }
        int x = min(k / 2, a_len);
        int y = k - x;
        if (a[a_start + x - 1] < b[b_start + y - 1]) {
            return getCertainMinFromTwoArr(a, b, a_start + x, b_start, a_len - x, b_len, k - x);
        } else if (a[a_start + x - 1] > b[b_start + y - 1]) {
            return getCertainMinFromTwoArr(a, b, a_start, b_start + y, a_len, b_len - y, k - y);
        } else {
            return a[a_start + x - 1];
        }
    }

    //TODO 时间复杂度 可以优化
    /* 直方图最大矩形覆盖
     * @param height: A list of integer
     * @return: The area of largest rectangle in the histogram
     */
    public int largestRectangleArea1(int[] height) {
        if(height.length==0 || height==null){
            return 0;
        }
        int len = height.length;
        int[] dp = new int[len];
        for(int i=0;i<len;i++){
            int temHeight = height[i];
            int temMax = height[i];
            for(int j=i;j<len;j++){
                if(height[j]<temHeight){
                    temHeight = height[j];
                }
                temMax = Math.max(temMax,(j-i+1)*temHeight);
            }
            dp[i] = temMax;
        }
        int re = Integer.MIN_VALUE;
        for(int x:dp){
            re = Math.max(re,x);
        }
        return re;
    }
    public int largestRectangleArea2(int[] height) {
        if (height.length == 0) return 0;
        int[] high = Arrays.copyOf(height, height.length + 1);
        int result = 0;
        Stack<Integer> rank = new Stack<Integer>();
        for (int i = 0; i < high.length; i++) {
            while (!rank.isEmpty() && high[i] < high[rank.peek()]) {
                int area = high[rank.pop()] * (rank.isEmpty() ? i : i - rank.peek() - 1);
                System.out.println("area:"+area);
                result = Math.max(result, area);
            }
            rank.push(i);
        }

        return result;
    }

    /**最大矩形
     * @param
     * @return an integer
     */
    public int maximalRectangle(boolean[][] s) {
        int height = s.length;
        if(height == 0) return 0;
        int width = s[0].length;
        if(width == 0) return 0;

        int[][] matrix = new int[height][width];

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if(i > 0)
                    matrix[i][j] = s[i][j] == true ? matrix[i - 1][j] + 1 : 0;
                else
                    matrix[i][j] = s[i][j] == true ? 1 : 0;
            }
        }
        printArr2(matrix);
        int res = 0;
        for(int i = 0; i < height; i++) {
            res = Math.max(res, getSquare(i, matrix));
        }
        return res;
    }
    private int getSquare(int i, int[][] matrix) {
        int res = 0;
        Stack<Integer> stack = new Stack<Integer>();
        for(int j = 0; j < matrix[0].length; j++) {
            if(stack.isEmpty() || matrix[i][j] > matrix[i][stack.peek()]) {
                stack.push(j);
            } else {
                int top = stack.peek();
                while(matrix[i][top] >= matrix[i][j]) {
                    stack.pop();
                    int length = stack.isEmpty() ? j : j - stack.peek() - 1;
                    res = Math.max(res, matrix[i][top] * length);
                    if(stack.isEmpty()) {
                        break;
                    }
                    top = stack.peek();
                }
                stack.push(j);
            }
        }
        while(!stack.isEmpty()) {
            int top = stack.pop();
            int length = stack.isEmpty() ? matrix[0].length : matrix[0].length - stack.peek() - 1;
            res = Math.max(res, matrix[i][top] * length);
        }
        return res;
    }

    /* 快速幂 (a^n)%b
     * @param a: A 32bit integer
     * @param b: A 32bit integer
     * @param n: A 32bit integer
     * @return: An integer
     */
    public int fastPower(int a, int b, int n) {
        if(n==0 || a==1){
            return 1%b;
        }
        if(n==1){
            return a%b;
        }
        long tem = fastPower(a,b,n/2);
        long re = (tem*tem)%b;
        if(n%2==1){
            re = (re*(a%b))%b;
        }
        return (int)re;
    }

    //背包2
    public int backPackII(int m, int[] A, int[] V) {
        int[][] dp = new int[A.length][m+1];
        for(int i=0;i<A.length;i++){
            for(int j=1;j<m+1;j++){
                if(i==0){
                    if(j>=A[i]){
                        dp[i][j] = V[i];
                    }
                }else{
                    if(j<A[i])
                        dp[i][j] = dp[i-1][j];
                    else
                        dp[i][j] = Math.max(dp[i-1][j-A[i]]+V[i],dp[i-1][j]);
                }
            }
        }
        return dp[A.length-1][m];
    }

    public static void main(String[] args) {
        new arithmetic().backPackII(100,new int[]{77,22,29,50,99},new int[]{92,22,87,46,90});
    }

    static void printArr(int[] x){
        for(int va:x){
            System.out.print(va+" ");
        }
        System.out.println();
    }

    static void printArr2(int[][] x){
        for(int i=0;i<x.length;i++){
            for(int j=0;j<x[0].length;j++){
                System.out.print(x[i][j]+" ");
            }
            System.out.println();
        }
    }

}
