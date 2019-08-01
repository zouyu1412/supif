package com.zouyu.leetcode;

import java.util.*;

import static java.lang.Math.abs;

/**
 * Created by zouy-c on 2018/3/9.
 */
public class EasyProblemSolution {

    public int[] twoSumI(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                System.out.println(new int[]{map.get(target - nums[i]), i});
            } else {
                map.put(nums[i], i);
            }
        }
        return null;
    }

    public int reverse(int x) {
        int sign = x < 0 ? -1 : 1;
        x = abs(x);
        int res = 0;
        while (x > 0) {
            if (Integer.MAX_VALUE / 10 < res || (Integer.MAX_VALUE - x % 10) < res * 10) {
                return 0;
            }
            res = res * 10 + x % 10;
            x /= 10;
        }

        return sign * res;
    }

    public boolean isPalindrome(int x) {
        if (x<0 || (x!=0 && x%10==0)) return false;
        int rev = 0;
        while (x>rev){
            rev = rev*10 + x%10;
            x = x/10;
        }
        return (x==rev || x==rev/10);
    }

    public int romanToInt(String s) {
        Map<Character,Integer> map = new HashMap<>();
        map.put('I',1);
        map.put('V',5);
        map.put('X',10);
        map.put('L',50);
        map.put('C',100);
        map.put('D',500);
        map.put('M',1000);
        int re = 0;
        for(int i=0;i<s.length();i++){
            if(map.get(s.charAt(i))>map.get(s.charAt(i+1))){
                re += map.get(s.charAt(i));
            }else{
                re -= map.get(s.charAt(i));
            }
        }
        return re+map.get(s.charAt(s.length()-1));
    }

    public String longestCommonPrefix(String[] strs) {
        if(strs==null||strs.length==0){
            return "";
        }
        Arrays.sort(strs);
        String start = strs[0];
        String end = strs[strs.length-1];
        int index = 0;
        if(end.startsWith(start))
            return start;
        while(start.charAt(index) == end.charAt(index))
            index++;
        return start.substring(0,index);
    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        for (char c : s.toCharArray()) {
            if (c == '(')
                stack.push(')');
            else if (c == '{')
                stack.push('}');
            else if (c == '[')
                stack.push(']');
            else if (stack.isEmpty() || stack.pop() != c)
                return false;
        }
        return stack.isEmpty();
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode re = new ListNode(0);
        ListNode po = re;
        if(l1==null) return l2;
        if(l2==null) return l1;
        while(l1!=null || l2!=null){
            if(l2==null) {po.next = l1;break;};
            if(l1==null) {po.next = l2;break;};
            if(l1.val>l2.val){
                po.next = l2;
                l2 = l2.next;
            }else{
                po.next = l1;
                l1 = l1.next;
            }
            po = po.next;
        }
        return re.next;
    }

    public int removeDuplicates(int[] A) {
        if (A.length==0) return 0;
        int j=0;
        for (int i=0; i<A.length; i++)
            if (A[i]!=A[j]) A[++j]=A[i];
        return ++j;
    }

    public int removeElementMy(int[] nums, int val) {
        if(nums.length == 0) return 0;
        int re = 0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]==val){
                for(int j=i;j<nums.length;j++){
                    if(nums[j]!=val){
                        int tem = nums[j];
                        nums[j] = nums[i];
                        nums[i] = tem;
                        re++;
                        break;
                    }
                }
            }else{
                re++;
            }
        }
        return re;
    }
    //破坏了原来数组
    public int removeElement(int[] A, int elem) {
        int m = 0;
        for(int i = 0; i < A.length; i++){
            if(A[i] != elem){
                A[m] = A[i];
                m++;
            }
        }
        return m;
    }

    public int strStr(String haystack, String needle) {
        for (int i = 0; ; i++) {
            for (int j = 0; ; j++) {
                if (j == needle.length()) return i;
                if (i + j == haystack.length()) return -1;
                if (needle.charAt(j) != haystack.charAt(i + j)) break;
            }
        }
    }

    public int searchInsert(int[] nums, int target) {
        int le = 0;
        int ri = nums.length;
        while(le<=ri){
            int mid = (le+ri)/2;
            if(nums[mid]==target){
                if(mid==0 || nums[mid-1]!=nums[mid]) return mid;
                ri = mid-1;
            }else if(nums[mid]>target){
                if(mid==0 || nums[mid-1]<target) return mid;
                ri = mid-1;
            }else{
                if(mid == nums.length-1) return mid+1;
                if(nums[mid+1]>=target) return mid+1;
                le = mid+1;
            }
        }
        return 0;
    }

    public String countAndSay(int n) {
        String[] res = new String[n];
        res[0] = "1";
        for(int i=1;i<n;i++){
            res[i] = trsf(res[i-1]);
        }
        return res[n-1];
    }
    private String trsf(String s) {
        StringBuilder sb = new StringBuilder();
        char cur = 0;
        int count = 0;
        for(int i=0;i<s.length();i++){
            if(i == 0){
                cur = s.charAt(i);
                count = 1;
            }else {
                if(s.charAt(i) == s.charAt(i-1)){
                    count++;
                }else{
                    sb.append(count+""+cur);
                    cur = s.charAt(i);
                    count = 1;
                }
            }
        }
        sb.append(count+""+cur);
        return sb.toString();
    }

    public int maxSubArrayⅠ(int[] nums) {
        int len = nums.length;
        int[] all = new int[len];//all[i] i后最大值
        int[] start = new int[len];//start[i] i起始最大值
        start[len-1] = nums[len-1];
        all[len-1] = nums[len-1];
        for(int i=len-2;i>=0;i--){
            start[i] = Math.max(nums[i]+start[i+1],nums[i]);
            all[i] = Math.max(start[i],all[i+1]);
        }
        return all[0];
    }
    //大神代码
    public static int maxSubArrayⅡ(int[] A) {
        int maxSoFar=A[0], maxEndingHere=A[0];
        for (int i=1;i<A.length;++i){
            maxEndingHere= Math.max(maxEndingHere+A[i],A[i]);
            maxSoFar=Math.max(maxSoFar, maxEndingHere);
        }
        return maxSoFar;
    }

    //等效代码  s.trim().length()-s.trim().lastIndexOf(" ")-1;
    public int lengthOfLastWord(String s) {
        int count = 0;
        for(int i = s.length()-1;i>=0;i--){
            if(s.charAt(i)!=' '){
                count++;
            }else{
                if(count>0) return count;
                count=0;
            }
        }
        return count;
    }

    public String addBinary(String a, String b) {
        if(a.length()>b.length()){
            return addBinary(b,a);
        }
        if(a.length() == 0){
            return b;
        }
        StringBuilder sb = new StringBuilder();
        int sum = 0;
        for(int i=a.length()-1,j=b.length()-1;j>=0;i--,j--){
            if(i>=0) sum+=a.charAt(i)-'0';
            if(j>=0) sum+=b.charAt(j)-'0';
            sb.append(sum%2);
            sum = sum/2;
        }
        if(sum!=0) sb.append(sum);
        return sb.reverse().toString();
    }

    public int mySqrt(int x) {
        if(x==0) return 0;
        int start = 1;
        int end = x;
        int mid;
        while(true){
            mid = (start+end)/2;
            if(mid > x/mid){
                end = mid - 1;
            }else{
                if(mid+1>x/(mid+1))
                    return mid;
                start = mid + 1;
            }
        }
    }

//    public int climbStairs(int n) {
//        int a = 1, b = 1;
//        while (n-- > 0)
//            a = (b += a) - a;
//        return a;
//    }
    public int climbStairs(int n) {
        if(n==1) return 1;
        if(n==2) return 2;
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = 2;
        for(int i=2;i<n;i++){
            dp[i] = dp[i-1]+dp[i-2];
        }
        return dp[n-1];
    }

    public ListNode deleteDuplicates(ListNode head) {
        ListNode re = new ListNode(0);
        re.next = head;
        int cur = head.val;
        while(head.next != null){
            while(head.next.val==cur){
                head.next = head.next.next;
            }
            cur = head.val;
            head = head.next;
        }
        return re.next;
    }

    //杨辉三角
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> preList = new ArrayList<>();
        while(numRows-->0){
            List<Integer> curList = new ArrayList<>();
            if(preList.isEmpty()){
                curList.add(1);

            }else{
                curList.add(1);
                for(int i=0;i<preList.size()-1;i++){
                    curList.add(preList.get(i)+preList.get(i+1));
                }
                curList.add(1);
            }
            result.add(curList);
            preList = curList;
        }
        return result;
    }

    //寻找最短路径   2-> 3 -> 5 -> 1 = 11
    //     [2],
    //    [3,4],
    //   [6,5,7],
    //  [4,1,8,3]
    //递归方式 ：超时     自上而下，求每个位置的最优解，然后遍历最后一行 取最小值即可
    public int minimumTotal(List<List<Integer>> triangle) {
        if(triangle == null || triangle.size() == 0){
            return 0;
        }
        int[][] dp = new int[triangle.size()][triangle.size()];
        for(int i=0;i<triangle.size();i++){
            for(int j=0;j<=i;j++){
                dp[i][j] = triangle.get(i).get(j);
            }
        }
        for(int i = 1;i<triangle.size();i++){
            for(int j = 0;j<=i;j++){
                if(j==0 || j==i){
                    dp[i][j] += dp[i-1][j==0?0:i-1];
                }else {
                    dp[i][j] += Math.min(dp[i-1][j-1],dp[i-1][j]);
                }
            }
        }
        int result = Integer.MAX_VALUE;
        for(int i=0;i<triangle.get(triangle.size()-1).size();i++){
            result = Math.min(dp[triangle.size()-1][i],result);
        }
        return result;
    }
//    public int minimumTotal(List<List<Integer>> triangle) {
//        if(triangle == null || triangle.size() == 0){
//            return 0;
//        }
//        Integer firstVal = triangle.get(0).get(0);
//        if(triangle.size()==1){
//            return firstVal;
//        }
//        return recu(triangle,firstVal,0,1,triangle.size());
//    }
//    private int recu(List<List<Integer>> tri,int curVal,int ind,int row,int height){
//        List<Integer> integers = tri.get(row);
//        if(row == height-1){
//            return curVal+Math.min(integers.get(ind),integers.get(ind+1));
//        }
//        return Math.min(recu(tri,curVal+integers.get(ind),ind,row+1,height),
//                recu(tri,curVal+integers.get(ind+1),ind+1,row+1,height));
//    }

    //Best Time to Buy and Sell Stock 一次买卖
    public int maxProfit(int[] prices) {
        if(prices == null || prices.length == 0) return 0;
        int result = Integer.MIN_VALUE;
        int curMin = Integer.MAX_VALUE;
        for(int i=0;i<prices.length;i++){
            curMin = Math.min(prices[i],curMin);
            result = Math.max(prices[i]-curMin,result);
        }
        return result;
    }

    //Best Time to Buy and Sell Stock II  多次买卖
    public int maxProfit2(int[] prices) {
        if(prices == null || prices.length <= 1) return 0;
        int result = 0;
        int curMin = prices[0];
        int curVal = 0;
        for(int i=1;i<prices.length;i++){
            if(prices[i]>=prices[i-1]){
                curVal = prices[i]-curMin;
            }else{
                curMin = prices[i];
                result += curVal;
                curVal = 0;
            }
        }
        result += curVal;
        return result;
    }

    //Best Time to Buy and Sell Stock III  k次买卖
    public int maxProfit3(int[] prices,int k) {
        if(k == 0 || prices == null || prices.length < 2){
            return 0;
        }
        Queue<Integer> queue = new PriorityQueue<>((o1,o2)-> o2.compareTo(o1));
        int curProfit = 0;
        int curMin = prices[0];
        for(int i=1;i<prices.length;i++){
            int tem = prices[i];
            if(tem < curMin){
                curMin = tem;
                if(curProfit > 0){
                    queue.offer(curProfit);
                    curProfit = 0;
                }
            }else{
                curProfit = tem - curMin;
            }
        }
        return 0;
    }
//    大神代码 两次机会
//    public int maxProfit(int[] prices) {
//        if (prices == null || prices.length <= 1) return 0;
//        int len = prices.length;
//        int[] dp = new int[5];
//        for (int i = len - 1; i >= 0; i--) {
//            dp[4] = Math.max(dp[4], dp[3] - prices[i]);//have (2 buy, 2 sell) left
//            dp[3] = Math.max(dp[3], dp[2] + prices[i]);//have (1 buy, 2 sell) left
//            dp[2] = Math.max(dp[2], dp[1] - prices[i]);//have (1 buy, 1 sell) left
//            dp[1] = Math.max(dp[1], prices[i]);//have (0 buy, 1 sell) left
//            //have (0 buy, 0 sell) left --> dp[0] = 0; no need to update value
//        }
//        return Math.max(dp[2], dp[4]); //slect the max of 1 transaction and 2 transactions
//    }

    //链表成环判断
    public boolean hasCycle(ListNode head) {
        if(head == null){
            return false;
        }
        ListNode node1 = head;
        ListNode node2 = head;
        while(node1.next != null && node2.next != null && node2.next.next != null){
            node1 = node1.next;
            node2 = node2.next.next;
            if(node1 == node2){
                return true;
            }
        }
        return false;
    }

    //侦测环开始的索引，否则返回空
    public ListNode detectCycle(ListNode head) {
        List<ListNode> list = new ArrayList<>();
        if(head == null){
            return null;
        }
        while(head != null){
            if(list.contains(head)){
                return head;
            }else {
                list.add(head);
            }
            head = head.next;
        }
        return null;
    }

    /**
     * 回文判断 "A man, a plan, a canal: Panama"： true
     * @param s
     * @return
     */
    public boolean isPalindrome(String s) {
        int start = 0;
        int end = s.length()-1;
        while(start<end){
            while(!isLegal(s.charAt(start)) && start < end) start++;
            while(!isLegal(s.charAt(end)) && start < end) end--;
            if(Character.toLowerCase(s.charAt(start)) != Character.toLowerCase(s.charAt(end))){
                return false;
            }
            start++;
            end--;
        }
        return true;
    }
    private boolean isLegal(char ch){
        return ((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122) || (ch >= 48 && ch <= 57));
    }

    /**
     * 主要的数 二分之一
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        if(nums == null || nums.length == 0) return 0;
        int count = 1;
        int result = nums[0];
        for(int i=1;i<nums.length;i++){
            if(nums[i] == result){
                count++;
            }else if(count == 0){
                count++;
                result = nums[i];
            }else {
                count--;
            }
        }
        return result;
    }

    /**
     *     1 -> A
     *     2 -> B
     *     3 -> C
     *     ...
     *     26 -> Z
     *     27 -> AA
     *     28 -> AB
     * @param n
     * @return
     */
    public String convertToTitle(int n) {
        int high = n / 26;
        int low = n%26;
        if(low == 0 && high != 0){
            high--;
            low=26;
        }
        char c = (char) (low + 'A' - 1);
        return (high == 0?"":convertToTitle(high))+c;
    }
    //上题逆向
    public int titleToNumber(String s) {
        if(s == null || s.length() == 0){
            return 0;
        }
        int len = s.length();
        char c = s.charAt(0);
        int val = c-'A'+1;
        double pow = Math.pow(26, len - 1);
        int fact = (int)pow;
        int curVal = val*fact;
        return curVal+titleToNumber(s.substring(1,s.length()));
    }

    /**
     * 寻找两个链表汇入点
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        //boundary check
        if(headA == null || headB == null) return null;
        ListNode a = headA;
        ListNode b = headB;
        //if a & b have different len, then we will stop the loop after second iteration
        while( a != b){
            //for the end of first iteration, we just reset the pointer to the head of another linkedlist
            a = a == null? headB : a.next;
            b = b == null? headA : b.next;
        }
        return a;
    }

    /**
     * 两数和
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSumII(int[] numbers, int target) {
        int le = 0,ri = numbers.length-1;
        while(ri > le){
            if(numbers[ri]+numbers[le]>target){
                ri --;
            }else if(numbers[ri]+numbers[le]<target){
                le ++;
            }else {
                return new int[]{le+1,ri+1};
            }
        }
        return null;
    }

    /**
     * BST 树是否存在两叶子和为target
     * @param root
     * @param k
     * @return
     */
    public boolean findTarget(TreeCluster.TreeNode root, int k) {
        final Map<Integer,Integer> map = new HashMap<>();
        return findInBST(map,root,k);
    }

    private boolean findInBST(Map<Integer, Integer> map, TreeCluster.TreeNode node, int k) {
        if(node == null){
            return false;
        }
        int curVal = node.val;
        if(map.containsKey(curVal)){
            return true;
        }else {
            map.put(k-curVal,curVal);
            return findInBST(map,node.left,k) || findInBST(map,node.right,k);
        }
    }

    /**
     * 10111 -> 11101 翻转二进制数  输出结果整数
     * @param n
     * @return
     */
    public int reverseBits(int n) {
        int re = 0;
        for(int i=0;i<32;i++){
            re += n&1;
            n = n >> 1;
            if(i<31) {
                re = re << 1;
            }
        }
        return re;
    }

    /**
     * 强盗抢劫
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        if(nums == null || nums.length == 0) return 0;
        if(nums.length == 1) return nums[0];
        if(nums.length == 2) return Math.max(nums[0],nums[1]);
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0],nums[1]);
        for(int i=2;i<nums.length;i++){
            dp[i] = Math.max(nums[i]+dp[i-2],dp[i-1]);
        }
        return dp[nums.length-1];
    }

    /** TODO 牛掰
     * 旋转数组
     * Input: [1,2,3,4,5,6,7] and k = 3
     * Output: [5,6,7,1,2,3,4]
     */
    public void rotate(int[] nums, int k) {
        if(nums == null || nums.length == 0){
            return;
        }
        k = k%nums.length;
        int count = 0;
        for(int i=0;count<nums.length;i++){
            int cur = i;
            int preVal = nums[i];
            do{
                int nextInd = (cur+k)%nums.length;
                int tem = nums[nextInd];
                nums[nextInd] = preVal;
                preVal = tem;
                cur = nextInd;
                count++;
            }while (cur != i);
        }
    }

    /** 是否是 开心数
     * Input: 19
     * Output: true
     * Explanation:
     * 1^2 + 9^2 = 82
     * 8^2 + 2^2 = 68
     * 6^2 + 8^2 = 100
     * 1^2 + 0^2 + 0^2 = 1
     * @param n
     * @return
     */
    public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        int curVal = n;
        do{
            curVal = getDigitSquareSum(curVal);
            if(curVal == 1){
                return true;
            }
        }while (set.add(curVal));
        return false;
    }
    private int getDigitSquareSum(int x){
        int sum = 0;
        while(x != 0){
            sum+=Math.pow((double)x%10,2);
            x = x/10;
        }
        return sum;
    }

    /**
     * 删除链表元素
     * Input: 1-2-3-3-4  3
     * Output: 1-2-4
     * @param head
     * @param val
     * @return
     */
    public ListNode removeElements(ListNode head, int val) {
       ListNode n1 = new ListNode(0);
       n1.next = head;
       ListNode pre = n1;
       ListNode cur = head;
       while(cur != null){
           if(cur.val == val){
               pre.next = cur.next;
               cur = cur.next;
           }else {
               pre = cur;
               cur = cur.next;
           }
       }
       return n1.next;
    }

    /** TODO 递推
     * 素数个数
     * @param n
     * @return
     */
    public int countPrimes(int n) {
        boolean[] notPrime = new boolean[n];
        int count = 0;
        for(int i = 2; i < n; i++) {
            if(!notPrime[i]) {
                count++;
                for(int j = i; j <= (n-1) / i; j++) {
                    int v = i * j;
                    notPrime[v] = true;
                }
            }
        }
        return count;
    }

    /**
     * 字符串同形判断
     *
     * Input: s = "egg", t = "add"
     * Output: true
     *
     * Input: s = "paper", t = "title"
     * Output: true
     * @param s
     * @param t
     * @return
     */
    public boolean isIsomorphic(String s, String t) {
        return fun(s,t) && fun(t,s);
    }
    private boolean fun(String s,String t){
        Map<Character,Character> map = new HashMap<>();
        for(int i=0;i<s.length();i++){
            char ch1 = s.charAt(i);
            char ch2 = t.charAt(i);
            if(map.containsKey(ch1)){
                char value = map.get(ch1);
                if(ch2 != value){
                    return false;
                }
            }else {
                map.put(ch1,ch2);
            }
        }
        return true;
    }

    /**
     * 重复元素1
     * TODO 空间复杂度较大
     * 判断是否包含重复元素
     * @param nums
     * @return
     */
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int x:nums){
            if(!set.add(x)){
                return true;
            }
        }
        return false;
    }

    /**
     * 重复元素2
     * k距离范围内 是否有重复元素
     * @param nums
     * @param k
     * @return
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if(nums == null || nums.length <=1){
            return false;
        }
        for(int i=1;i<nums.length;i++){
            int topLine = Math.max(i-k,0);
            for(int j=i-1;j>=topLine;j--){
                if(nums[j] == nums[i]){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * TODO 大神代码
     *     isPowerOfTwo(int n) {
     *         if(n<=0) return false;
     *         return !(n&(n-1));
     *     }
     * 判断n是否是2的指数值  1 2 4 8 16 32
     * @param n
     * @return
     */
    public boolean isPowerOfTwo(int n) {
        if(n==0){
            return false;
        }
        if(n == 1){
            return true;
        }
        int x = n/2;
        int y = n%2;
        if(y!=0){
            return false;
        }
        return isPowerOfTwo(x);
    }

    /**
     * TODO 反转链表 然后对比
     * 链表是否是循环
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        if(head == null || head.next == null){
            return true;
        }
        ListNode fast=head;
        ListNode slow=head;
        ListNode pre=null;
        while(fast != null && fast.next != null){
            pre = slow;
            slow =slow.next;
            fast = fast.next.next;
        }
        pre.next = null;
        slow = revNode(slow);
        while(head != null && slow != null){
            if(slow.val != head.val){
                return false;
            }
            slow = slow.next;
            head = head.next;
        }
        return true;
    }

    /**
     * 翻转链表
     * @param head
     * @return
     */
    private ListNode revNode(ListNode head) {
        ListNode pre = null;
        while(head != null){
            ListNode next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    /**
     * 树的最小公共祖先
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeCluster.TreeNode lowestCommonAncestor(TreeCluster.TreeNode root, TreeCluster.TreeNode p, TreeCluster.TreeNode q) {
        if(root == null){
            return null;
        }
        if(root.val == p.val || root.val == q.val){
            return root;
        }
        if(p.val > root.val && root.val> q.val || p.val<root.val && root.val < q.val){
            return root;
        }
        if(root.val > p.val && root.val > q.val){
            return lowestCommonAncestor(root.left,p,q);
        }else{
            return lowestCommonAncestor(root.right,p,q);
        }
    }

    /**
     * 是否是同基乱序的字符串
     * Input: s = "anagram", t = "nagaram"
     * Output: true
     * @param s
     * @param t
     * @return
     */
    public boolean isAnagram(String s, String t) {
        int[] words = new int[26];
        for(int i=0;i<s.length();i++){
            words[s.charAt(i)-'a']++;
        }
        for(int i=0;i<t.length();i++){
            words[t.charAt(i)-'a']--;
        }
        for(int i=0;i<26;i++){
            if(words[i] != 0){
                return false;
            }
        }
        return true;
    }
    //TODO  下面的算法同样的思路，比我的要快
    /**
    public boolean isAnagram(String s, String t) {
        int[] sarr = new int[128];
        for(char temp: s.toCharArray()) sarr[temp] += 1;
        for(char temp: t.toCharArray()) sarr[temp] -= 1;
        for(int i: sarr)
            if(i != 0) return false;
        return true;
    }*/

    /**
     * 寻找第三大的数
     * @param nums
     * @return
     */
    public int thirdMax(int[] nums) {
        Set<Integer> set = new TreeSet<>((o1,o2)-> o2.compareTo(o1));
        for(int x:nums){
            set.add(x);
        }
        int count = 3;
        int re = 0;
        int max = Integer.MIN_VALUE;
        for(Iterator<Integer> it = set.iterator();it.hasNext() && count>0;count--){
            re = it.next();
            max = Math.max(max,re);
        }
        if(nums.length < 3 || count > 0){
            return max;
        }
        return re;
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
    public int addDigits(int num) {
        int sum = 0;
        while(num>10){
            while(num>0){
                sum+=(num%10);
                num = num/10;
            }
            num = sum;
            sum = 0;
        }
        return num;
    }

    /**
     * 0 1 2 3 4.....  求消失的数
     * Input: [3,0,1]
     * Output: 2
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums) {
        int sum = (nums.length) * (nums.length + 1) / 2;
        int resum = 0;
        for(int i=0;i<nums.length;i++){
            resum += nums[i];
        }
        return sum-resum;
    }

    /**
     * TODO 大神代码 第一个坏版本
     * @param n
     * @return
     */
    public int firstBadVersion(int n) {
        int left = 1, right = n, mid;
        while(left < right) {
            mid = (left & right) + ((left ^ right) >> 1);
            if (isBadVersion(mid)) right = mid;
            else left = mid + 1;
        }
        return left;
    }
    boolean isBadVersion(int x){return false;}

    /**
     * Input: [0,1,0,3,12]
     * Output: [1,3,12,0,0]
     * 把0移到最后
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        int ind = 0;
        for(int i=0;i<nums.length;i++){
            if(nums[i] == 0){
                continue;
            }else{
                nums[ind] = nums[i];
                ind++;
            }
        }
        while(ind<nums.length){
            nums[ind] = 0;
            ind++;
        }
    }

    /**
     * Input: pattern = "abba", str = "dog cat cat dog"
     * Output: true
     * @param pattern
     * @param str
     * @return
     */
    public boolean wordPattern(String pattern, String str) {
        if(str == null || str.length() == 0){
            return false;
        }
        String[] split = str.split(" ");
        if(pattern.length() != split.length){
            return false;
        }
        Map<Character,String> map = new HashMap<>();
        for(int i= 0;i<pattern.length();i++){
            char c = pattern.charAt(i);
            String s = map.get(c);
            if(s == null){
                if(map.containsValue(split[i])){
                    return false;
                }
                map.put(c,split[i]);
            }else if(!s.equals(split[i])){
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否是4的幂
     * @param num
     * @return
     */
    public boolean isPowerOfFour(int num) {
        if(num<=0) return  false;
        while(num != 0){
            if(num == 1){
                return true;
            }else if((num&3)!= 0){
                return false;
            }
            num>>=2;
        }
        return false;
    }

    /**
     * 翻转
     * @param s
     */
    public void reverseString(char[] s) {
        for(int i=0;i<s.length/2;i++){
            s[i] = (char) (s[i]+s[s.length-i-1]-(s[s.length-i-1]=s[i]));
        }
    }

    /**
     * 元音 的翻转
     * @param s
     * @return
     */
    public String reverseVowels(String s) {
        List<Character> vowels = new ArrayList(){{add('a');add('e');add('i');add('o');add('u');add('A');add('E');add('I');add('O');add('U');}};
        if(s == null || s.length() == 0) return s;
        char[] chars = s.toCharArray();
        int start = 0;
        int end = s.length()-1;
        while(start<end){
            while(!vowels.contains(s.charAt(start)) && start<end) start++;
            while(!vowels.contains(s.charAt(end)) && start<end) end--;
            chars[end] = (char)(chars[start]+chars[end]-(chars[start]=chars[end]));
            start++;
            end--;
        }
        return new String(chars);
    }

    /**
     * TODO 大神代码
     * public int[] intersection(int[] nums1, int[] nums2) {
     * 	Set<Integer> set = Arrays.stream(nums1).mapToObj(e -> e).collect(Collectors.toSet());
     * 	return Arrays.stream(nums2).filter(set::contains).distinct().toArray();
     * }
     * 获取交集 结果数组元素唯一 任何顺序
     * Input: nums1 = [1,2,2,1], nums2 = [2,2]
     * Output: [2]
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int ind1 = 0;
        int ind2 = 0;
        Set<Integer> set = new HashSet<>();
        while(ind1 < nums1.length && ind2 < nums2.length){
            if(nums1[ind1] == nums2[ind2]){
                set.add(nums1[ind1]);
                ind1++;
                ind2++;
            }else if(nums1[ind1]>nums2[ind2]){
                ind2++;
            }else{
                ind1++;
            }
        }
        int[] re = new int[set.size()];
        int ind = 0;
        for(Iterator<Integer> it = set.iterator();it.hasNext();){
            re[ind++] = it.next();
        }

        return re;
    }

    /**
     * 元素可以出现多次  求两个数组交集
     * Input: nums1 = [1,2,2,1], nums2 = [2,2]
     * Output: [2,2]
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersectII(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int p1 = 0;
        int p2 = 0;
        List<Integer> re = new ArrayList<>();
        while(p1 < nums1.length && p2 < nums2.length){
            if(nums1[p1] == nums2[p2]){
                re.add(nums1[p1]);
                p1++;
                p2++;
            }else if(nums1[p1] > nums2[p2]){
                p2++;
            }else{
                p1++;
            }
        }
        int[] res = new int[re.size()];
        for(int i=0;i<re.size();i++){
            res[i] = re.get(i);
        }
        return res;
    }

    /**
     * 是否是完全平方数  16->true
     * @param num
     * @return
     */
    public boolean isPerfectSquare(int num) {
        if(num<=1){
            return true;
        }
        for(int i=1;i<=num/2;i++){
            if(i*i == num){
                return true;
            }
        }
        return false;
    }

    /**
     * Find the nth digit of the infinite integer sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...
     *
     * Input:
     * 11
     * Output:
     * 0
     * @param n
     * @return
     */
    public int findNthDigit(int n) {
        int ind = 1;
        int interval = 9;
        while(n>interval){
            ind++;
            n-=interval;
            interval = ind*((int)Math.pow(10,ind-1))*9;
            if(interval<0){
                break;
            }
        }
        int base = (int)Math.pow(10,ind-1);
        int num = base+(n-1)/ind;
        return String.valueOf(num).charAt((n-1)%ind)-'0';

    }

    /**
     * 转16进制
     * Input:
     * 26
     *
     * Output:
     * "1a"
     * @param num
     * @return
     */
    public String toHex(int num) {
        if(num == 0){
            return "0";
        }
        Map<Integer,Character> map = new HashMap(){{put(10,'a');put(11,'b');put(12,'c');put(13,'d');put(14,'e');put(15,'f');}};
        for(int i=0;i<10;i++){
            map.put(i,(char)('0'+i));
        }
        StringBuilder result = new StringBuilder();
        while(num!=0){
            int r = num & 15;
            num >>>= 4;
            result.append(map.get(r));
        }
        return result.reverse().toString();
    }

    public static void main(String[] args) {
        EasyProblemSolution easyProblemSolution = new EasyProblemSolution();
        System.out.println(easyProblemSolution.toHex(-1));

    }
}
