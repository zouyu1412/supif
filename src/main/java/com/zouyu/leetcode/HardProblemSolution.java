package com.zouyu.leetcode;

import java.util.*;

/**
 * Created by zouy-c on 2018/3/9.
 */
public class HardProblemSolution {

    public static void main(String[] args) {
        //LeetCodeUtil.printListNode(new HardProblemSolution().reverseKGroup(LeetCodeUtil.intArrayToListNode(new int[]{1,2,3,4,5}),2));
        new HardProblemSolution().insert(Arrays.asList(new Interval(1,5)),new Interval(6,8));

    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int sum = len1+len2;
        if(sum%2==1){
            return findKthNumber(nums1,0,len1,nums2,0,len2,sum/2+1);
        }else {
            return (double)(findKthNumber(nums1, 0, len1, nums2, 0, len2, sum / 2) + findKthNumber(nums1, 0, len1, nums2, 0, len2, sum / 2 + 1)) / 2;
        }
    }
    private int findKthNumber(int[] A, int startA, int endA, int[] B, int startB, int endB, int k) {
        int n = endA -startA;
        int m= endB-startB;
        if(n<=0){
            return B[startB+k-1];
        }
        if(m<=0){
            return A[startA+k-1];
        }
        if(k==1){
            return Math.min(A[startA],B[startB]);
        }
        int midA = (startA+endA)/2;
        int midB = (startB+endB)/2;
        if(A[midA]>B[midB]){
            if(n/2+m/2+1>=k) {
                return findKthNumber(A,startA,midA,B,startB,endB,k);
            }else {
                return findKthNumber(A, startA, endA, B, midB+1, endB, k -m/2 - 1);
            }
        }else{
            if(n/2+m/2+1>=k) {
                return findKthNumber(A,startA,endA,B,startB,midB,k);
            }else {
                return findKthNumber(A, midA+1, endA, B, startB, endB, k -n/2 - 1);
            }
        }
    }

    public boolean isMatch(String s, String p) {

        if (s == null || p == null) {
            return false;
        }
        boolean[][] dp = new boolean[s.length()+1][p.length()+1];
        dp[0][0] = true;
        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) == '*' && dp[0][i-1]) {
                System.out.println(i);
                dp[0][i+1] = true;
            }
        }
        for (int i = 0 ; i < s.length(); i++) {
            for (int j = 0; j < p.length(); j++) {
                if (p.charAt(j) == '.') {
                    dp[i+1][j+1] = dp[i][j];
                }
                if (p.charAt(j) == s.charAt(i)) {
                    dp[i+1][j+1] = dp[i][j];
                }
                if (p.charAt(j) == '*') {
                    if (p.charAt(j-1) != s.charAt(i) && p.charAt(j-1) != '.') {
                        dp[i+1][j+1] = dp[i+1][j-1];
                    } else {
                        dp[i+1][j+1] = (dp[i+1][j] || dp[i][j+1] || dp[i+1][j-1]);
                    }
                }
            }
        }
        return dp[s.length()][p.length()];
    }

    public ListNode mergeKLists(ListNode[] lists) {
        if(lists.length==0) return null;
        if(lists.length==1) return lists[0];
        if(lists.length==2) return mergeTwoLists(lists[0],lists[1]);
        ListNode re = new ListNode(0);
        int mid = lists.length/2;
        ListNode[] left = new ListNode[mid];
        ListNode[] right = new ListNode[lists.length-mid];
        System.arraycopy(lists,0,left,0,mid);
        System.arraycopy(lists,mid,right,0,lists.length-mid);
        ListNode le = mergeKLists(left);
        ListNode ri = mergeKLists(right);
        return mergeTwoLists(le,ri);
    }
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if(l1==null) return l2;
        if(l2==null) return l1;
        if(l1.val<l2.val){
            l1.next=mergeTwoLists(l1.next,l2);
            return l1;
        }else{
            l2.next=mergeTwoLists(l1,l2.next);
            return l2;
        }
    }

    //TODO 大神级代码
    public ListNode reverseKGroup(ListNode head, int k) {
        int n = 0;
        for (ListNode i = head; i != null; n++, i = i.next);

        ListNode dmy = new ListNode(0);
        dmy.next = head;
        for(ListNode prev = dmy, tail = head; n >= k; n -= k) {
            for (int i = 1; i < k; i++) {
                ListNode next = tail.next.next;
                tail.next.next = prev.next;
                prev.next = tail.next;
                tail.next = next;
            }
            System.out.println("===========");
            prev = tail;
            LeetCodeUtil.printListNode(prev);
            tail = tail.next;
            LeetCodeUtil.printListNode(tail);
            LeetCodeUtil.printListNode(dmy);
            System.out.println("=================");
        }
        return dmy.next;
    }

    //TODO 大神级代码
    public List<Integer> findSubstring(String S, String[] L) {
        List<Integer> result = new ArrayList<>();
        int size = L[0].length();
        if (L.length == 0 || L[0].isEmpty() || L[0].length() > S.length())
            return result;
        Map<String, Integer> hist = new HashMap<>();
        for (String w : L) {
            hist.put(w, !hist.containsKey(w) ? 1 : hist.get(w)+1);
        }
        for (int i = 0; i+size*L.length <= S.length(); i++) {
            if (hist.containsKey(S.substring(i, i+size))) {
                Map<String, Integer> currHist = new HashMap<>();
                for (int j = 0; j < L.length; j++) {
                    String word = S.substring(i+j*size, i+(j+1)*size);
                    currHist.put(word, !currHist.containsKey(word) ?
                            1 : currHist.get(word)+1);
                }
                if (currHist.equals(hist)) result.add(i);
            }
        }
        return result;
    }

    public int longestValidParentheses(String s) {
        Stack<Integer> stack = new Stack();
        int left = -1;
        int max = 0;
        for(int i= 0;i<s.length();i++){
            if(s.charAt(i)=='('){
                stack.push(i);
            }else{
                if(stack.isEmpty()){
                    left = i;
                }else{
                    stack.pop();
                    max = Math.max(max,stack.isEmpty()?(i-left):(i-stack.peek()));
                }
            }
        }
        return max;
    }

    public int firstMissingPositive(int[] nums) {
        for(int i=0;i<nums.length;){
            if(nums[i]<=0 || nums[i]>nums.length || nums[i] == i+1) i++;
            else if(nums[nums[i]-1] != nums[i]) nums[nums[i]-1] = nums[nums[i]-1]+nums[i]-(nums[i]=nums[nums[i]-1]);
            else i++;
        }
        LeetCodeUtil.printIntArr(nums);
        for(int i=0;i<nums.length;i++){
            if(nums[i]<=0 || nums[i]>nums.length || nums[nums[i]-1]!=i+1) return i+1;
        }
        return nums.length+1;
    }

    public int trap(int[] height) {
        if(height.length<=2) return 0;
        int sum = 0;
        int left = 0;
        int leftVal = Integer.MIN_VALUE;
        int rightVal = Integer.MIN_VALUE;
        int right = height.length-1;
        while(left<=right){
            if(height[left]<height[right]){
                leftVal = Math.max(height[left],leftVal);
                sum +=(leftVal-height[left++]);
            }else{
                rightVal = Math.max(height[right],rightVal);
                sum +=(rightVal-height[right--]);
            }
        }
        return sum;
    }

    public boolean isMatch2(String s, String p) {
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;
        for (int i = 1; i <= p.length(); i++) {
            dp[0][i] = dp[0][i - 1] && p.charAt(i - 1) == '*';
        }
        for (int j = 1; j <= p.length(); j++) {
             for (int i = 1; i <= s.length(); i++) {
                if(p.charAt(j-1) == s.charAt(i-1)) dp[i][j] = dp[i-1][j-1];
                else if(p.charAt(j-1) == '?') dp[i][j] = dp[i-1][j-1];
                else if(p.charAt(j-1) == '*') {
                    dp[i][j] = (dp[i - 1][j - 1] || dp[i][j - 1] || dp[i-1][j]);
                }
            }
        }
        return dp[s.length()][p.length()];
    }

    public int jump(int[] nums) {
        int maxDis = 0;   //当前最远的距离
        int max = 0;
        int count = 0;   //当前最远距离需要的步数
        for(int i=0;i<nums.length-1;i++){
            max = Math.max(nums[i]+i,max);
            if(max == nums.length-1) return count+1;
            if(i == maxDis){
                maxDis = max;
                count++;
            }
        }
        return count;
    }

    public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> res = new ArrayList<>();
        int i=0;
        while(i<intervals.size() && intervals.get(i).end < newInterval.start){
            res.add(intervals.get(i));
            i++;
        }
        while(i<intervals.size() && intervals.get(i).start < newInterval.end){
            newInterval = new Interval(Math.min(intervals.get(i).start,newInterval.start),
                    Math.max(intervals.get(i).end,newInterval.end));
            i++;
        }
        res.add(newInterval);
        while(i<intervals.size()){
            res.add(intervals.get(i++));
        }
        return res;
    }

    public String minWindow(String s, String t) {
        int[] chars = new int[128];
        int count = t.length();
        int begin = 0;
        int end = 0;
        int d = Integer.MAX_VALUE;
        int head = 0;
        for(char c:t.toCharArray()){
            chars[c]++;
        }
        while(end<s.length()){
            if(chars[s.charAt(end++)]-->0) count--;
            while(count==0){
                if(end-begin<d) d=end-(head=begin);
                if(chars[s.charAt(begin++)]++==0) count++;
            }
        }
        return d==Integer.MAX_VALUE?"":s.substring(head,head+d);
    }

    public int largestRectangleArea(int[] heights) {
        int len = heights.length;
        Stack<Integer> stack = new Stack();
        int maxArea = 0;
        for(int i=0;i<=len;i++){
            int cur = (i==len?0:heights[i]);
            if(stack.isEmpty() || cur>=heights[stack.peek()]){
                stack.push(i);
            }else{
                int tem = stack.pop();
                maxArea = Math.max(maxArea,heights[tem]*(stack.isEmpty()?i:i-1-stack.peek()));
                i--;
            }
        }
        return maxArea;
    }

    //TODO 运行超时 55/66 pass
    public int maximalRectangle(char[][] matrix) {
        if(matrix==null || matrix.length==0) return 0;
        return maxRT(matrix,0,matrix.length-1,0,matrix[0].length-1);
    }

    private int maxRT(char[][] matrix, int ls, int le, int rs, int re) {
        if(ls>le || rs>re || ls<0 || le >= matrix.length || rs <0 || re >= matrix[0].length) return 0;
        boolean canFill = true;
        for(int i=ls;i<=le;i++){
            for(int j=rs;j<=re;j++){
                if(matrix[i][j] == '0'){
                    return Math.max(Math.max(maxRT(matrix,ls,i-1,rs,re),maxRT(matrix,i+1,le,rs,re)),Math.max(maxRT(matrix,ls,le,rs,j-1),maxRT(matrix,ls,le,j+1,re)));
                }
            }
        }
        return (le-ls+1)*(re-rs+1);
    }
}
