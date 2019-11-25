package com.zouyu.leetcode;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by zouy-c on 2018/3/9.
 */
public class HardProblemSolution {

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

    //文章格式化
    public List<String> fullJustify(String[] words, int maxWidth) {
        int wordNum = words.length;
        List<String> result = new ArrayList<>();
        List<List<String>> list = new ArrayList<>();
        int leftLength = maxWidth;
        List<String> oneLineWords = new ArrayList<>();

        for(int i=0;i<wordNum;i++){
            String curStr = words[i];
            if(oneLineWords.size()==0){
                oneLineWords.add(curStr);
                leftLength -= curStr.length();
                if(i==wordNum-1){
                    list.add(oneLineWords);
                }
                continue;
            }
            if(curStr.length()+1<=leftLength){
                oneLineWords.add(curStr);
                leftLength-=(curStr.length()+1);
                if(i==wordNum-1){
                    list.add(oneLineWords);
                }
            }else{
                list.add(new ArrayList<>(oneLineWords));
                oneLineWords = new ArrayList<>();
                leftLength = maxWidth;
                i--;
            }
        }
        for(int i=0;i<list.size();i++){
            String curLine = "";
            List<String> cur = list.get(i);
            if(i==list.size()-1){
                for(int j=0;j<cur.size();j++){
                    if(j==0){
                        curLine += cur.get(j);
                    }else {
                        curLine += (" " + cur.get(j));
                    }
                }
                while(curLine.length()<maxWidth){
                    curLine+=" ";
                }
                result.add(curLine);
            }else{
                result.add(fillLineWithWord(curLine,cur,maxWidth));
            }
        }
        return result;
    }

    private String fillLineWithWord(String curLine, List<String> cur, int maxWidth) {
        StringBuilder sb = new StringBuilder();
        if(cur.size()==1){
            sb.append(cur.get(0));
            while(sb.length()<maxWidth){
                sb.append(" ");
            }
            return sb.toString();
        }
        int wLen = 0;
        for(int i = 0;i<cur.size();i++){
            wLen += cur.get(i).length();
        }
        int nullSize = maxWidth - wLen;
        int r = nullSize/(cur.size()-1);
        int d = nullSize%(cur.size()-1);
        for(int i=0;i<cur.size();i++){
            if(i == 0){
                sb.append(cur.get(i));
            }else {
                int curNullSize = r + (i<=d?1:0);
                for(int j=0;j<curNullSize;j++){
                    sb.append(" ");
                }
                sb.append(cur.get(i));
            }
        }
        return sb.toString();
    }

    //单词汉明距离 插入 替换 删除
    public int minDistance(String word1, String word2) {
        if(StringUtils.isEmpty(word1)){
            return word2.length();
        }
        if(StringUtils.isEmpty(word2)){
            return word1.length();
        }
        int[][] dp = new int[word1.length()+1][word2.length()+1];
        for(int i = 1;i<dp.length;i++){
            for(int j= 1;j<dp[0].length;j++){
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    dp[i][j] = 1+Math.min(dp[i-1][j],Math.min(dp[i][j-1],dp[i-1][j-1])-1);
                }else{
                    dp[i][j] = 1+Math.min(dp[i-1][j],Math.min(dp[i][j-1],dp[i-1][j-1]));
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }

    /**
     * 加血  掉血  只能往下 往右   求最少的初始血量
     *  -2    3    -3
     *  -5    -10   1
     *  10    30    -5
     * @param dungeon
     * @return
     */
    public int calculateMinimumHP(int[][] dungeon) {
        int row = dungeon.length;
        int col = dungeon[0].length;
        int[][] dp = new int[row][col];
        dp[row-1][col-1] = Math.max(1-dungeon[row-1][col-1],1);
        for(int i = row -2; i>=0; i--){
            dp[i][col -1] = Math.max(dp[i+1][col-1] - dungeon[i][col-1],1);
        }
        for(int i = col -2; i>=0; i--){
            dp[row-1][i] = Math.max(dp[row-1][i+1] - dungeon[row-1][i],1);
        }
        for(int i=row-2;i>=0;i--){
            for(int j=col-2;j>=0;j--){
                dp[i][j] = Math.max(Math.min(dp[i+1][j],dp[i][j+1])-dungeon[i][j],1);
            }
        }
        return dp[0][0];
    }

    /**
     * 求最大的夹缝   linear
     * Input: [3,6,9,1]
     * Output: 3
     * Explanation: The sorted form of the array is [1,3,6,9], either
     *              (3,6) or (6,9) has the maximum difference 3
     * @param nums
     * @return
     */
    public int maximumGap(int[] nums) {
        if(nums == null || nums.length <=1){
            return 0;
        }
        int len = nums.length;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for(int x:nums){
            max = Math.max(max,x);
            min = Math.min(min,x);
        }
        if(max == min){
            return 0;
        }
        int bucketSize = (max-min)/len+1;
        int bucketNum = (max-min)/bucketSize+1;
        Bucket[] buckets = new Bucket[bucketNum];
        for(int i=0;i<bucketNum;i++){
            buckets[i] = new Bucket();
        }
        for(int i=0;i<len;i++){
            int ind = (nums[i]-min)/bucketSize;
            if(buckets[ind].isUsed()){
                buckets[ind].setLeft(Math.min(nums[i],buckets[ind].getLeft()));
                buckets[ind].setRight(Math.max(nums[i],buckets[ind].getRight()));
            }else{
                buckets[ind].setLeft(nums[i]);
                buckets[ind].setRight(nums[i]);
                buckets[ind].setUsed(true);
            }
        }
        int answer = buckets[0].getRight()-buckets[0].getLeft();
        int preRight = buckets[0].getRight();
        for(int i=1;i<bucketNum;i++){
            if(buckets[i].isUsed()){
                answer = Math.max(buckets[i].getRight()-buckets[i].getLeft(),answer);
                answer = Math.max(buckets[i].getLeft()-preRight,answer);
                preRight = buckets[i].getRight();
            }
        }
        return answer;
    }
    private class Bucket{
        boolean isUsed;
        int left;
        int right;
        public boolean isUsed() {
            return isUsed;
        }

        public void setUsed(boolean used) {
            isUsed = used;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public int getRight() {
            return right;
        }

        public void setRight(int right) {
            this.right = right;
        }
    }

    /** TODO 接medium难度 课程1 2
     * 课程3  [t,d] t->持续时间  d->多少天内完成课程   求最多可以完成多少门课程
     * Input: [[100, 200], [200, 1300], [1000, 1250], [2000, 3200]]
     * Output: 3
     * @param courses
     * @return
     */
    public int scheduleCourse(int[][] courses) {
        Map<Integer,Integer> map = new TreeMap<>(Integer::compareTo);
        Map<Integer,Map.Entry> courseMap = new HashMap<>();
        boolean[] visit = new boolean[courses.length];
        int maxDay = 0;
        for(int i=0;i<courses.length;i++){
            map.put(courses[i][1],courses[i][0]);
            maxDay = Math.max(maxDay,courses[i][1]);
        }
        int ind = 1;
        for(Map.Entry entry:map.entrySet()){
            courseMap.put(ind++,entry);
        }
        int[][] dp = new int[courses.length+1][maxDay+1];
        for(int i=1;i<=courses.length;i++){
            Map.Entry entry = courseMap.get(i);
            int dayLine = (int)entry.getKey();
            int duration = (int)entry.getValue();
            for(int j=1;j<=maxDay;j++){
                if(j<=dayLine){
                    if(duration<=j && !visit[i-1]){
                        dp[i][j] = Math.max(Math.max(dp[i-1][j],dp[i][j-1]),dp[i][j-duration]+1);
                        visit[i-1] = true;
                    }else{
                        dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
                    }
                }else{
                    dp[i][j] = dp[i][dayLine];
                }
            }
            pri(dp);

        }

        return dp[courses.length][maxDay];
    }

    /**
     * Write a one-pass algorithm with O(1) extra space to determine, if your path crosses itself, or not.
     * Example 1:
     *
     * ┌───┐
     * │      │
     * └───┼──>
     *         │
     *
     * Input: [2,1,1,2]
     * Output: true
     * @param x
     * @return
     * TODO master code
     * public boolean isSelfCrossing(int[] x) {
     *         if(x.length < 4)    return false;
     *         int i = 0, base = 0;
     *         while(i+4 <= x.length){
     *             if(x[i] - x[i+2] >= 0 && x[i+1] - x[i+3] <= base)  return true;
     *             else if(x[i+1] - x[i+3] > base)     base = 0;
     *             else    base = x[i];
     *             i++;
     *         }
     *         return false;
     *     }
     */
    public boolean isSelfCrossing(int[] x) {
        int curX = 0;
        int curY = 0;
        Set<Point> points = new HashSet<>();
        points.add(new Point(curX,curY));
        for(int i=0;i<x.length;i++){
            if(i%4 == 0){
                for(int j=curY+1;j<=curY+x[i];j++){
                    if(!points.add(new Point(curX,j))){
                        return true;
                    }
                }
                curY += x[i];
            }else if(i%4 == 1){
                for(int j=curX-1;j>=curX-x[i];j--){
                    if(!points.add(new Point(j,curY))){
                        return true;
                    }
                }
                curX -= x[i];
            }else if(i%4 == 2){
                for(int j=curY-1;j>=curY-x[i];j--){
                    if(!points.add(new Point(curX,j))){
                        return true;
                    }
                }
                curY -= x[i];
            }else if(i%4 == 3){
                for(int j=curX+1;j<=curX+x[i];j++){
                    if(!points.add(new Point(j,curY))){
                        return true;
                    }
                }
                curX += x[i];
            }
        }
        return false;
    }
    private class Point{
        int x;
        int y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Point){
                Point other = (Point) obj;
                return other.x == this.x && other.y == this.y;
            }
            return false;
        }
        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }


    /**
     * 求右侧比当前元素小的个数
     * Input: [5,2,6,1]
     * Output: [2,1,1,0]
     * @param nums
     * @return
     */
    public List<Integer> countSmaller(int[] nums) {
        return null;
    }

    public static void main(String[] args) {
        //LeetCodeUtil.printListNode(new HardProblemSolution().reverseKGroup(LeetCodeUtil.intArrayToListNode(new int[]{1,2,3,4,5}),2));
//        System.out.println(new HardProblemSolution().isSelfCrossing(new int[]{2,1,1,2}));
//        System.out.println(new HardProblemSolution().scheduleCourse(new int[][]{{1,2},{4,15},{10,14},{20,32}}));
    }

    private void pri(int[][] x){
        for(int i=0;i<x.length;i++){
            System.out.println(Arrays.toString(x[i]));
        }
        System.out.println("======================");
    }
}
