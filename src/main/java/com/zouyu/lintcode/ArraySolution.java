package com.zouyu.lintcode;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Created by zouy-c on 2017/12/19.
 */
public class ArraySolution {
    /*
     * @param A: An array of integers
     * @return: An integer
     */
    public int firstMissingPositive(int[] A) {
        Set<Integer> set = new HashSet();
        for (int x : A) {
            set.add(x);
        }
        for (int i = 1; ; i++) {
            boolean tem = set.add(i);
            if (tem == true) {
                return i;
            }
        }
    }

    /* 主元素
     * @param nums: a list of integers
     * @return: find a  majority number
     */
    public int majorityNumber(List<Integer> nums) {
        int count = 0;
        int result = 0;
        for (int i = 0; i < nums.size(); i++) {
            if (count == 0) {
                result = nums.get(i);
                count++;
            } else {
                if (result == nums.get(i)) {
                    count++;
                } else {
                    count--;
                }
            }
        }
        return result;
    }

    //TODO 90%
    /* 主元素2
     * @param nums: a list of integers
     * @return: The majority number that occurs more than 1/3
     */
    public int majorityNumberTwo(List<Integer> nums) {
        int re1 = 0;
        int re2 = 0;
        int count1 = 0;
        int count2 = 0;
        for (int i = 0; i < nums.size(); i++) {
            if (count1 == 0) {
                if (count2 != 0 && re2 == nums.get(i)) {
                    count2++;
                } else {
                    re1 = nums.get(i);
                    count1++;
                }
            } else if (count2 == 0) {
                if (count1 != 0 && re1 == nums.get(i)) {
                    count1++;
                } else {
                    re2 = nums.get(i);
                    count2++;
                }
            } else {
                if (nums.get(i) == re1) {
                    count1++;
                } else if (nums.get(i) == re2) {
                    count2++;
                } else {
                    count1--;
                    count2--;
                }
            }
        }
        return count1 > count2 ? re1 : re2;
    }

    /* 最大子数组
     * @param nums: A list of integers
     * @return: A integer indicate the sum of max subarray
     */
    public int maxSubArray(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int curSum = 0;
        for (int i = 0; i < nums.length; i++) {
            curSum += nums[i];
            if (curSum > maxSum)
                maxSum = curSum;
            if (curSum < 0) {
                curSum = 0;
            }
        }
        return maxSum;
    }

    /* 最大子数组2   给定一个整数数组，找出两个不重叠 子数组使得它们的和最大
     * @param nums: A list of integers
     * @return: An integer denotes the sum of max two non-overlapping subarrays
     */
    public int maxTwoSubArrays(List<Integer> nums) {
        int len = nums.size();
        int[] sumLeft = new int[len];  //sumLeft[i]从0-i最大连续和
        int[] sumRight = new int[len];  //sumRight[i]从i到nums.size()最大连续和
        int curSum = nums.get(0);
        sumLeft[0] = curSum;
        for (int i = 1; i < len - 1; i++) {
            if (curSum <= 0) {
                curSum = nums.get(i);
            } else {
                curSum += nums.get(i);
            }
            if (curSum > sumLeft[i - 1]) {
                sumLeft[i] = curSum;
            } else {
                sumLeft[i] = sumLeft[i - 1];
            }
        }
        //重置curSum 构建sumRight
        curSum = nums.get(len - 1);
        sumRight[len - 1] = curSum;
        for (int i = nums.size() - 2; i > 0; i--) {
            if (curSum <= 0) {
                curSum = nums.get(i);
            } else {
                curSum += nums.get(i);
            }
            if (curSum > sumRight[i + 1]) {
                sumRight[i] = curSum;
            } else {
                sumRight[i] = sumRight[i + 1];
            }
        }

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < len - 1; i++) {
            if (max < sumLeft[i] + sumRight[i + 1])
                max = sumLeft[i] + sumRight[i + 1];
        }
        return max;
    }

    //TODO 无法解决数组全为负数的问题
    /* 最大子数组3   给定一个整数数组，找出k个不重叠 子数组使得它们的和最大
     * @param nums: A list of integers
     * @param k: An integer denote to find k non-overlapping subarrays
     * @return: An integer denote the sum of max k non-overlapping subarrays
     */
    public int maxSubArray(int[] nums, int k) {
        int len = nums.length;
        int[][] dp = new int[len + 1][k + 1];  //dp[i][j]到第i个元素 分成j个子数组 最大和
        for (int i = 0; i < len + 1; i++) {
            dp[i][0] = 0;
        }
        for (int i = 1; i <= len; i++) {
            for (int j = 1; j <= Math.min(i, k); j++) {
                int endMax = 0;
                int mx = Integer.MIN_VALUE;
                for (int p = i; p >= j; p--) {
                    endMax = Math.max(nums[p - 1], nums[p - 1] + endMax);
                    mx = Math.max(mx, endMax);
                    dp[i][j] = Math.max(dp[i][j], dp[p - 1][j - 1] + mx);
                }
            }
        }
        return dp[len][k];
    }

    /* 最大子数组差
     * @param nums: A list of integers
     * @return: An integer indicate the value of maximum difference between two substrings
     */
    public int maxDiffSubArrays(int[] nums) {
        int len = nums.length;
        int[] maxLe = new int[len];
        int[] minLe = new int[len];
        int[] maxRi = new int[len];
        int[] minRi = new int[len];
        int sumMax = nums[0];
        int sumMin = nums[0];
        maxLe[0] = sumMax;
        minLe[0] = sumMin;
        for (int i = 1; i < len - 1; i++) {
            if (sumMax > 0)
                sumMax += nums[i];
            else
                sumMax = nums[i];
            if (sumMin < 0)
                sumMin += nums[i];
            else
                sumMin = nums[i];
            if (maxLe[i - 1] < sumMax)
                maxLe[i] = sumMax;
            else
                maxLe[i] = maxLe[i - 1];
            if (minLe[i - 1] > sumMin)
                minLe[i] = sumMin;
            else
                minLe[i] = minLe[i - 1];
        }
        sumMax = nums[len - 1];
        sumMin = nums[len - 1];
        maxRi[len - 1] = sumMax;
        minRi[len - 1] = sumMin;
        for (int i = len - 2; i > 0; i--) {
            if (sumMax > 0)
                sumMax += nums[i];
            else
                sumMax = nums[i];
            if (sumMin < 0)
                sumMin += nums[i];
            else
                sumMin = nums[i];
            if (maxRi[i + 1] < sumMax)
                maxRi[i] = sumMax;
            else
                maxRi[i] = maxRi[i + 1];
            if (minRi[i + 1] > sumMin)
                minRi[i] = sumMin;
            else
                minRi[i] = minRi[i + 1];
        }
        int re = Integer.MIN_VALUE;
        for (int i = 0; i < len - 1; i++) {
            int tem1 = Math.abs(maxLe[i] - minRi[i + 1]);
            int tem2 = Math.abs(minLe[i] - maxRi[i + 1]);
            re = Math.max(Math.max(tem1, tem2), re);
        }
        return re;
    }

    /* 买卖股票的最佳时机
     * @param prices: Given an integer array
     * @return: Maximum profit
     */
    public int maxProfit1(int[] prices) {
        if (prices.length == 0 || prices == null) {
            return 0;
        }
        int hisMin = prices[0];
        int hisMaxDif = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < hisMin) {
                hisMin = prices[i];
            } else if (prices[i] - hisMin > hisMaxDif) {
                hisMaxDif = prices[i] - hisMin;
            }
        }
        return hisMaxDif;
    }

    /* 买彩票的最佳时机2
     * @param prices: Given an integer array
     * @return: Maximum profit
     */
    public int maxProfit2(int[] prices) {
        if (prices.length == 0 || prices == null) {
            return 0;
        }
        int cur = prices[0];
        int res = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] <= cur) {
                cur = prices[i];
            } else {
                res += (prices[i] - cur);
                cur = prices[i];
            }
        }
        return res;
    }

    /* 买彩票的最佳时机4
     * @param K: An integer
     * @param prices: An integer array
     * @return: Maximum profit
     */
    public int maxProfit(int k, int[] prices) {
        int N = prices.length;
        if (k >= N)
            return simpleMaxProfit(prices);
        int[] local = new int[k + 1], global = new int[k + 1];
        int[] prevLocal = new int[k + 1], prevGlobal = new int[k + 1];
        for (int i = 1; i < N; ++i) {
            prevLocal = local;
            prevGlobal = global;
            local = new int[k + 1];
            global = new int[k + 1];
            int diff = prices[i] - prices[i - 1];
            for (int j = 1; j <= k; ++j) {
                local[j] = Math.max(prevGlobal[j - 1], prevLocal[j]) + diff;
                global[j] = Math.max(local[j], prevGlobal[j]);
            }
        }
        return global[k];
    }

    int simpleMaxProfit(int[] prices) {
        int N = prices.length;
        if (N <= 1)
            return 0;
        int sum = 0;
        for (int i = 1; i < N; i++) {
            int diff = prices[i] - prices[i - 1];
            if (diff > 0)
                sum += diff;
        }
        return sum;
    }

    /* 最大乘积子序
     * @param nums: An array of integers
     * @return: An integer
     */
    public int maxProduct(int[] nums) {
        int posMax = nums[0];
        int negMax = nums[0];
        int ret = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int temPosMax = posMax;
            int temNegMax = negMax;
            posMax = Math.max(nums[i], Math.max(nums[i] * temNegMax, nums[i] * temPosMax));
            negMax = Math.min(nums[i], Math.min(nums[i] * temNegMax, nums[i] * temPosMax));
            ret = Math.max(ret, posMax);
        }
        return ret;
    }

    /* 硬币排成线 II
     * @param values: a vector of integers
     * @return: a boolean which equals to true if the first player will win
     */
    public boolean firstWillWin(int[] values) {
        if (values.length <= 2) {
            return true;
        }
        int len = values.length;
        int dp[] = new int[len + 1];
        dp[len] = 0;
        dp[len - 1] = values[len - 1];
        dp[len - 2] = values[len - 1] + values[len - 2];
        dp[len - 3] = values[len - 2] + values[len - 3];
        for (int i = len - 4; i >= 0; i--) {
            dp[i] = Math.max(values[i] + Math.min(dp[i + 2], dp[i + 3]), values[i] + values[i + 1] + Math.min(dp[i + 3], dp[i + 4]));
        }
        int sum = 0;
        for (int x : values) {
            sum += x;
        }
        return dp[0] > sum - dp[0];
    }

    /* 吹气球
    * @param nums: A list of integer
    * @return: An integer, maximum coins
    */
    public int maxCoins(int[] nums) {
        // Write your code here
        int len = nums.length;
        int[][] dp = new int[len + 2][len + 2];
        int[][] visit = new int[len + 2][len + 2];
        int[] numsPlus = new int[len + 2];

        for (int i = 1; i < len + 1; i++) {
            numsPlus[i] = nums[i - 1];
        }
        numsPlus[0] = 1;
        numsPlus[len + 1] = 1;

        int result = search(dp, visit, numsPlus, 1, len);

        return result;
    }

    public int search(int[][] dp, int[][] visit, int[] numsPlus, int start, int end) {
        if (visit[start][end] == 1) {
            return dp[start][end];
        }
        int res = 0;
        for (int i = start; i <= end; i++) {
            int mid = numsPlus[start - 1] * numsPlus[i] * numsPlus[end + 1];
            int right = search(dp, visit, numsPlus, start, i - 1);
            int left = search(dp, visit, numsPlus, i + 1, end);
            res = Math.max(res, mid + right + left);
        }
        visit[start][end] = 1;
        dp[start][end] = res;

        return res;
    }

    /* 接雨水2  矩阵接雨水
     * @param : a matrix of integers
     * @return: an integer
     */
    public int trapRainWater(int[][] heightMap) {
        //一个单元格用一个Cell来表示
        class Cell {
            int x, y, h;

            Cell(int x, int y, int height) {
                this.x = x;
                this.y = y;
                h = height;
            }
        }
        if (heightMap == null || heightMap.length == 0 || heightMap[0].length == 0) {
            return 0;
        }
        int m = heightMap.length;
        int n = heightMap[0].length;
        //优先队列，每次按照优先度输出队列，而不是按照顺序，这里是每次输出最矮的哪一个
        PriorityQueue<Cell> pq = new PriorityQueue<>((v1, v2) -> v1.h - v2.h);
        boolean[][] visited = new boolean[m][n];
        //将四周初始化为访问过的，周围的一边是怎么都没法盛水的
        for (int i = 0; i < n; i++) {
            visited[0][i] = true;
            visited[m - 1][i] = true;
            pq.offer(new Cell(0, i, heightMap[0][i]));
            pq.offer(new Cell(m - 1, i, heightMap[m - 1][i]));
        }
        for (int i = 1; i < m - 1; i++) {
            visited[i][0] = true;
            visited[i][n - 1] = true;
            pq.offer(new Cell(i, 0, heightMap[i][0]));
            pq.offer(new Cell(i, n - 1, heightMap[i][n - 1]));
        }
        //四个方向
        int[] xs = {0, 0, 1, -1};
        int[] ys = {1, -1, 0, 0};
        int sum = 0;
        //开始计算收集到的雨水，每次取出符合条件最矮的按个，然后计算差值，就是当前单元格可以容纳的了
        while (!pq.isEmpty()) {
            Cell cell = pq.poll();
            for (int i = 0; i < 4; i++) {
                int nx = cell.x + xs[i];
                int ny = cell.y + ys[i];
                if (nx >= 0 && nx < m && ny >= 0 && ny < n && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    sum += Math.max(0, cell.h - heightMap[nx][ny]);
                    pq.offer(new Cell(nx, ny, Math.max(heightMap[nx][ny], cell.h)));
                }
            }
        }
        return sum;
    }

    /* 跳跃游戏
     * @param A: A list of integers
     * @return: A boolean
     */
    public boolean canJump(int[] A) {
        int scope = 0;
        for(int i=0;i<A.length;i++){
            if(i>scope){
                return false;
            }
            scope = Math.max(scope,i+A[i]);
            if(scope>=A.length-1){
                return true;
            }
        }
        return true;
    }

    /* 跳跃游戏II
     * @param A: A list of integers
     * @return: An integer
     */
    public int jump(int[] A) {
        //return jumpfun(A,0);
        int[] dp = new int[A.length];
        dp[0] = 0;
        for(int i=1;i<A.length;i++){
            for(int j=0;j<i;j++){
                if(j+A[j]>=i){
                    dp[i] = dp[j]+1;
                    break;
                }
            }
        }
        return dp[A.length-1];
    }

    //TODO 运行超时
    private static int jumpfun(int[] arr, int count) {
        if(arr[0]>=arr.length-1){
            return count+1;
        }
        int re = Integer.MAX_VALUE;
        for(int i=1;i<=arr[0];i++){
            int[] tem = new int[arr.length-i];
            System.arraycopy(arr,i,tem,0,arr.length-i);
            re = Math.min(re,jumpfun(tem,1));
        }
        return re;
    }

    public static void main(String[] args) {
        ArraySolution sol = new ArraySolution();
        int[] nums = {4, 6, 1, 1, 4, 2, 5};
        System.out.println(sol.maxProfit(2, nums));
    }
}
