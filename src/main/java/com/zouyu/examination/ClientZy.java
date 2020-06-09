package com.zouyu.examination;

import com.zouyu.leetcode.ListNode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author:zouyu
 * @Date:2020/5/12 18:43
 */
public class ClientZy {

    public static void main(String[] args) {

//        MySingleTon instance = MySingleTon.getInstance();
//        System.out.println(instance.hashCode());
//        int[] x = new int[]{0,3,-1,4,2,0,-1,334,0,31,3,-3,-11};
//        System.out.println(Arrays.toString(x));
//        sortQuick(x, 0, x.length-1);
//        System.out.println(Arrays.toString(x));
//        String s = longestPalindrome("abcda");
//        List<Integer> anagrams = findAnagrams("abab", "ab");
//        searchInsert(new int[]{1,3,5},3);
//        BigDecimal i = new ClientZy().get();
//        System.out.println(i);
        int i1 = NumberOf1Between1AndN_Solution(99);
        System.out.println(i1);
        int i2 = NumberOf1Between1AndN_Solution(999);
        System.out.println(i2);
        int i3 = NumberOf1Between1AndN_Solution(9999);
        System.out.println(i3);
        int i4 = NumberOf1Between1AndN_Solution(99999);
        System.out.println(i4);
        int i5 = NumberOf1Between1AndN_Solution(999999);
        System.out.println(i5);
        int i6 = NumberOf1Between1AndN_Solution(9999999);
        System.out.println(i6);
    }

        public static int NumberOf1Between1AndN_Solution(int n) {
            int res = 0,len=1,num=n;
            while((num/10)!=0){
                len++;
                num = num/10;
            }//求出是几位数
            res = doSum(n,len);
            return res;
        }
        public static int  doSum(int end,int len) {
            //若为1~0或者长度为0，直接返回0
            if(end == 0||len==0)return 0;
            //只剩一位的情况
            if(len == 1 && end ==0)return 0;
            if(len == 1 && end > 0)return 1;

            int high,rest;
            int pow = (int)Math.pow(10, len-1);//10^(len-1)
            int mul = end/pow;//最高位
            //求最高位上1的个数
            if(mul==1)
                high = end-pow+1;
            else high = pow;
            rest = mul * (len-1)*(pow/10);//除最高位以外，剩余位出现1的个数
            //和的前两项为（end%pow+1 ~ end）中1出现的次数
            //和的最后一项(1 ~ end%pow)中1的出现的次数
            return high + rest +doSum(end-mul*pow,len-1);
        }


    static void quickSort(int[] arr, int from, int to){
        if(from >= to){
            return;
        }
        int key = arr[from];
        int start = from;
        int end = to;
        while(start<end) {
            while (start < end && arr[end] >= key) {
                end--;
            }
            while (start < end && arr[start] <= key) {
                start++;
            }

            arr[start] = arr[end] + arr[start] - (arr[end] = arr[start]);
        }
        arr[end] = arr[from]+arr[end]-(arr[from]=arr[end]);
        quickSort(arr, from, end-1);
        quickSort(arr, end+1, to);
    }

    static void insertOrder(int[] arr){
        int tem = 0;
        int ind = 0;
        for(int i=1;i<arr.length;i++){
            tem = arr[i];
            ind = i;
            while(ind>=1 && arr[ind-1]>tem){
                arr[ind] = arr[ind-1];
                ind--;
            }
            arr[ind] = tem;
        }
    }

    public static int numOfSubarrays(int[] arr, int k, int threshold) {
        int limit = k*threshold;
        int start = 0;
        int end = 0;
        int re = 0;
        int curSum = 0;
        while(end-start+1<=k){
            curSum += arr[end];
            end++;
        }
        for(;end<arr.length;end++,start++){

            if(curSum>=limit){
                re++;
            }
            curSum+=arr[end];
            curSum-=arr[start];
        }
        if(curSum>limit){
            re++;
        }
        return re;
    }

    public BigDecimal get(){
        BigDecimal sum = new BigDecimal("0");
        BigDecimal lastSum = new BigDecimal("1");
        int from = 1;
        Set<Integer> history = new HashSet<>();
        for(int i=2;i<100;i++){
            if(isSuShu(i, history)){
                BigDecimal cur = lastSum.multiply(new BigDecimal(getJieCheng(from, i)));
                sum = sum.add(cur);
                lastSum = cur;
                from = i+1;
                history.add(i);
            }
        }
        return sum;
    }

    private boolean isSuShu(int x, Set<Integer> history) {
        if(x == 2 ){
            return true;
        }
        for(int item:history){
            if(x % item == 0){
                return false;
            }
        }
        return true;
    }

    public long getJieCheng(int from, int to){
        long re = 1;
        for(int i = from;i<=to;i++){
            re*=i;
        }
        return re;
    }

    public static int searchInsert(int[] nums, int target) {
        if(target<=nums[0]){
            return 0;
        }else if(target>nums[nums.length-1]){
            return nums.length;
        }
        int s = 0;
        int e = nums.length-1;

        while(s<=e){
            int mid = (s+e)/2;
            if(nums[mid]>target){
                e = mid -1;
            }else if(nums[mid]<target){
                return mid+1;
            }else{
                return mid;
            }
        }
        return s;
    }

    public static String longestPalindrome(String s) {
        if(s == null || s.length() ==0){
            return s;
        }
        boolean[][] dp = new boolean[s.length()][s.length()];
        for(int i=0;i<s.length();i++){
            dp[i][i] = true;
        }

        for(int i=0;i<dp.length;i++){
            for(int j=0;j<dp.length;j++){
                if((j>=i&&j-i<=3)||(i>j&&i-j<=3)){
                    dp[i][j] = s.charAt(i) == s.charAt(j);
                    dp[j][i] = dp[i][j];
                }else{
                    dp[i][j] = j==0?(dp[1][i-1]&&(s.charAt(i)==s.charAt(0))):(dp[i+1][j-1] && (s.charAt(i)==s.charAt(j)));
                    dp[j][i] = dp[i][j];
                }
                System.out.println("i========"+i+" j========"+j);
                printArr(dp);
//                if(dp[i][j] && j-i+1>max){
//                    max = j-i+1;
//                    start = i;
//                    end = j;
//                }
            }
        }

        String res = "";
        for(int i =0;i<dp.length;i++){
            for(int j=i;j<dp.length;j++){
                if(dp[i][j] && s.substring(i,j+1).length()>res.length()){
                    res = s.substring(i, j+1);
                }
            }
        }
        return res;
    }

    public static void printArr(boolean[][] bos){
        for(int i=0;i<bos.length;i++){
            for(int j=0;j<bos[0].length;j++){
                System.out.print(bos[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("===============================");
    }

    public static List<Integer> findAnagrams(String s, String p) {
        int start = 0;
        int end = 0;
        List<Integer> res = new ArrayList<>();
        int[] map = new int[26];
        for(char c:p.toCharArray()){
            map[c-'a']++;
        }
        int count = p.length();
        while(end<s.length()){
            char c = s.charAt(end++);
            if(map[c-'a']-->0){
                count--;
            }
            if(end-start == p.length()){
                if(count == 0){
                    res.add(start);
                }
                if(map[s.charAt(start++)-'a']++>=0){
                    count++;
                }
            }
        }
        return res;
    }

    private static void sortMyWay(int[] x){
        if(x == null|| x.length == 0){
            return;
        }
        int le = 0;
        int ri = x.length-1;
        for(int i=0;i<ri;i++){
            if(x[i]==0){
                continue;
            }else if(x[i]>0){
                x[i] = x[i]+x[ri]-(x[ri]=x[i]);
                ri--;
                i--;
            }else{
                x[i] = x[i]+x[le]-(x[le]=x[i]);
                le ++;
            }
        }

    }

    private static void sortQuick(int[] x, int startInd, int endInd){
        if(startInd>=endInd){
            return;
        }
        int left = startInd;
        int right = endInd;
        int key = x[startInd];
        while(left<right){
            while(x[left]<=key && left<right){
                left++;
            }
            while (x[right]>=key && left<right){
                right--;
            }
            x[left] = x[left]+x[right] - (x[right]=x[left]);
        }
        x[right] = x[right]+x[startInd]-(x[startInd]=x[right]);
        sortQuick(x,startInd,left-1);
        sortQuick(x,right+1,endInd);

    }

    private ListNode reverse(ListNode head){
        ListNode pre = null;
        while(head != null){
            ListNode tem = head.next;
            head.next = pre;
            pre = head;
            head = tem;
        }
        return pre;
    }

    /**
     * Input: 38
     * Output: 2
     * Explanation: The process is like: 3 + 8 = 11, 1 + 1 = 2.
     *              Since 2 has only one digit, return it.
     * 数字 各个位求和 最后只有一位 输出
     * @param num
     * @return
     * TODO 大神一行
     * return (1 + (num - 1) % 9);
     * return num == 0 ? 0 : num % 9 == 0 ? 9 : num % 9;
     */
}
