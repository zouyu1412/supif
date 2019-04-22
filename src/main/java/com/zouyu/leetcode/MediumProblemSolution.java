package com.zouyu.leetcode;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by zouy-c on 2018/3/9.
 */
public class MediumProblemSolution {

    private static void test() {

        List<Integer> li = new ArrayList<>();
        List<List<Integer>> list = new ArrayList<>();
        li.add(1);
        li.add(2);
        li.add(3);
        list.add(li);
        li.add(4);
        li.add(5);
        li.set(4, 10);
        for (List<Integer> x : list) {
            for (Integer y : x) {
                System.out.println(y);
            }
        }
        int[] xx = new int[]{1, 2, 3}.clone();
        for (int xxx : xx) {
            System.out.println(xxx);
        }
        String s = "0";
        char c = 'a';
        System.out.println(s == "0");
        System.out.println("0" == "0");
        System.out.println(c == 'a');
        System.out.println('2' - '0');
//        System.out.println(214748364*10+6);
//        System.out.println(214748364*10+7);
//        System.out.println(214748364*10+8);
//        System.out.println(214748364*10+9);
//        System.out.println(214748364*10+10);
//        System.out.println(214748364*10+11);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = null;
        ListNode head = null;
        boolean isOverflow = false;
        while (l1 != null || l2 != null || isOverflow) {
            int val1;
            int val2;
            int extraVal = isOverflow ? 1 : 0;
            if (l1 == null) {
                val1 = 0;
            } else {
                val1 = l1.val;
                l1 = l1.next;
            }
            if (l2 == null) {
                val2 = 0;
            } else {
                val2 = l2.val;
                l2 = l2.next;
            }

            if (result == null) {
                result = new ListNode((val1 + val2 + extraVal) % 10);
                head = result;
            } else {
                head.next = new ListNode((val1 + val2 + extraVal) % 10);
                head = head.next;
            }
            isOverflow = (val1 + val2 + extraVal) / 10 == 1;

        }
        return result;
    }

    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int result = Integer.MIN_VALUE;
        for (int i = 0, j = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                j = Math.max(map.get(s.charAt(i)) + 1, j);
            }
            map.put(s.charAt(i), i);
            result = Math.max(result, i - j + 1);
        }
        return result;
    }

    public String longestPalindrome(String s) {
        int len = s.length();
        String res = null;
        boolean[][] dp = new boolean[len][len];
        for (int i = len - 1; i >= 0; i--) {
            for (int j = i; j < len; j++) {
                dp[i][j] = (s.charAt(i) == s.charAt(j) && (j - i < 3 || dp[i + 1][j - 1]));
                if (dp[i][j] && (res == null || j - i + 1 > res.length())) {
                    res = s.substring(i, j + 1);
                }
            }
        }
        return res;
    }

    public String convert(String s, int numRows) {
        if (s.length() <= 1 || numRows <= 1) return s;
        StringBuilder[] sbs = new StringBuilder[numRows];
        int index = 0;
        int incre = 1;
        for (int i = 0; i < numRows; i++) {
            sbs[i] = new StringBuilder();
        }
        for (int i = 0; i < s.length(); i++) {
            sbs[index].append(s.charAt(i));
            if (index == 0) incre = 1;
            if (index == numRows - 1) incre = -1;
            index += incre;
        }
        String res = "";
        for (int i = 0; i < numRows; i++) {
            res += sbs[i];
        }
        return res;
    }

    public int myAtoi(String str) {
        System.out.println(str);
        str = str.trim();
        int sign = 1;
        boolean hasSign = false;
        int res = 0;
        for (int i = 0; i < str.length(); i++) {
            char tem = str.charAt(i);
            if (tem == '-') {
                if (!hasSign) {
                    sign = -1;
                    hasSign = true;
                } else {
                    return 0;
                }
            } else if (tem == '+') {
                if (!hasSign) {
                    sign = 1;
                    hasSign = true;
                } else {
                    return 0;
                }
            } else if (tem >= '0' && tem <= '9') {
                if (sign == 1 && (Integer.MAX_VALUE / 10 < res || Integer.MAX_VALUE - Integer.parseInt(String.valueOf(tem)) < res * 10)) {
                    System.out.println("直接返回最大值？");
                    return Integer.MAX_VALUE;
                }
                if (sign == -1 && (Integer.MIN_VALUE / 10 + res > 0 || Integer.MIN_VALUE + Integer.parseInt(String.valueOf(tem)) > sign * res * 10)) {
                    System.out.println("直接返回最小值？");
                    return Integer.MIN_VALUE;
                }
                res = res * 10 + Integer.parseInt(String.valueOf(tem));
                System.out.println(sign + " " + res);

            } else {
                return res * sign;
            }
        }
        return res * sign;
    }

    public int maxArea(int[] height) {
        int start = 0;
        int end = height.length - 1;
        int re = Integer.MIN_VALUE;
        while (start < end) {
            re = Math.max(re, (end - start) * Math.min(height[start], height[end]));
            if (height[start] > height[end]) {
                end--;
            } else {
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
        return M[num / 1000] + C[(num % 1000) / 100] + X[(num % 100) / 10] + I[num % 10];
    }

    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    j++;
                    continue;
                }
                if (k < nums.length - 1 && nums[k] == nums[k + 1]) {
                    k--;
                    continue;
                }
                int sum = nums[i] + nums[j] + nums[k];
                if (sum < 0) {
                    i++;
                } else if (sum > 0) {
                    k--;
                } else {
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
        for (int i = 0; i < nums.length - 2; i++) {
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                re = (Math.abs(target - sum) < Math.abs(target - re) ? sum : re);
                System.out.println(re);
                if (sum > target) {
                    k--;
                } else if (sum < target) {
                    j++;
                } else {
                    return sum;
                }
            }
        }
        return re;
    }

    public List<String> letterCombinations(String digits) {
        LinkedList<String> ans = new LinkedList<String>();
        if (digits.isEmpty()) return ans;
        String[] mapping = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        ans.add("");
        for (int i = 0; i < digits.length(); i++) {
            int x = Character.getNumericValue(digits.charAt(i));
            while (ans.peek().length() == i) {
                String t = ans.remove();
                for (char s : mapping[x].toCharArray())
                    ans.add(t + s);
            }
        }
        return ans;
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> re = new ArrayList<>();
        if (nums.length < 4) return re;
        Arrays.sort(nums);
        int max = nums.length - 1;
        if (4 * nums[0] > target || 4 * nums[max] < target) return re;
        for (int i = 0; i < max - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            if (4 * nums[i] > target) break;
            if (nums[i] + 3 * nums[max] < target) continue;
            for (int j = i + 1; j < max - 1; j++) {
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                if (nums[j] * 3 > target - nums[i]) break;
                if (nums[j] + 2 * nums[max] < target - nums[i]) continue;
                int low = j + 1;
                int high = max;
                while (low < high) {
                    int sum = nums[i] + nums[j] + nums[low] + nums[high];
                    if (target < sum) {
                        high--;
                    } else if (target > sum) {
                        low++;
                    } else {
                        re.add(Arrays.asList(nums[i], nums[j], nums[low++], nums[high--]));
                        while (low < high && nums[low] == nums[low - 1]) {
                            low++;
                        }
                        while (low < high && nums[high] == nums[high + 1]) {
                            high--;
                        }
                    }
                }
            }
        }
        return re;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode re = new ListNode(0);
        re.next = head;
        ListNode fast = re, slow = re;
        while (n-- > 0) {
            fast = fast.next;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        if (slow.next != null)
            slow.next = slow.next.next;
        return re.next;
    }

    public List<String> generateParenthesis(int n) {
        Set<String> re = new HashSet<>();
        String path = "()";
        recursion(re, path, 1, n);
        return new ArrayList<>(re);
    }

    private void recursion(Set<String> re, String path, int cur, int n) {
        if (n == cur) {
            re.add(path);
            return;
        }
        String parenthesis = "()";
        for (int i = 0; i <= parenthesis.length(); i++) {
            String left = parenthesis.substring(0, i);
            String right = parenthesis.substring(i, parenthesis.length());
            recursion(re, left + path + right, cur + 1, n);
        }
    }

    public ListNode swapPairsMy(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode re = new ListNode(0);
        ListNode tem = re;
        while (head != null && head.next != null) {
            tem.next = new ListNode(head.next.val);
            tem.next.next = new ListNode(head.val);
            head = head.next.next;
            tem = tem.next.next;
        }
        tem.next = head;
        return re.next;
    }

    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode re = new ListNode(0);
        re.next = head;
        for (ListNode cur = re; cur.next != null && cur.next.next != null; cur = cur.next.next) {
            cur.next = swap(cur.next, cur.next.next);
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
        return result > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) result;
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
        while ((sum + sum) <= dividend) {
            sum += sum;
            divide += divide;
        }

        // Make a recursive call for (devided-sum) and add it to the result
        return negative ? -(divide + divideLong((dividend - sum), divisor)) :
                (divide + divideLong((dividend - sum), divisor));
    }

    public void nextPermutation(int[] nums) {
        if (nums.length <= 1) return;
        int ind = nums.length - 2;
        while (ind >= 0 && nums[ind] >= nums[ind + 1]) ind--;
        if (ind >= 0) {
            int j = nums.length - 1;
            while (nums[j] <= nums[ind]) j--;
            nums[ind] = nums[j] + nums[ind] - (nums[j] = nums[ind]);
        }
        for (int i = ind + 1, ii = nums.length - 1; i < ii; i++, ii--) {
            nums[i] = nums[i] + nums[ii] - (nums[ii] = nums[i]);
        }
    }

    public int search(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (nums[mid] == target) return mid;
            if (nums[start] <= nums[mid]) {
                if (nums[mid] > target && target >= nums[start]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }
            if (nums[mid] <= nums[end]) {
                if (nums[mid] < target && target <= nums[end]) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }
        }
        return -1;
    }

    public int[] searchRange(int[] nums, int target) {
        int p1 = searchInsert(nums, target);
        int p2 = searchInsert(nums, target + 1);
        int[] re = new int[p2 - p1];
        for (int i = 0; i < re.length; i++) {
            re[i] = p1 + i;
        }
        return re;
    }

    private static int searchInsert(int[] nums, int target) {
        int le = 0;
        int ri = nums.length;
        while (le <= ri) {
            int mid = (le + ri) / 2;
            if (nums[mid] == target) {
                if (mid == 0 || nums[mid - 1] != nums[mid]) return mid;
                ri = mid - 1;
            } else if (nums[mid] > target) {
                if (mid == 0 || nums[mid - 1] < target) return mid;
                ri = mid - 1;
            } else {
                if (mid == nums.length - 1 || nums[mid + 1] >= target) return mid + 1;
                le = mid + 1;
            }
        }
        return 0;
    }

    //TODO 大神级代码
    public boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < 9; i++) {
            HashSet<Character> rows = new HashSet<Character>();
            HashSet<Character> columns = new HashSet<Character>();
            HashSet<Character> cube = new HashSet<Character>();
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.' && !rows.add(board[i][j]))
                    return false;
                if (board[j][i] != '.' && !columns.add(board[j][i]))
                    return false;
                int RowIndex = 3 * (i / 3);
                int ColIndex = 3 * (i % 3);
                if (board[RowIndex + j / 3][ColIndex + j % 3] != '.' && !cube.add(board[RowIndex + j / 3][ColIndex + j % 3]))
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
            for (int i = start; i < candidates.length && target >= candidates[i]; i++) {
                removeList.add(candidates[i]);
                weave(candidates, target - candidates[i], re, removeList, i);
                removeList.remove(removeList.size() - 1);
            }
        }
    }

    public String multiply(String num1, String num2) {
        if (num1.equals("0") || num2.equals("0")) return "0";
        StringBuilder sb = new StringBuilder();
        int len1 = num1.length();
        int len2 = num2.length();
        int arr[] = new int[len1 + len2];
        for (int i = len1 - 1; i >= 0; i--) {
            for (int j = len2 - 1; j >= 0; j--) {
                int tem = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int p2 = i + j + 1;
                int p1 = i + j;
                int sum = arr[p2] + tem;
                arr[p1] += sum / 10;
                arr[p2] = sum % 10;
            }
        }
        for (int x : arr) {
            sb.append(!(sb.length() == 0 && x == 0) ? x : "");
        }
        return sb.length() > 0 ? sb.toString() : "0";
    }

    public List<List<Integer>> permute(int[] nums) {
        //Arrays.sort(nums);
        List<List<Integer>> re = new ArrayList<>();
        backTracking1(nums, re, new ArrayList<Integer>(), 0, nums.length, new boolean[nums.length]);
        return re;
    }

    private void backTracking1(int[] nums, List<List<Integer>> re, ArrayList<Integer> list, int ind, int length, boolean[] used) {
        if (ind == length) {
            re.add((ArrayList<Integer>) list.clone());
        } else {
            for (int i = 0; i < length; i++) {
                if (!used[i]) {
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
        backTracking2(nums, re, new ArrayList<Integer>(), 0, nums.length, new boolean[nums.length]);
        return re;
    }

    private void backTracking2(int[] nums, List<List<Integer>> re, ArrayList<Integer> list, int ind, int length, boolean[] used) {
        if (ind == length) {
            re.add((ArrayList<Integer>) list.clone());
        } else {
            for (int i = 0; i < length; i++) {
                if (!used[i] && !(i > 0 && nums[i - 1] == nums[i] && !used[i - 1])) {
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
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
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
        if (n < 0) return (1 / x) * myPow(1.0 / x, -(n + 1));
        if (n == 0) return 1;
        double tem = myPow(x, n / 2);
        double re = tem * tem;
        return n % 2 == 0 ? re : re * x;
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return Collections.emptyList();
        int rowLen = matrix.length;
        int colLen = matrix[0].length;
        List<Integer> re = new ArrayList<>();
        settle(matrix, re, 0, rowLen - 1, 0, colLen - 1);
        return re;
    }

    private void settle(int[][] A, List<Integer> re, int rowStart, int rowEnd, int colStart, int colEnd) {
        if (rowStart == rowEnd) {
            for (int i = colStart; i <= colEnd; i++) re.add(A[rowStart][i]);
            return;
        } else if (colStart == colEnd) {
            for (int i = rowStart; i <= rowEnd; i++) re.add(A[i][colStart]);
            return;
        } else {
            for (int i = colStart; i <= colEnd - 1; i++) {
                re.add(A[rowStart][i]);
            }
            for (int i = rowStart; i <= rowEnd - 1; i++) {
                re.add(A[i][colEnd]);
            }
            for (int i = colEnd; i >= colStart + 1; i--) {
                re.add(A[rowEnd][i]);
            }
            for (int i = rowEnd; i >= rowStart + 1; i--) {
                re.add(A[i][colStart]);
            }
            if (rowEnd - rowStart >= 2 && colEnd - colStart >= 2) {
                settle(A, re, rowStart + 1, rowEnd - 1, colStart + 1, colEnd - 1);
            }
        }
        return;
    }

    public List<Interval> merge(List<Interval> intervals) {
        if (intervals.size() < 2) return intervals;
        intervals.sort((o1, o2) -> o2.start - o1.start);
        int start = intervals.get(0).start;
        int end = intervals.get(0).end;
        List<Interval> result = new ArrayList<>();
        for (int i = 1; i < intervals.size(); i++) {
            Interval temObj = intervals.get(i);
            if (temObj.start > end) {
                result.add(new Interval(start, end));
                start = temObj.start;
                end = temObj.end;
            } else {
                start = Math.min(start, temObj.start);
                end = Math.max(end, temObj.end);
            }
        }
        result.add(new Interval(start, end));
        return result;
    }

    public String getPermutation(int n, int k) {
        List<Integer> nums = IntStream.rangeClosed(1, n).boxed().collect(Collectors.toList());
        return "";
    }

    public ListNode rotateRight(ListNode head, int k) {
        if (k == 0 || head == null) {
            return head;
        }
        ListNode end = head;
        int count = 1;
        for (; end.next != null; end = end.next) count++;
        end.next = head;
        int realK = count - k % count;
        while (realK > 0) {
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
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid[0][0] == 1) {
            return 0;
        }
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int dp[][] = new int[m][n];
        for (int i = 0; i < m; i++) {
            if (obstacleGrid[i][0] == 0)
                dp[i][0] = 1;
            else
                break;
        }
        for (int i = 0; i < n; i++) {
            if (obstacleGrid[0][i] == 0)
                dp[0][i] = 1;
            else
                break;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] == 0) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                } else {
                    dp[i][j] = 0;
                }
            }
        }
        return dp[m - 1][n - 1];
    }

    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int dp[][] = new int[m][n];
        int x = 0;
        int y = 0;
        for (int i = 0; i < m; i++) {
            x += grid[i][0];
            dp[i][0] = x;
        }
        for (int i = 0; i < n; i++) {
            y += grid[0][i];
            dp[0][i] = y;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[m - 1][n - 1];
    }

    public void setZeroes(int[][] matrix) {
        List<Integer> xz = new ArrayList<>();
        List<Integer> yz = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    xz.add(i);
                    yz.add(j);
                }
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (xz.contains(i) || yz.contains(j)) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        int x = matrix.length;
        int y = matrix[0].length;
        int start = 0;
        int end = x * y - 1;
        int mid;
        while (start <= end) {
            mid = (start + end) / 2;
            if (matrix[mid / y][mid % y] == target) {
                return true;
            } else if (matrix[mid / y][mid % y] > target) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return false;
    }

    public void sortColors(int[] nums) {
        int len = nums.length;
        if (len < 2) return;
        int cnt0 = 0;
        int cnt1 = 0;
        for (int i = 0; i < len; i++) {
            if (nums[i] == 2) {
                while (nums[--len] == 2 && len > i) ;
                nums[i] = nums[len];
                nums[len] = 2;
            }
            if (i == len) return;
            if (nums[i] == 0) {
                if (nums[cnt0] == 1)
                    nums[cnt0 + cnt1] = 1;
                nums[cnt0++] = 0;
            } else {
                nums[cnt0 + cnt1++] = 1;
            }
        }
    }

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> re = new ArrayList<>();
        recur(1, n, k, new ArrayList<Integer>(), re);
        return re;
    }

    private void recur(int begin, int n, int k, List<Integer> tem, List<List<Integer>> re) {
        if (k == 0) {
            re.add(new ArrayList<>(tem));
            return;
        }
        for (int i = begin; i <= n - k + 1; i++) {
            tem.add(i);
            recur(i + 1, n, k - 1, tem, re);
            tem.remove(tem.size() - 1);
        }
    }

    public List<List<Integer>> subsets(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> re = new ArrayList<>();
        re.add(new ArrayList<>());
        recur(0, nums, new ArrayList<Integer>(), re);
        System.out.println(re);
        return re;
    }

    private void recur(int start, int[] nums, ArrayList<Integer> tem, List<List<Integer>> re) {
        for (int i = start; i < nums.length; i++) {
            tem.add(nums[i]);
            re.add(new ArrayList<>(tem));
            recur(i + 1, nums, tem, re);
            tem.remove(tem.size() - 1);
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
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (exist(board, i, j, chars, 0)) return true;
            }
        }
        return false;
    }

    private boolean exist(char[][] board, int x, int y, char[] chars, int len) {
        if (len == chars.length) return true;
        if (x < 0 || y < 0 || x == board.length || y == board[0].length) return false;
        if (board[x][y] != chars[len]) return false;
        board[x][y] ^= 256;
        boolean exist = exist(board, x + 1, y, chars, len + 1)
                || exist(board, x - 1, y, chars, len + 1)
                || exist(board, x, y + 1, chars, len + 1)
                || exist(board, x, y - 1, chars, len + 1);
        board[x][y] ^= 256;
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
        int cur = nums[0];
        int count = 1;
        int begin = 1;
        for (int i = 1; i < nums.length; i++) {
            if (cur == nums[i]) {
                count++;
                if (count <= 2) {
                    nums[begin++] = cur;
                }
            } else {
                nums[begin++] = nums[i];
                cur = nums[i];
                count = 1;
            }
        }
        return begin;
    }

    public boolean search2(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        int mid;
        while (start <= end) {
            mid = (start + end) / 2;
            if (nums[mid] == target) {
                return true;
            }
            if (nums[mid] < nums[end] || nums[mid] < nums[start]) {
                if (target > nums[mid] && target <= nums[end]) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            } else if (nums[mid] > nums[end] || nums[mid] > nums[start]) {
                if (target < nums[mid] && target >= nums[start]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } else {
                end--;
            }
        }
        return false;
    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) return null;
        ListNode re = new ListNode(0);
        ListNode tem = new ListNode(0);
        tem.next = head;
        re.next = tem;
        while (tem.next != null) {
            int cur = tem.next.val;
            if (tem.next.next != null) {
                if (tem.next.next.val == cur) {
                    while (tem.next != null && tem.next.val == cur) {
                        tem.next = tem.next.next;
                    }
                } else {
                    tem = tem.next;
                }
            } else {
                return re.next.next;
            }
        }
        return re.next.next;

    }

    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> re = new ArrayList<>();
        Arrays.sort(nums);
        boolean[] used = new boolean[nums.length];
        newRecur(0, new ArrayList<Integer>(), nums, re, used);
        return re;
    }

    private void newRecur(int begin, List<Integer> curList, int[] nums, List<List<Integer>> re, boolean[] used) {
        re.add(new ArrayList(curList));
        for (int i = begin; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) continue;
            curList.add(nums[i]);
            used[i] = true;
            newRecur(i + 1, curList, nums, re, used);
            used[i] = false;
            curList.remove(curList.size() - 1);
        }
    }

    /*
      Input: "12"
      Output: 2
      Explanation: It could be decoded as "AB" (1 2) or "L" (12).
     */
    public int numDecodings(String s) {
        if (s == null || s.isEmpty()) return 0;
        int[] dp = new int[s.length() + 1];
        dp[0] = 1;
        dp[1] = s.charAt(0) == '0' ? 0 : 1;
        for (int i = 2; i <= s.length(); i++) {
            int num1 = Integer.parseInt(s.substring(i - 1, i));
            int num2 = Integer.parseInt(s.substring(i - 2, i));
            if (num1 > 0) {
                dp[i] += dp[i - 1];
            }
            if (num2 > 9 && num2 < 27) {
                dp[i] += dp[i - 2];
            }
        }
        return dp[s.length()];
    }

    public int numTrees(int n) {
        int re = 0;
        int[] dp = new int[n];
        dp[0] = dp[1] = 1;
        for (int i = 2; i < n; i++) {
            for (int j = 0; j < i; j++) {
                re += dp[j] * dp[i - j - 1];
            }
        }
        return re;
    }

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int len = gas.length;
        int gSum = 0;
        int cSum = 0;
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            gSum += gas[i];
            cSum += cost[i];
            arr[i] = gas[i] - cost[i];
        }
        if (gSum < cSum) {
            return -1;
        }
        return 0;
    }

    //被X围住的O 变成X
    public void solve(char[][] board) {
        if (board == null || board.length == 0) {
            return;
        }
        int row = board.length;
        int col = board[0].length;
        if (row <= 1 && col <= 1) {
            return;
        }
        boolean[][] isTravelled = new boolean[row][col];
        boolean[][] isO = new boolean[row][col];
        for (int i = 0; i < col - 1; i++) {
            char c = board[0][i];
            if (c == 'O' && !isTravelled[0][i]) {
                travel(0, i, 1, board, isTravelled, isO);
            }
        }
        for (int i = 0; i < row - 1; i++) {
            char c = board[i][col - 1];
            if (c == 'O' && !isTravelled[i][col - 1]) {
                travel(i, col - 1, 4, board, isTravelled, isO);
            }
        }
        for (int i = col - 1; i > 0; i--) {
            char c = board[row - 1][i];
            if (c == 'O' && !isTravelled[row - 1][i]) {
                travel(row - 1, i, 3, board, isTravelled, isO);
            }
        }
        for (int i = row - 1; i > 0; i--) {
            char c = board[i][0];
            if (c == 'O' && !isTravelled[i][0]) {
                travel(i, 0, 2, board, isTravelled, isO);
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (!isO[i][j]) {
                    board[i][j] = 'X';
                }
            }
        }
    }

    private void travel(int x, int y, int dir, char[][] bMap, boolean[][] tMap, boolean[][] isO) {
        if (x < 0 || x > tMap.length - 1) return;
        if (y < 0 || y > tMap[0].length - 1) return;
        if (tMap[x][y]) return;
        tMap[x][y] = true;
        if (bMap[x][y] == 'O') {
            isO[x][y] = true;
            List<Integer> otherThreeDir = getOtherThreeDir(dir);
            for (int di : otherThreeDir) {
                if (di == 1) {
                    travel(x + 1, y, di, bMap, tMap, isO);
                } else if (di == 2) {
                    travel(x, y + 1, di, bMap, tMap, isO);
                } else if (di == 3) {
                    travel(x - 1, y, di, bMap, tMap, isO);
                } else {
                    travel(x, y - 1, di, bMap, tMap, isO);
                }
            }
        }
    }

    private List<Integer> getOtherThreeDir(int dir) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        if (dir == 1) {
            list.remove(new Integer(3));
        } else if (dir == 2) {
            list.remove(new Integer(4));
        } else if (dir == 3) {
            list.remove(new Integer(1));
        } else {
            list.remove(new Integer(2));
        }
        return list;
    }

    /**
     * 深拷贝 带随机指针的链表
     *
     * @param head
     * @return
     */
    public RandomListNode copyRandomList(RandomListNode head) {
        if (head == null) {
            return null;
        }
        final Map<RandomListNode, RandomListNode> oldToNewMap = new HashMap<RandomListNode, RandomListNode>();
        RandomListNode cur = head;
        while (cur != null) {
            oldToNewMap.put(cur, new RandomListNode(cur.label));
            cur = cur.next;
        }
        for (Map.Entry<RandomListNode, RandomListNode> entry : oldToNewMap.entrySet()) {
            final RandomListNode newNode = entry.getValue();
            RandomListNode oldNode = entry.getKey();
            newNode.next = oldToNewMap.get(oldNode.next);
            newNode.random = oldToNewMap.get(oldNode.random);
        }
        return oldToNewMap.get(head);
    }

    /**
     * 获取开始单词 到结束单词的最短路径 每次变换一个字母
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public int ladderLength(String beginWord, String endWord, Set<String> wordList) {
        Set<String> beginSet = new HashSet<String>(), endSet = new HashSet<String>();

        int len = 1;
        int strLen = beginWord.length();
        HashSet<String> visited = new HashSet<String>();

        beginSet.add(beginWord);
        endSet.add(endWord);
        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            if (beginSet.size() > endSet.size()) {
                Set<String> set = beginSet;
                beginSet = endSet;
                endSet = set;
            }

            Set<String> temp = new HashSet<String>();
            for (String word : beginSet) {
                char[] chs = word.toCharArray();

                for (int i = 0; i < chs.length; i++) {
                    for (char c = 'a'; c <= 'z'; c++) {
                        char old = chs[i];
                        chs[i] = c;
                        String target = String.valueOf(chs);

                        if (endSet.contains(target)) {
                            return len + 1;
                        }

                        if (!visited.contains(target) && wordList.contains(target)) {
                            temp.add(target);
                            visited.add(target);
                        }
                        chs[i] = old;
                    }
                }
            }

            beginSet = temp;
            len++;
        }

        return 0;
    }

    /**
     * 重新排序  1 2 3 4 5 -> 1 5 2 4 3
     * @param head
     */
    public void reorderList(ListNode head) {
        if(head == null || head.next == null){
            return;
        }
        int count = 1;
        ListNode cur = head;
        Map<Integer,ListNode> indToNode = new HashMap<>();
        indToNode.put(count,cur);
        while(cur.next != null){
            count++;
            cur = cur.next;
            indToNode.put(count,cur);
        }
//        int ind = count;
        int c = (count-1)/2;
        ListNode cuNode = head;
        while(c>0){
            ListNode tem = cuNode.next;
            ListNode listNode = indToNode.get(count);
            cuNode.next = listNode;
            listNode.next = tem;
            cuNode = tem;
            count--;
            c--;
        }
        ListNode curr = head;
        while(count>1){
            cur = cur.next;

        }
        cur.next = null;
        System.out.println(head.val);
    }

    /** TODO 超时了
     * Input: s = "applepenapple", wordDict = ["apple", "pen"]
     * Output: true
     * @param s
     * @param wordDict
     * @return
     */
//    public boolean wordBreak(String s, List<String> wordDict) {
//        if(s == null || s.length() == 0){
//            return true;
//        }
//        boolean result = false;
//        for(String word:wordDict){
//            if(s.startsWith(word)){
//                result = result || wordBreak(s.substring(word.length()),wordDict);
//            }
//        }
//        return result;
//    }
    public boolean wordBreak(String s, List<String> wordDict) {
        if(s == null || s.length() == 0){
            return true;
        }
        boolean[] dp = new boolean[s.length()+1];
        dp[0] = true;
        List<String> candidateString = new ArrayList<>();
        String temStr = "";
        for(int i=0;i<s.length();i++){
            if(dp[i]){
                temStr = s.substring(i,s.length());
                for(String word:wordDict){
                    if(temStr.startsWith(word)){
                        dp[i+word.length()] = true;
                    }
                }
            }
        }
        return dp[s.length()];
    }

    /** TODO 超时
     * s = "catsanddog"
     * wordDict = ["cat", "cats", "and", "sand", "dog"]
     * Output:
     * [
     *   "cats and dog",
     *   "cat sand dog"
     * ]
     * @param s
     * @param wordDict
     * @return
     */
    public List<String> wordBreakPro(String s, List<String> wordDict) {
        Stack<List<String>> stack = new Stack<>();
        stack.push(new ArrayList<>());
        List<List<String>> preRe = new ArrayList<>();
        while(!stack.empty()){
            List<String> topItem = stack.pop();
            int len = getStrListLength(topItem);
            if(len == s.length()){
                preRe.add(topItem);
                continue;
            }
            String temStr = s.substring(len,s.length());
            for(String word:wordDict){
                if(temStr.startsWith(word)){
                    topItem.add(word);
                    stack.push(new ArrayList<>(topItem));
                    topItem.remove(topItem.size()-1);
                }
            }
        }
        List<String> result = new ArrayList<>();
        for(List<String> item:preRe){
            StringBuilder sb = new StringBuilder();
            for(String str:item){
                sb.append(str+" ");
            }
            result.add(sb.substring(0,sb.length()-1));
        }
        return result;
    }
    private int getStrListLength(List<String> list){
        if(list == null || list.size() == 0){
            return  0;
        }else{
            return list.stream().map(String::length).reduce(0,Integer::sum).intValue();
        }
    }
    //TODO 大神代码
    public List<String> wordBreakProMaster(String s, List<String> wordDict) {
        List<String> res = new ArrayList<>();

        // build a map to store all the already searched string results
        HashMap<String, List<String>> map = new HashMap<>();

        if(s == null || s.length() == 0 || wordDict == null || wordDict.size() == 0) {
            return res;
        }

        return helper(s, wordDict, map);
    }
    private List<String> helper(String s, List<String> wordDict, HashMap<String, List<String>> map) {
        if(map.containsKey(s)) {
            return map.get(s);
        }
        List<String> res = new ArrayList<>();
        for(String w:wordDict) {
            if(s.startsWith(w)) {
                if(s.length() == w.length()) {
                    res.add(w);
                } else {
                    List<String> breakups = helper(s.substring(w.length()), wordDict, map);
                    for(String b : breakups) {
                        res.add(w + " " + b);
                    }
                }
            }
        }
        map.put(s, res);
        return res;
    }

    /**
     * Input: "  the   sky is blue"
     * Output: "blue is sky the"
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        if(s == null) return s;
        char[] chars = s.toCharArray();
        int len = chars.length;
        reverseCharArr(chars,0,len-1);
        int ind = 0;
        boolean hasBlack = true;
        for(int i=0;i<len;i++){
            if(chars[i] != ' '){
                chars[ind] = chars[i];
                ind++;
                hasBlack = false;
            }else if(!hasBlack){
                chars[ind] = chars[i];
                ind++;
                hasBlack = true;
            }else {
                hasBlack = true;
            }
        }
        int count = ind;
        while(ind != len){
            chars[ind] = ' ';
            ind++;
        }
        int start,end;
        for(int i=0;i<count;i++){
            if(chars[i] != ' '){
                start = i;
                end = i;
                while(end+1<count && chars[end+1] != ' '){
                    end++;
                }
                reverseCharArr(chars,start,end);
                i=end;
            }
        }
        return String.valueOf(chars).trim();
    }

    private void reverseCharArr(char[] chars,int start,int end){
        if(chars == null || chars.length<2) return;
        if(start <0 || end > chars.length-1 || start == end) return;
        int len = end - start + 1;
        for(int i=0;i<len/2;i++){
            char tem = chars[start+i];
            chars[start+i] = chars[end-i];
            chars[end-i] = tem;
        }
    }

    /**
     * if A[i]>=0:
     * 	   maxDP[i] = max(A[i], A[i]*maxDP[i-1]);
     * 	   minDP[i] = max(A[i], A[i]*minDP[i-1]);
     * else: // Negative A[i]
     * 	   maxDP[i] = max(A[i], minDP[i-1]*A[i]);
     *     minDP[i] = min(A[i], maxDP[i-1]*A[i]);
     * 子串最大乘积
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {
        if(nums == null || nums.length ==0) return 0;
        //contains the N's number max value
        int[] conN = new int[nums.length];
        //to N-1's number max value
        int[] toNm1 = new int[nums.length];
        conN[0] = nums[0];
        toNm1[0] = Integer.MIN_VALUE;
        for(int i = 1;i<nums.length;i++){
            conN[i] = countMaxMultiTo(nums,i);
            toNm1[i] = Math.max(toNm1[i-1],conN[i-1]);
        }
        return Math.max(conN[nums.length-1],toNm1[nums.length-1]);
    }
    private int countMaxMultiTo(int[] arr,int n){
        if(arr == null || arr.length<=n) return 0;
        int cur = 1;
        int max = Integer.MIN_VALUE;
        for(int i=n;i>=0;i--){
            cur = cur * arr[i];
            max = Math.max(max,cur);
        }
        return max;
    }

    /**
     * (i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).
     * Input: [3,4,5,1,2]
     * Output: 1
     * @param nums
     * @return
     */
    public int findMin(int[] nums) {
        return findM(nums,0,nums.length-1);
    }
    private int findM(int[] nums,int start,int end){
        if(start == end -1) return Math.min(nums[start],nums[end]);
        if(start == end) return nums[start];
        int mid = (start+end)/2;
        if(nums[mid]>nums[end]){
            return findM(nums,mid+1,end);
        }
        if(nums[mid] >nums[mid-1]){
            return findM(nums,start,mid-1);
        }else {
            return nums[mid];
        }
    }

    /**
     * 求岛屿数  1 陆地  0 水
     * Input:
     * 11000
     * 11000
     * 00100
     * 00011
     *
     * Output: 3
     * @param grid
     * @return
     */
    public int numIslands(char[][] grid) {
        if(grid == null || grid.length == 0 || grid[0].length == 0){
            return 0;
        }
        int count = 0;
        int row = grid.length;
        int col = grid[0].length;
        boolean[][] tourMap = new boolean[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(grid[i][j] == '1' && !tourMap[i][j]){
                    tour(i,j,tourMap,grid,0);
                    count++;
                }
            }
        }
        return count;
    }
    private void tour(int x, int y, boolean[][] tourMap, char[][] grid, int direction) {
        if(x<tourMap.length && x> -1 && y <tourMap[0].length && y > -1){
            if(tourMap[x][y]){
                return;
            }
            tourMap[x][y] = true;
            if(grid[x][y] == '0'){
                return;
            }
        }else{
            return;
        }
        List<Integer> directions = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        directions.remove(new Integer(direction));
        if(directions.contains(1)){
            tour(x+1,y,tourMap,grid,3);
        }
        if(directions.contains(2)){
            tour(x,y+1,tourMap,grid,4);
        }
        if(directions.contains(3)){
            tour(x-1,y,tourMap,grid,1);
        }
        if(directions.contains(4)){
            tour(x,y-1,tourMap,grid,2);
        }
    }

    /**
     * 寻找数组峰值位置  1 2 3 1  -> 2
     * @param nums
     * @return
     */
    public int findPeakElement(int[] nums) {
        if(nums.length == 1){
            return 0;
        }
        return findPeakInd(nums,0,nums.length-1);
    }
    private int findPeakInd(int[] nums, int start, int end) {
        if(isPeak(nums,start)) return start;
        if(isPeak(nums,end)) return end;
        int mid = (start+end)/2;
        if(isPeak(nums,mid)) return mid;
        if(nums[mid] > nums[start] && nums[mid] > nums[end]){
            if(nums[mid]>nums[mid-1]){
                return findPeakInd(nums,mid+1,end);
            }else {
                return findPeakInd(nums,start,mid-1);
            }
        }else if(nums[mid] <= nums[start] && nums[mid] > nums[end]){
            return findPeakInd(nums,start,mid-1);
        }else if(nums[mid] > nums[start] && nums[mid] <= nums[end]){
            return findPeakInd(nums,mid+1,end);
        }else if(nums[mid] <= nums[start] && nums[mid] <= nums[end]){
            return findPeakInd(nums,start,mid-1);
        }
        return 0;
    }
    private boolean isPeak(int[] nums,int ind){
        if(nums.length == 0) return false;
        if(nums.length == 1) return true;
        if(ind == 0){
            return nums[ind] > nums[ind+1];
        }else if(ind == nums.length-1){
            return nums[ind] > nums[ind-1];
        }else{
            return nums[ind] > nums[ind+1] && nums[ind] > nums[ind-1];
        }
    }

    /**
     * DNA序列 寻找出现1次以上的 10连序列
     * Input: s = "AAAAACCCCCAAAAACCCCCCAAAAAGGGTTT"
     * Output: ["AAAAACCCCC", "CCCCCAAAAA"]
     * @param s
     * @return
     */
    public List<String> findRepeatedDnaSequences(String s) {
        if(s == null || s.length() <=10) return null;
        List<String> re = new ArrayList<>();
        int len = s.length();
        Set<String> set = new HashSet<>();
        for(int i=10;i<=len;i++){
            String tem = s.substring(i - 10, i);
            if(!set.add(tem) && !re.contains(tem)){
                re.add(tem);
            }
        }
        return re;
    }

    /**
     * 抢劫2 首尾相连
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        EasyProblemSolution easy = new EasyProblemSolution();
        if(nums == null || nums.length<=2){
            easy.rob(nums);
        }
        int[] le = new int[nums.length-1];
        int[] ri = new int[nums.length-1];
        System.arraycopy(nums,0,le,0,nums.length-1);
        System.arraycopy(nums,1,ri,0,nums.length-1);
        return Math.max(easy.rob(le),easy.rob(ri));
    }

    /** TODO master
     * 课程1  [1,2]完成1课程前 得先完成2课程  总共需要完成numCourse个课程
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if(prerequisites == null || prerequisites.length == 0){
            return true;
        }
        ArrayList[] graph = new ArrayList[numCourses];
        for(int i=0;i<numCourses;i++){
            graph[i] = new ArrayList();
        }
        boolean[] visit = new boolean[numCourses];
        for(int i=0;i<prerequisites.length;i++){
            graph[prerequisites[i][1]].add(prerequisites[i][0]);
        }
        for(int i=0;i<numCourses;i++){
            if(!gra(graph,visit,i)){
                return false;
            }
        }
        return true;
    }

    private boolean gra(ArrayList[] graph, boolean[] visit, int ind) {
        if(visit[ind] == true){
            return false;
        }
        for(int i=0;i<graph[ind].size();i++){
            visit[ind] = true;
            int next = (int)graph[ind].get(i);
            if(!gra(graph,visit,next)){
                return false;
            }
            visit[ind] = false;
        }
        return true;
    }

    /**
     * 课程2  求课程学习路径  不能完成课程返回emptyList
     * Input: 4, [[1,0],[2,0],[3,1],[3,2]]
     * Output: [0,1,2,3] or [0,2,1,3]
     * Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        ArrayList[] graph = new ArrayList[numCourses];
        int[] depend = new int[numCourses];
        List<Integer> answer = new ArrayList<>();
        for(int i=0;i<numCourses;i++){
            graph[i] = new ArrayList();
        }
        for(int i=0;i<prerequisites.length;i++){
            depend[prerequisites[i][0]]++;
            graph[prerequisites[i][1]].add(prerequisites[i][0]);
        }
        Queue queue = new LinkedList();
        for(int i=0;i<numCourses;i++){
            if(depend[i] == 0){
                answer.add(i);
                queue.offer(i);
            }
        }
        while(!queue.isEmpty()){
            int val = (int)queue.poll();
            for(int i=0;i<graph[val].size();i++){
                int tem = (int) graph[val].get(i);
                depend[tem]--;
                if(depend[tem] == 0){
                    answer.add(tem);
                    queue.offer(tem);
                }
            }
        }
        if(answer.size()<numCourses){
            return new int[0];
        }
        int[] re = new int[numCourses];
        for(int i=0;i<numCourses;i++){
            re[i] = answer.get(i);
        }
        return re;
    }

    /**
     * 寻找nums最小子数组,求其长度 使得其和大于s
     * @param s
     * @param nums
     * @return
     */
    public int minSubArrayLen(int s, int[] nums) {
        int minLength = nums.length;
        int curLen = 0;
        int i = 0;
        int j = 0;
        int sum = 0;
        for(i=0;i<nums.length;i++){
            sum += nums[i];
            curLen++;
            while(sum >= s){
                minLength = Math.min(minLength,curLen);
                sum -= nums[j];
                curLen--;
                j++;
            }
        }
        if(sum < s && curLen == nums.length){
            return 0;
        }
        return minLength;
    }

    /** TODO 桶排序
     * 重复元素3  nums[i] nums[j]   abs[i-j]<=k  abs[nums[i]-nums[j]]<=t
     * @param nums
     * @param k
     * @param t
     * @return
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if(t<0) return false;
        int bucketSize = t+1;
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i<nums.length;i++){
            int no = getBucketNo(nums[i],bucketSize);
            if(map.containsKey(no)){
                return true;
            }else if(map.containsKey(no-1) && Math.abs(nums[i]-map.get(no-1))<bucketSize){
                return true;
            }else if(map.containsKey(no+1) && Math.abs(nums[i]-map.get(no+1))<bucketSize){
                return true;
            }
            if(i>=k) {
                map.remove(getBucketNo(nums[i - k], bucketSize));
            }
            map.put(no,nums[i]);
        }
        return false;
    }
    private int getBucketNo(int k,int size){
        return k<0?k/size-1:k/size;
    }

    /** TODO 最大正方形面积 1为有效 0无效面积
     * 利用dp
     * Input:
     * 1 0 1 0 0
     * 1 0 1 1 1
     * 1 1 1 1 1
     * 1 0 0 1 0
     *
     * Output: 4
     * @param matrix
     * @return
     */
    public int maximalSquare(char[][] matrix) {
        if(matrix.length == 0 || matrix[0].length == 0) return 0;
        int result = 0;
        int[] dp = new int[matrix[0].length];
        for(int i=0;i<matrix.length;i++){
            int[] curRow = new int[matrix[0].length];
            for(int j=0;j<matrix[0].length;j++){
                int le = j>0?curRow[j-1]:0;
                int top = i>0?dp[j]:0;
                int topLe = i>0&&j>0?dp[j-1]:0;
                if(matrix[i][j] == '1'){
                    curRow[j] = Math.min(le,Math.min(top,topLe))+1;
                }
                result = Math.max(result,curRow[j]*curRow[j]);
            }
            dp = curRow;
        }
        return result;
    }

    /**
     * (A,B)-(C,D) (E,F)-(G,H)所覆盖的总面积
     * @return
     */
    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        if(A>E){
            return computeArea(E,F,G,H,A,B,C,D);
        }
        int sum = calSquare(A, B, C, D) + calSquare(E, F, G, H);
        int totalSpace = 0;
        if(C<=E){
            return sum;
        }else if(A<=E && E<=C && C<=G){
            if(F>=D || B>=H){
                return sum;
            }else{
                return calSquare(A,B,E,D)+calSquare(C,F,G,H)+calSquare(E,Math.min(B,F),C,Math.max(D,H));
            }
        }else if(A<=E && E<=G && G<=C){
            if(B>=H || F>=D){
                return sum;
            }else{
                return calSquare(A,B,E,D)+calSquare(G,B,C,D)+calSquare(E,Math.min(B,F),G,Math.max(D,H));
            }
        }
        return 0;
    }
    private int calSquare(int A,int B,int C, int D){
        if(C<=A || D<=B){
            return 0;
        }
        return (C-A)*(D-B);
    }

    /**
     * 选出来候选人 要判断是否大于总数的三分之一
     * 主元素2 三分之一以上的元素
     * @param nums
     * @return
     */
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> result = new ArrayList<>();
        if(nums.length ==0) return result;
        int t1=0,t2=0;
        int count1=0,count2=0;
        for(int i=0;i<nums.length;i++){
            if(nums[i]==t1){
                count1++;
            }else if(nums[i]==t2){
                count2++;
            }else if(count1==0){
                t1=nums[i];
                count1++;
            }else if(count2==0){
                t2=nums[i];
                count2++;
            }else{
                count1--;
                count2--;
            }
        }
        if(count1 == 0 && count2 == 0){
            return result;
        }
        if(canDo(count1,t1,nums)){
            result.add(t1);
        }
        if(canDo(count2,t2,nums)){
            result.add(t2);
        }
        return result;
    }
    private boolean canDo(int count,int t,int[] nums){
        if(count <= 0){
            return false;
        }
        int sum = 0;
        for(int x:nums){
            if(x==t){
                sum++;
            }
        }
        return sum>nums.length/3;
    }

    /**
     * 求数组范围概述
     * Input:  [0,1,2,4,5,7]
     * Output: ["0->2","4->5","7"]
     * @param nums
     * @return
     */
    public List<String> summaryRanges(int[] nums) {

        List<String> re = new ArrayList<>();
        if(nums == null || nums.length == 0)return re;
        if(nums.length == 1){
            re.add(""+nums[0]);
            return re;
        }
        int pre = nums[0];
        int start=nums[0];
        StringBuilder ran = new StringBuilder(nums[0]);
        for(int i=1;i<nums.length;i++){
            if(nums[i]==pre+1){
                pre++;
            }else{
                if(pre == start){
                    re.add(""+pre);
                }else{
                    re.add(start+"->"+pre);
                }
                pre = nums[i];
                start = nums[i];
            }
        }
        if(pre == start){
            re.add(""+pre);
        }else{
            re.add(start+"->"+pre);
        }
        return re;
    }

    /**
     * 矩阵搜索   从左->右 递增   从上->下 递增
     * [
     *   [1,   4,  7, 11, 15],
     *   [2,   5,  8, 12, 19],
     *   [3,   6,  9, 16, 22],
     *   [10, 13, 14, 17, 24],
     *   [18, 21, 23, 26, 30]
     * ]
     * Given target = 5, return true.
     * Given target = 20, return false.
     *
     * TODO 大神代码
     * public boolean searchMatrix(int[][] matrix, int target) {
     *         if(matrix.length == 0) return false;
     *         int row = 0, col = matrix[0].length-1;
     *         while(row <= matrix.length-1 && col >= 0){
     *             if(matrix[row][col] == target) return true;
     *             else if(matrix[row][col] < target) row++;
     *             else if(matrix[row][col] > target) col--;
     *         }
     *         return false;
     *     }
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrixII(int[][] matrix, int target) {
        if(matrix == null || matrix.length == 0 || matrix[0].length == 0){
            return false;
        }
        int col = matrix.length;
        int row = matrix[0].length;
        if(target > matrix[col-1][row-1] || target < matrix[0][0]){
            return false;
        }
        for(int i=0;i<col;i++){
            if(matrix[i][0] < target){
                if(searchSortedArr(matrix[i],target)){
                    return true;
                }
            }else if(matrix[i][0] > target){
                return false;
            }else {
                return true;
            }
        }
        return false;
    }
    private boolean searchSortedArr(int[] arr,int target){
        if(arr[0] > target){
            return false;
        }
        int start = 0;
        int end = arr.length-1;
        while(start <= end){
            int mid = (start+end)/2;
            if(arr[mid]>target){
                end = mid-1;
            }else if(arr[mid]<target){
                start = mid+1;
            }else {
                return true;
            }
        }
        return false;
    }

    /**
     * TODO DP也可以
     * 一个算式的所有括号形式的结果
     * Input: "2*3-4*5"
     * Output: [-34, -14, -10, -10, 10]
     * Explanation:
     * (2*(3-(4*5))) = -34
     * ((2*3)-(4*5)) = -14
     * ((2*(3-4))*5) = -10
     * (2*((3-4)*5)) = -10
     * (((2*3)-4)*5) = 10
     * @param input
     * @return
     */
    public List<Integer> diffWaysToCompute(String input) {
        List<Integer> re = new ArrayList<>();
        boolean hasCom = false;
        for(int i=0;i<input.length();i++){
            char c = input.charAt(i);
            if(c == '-' || c == '+' || c == '*'){
                hasCom = true;
                String pre = input.substring(0, i);
                String suf = input.substring(i+1);
                List<Integer> preList = diffWaysToCompute(pre);
                List<Integer> sufList = diffWaysToCompute(suf);
                List<Integer> mulResultList = getMulResultList(c, preList, sufList);
                re.addAll(mulResultList);
            }
        }
        if(hasCom){
            return new ArrayList<>(re);
        }else {
            return new ArrayList<Integer>(){{add(Integer.valueOf(input));}};
        }
    }
    private List<Integer> getMulResultList(char c, List<Integer> preList, List<Integer> sufList) {
        List<Integer> result = new ArrayList<>();
        for(int i=0;i<preList.size();i++){
            int preVal = preList.get(i);
            for(int j=0;j<sufList.size();j++){
                int sufVal = sufList.get(j);
                result.add(computerNum(preVal,sufVal,c));
            }
        }
        return result;
    }
    private int computerNum(int a, int b, char x) {
        if(x == '-'){
            return a-b;
        }else if(x == '+'){
            return a+b;
        }else{
            return a*b;
        }
    }

    /** TODO 递归超时  用堆栈
     * 计算算术表达式  + - * /
     * Input: "3+2*2"
     * Output: 7
     * @param s
     * @return
     */
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        s = s.replace(" ","");
        int cur = 0;
        int result = 0;
        char sign = '+';
        for(int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if(Character.isDigit(c)){
                cur = c-'0';
                while(i+1 < s.length() && Character.isDigit(s.charAt(i+1))){
                    i++;
                    cur = cur*10 + (s.charAt(i)-'0');
                }
            }
            if(sign == '+'){
                stack.push(cur);
            }else if(sign == '-'){
                stack.push(-cur);
            }else if(sign == '*'){
                stack.push(stack.pop()*cur);
            }else{
                stack.push(stack.pop()/cur);
            }
            if(i < s.length()-1) {
                sign = s.charAt(i+1);
                i++;
            }
        }
        for(int x:stack){
            result += x;
        }
        return result;
    }
    //TODO 有点费解
    public int calculate_Pro(String s) {
        int idx = 0, sum = 0, pre = 0;
        s = s.replaceAll(" ", "");
        while(idx < s.length()) {
            int start = idx++;
            while(idx < s.length() && Character.isDigit(s.charAt(idx))) idx++;
            int v = Integer.valueOf(s.substring(Character.isDigit(s.charAt(start))? start : start + 1, idx));
            if(s.charAt(start) == '-') v = -v;
            if(s.charAt(start) == '/' || s.charAt(start) == '*') {
                sum -= pre;
                if(s.charAt(start) == '/') pre /= v;
                if(s.charAt(start) == '*') pre *= v;
            } else {
                pre = v;
            }
            sum += pre;
        }
        return sum;
    }

    /**
     * 根据数组 输出 当前元素以外的所有元素乘积的数组
     * Input:  [1,2,3,4]
     * Output: [24,12,8,6]
     * @param nums
     * @return
     */
    public int[] productExceptSelf(int[] nums) {
        int sum = 1;
        int zeroCount = 0;
        for(int x:nums){
            if(x == 0){
                zeroCount++;
            }else {
                sum *= x;
            }
        }
        if(zeroCount >1){
            int[] re = new int[nums.length];
            Arrays.fill(re,0);
            return re;
        }
        for(int i=0;i<nums.length;i++){
            if(zeroCount ==1){
                nums[i] = nums[i] == 0?sum:0;
            }else{
                nums[i] = nums[i]==0? sum : sum/nums[i];
            }
        }
        return nums;
    }

    /**
     * TODO 大神三行代码
     *     public int rangeBitwiseAnd(int m, int n) {
     *         if (m == n) {
     *             return m;
     *         }
     *         return 2 * rangeBitwiseAnd(m >> 1, n >> 1);
     *     }
     * 求[m,n]范围内所有数按位与后的结果
     *  [5,7] -> 5&6&7 -> 4
     * @param m
     * @param n
     * @return
     */
    public int rangeBitwiseAnd(int m, int n) {
        if(m==n) return n;
        int ind = 1<<30;
        int re = 0;
        while(ind >0){
            if( (m&ind)>0 && (n&ind)>0){
                re+=ind;
            }else if((m&ind) != (n&ind)){
                return re;
            }
            ind>>=1;
        }
        return re;
    }

    /**
     * TODO 寻找第k大元素
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for(int x:nums){
            queue.offer(x);
        }
        int re = 0;
        int loop = nums.length-k;
        while(loop>=0){
            re = queue.poll();
            loop--;
        }
        return re;
    }

    /**
     * k数 和为n 1-9的集合中选  不能重复
     * Input: k = 3, n = 9
     * Output: [[1,2,6], [1,3,5], [2,3,4]]
     * @param k
     * @param n
     * @return
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> re = new ArrayList<>();
        if(n< k || n>k*9){
            return re;
        }
        List<Integer> tem = new ArrayList<>();
        int[] nums = new int[]{1,2,3,4,5,6,7,8,9};
        for(int i=0;i<nums.length;i++){
            tem.add(nums[i]);
            doRecur(re,i+1,tem,nums,nums[i],k,n);
            tem.remove(tem.size()-1);
        }
        return re;
    }

    private void doRecur(List<List<Integer>> re, int start, List<Integer> tem, int[] nums,int sum,int k,int target) {
        if(sum == target && tem.size()==k){
            re.add(new ArrayList<>(tem));
        }
        if(start > nums.length-1){
            return;
        }
        for(int i=start;i<nums.length;i++){
            tem.add(nums[i]);
            doRecur(re,i+1,tem,nums,sum+nums[i],k,target);
            tem.remove(tem.size()-1);
        }
    }

    /**
     * 有俩数出现一次 其他的出现两次  找出这俩数
     * Input:  [1,2,1,3,2,5]
     * Output: [3,5]
     * @param nums
     * @return
     */
    public int[] singleNumber(int[] nums) {
        int x = 0;
        for(int a:nums){
            x^=a;
        }

        System.out.println(x);
        return null;
    }

    public static void main(String[] args) throws Exception{
//        MediumProblemSolution mediumProblemSolution = new MediumProblemSolution();
//        int[] x = mediumProblemSolution.singleNumber(new int[]{1,2,3,8,7,3,7,8});

//        System.out.println(lists.size());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> list = new ArrayList<>();
        Date parse1 = simpleDateFormat.parse("2019-4-21");
        list.add(parse1);
        Date date = list.stream().max(Date::compareTo).get();
        System.out.println(date.toString());
        Date parse2 = simpleDateFormat.parse("2019-4-23");
        Date parse3 = simpleDateFormat.parse("2019-4-25");
        list.add(parse2);
        list.add(parse3);
        date = list.stream().max(Date::compareTo).get();
        System.out.println(date.toString());
        System.out.println(1^3);
    }
}
