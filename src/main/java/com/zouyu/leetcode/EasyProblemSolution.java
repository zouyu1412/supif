package com.zouyu.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static java.lang.Math.abs;

/**
 * Created by zouy-c on 2018/3/9.
 */
public class EasyProblemSolution {

    public static void main(String[] args) {
        System.out.println(new EasyProblemSolution().countAndSay(4));
    }

    public int[] twoSum(int[] nums, int target) {
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


}
