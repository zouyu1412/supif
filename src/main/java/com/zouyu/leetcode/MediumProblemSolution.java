package com.zouyu.leetcode;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by zouy-c on 2018/3/9.
 */
public class MediumProblemSolution {

    private static void test(){

        List<Integer> li = new ArrayList<>();
        List<List<Integer>> list = new ArrayList<>();
        li.add(1);
        li.add(2);
        li.add(3);
        list.add(li);
        li.add(4);
        li.add(5);
        li.set(4,10);
        for(List<Integer> x:list){
            for(Integer y:x){
                System.out.println(y);
            }
        }
        int[] xx = new int[]{1,2,3}.clone();
        for(int xxx:xx){
            System.out.println(xxx);
        }
        String s = "0";
        char c = 'a';
        System.out.println(s=="0");
        System.out.println("0"=="0");
        System.out.println(c == 'a');
        System.out.println('2'-'0');
//        System.out.println(214748364*10+6);
//        System.out.println(214748364*10+7);
//        System.out.println(214748364*10+8);
//        System.out.println(214748364*10+9);
//        System.out.println(214748364*10+10);
//        System.out.println(214748364*10+11);
    }

    public static void main(String[] args) {
//        new MediumProblemSolution().subsets(new int[]{1,2,3});
        //test();
        System.out.println("zouyu".substring(1,2));
        System.out.println("zouyu".substring(0,2));
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = null;
        ListNode head = null;
        boolean isOverflow = false;
        while(l1!=null || l2!=null || isOverflow){
            int val1;
            int val2;
            int extraVal = isOverflow ? 1 : 0;
            if(l1==null){
                val1 = 0;
            }else{
                val1 = l1.val;
                l1 = l1.next;
            }
            if(l2==null){
                val2 = 0;
            }else{
                val2 = l2.val;
                l2 = l2.next;
            }

            if(result==null) {
                result = new ListNode((val1 + val2+ extraVal) % 10  );
                head = result;
            }else{
                head.next = new ListNode((val1 + val2+extraVal) % 10);
                head=head.next;
            }
            isOverflow = (val1+val2+extraVal)/10==1;

        }
        return result;
    }

    public int lengthOfLongestSubstring(String s) {
        Map<Character,Integer> map = new HashMap<>();
        int result = Integer.MIN_VALUE;
        for(int i=0,j=0;i<s.length();i++){
            if(map.containsKey(s.charAt(i))){
                j = Math.max(map.get(s.charAt(i))+1,j);
            }
            map.put(s.charAt(i),i);
            result  = Math.max(result,i-j+1);
        }
        return result;
    }

    public String longestPalindrome(String s) {
        int len = s.length();
        String res = null;
        boolean[][] dp = new boolean[len][len];
        for(int i=len-1;i>=0;i--){
            for(int j=i;j<len;j++){
                dp[i][j] = (s.charAt(i)==s.charAt(j) && (j-i<3 || dp[i+1][j-1]));
                if(dp[i][j] && (res==null || j-i+1>res.length())){
                    res = s.substring(i,j+1);
                }
            }
        }
        return  res;
    }

    public String convert(String s, int numRows) {
        if(s.length()<=1 || numRows<=1) return s;
        StringBuilder[] sbs = new StringBuilder[numRows];
        int index = 0;
        int incre = 1;
        for(int i=0;i<numRows;i++){
            sbs[i] = new StringBuilder();
        }
        for(int i=0;i<s.length();i++){
            sbs[index].append(s.charAt(i));
            if(index == 0) incre = 1;
            if(index == numRows-1 ) incre = -1;
            index += incre;
        }
        String res = "";
        for(int i=0;i<numRows;i++){
            res+=sbs[i];
        }
        return res;
    }

    public int myAtoi(String str) {
        System.out.println(str);
        str = str.trim();
        int sign = 1;
        boolean hasSign = false;
        int res = 0;
        for(int i=0;i<str.length();i++){
            char tem = str.charAt(i);
            if(tem=='-') {
                if (!hasSign) {
                    sign = -1;
                    hasSign = true;
                }else{
                    return 0;
                }
            }else if(tem=='+'){
                if(!hasSign){
                    sign = 1;
                    hasSign = true;
                }else{
                    return 0;
                }
            }else if(tem>='0' && tem <='9'){
                if(sign == 1&&(Integer.MAX_VALUE / 10 < res || Integer.MAX_VALUE - Integer.parseInt(String.valueOf(tem)) < res * 10 )){
                    System.out.println("直接返回最大值？");
                    return Integer.MAX_VALUE;
                }
                if(sign == -1&&(Integer.MIN_VALUE / 10 + res >0 || Integer.MIN_VALUE + Integer.parseInt(String.valueOf(tem)) > sign*res * 10)){
                    System.out.println("直接返回最小值？");
                    return Integer.MIN_VALUE;
                }
                res = res*10 + Integer.parseInt(String.valueOf(tem));
                System.out.println(sign+" "+res);

            }else{
                return res*sign;
            }
        }
        return res*sign;
    }

    public int maxArea(int[] height) {
        int start = 0;
        int end = height.length-1;
        int re = Integer.MIN_VALUE;
        while(start<end){
            re = Math.max(re,(end-start)*Math.min(height[start],height[end]));
            if(height[start]>height[end]){
                end--;
            }else{
                start++;
            }
        }
        return re;
    }

    public String intToRoman(int num) {
        String M[] = {"", "M", "MM", "MMM"};
        String C[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String X[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String I[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        return M[num/1000] + C[(num%1000)/100]+ X[(num%100)/10] + I[num%10];
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for(int i=0;i<nums.length-2;i++){
            if(i>0&&nums[i]==nums[i-1])continue;
            int j = i+1;
            int k = nums.length-1;
            while(j<k){
                if(j>i+1&&nums[j]==nums[j-1]){
                    j++;
                    continue;
                }
                if(k<nums.length-1&&nums[k]==nums[k+1]){
                    k--;
                    continue;
                }
                int sum = nums[i]+nums[j]+nums[k];
                if(sum<0){
                    i++;
                }else if(sum>0){
                    k--;
                }else{
                    List<Integer> ele = new ArrayList<>();
                    ele.add(nums[i]);
                    ele.add(nums[j++]);
                    ele.add(nums[k--]);
                    result.add(ele);
                }
            }
        }
        return result;
    }

    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int re = Integer.MAX_VALUE;
        for(int i=0;i<nums.length-2;i++){
            int j = i+1;
            int k = nums.length-1;
            while(j<k){
                int sum = nums[i]+nums[j]+nums[k];
                re = (Math.abs(target-sum)<Math.abs(target-re)?sum:re);
                System.out.println(re);
                if(sum>target){
                    k--;
                }else if(sum<target){
                    j++;
                }else{
                    return sum;
                }
            }
        }
        return re;
    }

    public List<String> letterCombinations(String digits) {
        LinkedList<String> ans = new LinkedList<String>();
        if(digits.isEmpty()) return ans;
        String[] mapping = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        ans.add("");
        for(int i =0; i<digits.length();i++){
            int x = Character.getNumericValue(digits.charAt(i));
            while(ans.peek().length()==i){
                String t = ans.remove();
                for(char s : mapping[x].toCharArray())
                    ans.add(t+s);
            }
        }
        return ans;
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> re = new ArrayList<>();
        if(nums.length<4) return re;
        Arrays.sort(nums);
        int max = nums.length-1;
        if(4*nums[0]>target || 4*nums[max]<target) return re;
        for(int i=0;i<max-2;i++){
            if(i>0&&nums[i]==nums[i-1]) continue;
            if(4*nums[i]>target) break;
            if(nums[i]+3*nums[max]<target)continue;
            for(int j=i+1;j<max-1;j++){
                if(j>i+1&&nums[j]==nums[j-1]) continue;
                if(nums[j]*3>target-nums[i]) break;
                if(nums[j]+2*nums[max]<target-nums[i]) continue;
                int low = j+1;
                int high = max;
                while(low<high) {
                    int sum = nums[i] + nums[j] + nums[low] + nums[high];
                    if (target < sum) {
                        high--;
                    } else if (target > sum) {
                        low++;
                    } else {
                        re.add(Arrays.asList(nums[i], nums[j],nums[low++],nums[high--]));
                        while(low<high && nums[low]==nums[low-1]){low++;}
                        while(low<high && nums[high]==nums[high+1]){high--;}
                    }
                }
            }
        }
        return re;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode re = new ListNode(0);
        re.next = head;
        ListNode fast = re,slow = re;
        while(n-->0){
            fast = fast.next;
        }
        while(fast.next!=null){
            fast = fast.next;
            slow = slow.next;
        }
        if(slow.next!=null)
            slow.next = slow.next.next;
        return re.next;
    }

    public List<String> generateParenthesis(int n) {
        Set<String> re=  new HashSet<>();
        String path = "()";
        recursion(re,path,1,n);
        return new ArrayList<>(re);
    }
    private void recursion(Set<String> re, String path, int cur, int n) {
        if(n==cur){
            re.add(path);
            return;
        }
        String parenthesis = "()";
        for(int i=0;i<=parenthesis.length();i++){
            String left = parenthesis.substring(0,i);
            String right = parenthesis.substring(i,parenthesis.length());
            recursion(re,left+path+right,cur+1,n);
        }
    }

    public ListNode swapPairsMy(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode re = new ListNode(0);
        ListNode tem = re;
        while(head!=null&&head.next!=null){
            tem.next = new ListNode(head.next.val);
            tem.next.next = new ListNode(head.val);
            head = head.next.next;
            tem = tem.next.next;
        }
        tem.next = head;
        return re.next;
    }
    public ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null) return head;
        ListNode re = new ListNode(0);
        re.next = head;
        for(ListNode cur = re;cur.next!=null&&cur.next.next!=null;cur = cur.next.next){
            cur.next = swap(cur.next,cur.next.next);
        }
        return re.next;
    }
    private ListNode swap(ListNode l1, ListNode l2) {
        l1.next = l2.next;
        l2.next = l1;
        return l2;
    }

    public int divide(int dividend, int divisor) {
        long result = divideLong(dividend, divisor);
        return result > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)result;
    }
    // It's easy to handle edge cases when
    // operate with long numbers rather than int
    public long divideLong(long dividend, long divisor) {

        // Remember the sign
        boolean negative = dividend < 0 != divisor < 0;

        // Make dividend and divisor unsign
        if (dividend < 0) dividend = -dividend;
        if (divisor < 0) divisor = -divisor;

        // Return if nothing to divide
        if (dividend < divisor) return 0;

        // Sum divisor 2, 4, 8, 16, 32 .... times
        long sum = divisor;
        long divide = 1;
        while ((sum+sum) <= dividend) {
            sum += sum;
            divide += divide;
        }

        // Make a recursive call for (devided-sum) and add it to the result
        return negative ? -(divide + divideLong((dividend-sum), divisor)) :
                (divide + divideLong((dividend-sum), divisor));
    }

    public void nextPermutation (int[] nums){
        if(nums.length<=1) return;
        int ind = nums.length-2;
        while(ind>=0&&nums[ind]>=nums[ind+1]) ind--;
        if(ind>=0) {
            int j = nums.length - 1;
            while (nums[j] <= nums[ind]) j--;
            nums[ind] = nums[j] + nums[ind] - (nums[j] = nums[ind]);
        }
        for(int i=ind+1,ii=nums.length-1;i<ii;i++,ii--){
            nums[i] = nums[i]+nums[ii]-(nums[ii]=nums[i]);
        }
    }

    public int search(int[] nums, int target) {
        int start = 0;
        int end = nums.length-1;
        while(start<=end){
            int mid = (start+end)/2;
            if(nums[mid] == target) return mid;
            if(nums[start]<=nums[mid]){
                if(nums[mid]>target && target >= nums[start]){
                    end = mid-1;
                }else{
                    start = mid+1;
                }
            }
            if(nums[mid]<=nums[end]){
                if(nums[mid]<target && target <= nums[end]){
                    start = mid+1;
                }else{
                    end = mid-1;
                }
            }
        }
        return -1;
    }

    public int[] searchRange(int[] nums, int target) {
        int p1 = searchInsert(nums, target);
        int p2 = searchInsert(nums, target + 1);
        int[] re = new int[p2-p1];
        for(int i=0;i<re.length;i++){
            re[i] = p1+i;
        }
        return re;
    }
    private static int searchInsert(int[] nums, int target) {
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
                if(mid == nums.length-1 || nums[mid+1]>=target) return mid+1;
                le = mid+1;
            }
        }
        return 0;
    }

    //TODO 大神级代码
    public boolean isValidSudoku(char[][] board) {
        for(int i = 0; i<9; i++){
            HashSet<Character> rows = new HashSet<Character>();
            HashSet<Character> columns = new HashSet<Character>();
            HashSet<Character> cube = new HashSet<Character>();
            for (int j = 0; j < 9;j++){
                if(board[i][j]!='.' && !rows.add(board[i][j]))
                    return false;
                if(board[j][i]!='.' && !columns.add(board[j][i]))
                    return false;
                int RowIndex = 3*(i/3);
                int ColIndex = 3*(i%3);
                if(board[RowIndex + j/3][ColIndex + j%3]!='.' && !cube.add(board[RowIndex + j/3][ColIndex + j%3]))
                    return false;
            }
        }
        return true;
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> re = new ArrayList<>();
        List<Integer> removeList = new ArrayList<>();
        weave(candidates, target, re, removeList, 0);
        return re;
    }
    private void weave(int[] candidates, int target, List<List<Integer>> re, List<Integer> removeList, int start) {
        if (target == 0) {
            re.add(new ArrayList<>(removeList));
        } else if (target > 0) {
            for (int i = start; i < candidates.length && target>=candidates[i]; i++) {
                removeList.add(candidates[i]);
                weave(candidates, target - candidates[i], re, removeList, i);
                removeList.remove(removeList.size() - 1);
            }
        }
    }

    public String multiply(String num1, String num2) {
        if(num1.equals("0") || num2.equals("0")) return "0";
        StringBuilder sb = new StringBuilder();
        int len1 = num1.length();
        int len2 = num2.length();
        int arr[] = new int[len1+len2];
        for(int i=len1-1;i>=0;i--){
            for(int j=len2-1;j>=0;j--){
                int tem = (num1.charAt(i)-'0')*(num2.charAt(j)-'0');
                int p2 = i+j+1;
                int p1 = i+j;
                int sum = arr[p2]+tem;
                arr[p1] += sum/10;
                arr[p2] = sum%10;
            }
        }
        for(int x:arr){
            sb.append(!(sb.length()==0 && x == 0)? x : "");
        }
        return sb.length()>0?sb.toString():"0";
    }

    public List<List<Integer>> permute(int[] nums) {
        //Arrays.sort(nums);
        List<List<Integer>> re = new ArrayList<>();
        backTracking1(nums, re,new ArrayList<Integer>(),0,nums.length, new boolean[nums.length]);
        return re;
    }
    private void backTracking1(int[] nums, List<List<Integer>> re, ArrayList<Integer> list, int ind, int length, boolean[] used) {
        if(ind == length){
            re.add((ArrayList<Integer>)list.clone());
        }else{
            for(int i=0;i<length;i++){
                if(!used[i]) {
                    used[i] = true;
                    list.add(nums[i]);
                    backTracking1(nums, re, list, ind + 1, length, used);
                    list.remove(list.size() - 1);
                    used[i] = false;
                }
            }
        }
    }

    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> re = new ArrayList<>();
        backTracking2(nums, re, new ArrayList<Integer>(),0,nums.length, new boolean[nums.length]);
        return re;
    }
    private void backTracking2(int[] nums, List<List<Integer>> re, ArrayList<Integer> list, int ind, int length, boolean[] used) {
        if(ind == length){
            re.add((ArrayList<Integer>)list.clone());
        }else{
            for(int i=0;i<length;i++){
                if(!used[i] && !(i>0 && nums[i-1]==nums[i] && !used[i-1])) {
                    used[i] = true;
                    list.add(nums[i]);
                    backTracking2(nums, re, list, ind + 1, length, used);
                    list.remove(list.size() - 1);
                    used[i] = false;
                }
            }
        }
    }

    public void rotate(int[][] matrix) {
        int len = matrix.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < i; j++) {
                matrix[i][j] = matrix[i][j] + matrix[j][i] - (matrix[j][i] = matrix[i][j]);
            }
        }
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len / 2; j++) {
                matrix[i][j] = matrix[i][j] + matrix[i][len - j - 1] - (matrix[i][len - j - 1] = matrix[i][j]);
            }
        }
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String,List<String>> map = new HashMap<>();
        for(int i =0;i<strs.length;i++){
            char[] chs = strs[i].toCharArray();
            Arrays.sort(chs);
            String key = String.valueOf(chs);
            List<String> val = map.get(key);
            if (!map.containsKey(key)) map.put(key, new ArrayList<String>());
            map.get(key).add(strs[i]);
        }
        return new ArrayList<List<String>>(map.values());
    }

    public double myPow(double x, int n) {
        if(n<0) return (1/x)*myPow(1.0/x,-(n+1));
        if(n==0) return 1;
        double tem = myPow(x,n/2);
        double re = tem*tem;
        return n%2==0? re : re*x;
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        if(matrix.length == 0 || matrix[0].length==0) return Collections.emptyList();
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        List<Integer> re = new ArrayList<>();
        settle(matrix, re,0,rowLen-1,0,colLen-1);
        return re;
    }
    private void settle(int[][] A, List<Integer> re, int rowStart, int rowEnd, int colStart, int colEnd) {
        if(rowStart == rowEnd){
            for(int i=colStart;i<=colEnd;i++) re.add(A[rowStart][i]);
            return;
        }else if(colStart == colEnd){
            for(int i=rowStart;i<=rowEnd;i++) re.add(A[i][colStart]);
            return;
        }else{
            for(int i=colStart;i<=colEnd-1;i++){
                re.add(A[rowStart][i]);
            }
            for(int i=rowStart;i<=rowEnd-1;i++){
                re.add(A[i][colEnd]);
            }
            for(int i=colEnd;i>=colStart+1;i--){
                re.add(A[rowEnd][i]);
            }
            for(int i=rowEnd;i>=rowStart+1;i--){
                re.add(A[i][colStart]);
            }
            if(rowEnd-rowStart>=2 && colEnd-colStart>=2) {
                settle(A, re, rowStart + 1, rowEnd - 1, colStart + 1, colEnd - 1);
            }
        }
        return;
    }

    public List<Interval> merge(List<Interval> intervals) {
        if(intervals.size()<2) return intervals;
        intervals.sort((o1,o2)->o2.start-o1.start);
        int start = intervals.get(0).start;
        int end = intervals.get(0).end;
        List<Interval> result = new ArrayList<>();
        for(int i=1;i<intervals.size();i++){
            Interval temObj = intervals.get(i);
            if(temObj.start > end){
                result.add(new Interval(start,end));
                start = temObj.start;
                end = temObj.end;
            }else{
                start = Math.min(start,temObj.start);
                end = Math.max(end,temObj.end);
            }
        }
        result.add(new Interval(start,end));
        return result;
    }

    public String getPermutation(int n, int k) {
        List<Integer> nums = IntStream.rangeClosed(1,n).boxed().collect(Collectors.toList());
        return "";
    }

    public ListNode rotateRight(ListNode head, int k) {
        if(k == 0 || head == null){
            return head;
        }
        ListNode end = head;
        int count = 1;
        for(;end.next!=null;end = end.next) count++;
        end.next = head;
        int realK = count - k%count;
        while(realK>0){
            end = end.next;
            realK--;
        }
        ListNode re = end.next;
        end.next = null;
        return re;
    }

//    If you mark the south move as '1' and the east move as '0'. This problem shall be equal to :
//    Given (m+n-2) bits. you can fill in '1' for (m-1) times and '0' for (n-1) times, what is the number of different numbers?
//    the result is clear that the formula shall be C(m-1)(m+n-2), where m-1 is the superscript behind C and m+n-2 is the subscript behind C.
//    To avoid overflow, I write the program in this manner.
//    TODO 大神代码
//    public int uniquePaths(int m, int n) {
//        long result = 1;
//        for(int i=0;i<Math.min(m-1,n-1);i++)
//            result = result*(m+n-2-i)/(i+1);
//        return (int)result;
//
//    }
    public int uniquePaths(int m, int n) {
        int dp[][] = new int[m][n];
        for(int i=0;i<m;i++){
            dp[i][0] = 1;
        }
        for(int i=0;i<n;i++){
            dp[0][i] = 1;
        }
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                dp[i][j] = dp[i-1][j]+dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
     }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if(obstacleGrid[0][0]==1){
            return 0;
        }
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int dp[][] = new int[m][n];
        for(int i=0;i<m;i++){
            if(obstacleGrid[i][0]==0)
                dp[i][0] = 1;
            else
                break;
        }
        for(int i=0;i<n;i++){
            if(obstacleGrid[0][i]==0)
                dp[0][i] = 1;
            else
                break;
        }
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                if(obstacleGrid[i][j]==0) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }else{
                    dp[i][j] = 0;
                }
            }
        }
        return dp[m-1][n-1];
    }

    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int dp[][] = new int[m][n];
        int x = 0;
        int y = 0;
        for(int i=0;i<m;i++){
            x+=grid[i][0];
            dp[i][0] = x;
        }
        for(int i=0;i<n;i++){
            y+=grid[0][i];
            dp[0][i] = y;
        }
        for(int i=1;i<m;i++){
            for(int j=1;j<n;j++){
                dp[i][j] = Math.min(dp[i - 1][j],dp[i][j - 1])+grid[i][j];
            }
        }
        return dp[m-1][n-1];
    }

    public void setZeroes(int[][] matrix) {
        List<Integer> xz = new ArrayList<>();
        List<Integer> yz = new ArrayList<>();
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                if(matrix[i][j] == 0){
                    xz.add(i);
                    yz.add(j);
                }
            }
        }
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
                if(xz.contains(i) || yz.contains(j)){
                    matrix[i][j] = 0;
                }
            }
        }
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        int x = matrix.length;
        int y = matrix[0].length;
        int start = 0;
        int end = x*y-1;
        int mid;
        while(start<=end){
            mid = (start+end)/2;
            if(matrix[mid/y][mid%y]==target){
                return true;
            }else if(matrix[mid/y][mid%y]>target){
                end = mid-1;
            }else{
                start = mid+1;
            }
        }
        return false;
    }

    public void sortColors(int[] nums) {
       int len = nums.length;
       if(len<2) return;
       int cnt0 = 0;
       int cnt1 = 0;
       for(int i=0;i<len;i++){
           if(nums[i]==2){
               while(nums[--len]==2 && len>i);
               nums[i] = nums[len];
               nums[len] = 2;
           }
           if(i==len) return;
           if(nums[i]==0){
                if(nums[cnt0]==1)
                    nums[cnt0+cnt1]=1;
                nums[cnt0++]=0;
           }else{
               nums[cnt0+cnt1++]=1;
           }
       }
    }

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> re = new ArrayList<>();
        recur(1,n,k,new ArrayList<Integer>(),re);
        return re;
    }
    private void recur(int begin,int n, int k, List<Integer> tem, List<List<Integer>> re) {
        if(k==0){
            re.add(new ArrayList<>(tem));
            return;
        }
        for(int i=begin;i<=n-k+1;i++){
            tem.add(i);
            recur(i+1,n,k-1,tem,re);
            tem.remove(tem.size()-1);
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> re = new ArrayList<>();
        re.add(new ArrayList<>());
        recur(0,nums,new ArrayList<Integer>(),re);
        System.out.println(re);
        return re;
    }
    private void recur(int start, int[] nums, ArrayList<Integer> tem, List<List<Integer>> re) {
        for(int i=start;i<nums.length;i++){
            tem.add(nums[i]);
            re.add(new ArrayList<>(tem));
            recur(i+1,nums,tem,re);
            tem.remove(tem.size()-1);
        }
    }
    //TODO 大神代码
//    public List<List> subsets(int[] nums) {
//        List<List> arr=new ArrayList<List>();
//        arr.add(new ArrayList());
//        int l=nums.length;
//        int s=0;
//        for(int i=0;i<l;i++){
//            s=arr.size();
//            int n=nums[i];
//            for(int j=0;j<s;j++){
//                List res=new ArrayList(arr.get(j));
//                res.add(n);
//                arr.add(res);
//            }
//        }
//        return arr;
//    }

    //Given a 2D board and a word, find if the word exists in the grid
    public boolean exist(char[][] board, String word) {
        char[] chars = word.toCharArray();
        for(int i=0;i<board.length;i++){
            for(int j=0;j<board[0].length;j++){
                if(exist(board,i,j,chars,0)) return true;
            }
        }
        return false;
    }

    private boolean exist(char[][] board, int x, int y, char[] chars, int len) {
        if(len == chars.length) return true;
        if(x<0 || y<0 || x==board.length || y== board[0].length) return false;
        if(board[x][y]!=chars[len]) return false;
        board[x][y] ^=256;
        boolean exist = exist(board, x+1, y, chars, len+1)
                || exist(board, x-1, y, chars, len+1)
                || exist(board, x, y+1, chars, len+1)
                || exist(board, x, y-1, chars, len+1);
        board[x][y] ^=256;
        return exist;
    }

    //TODO 大神代码
//    public int removeDuplicates(int[] nums) {
//        int i = 0;
//        for (int n : nums)
//            if (i < 2 || n > nums[i-2])
//                nums[i++] = n;
//        return i;
//    }
    public int removeDuplicates(int[] nums) {
        int cur=nums[0];
        int count=1;
        int begin=1;
        for(int i=1;i<nums.length;i++){
            if(cur == nums[i]){
                count++;
                if(count<=2){
                    nums[begin++]=cur;
                }
            }else{
                nums[begin++]=nums[i];
                cur =nums[i];
                count=1;
            }
        }
        return begin;
    }

    public boolean search2(int[] nums, int target) {
        int start = 0;
        int end = nums.length-1;
        int mid;
        while(start<=end){
            mid = (start+end)/2;
            if(nums[mid]==target) {
                return true;
            }
            if(nums[mid]<nums[end] || nums[mid]<nums[start]){
                if(target>nums[mid] && target <= nums[end]){
                    start = mid +1;
                }else{
                    end = mid -1;
                }
            }else if(nums[mid]>nums[end] || nums[mid]>nums[start]){
                if(target <nums[mid] && target>=nums[start]){
                    end = mid-1;
                }else{
                    start = mid+1;
                }
            }else{
                end--;
            }
        }
        return false;
    }

    public ListNode deleteDuplicates(ListNode head) {
        if(head == null) return null;
        ListNode re = new ListNode(0);
        ListNode tem = new ListNode(0);
        tem.next = head;
        re.next = tem;
        while(tem.next!=null){
            int cur = tem.next.val;
            if(tem.next.next!=null){
                if(tem.next.next.val == cur) {
                    while (tem.next != null && tem.next.val == cur) {
                        tem.next = tem.next.next;
                    }
                }else{
                    tem = tem.next;
                }
            }else{
                return re.next.next;
            }
        }
        return re.next.next;

    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> re = new ArrayList<>();
        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];
        newRecur(0,new ArrayList<Integer>(),nums,re,used);
        return re;
    }

    private void newRecur(int begin, List<Integer> curList, int[] nums, List<List<Integer>> re, boolean[] used) {
        re.add(new ArrayList(curList));
        for(int i=begin;i<nums.length;i++){
            if(i>0&&nums[i]==nums[i-1]&&!used[i-1]) continue;
            curList.add(nums[i]);
            used[i] = true;
            newRecur(i+1,curList,nums,re,used);
            used[i] = false;
            curList.remove(curList.size()-1);
        }
    }

    /*
      Input: "12"
      Output: 2
      Explanation: It could be decoded as "AB" (1 2) or "L" (12).
     */
    public int numDecodings(String s) {
        if(s == null || s.isEmpty()) return  0;
        int[] dp = new int[s.length()+1];
        dp[0] = 1;
        dp[1] = s.charAt(0) == '0'? 0 : 1;
        for(int i=2;i<=s.length();i++){
            int num1 = Integer.parseInt(s.substring(i-1,i));
            int num2 = Integer.parseInt(s.substring(i-2,i));
            if(num1>0){
                dp[i] += dp[i-1];
            }
            if(num2>9 && num2<27){
                dp[i] += dp[i-2];
            }
        }
        return dp[s.length()];
    }

    public int numTrees(int n) {
        int re = 0;
        int[] dp = new int[n];
        dp[0] = dp[1] = 1;
        for(int i=2;i<n;i++){
            for(int j=0;j<i;j++){
                re += dp[j]*dp[i-j-1];
            }
        }
        return re;
    }

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int len = gas.length;
        int gSum = 0;
        int cSum = 0;
        int[] arr = new int[len];
        for(int i=0;i<len;i++){
            gSum += gas[i];
            cSum += cost[i];
            arr[i] = gas[i]-cost[i];
        }
        if(gSum<cSum){
            return -1;
        }

        return 0;
    }
}

