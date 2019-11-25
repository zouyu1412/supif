package com.zouyu.leetcode;

import java.util.*;

/**
 * Created by zouy-c on 2018/3/19.
 */
public class MasterClassicCode {

    //k数和
    List<List<Integer>> kSum_Trim(int[] a, int target, int k) {
        List<List<Integer>> result = new ArrayList<>();
        if (a == null || a.length < k || k < 2) return result;
        Arrays.sort(a);
        kSum_Trim(a, target, k, 0, result, new ArrayList<>());
        return result;
    }

    void kSum_Trim(int[] a, int target, int k, int start, List<List<Integer>> result, List<Integer> path) {
        int max = a[a.length - 1];
        if (a[start] * k > target || max * k < target) return;

        if (k == 2) {                        // 2 Sum
            int left = start;
            int right = a.length - 1;
            while (left < right) {
                if (a[left] + a[right] < target) left++;
                else if (a[left] + a[right] > target) right--;
                else {
                    result.add(new ArrayList<>(path));
                    result.get(result.size() - 1).addAll(Arrays.asList(a[left], a[right]));
                    left++;
                    right--;
                    while (left < right && a[left] == a[left - 1]) left++;
                    while (left < right && a[right] == a[right + 1]) right--;
                }
            }
        } else {                        // k Sum
            for (int i = start; i < a.length - k + 1; i++) {
                if (i > start && a[i] == a[i - 1]) continue;
                if (a[i] + max * (k - 1) < target) continue;
                if (a[i] * k > target) break;
                if (a[i] * k == target) {
                    if (a[i + k - 1] == a[i]) {
                        result.add(new ArrayList<>(path));
                        List<Integer> temp = new ArrayList<>();
                        for (int x = 0; x < k; x++) temp.add(a[i]);
                        result.get(result.size() - 1).addAll(temp);    // Add result immediately.
                    }
                    break;
                }
                path.add(a[i]);
                kSum_Trim(a, target - a[i], k - 1, i + 1, result, path);
                path.remove(path.size() - 1);        // Backtracking
            }
        }
    }

    public int divide(int dividend, int divisor) {
        if (divisor == 1) // Trival case 1
            return dividend;
        // Use negative integers to avoid integer overflow
        if (dividend > 0)
            return -divide(-dividend, divisor);
        if (divisor > 0)
            return -divide(dividend, -divisor);
        if (dividend > divisor) // Trivial case 2
            return 0;
        if ((dividend == Integer.MIN_VALUE) && (divisor == -1)) // Overflow case
            return Integer.MAX_VALUE;

        // Find the highest mult = (divisor * 2^shifts) which is <= dividend
        // by shifting mult to the left without causing an overflow.
        // At most (log2(|dividend|) - log2(|divisor|) + 1) iterations.
        int min_divisor = Integer.MIN_VALUE >> 1;
        int mult = divisor; // = divisor * 2^shifts
        int shifts = 0;
        while ((mult >= min_divisor) && (mult > dividend)) {
            mult <<= 1;
            ++shifts;
        }

        System.out.println(mult);
        System.out.println(shifts);
        // Compute the result by shifting mult to the right.
        // At most (log2(|dividend|) - log2(|divisor|) + 1) iterations for the outer loop.
        // At most (log2(|dividend|) - log2(|divisor|) + 1) iterations for the inner loop
        // (in total, not per outer iteration).
        int result = 0;
        int power = 1 << shifts; // = 2^shifts

        System.out.println(power);
        while (dividend <= divisor) {
            shifts = 0;
            while (mult < dividend) {
                mult >>= 1;
                ++shifts;
            }
            dividend -= mult;
            power >>= shifts;
            result |= power; // Adds power to result
        }
        return result;
    }

    //数独解决
    public void solveSudoku(char[][] board) {
        if (board == null || board.length == 0)
            return;
        solve(board);
    }

    public boolean solve(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '.') {
                    for (char c = '1'; c <= '9'; c++) {//trial. Try 1 through 9
                        if (isValid(board, i, j, c)) {
                            board[i][j] = c; //Put c for this cell

                            if (solve(board))
                                return true; //If it's the solution return true
                            else
                                board[i][j] = '.'; //Otherwise go back
                        }
                    }

                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(char[][] board, int row, int col, char c) {
        for (int i = 0; i < 9; i++) {
            if (board[i][col] != '.' && board[i][col] == c) return false; //check row
            if (board[row][i] != '.' && board[row][i] == c) return false; //check column
            if (board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] != '.' &&
                    board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c) return false; //check 3*3 block
        }
        return true;
    }

    //路径简化
    public static String simplifyPath(String path) {
        String[] paths = path.split("/");
        StringBuilder out = new StringBuilder();
        int jump = 0;
        for (int i = paths.length - 1; i >= 0; i -= 1) {
            String p = paths[i];
            System.out.println(p + " ===start==cur");
            if (p.equals(".") || p.equals("")) {
                continue;
            }
            if (p.equals("..")) {
                jump += 1;
                continue;
            }
            System.out.println(jump);
            if (jump == 0) {
                out.insert(0, "/" + p);
            } else {
                jump -= 1;
            }
        }
        if (out.length() == 0) {
            return "/";
        }

        return out.toString();
    }

    //直方图最大矩形面积
    public int largestRectangleArea(int[] height) {
        int len = height.length;
        Stack<Integer> s = new Stack<Integer>();
        int maxArea = 0;
        for (int i = 0; i <= len; i++) {
            int h = (i == len ? 0 : height[i]);
            if (s.isEmpty() || h >= height[s.peek()]) {
                s.push(i);
            } else {
                int tp = s.pop();
                maxArea = Math.max(maxArea, height[tp] * (s.isEmpty() ? i : i - 1 - s.peek()));
                i--;
            }
        }
        return maxArea;
    }

    //获取向下(非必须根到叶子)路径为固定和数量
    public int pathSum(TreeCluster.TreeNode root, int sum) {
        return root != null ? f(root, sum) + pathSum(root.left, sum) + pathSum(root.right, sum) : 0;
    }

    private int f(TreeCluster.TreeNode root, int sum) {
        return root != null ? ((root.val == sum ? 1 : 0) + f(root.left, sum - root.val) + f(root.right, sum - root.val)) : 0;
    }

    //大神代码 换成常量空间
    public void connect(TreeCluster.TreeLinkNode root) {
        Queue<TreeCluster.TreeLinkNode> queue = new LinkedList<>();
        if (root == null) return;
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            TreeCluster.TreeLinkNode tem = null;
            for (int i = 0; i < size; i++) {
                root = queue.poll();
                root.next = tem;
                tem = root;
                if (root.right != null) queue.offer(root.right);
                if (root.left != null) queue.offer(root.left);
            }
        }
    }

    //大神代码
    public int sumNumbers(TreeCluster.TreeNode root) {
        return rootToLeafSum(root, 0);
    }

    private int rootToLeafSum(TreeCluster.TreeNode root, int sum) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return sum * 10 + root.val;
        return rootToLeafSum(root.left, sum * 10 + root.val) + rootToLeafSum(root.right, sum * 10 + root.val);
    }


    //X包围的O变成X BFS
    int[][] dirs = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public void solveII(char[][] board) {
        if (board.length == 0) return;
        //Add all boundry 'O'
        Queue<int[]> bfs = new ArrayDeque<>();
        boolean[][] visited = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if ((i == 0 || i == board.length - 1 || j == 0 || j == board[0].length - 1) && board[i][j] == 'O') {
                    bfs.add(new int[]{i, j});
                    visited[i][j] = true;
                    board[i][j] = 'K';
                }
            }
        }

        //Find any 'O' who are on the same connected component as the boundry 'O' and mark them
        while (!bfs.isEmpty()) {
            int size = bfs.size();
            for (int i = 0; i < size; i++) {
                int[] curr = bfs.poll();
                for (int[] dir : dirs) {
                    int di = curr[0] + dir[0];
                    int dj = curr[1] + dir[1];
                    if (!inBounds(di, dj, board) || visited[di][dj] || board[di][dj] != 'O') continue;
                    visited[di][dj] = true;
                    bfs.add(new int[]{di, dj});
                    board[di][dj] = 'K';
                }
            }
        }

        //Set correct value to all board pieces
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X';
                else if (board[i][j] == 'K') board[i][j] = 'O';
            }
        }


    }

    boolean inBounds(int i, int j, char[][] board) {
        return i >= 0 && j >= 0 && i <= board.length - 1 && j <= board[i].length - 1;
    }

    /**
     * Input: "  the   sky is blue"
     * Output: "blue is sky the"
     *
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        if (s == null) return null;
        char[] a = s.toCharArray();
        int n = a.length;
        // step 1. reverse the whole string
        reverse(a, 0, n - 1);
        // step 2. reverse each word
        reverseWords(a, n);
        // step 3. clean up spaces
        return cleanSpaces(a, n);
    }

    void reverseWords(char[] a, int n) {
        int i = 0, j = 0;
        while (i < n) {
            while (i < j || i < n && a[i] == ' ') i++; // skip spaces
            while (j < i || j < n && a[j] != ' ') j++; // skip non spaces
            reverse(a, i, j - 1);                      // reverse the word
        }
    }

    // trim leading, trailing and multiple spaces
    String cleanSpaces(char[] a, int n) {
        int i = 0, j = 0;
        while (j < n) {
            while (j < n && a[j] == ' ') j++;             // skip spaces
            while (j < n && a[j] != ' ') a[i++] = a[j++]; // keep non spaces
            while (j < n && a[j] == ' ') j++;             // skip spaces
            if (j < n) a[i++] = ' ';                      // keep only one space
        }
        return new String(a).substring(0, i);
    }

    // reverse a[] from a[i] to a[j]
    private void reverse(char[] a, int i, int j) {
        while (i < j) {
            char t = a[i];
            a[i++] = a[j];
            a[j--] = t;
        }
    }

    /**
     * 1 2 3 2 -> 2 寻找峰值位置
     *
     * @param nums
     * @return
     */
    public int findPeakElement(int[] nums) {
        int start = 0, end = nums.length - 1;
        while (start < end) {
            if (nums[start] > nums[start + 1])
                return start;
            start++;
            if (nums[end] > nums[end - 1])
                return end;
            end--;
        }
        return end;
    }

    /**
     * 判断字符串是否同形 Easy
     *
     * @param args
     */
    public boolean isIsomorphic(String s, String t) {
        Map m = new HashMap();
        for (Integer i = 0; i < s.length(); ++i) {
            Object put = m.put(s.charAt(i), i);
            Object put1 = m.put(t.charAt(i) + "", i);

            if (put != put1)
                return false;
        }
        return true;
    }

    /**
     * 完成课程  [1,2]完成1课程前 得先完成2课程  总共需要完成numCourse个课程
     * TODO Medium
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        ArrayList[] graph = new ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++)
            graph[i] = new ArrayList();

        boolean[] visited = new boolean[numCourses];
        for (int i = 0; i < prerequisites.length; i++) {
            graph[prerequisites[i][1]].add(prerequisites[i][0]);
        }

        for (int i = 0; i < numCourses; i++) {
            if (!dfs(graph, visited, i))
                return false;
        }
        return true;
    }

    private boolean dfs(ArrayList[] graph, boolean[] visited, int course) {
        if (visited[course])
            return false;
        else
            visited[course] = true;
        ;

        for (int i = 0; i < graph[course].size(); i++) {
            if (!dfs(graph, visited, (int) graph[course].get(i)))
                return false;
        }
        visited[course] = false;
        return true;
    }

    /**
     * TODO hardProblem 课程3
     * @param courses
     * @return
     */
    public int scheduleCourse(int[][] courses) {
        List<Pair> list = new ArrayList<>();
        for (int i = 0; i < courses.length; i++) {
            Pair p = new Pair(courses[i][1], courses[i][0]);
            list.add(p);
        }
        Collections.sort(list);
        int total = 0;
        int size = list.size();
        PriorityQueue<Pair> q = new PriorityQueue<>(size, new LengthComparator());
        for (int i = 0; i < size; i++) {
            Pair p = list.get(i);
            if (p.length > p.limit)
                continue;
            if (total + p.length <= p.limit) {
                q.add(p);
                total += p.length;
            } else {
                Pair maxPair = q.peek();
                if (maxPair.length > p.length) {
                    Pair removed = q.poll();
                    total -= removed.length;
                    q.add(p);
                    total += p.length;
                }
            }
        }
        return q.size();
    }
    class Pair implements Comparable<Pair> {
        int limit;
        int length;
        public Pair(int limit, int length) {
            this.limit = limit;
            this.length = length;
        }
        @Override
        public int compareTo(Pair p1) {
            return this.limit - p1.limit;
        }
    }
    class LengthComparator implements Comparator<Pair> {
        @Override
        public int compare(Pair p1, Pair p2) {
            return p2.length - p1.length;
        }
    }

    /**
     * Input:
     * 1 0 1 0 0
     * 1 0 1 1 1
     * 1 1 1 1 1
     * 1 0 0 1 0
     *
     * Output: 4
     * 最大正方形 1有效 0无效
     * @param matrix
     * @return
     */
    public int maximalSquare(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int[] dp = new int[matrix[0].length];
        int ans = 0;
        for (int i = 0; i < matrix.length; i++) {
            int[] newRow = new int[matrix[0].length];
            for (int j = 0; j < matrix[0].length; j++) {
                int left = (j - 1 >= 0) ? newRow[j - 1] : 0;
                int top = (i - 1 >= 0) ? dp[j] : 0;
                int topLeft = (i - 1 >= 0 && j - 1 >= 0) ? dp[j - 1] : 0;
                if (matrix[i][j] == '1') {
                    newRow[j] = Math.min(left, Math.min(top, topLeft)) + 1;
                }
                ans = Math.max(ans, newRow[j] * newRow[j]);
            }
            dp = newRow;
        }
        return ans;
    }

    /**
     * 根据数组 输出 当前元素以外的所有元素乘积的数组
     * Input:  [1,2,3,4]
     * Output: [24,12,8,6]
     * @param nums
     * @return
     */
    public int[] productExceptSelf(int[] nums) {
        int[] result = new int[nums.length];
        for (int i = 0, tmp = 1; i < nums.length; i++) {
            result[i] = tmp;
            tmp *= nums[i];
        }
        for (int i = nums.length - 1, tmp = 1; i >= 0; i--) {
            result[i] *= tmp;
            tmp *= nums[i];
        }
        return result;
    }

    /**
     * 第n个丑数  2 3 5因子
     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {
        int[] ugly = new int[n];
        ugly[0] = 1;
        int index2 = 0, index3 = 0, index5 = 0;
        int factor2 = 2, factor3 = 3, factor5 = 5;
        for(int i=1;i<n;i++){
            int min = Math.min(Math.min(factor2,factor3),factor5);
            ugly[i] = min;
            if(factor2 == min)
                factor2 = 2*ugly[++index2];
            if(factor3 == min)
                factor3 = 3*ugly[++index3];
            if(factor5 == min)
                factor5 = 5*ugly[++index5];
        }
        return ugly[n-1];
    }

    /**
     * TODO 大神代码 原地进行 游戏人生 MediumProblem
     * @param board
     */
    public void gameOfLife(int[][] board) {
        //sanity check
        if (board == null || board.length == 0) {
            return;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] += count(board, i, j) * 10;
                priArr(board);
                System.out.println();
            }
        }
        //modify
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int shi = board[i][j] / 10;
                if (shi == 3) {
                    board[i][j] = 1;
                } else if (shi < 2 || shi > 3) {
                    board[i][j] = 0;
                } else {
                    board[i][j] %= 10;
                }
            }
        }
        return;
    }
    public int count(int[][] board, int i, int j) {
        int res = 0;
        if (i >= 1 && j >= 1 && getV(board, i - 1, j - 1) )            // up left
            res++;
        if (i >= 1 && getV(board, i - 1, j) )                           // up
            res++;
        if (i >= 1 && j <= board[0].length - 2 && getV(board, i - 1, j + 1) ) //upright
            res++;
        if (j >= 1 && getV(board, i, j - 1))                        //left
            res++;
        if (j <= board[0].length - 2 && getV(board, i, j + 1) )         //right
            res++;
        if (i <= board.length - 2 && j >= 1 && getV(board, i + 1, j - 1))   // botleft
            res++;
        if (i <= board.length - 2 && getV(board, i + 1, j))         //down\
            res++;
        if (i <= board.length - 2 && j <= board[0].length - 2 && getV(board, i + 1, j + 1))  // botright
            res++;

        return res;
    }
    public boolean getV(int[][] board, int i, int j) {
        if (board[i][j] < 10) {
            return board[i][j] > 0;
        }
        return board[i][j] % 10 > 0;
    }

    /**
     * 最大递增序列长度 Medium
     * @param a
     * @param low
     * @param high
     * @param x
     * @return
     */
    public static int findPositionToReplace(int[] a, int low, int high, int x) {
        int mid;
        while (low <= high) {
            mid = low + (high - low) / 2;
            if (a[mid] == x)
                return mid;
            else if (a[mid] > x)
                high = mid - 1;
            else
                low = mid + 1;
        }
        return low;
    }
    public static int lengthOfLIS(int[] nums) {
        if (nums == null | nums.length == 0)
            return 0;
        int n = nums.length, len = 0;
        int[] increasingSequence = new int[n];
        increasingSequence[len++] = nums[0];
        for (int i = 1; i < n; i++) {
            if (nums[i] > increasingSequence[len - 1])
                increasingSequence[len++] = nums[i];
            else {
                int position = findPositionToReplace(increasingSequence, 0, len - 1, nums[i]);
                increasingSequence[position] = nums[i];
            }
        }
        return len;
    }

    /**
     * 股票利润 可以买任意次数  但是卖完必须"冷却"一天
     * Input: [1,2,3,0,2]
     * Output: 3
     * Explanation: transactions = [buy, sell, cooldown, buy, sell]
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int L = prices.length;
        if(L < 2) return 0;

        int has1_doNothing = -prices[0];
        int has1_Sell = 0;
        int has0_doNothing = 0;
        int has0_Buy = -prices[0];
        System.out.println(has1_doNothing + "    " + has0_Buy+"    " + has0_doNothing + "    " + has1_Sell);
        for(int i=1; i<L; i++) {
            has1_doNothing = has1_doNothing > has0_Buy ? has1_doNothing : has0_Buy;
            has0_Buy = -prices[i] + has0_doNothing;
            has0_doNothing = has0_doNothing > has1_Sell ? has0_doNothing : has1_Sell;
            has1_Sell = prices[i] + has1_doNothing;

            System.out.println(has1_doNothing + "    " + has0_Buy+"    " + has0_doNothing + "    " + has1_Sell);
        }
        return has1_Sell > has0_doNothing ? has1_Sell : has0_doNothing;
    }


    /**
     * 一个无向图 随意选区root节点 求最小高度的根节点集合
     *
     * Input: n = 6, edges = [[0, 3], [1, 3], [2, 3], [4, 3], [5, 4]]
     *
     *      0  1  2
     *       \ | /
     *         3
     *         |
     *         4
     *         |
     *         5
     *
     * Output: [3, 4]
     * @param n
     * @param edges
     * @return
     */
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n == 1) return Collections.singletonList(0);

        List<Set<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; ++i) adj.add(new HashSet<>());
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        List<Integer> leaves = new ArrayList<>();
        for (int i = 0; i < n; ++i)
            if (adj.get(i).size() == 1) leaves.add(i);

        while (n > 2) {
            n -= leaves.size();
            List<Integer> newLeaves = new ArrayList<>();
            for (int i : leaves) {
                int j = adj.get(i).iterator().next();
                adj.get(j).remove(i);
                if (adj.get(j).size() == 1) newLeaves.add(j);
            }
            leaves = newLeaves;
        }
        return leaves;
    }

    /** TODO medium
     * 字典找两个 不含重复字母的 word  求len[word1]*len[word2] 最大值
     * Input: ["abcw","baz","foo","bar","xtfn","abcdef"]
     * Output: 16
     * Explanation: The two words can be "abcw", "xtfn".
     * @param words
     * @return
     */
//    public int maxProduct(String[] words) {
//        int res = 0, n = words.length;
//        Map<Character, Set<Integer>> charToWordIdxs = new HashMap<>();
//        for(char c = 'a'; c <= 'z'; ++c) charToWordIdxs.put(c, new HashSet<>());
//        Set<Integer> idxs = new HashSet<>();
//        for(int i = 0; i < n; ++i) {
//            if(i != 0) idxs.add(i - 1);
//            String word = words[i];
//            Set<Integer> validSet = new HashSet<>(idxs);
//            for(int j = 0; j < word.length(); ++j) {
//                char c = word.charAt(j);
//                Set<Integer> commonWordIdxs = charToWordIdxs.get(c);
//                validSet.removeAll(commonWordIdxs);
//                charToWordIdxs.get(word.charAt(j)).add(i);
//            }
//            for(int validIdx: validSet) {
//                res =Math.max(res,  words[validIdx].length() * words[i].length());
//            }
//        }
//        return res;
//    }
    public int maxProduct(String[] words) {
        int max = 0;

        Arrays.sort(words, (a, b) -> b.length() - a.length());

        int[] masks = new int[words.length]; // alphabet masks

        for(int i = 0; i < masks.length; i++){
            for(char c: words[i].toCharArray()){
                masks[i] |= 1 << (c - 'a');
            }
        }

        for(int i = 0; i < masks.length; i++){
            if(words[i].length() * words[i].length() <= max) break; //prunning
            for(int j = i + 1; j < masks.length; j++){
                if((masks[i] & masks[j]) == 0){
                    max = Math.max(max, words[i].length() * words[j].length());
                    break; //prunning
                }
            }
        }

        return max;
    }

    /**
     * TODO 三种方法  线段树 二叉索引树 归并排序    下面为第二种方法
     * 求右侧小于当前元素的个数数组
     * @param nums
     * @return
     */
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new LinkedList<Integer>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        // find min value and minus min by each elements, plus 1 to avoid 0 element
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            min = (nums[i] < min) ? nums[i]:min;
        }
        int[] nums2 = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            nums2[i] = nums[i] - min + 1;
            max = Math.max(nums2[i],max);
        }
        int[] tree = new int[max+1];
        for (int i = nums2.length-1; i >= 0; i--) {
            res.add(0,get(nums2[i]-1,tree));
            update(nums2[i],tree);
        }
        return res;
    }

    /**
     * 38288的二进制是1001010110010000，所以lowbit(38288)=16(10000)。在程序实现中，lowbit(x)=x&-x
     */
    private int get(int i, int[] tree) {
        int num = 0;
        while (i > 0) {
            num +=tree[i];
            i -= i&(-i);
        }
        return num;
    }
    private void update(int i, int[] tree) {
        while (i < tree.length) {
            tree[i] ++;
            i += i & (-i);
        }
    }

    /**
     * 最后剩下的
     * 先左->右  除去1 3 5 7..个元素   再右->左 除去1 3 5 7.. 个元素 求剩下的最后一个元素
     * Input:
     * n = 9,
     * 1 2 3 4 5 6 7 8 9
     * 2 4 6 8
     * 2 6
     * 6
     * @param n
     * @return
     */
    public int lastRemaining(int n) {
        if (n < 2) { return n; }

        int step = 2, curr = 2;
        while (curr + step >= 1 && curr + step <= n) {
            if (step > 0) {
                while (curr + step <= n) {
                    curr += step;
                }
                curr -= step;
                step *= -2;
            } else {
                while (curr + step >= 1) {
                    curr += step;
                }
                curr -= step;
                step *= -2;
            }
        }
        return curr;
    }

    /**
     * s 经过k次替换字符操作 最多能有多长的同字符串
     * Input:
     * s = "AABABBA", k = 1
     *
     * Output:
     * 4
     * @param s
     * @param k
     * @return
     */
    public int characterReplacement(String s, int k) {
        int len = s.length();
        int[] count = new int[26];
        int start = 0, maxCount = 0, maxLength = 0;
        for (int end = 0; end < len; end++) {
            int curCount = ++count[s.charAt(end) - 'A'];
            if (curCount > maxCount) {
                maxCount = curCount;
            } else {
                if (end - start + 1 - maxCount > k) {
                    count[s.charAt(start) - 'A']--;
                    start++;
                    continue;
                }
            }
            maxLength = Math.max(maxLength, end - start + 1);
        }
        return maxLength;
    }

    /**
     * CORE 不用额外空间 O(n) 时间复杂度
     * 出现两次的数字
     * Input:
     * [4,3,2,7,8,2,3,1]
     *
     * Output:
     * [2,3]
     * @param nums
     * @return
     */
    public List<Integer> findDuplicates(int[] nums) {
        int n;
        int valAtIndex ;
        List<Integer> ans = new ArrayList<Integer>();
        //[4,3,2,7,8,2,3,1]
        //idea here we iterate the num array and mark the value at num[num[i]] to be negative, if we see any negative value while iteration it means we have already visited it
        for(int i=0;i<nums.length;i++){
            n=nums[i];
            n = Math.abs(n);
            valAtIndex = nums[n-1];
            if(valAtIndex>0){
                nums[n-1] = -1*valAtIndex;
            }else{
                ans.add(n);
            }
        }
        return ans;
    }

    public int trapRainWater(int[][] heightMap) {
        if(heightMap == null || heightMap.length <=2 || heightMap[0].length<=2){
            return 0;
        }
        PriorityQueue<Cell> queue = new PriorityQueue(Comparator.comparingInt(Cell::getH));
        int rowLen = heightMap.length;
        int colLen = heightMap[0].length;

        boolean[][] visit = new boolean[rowLen][colLen];

        for(int i=0;i<rowLen;i++){
            queue.offer(new Cell(i,0,heightMap[i][0]));
            queue.offer(new Cell(i,colLen-1,heightMap[i][colLen-1]));
            visit[i][0] = true;
            visit[i][colLen-1] = true;
        }
        for(int i=1;i<colLen-1;i++){
            queue.offer(new Cell(0,i,heightMap[0][i]));
            queue.offer(new Cell(rowLen-1,i,heightMap[rowLen-1][i]));
            visit[0][i] = true;
            visit[rowLen-1][i] = true;
        }

        int[] x_dir = new int[]{1,-1,0,0};
        int[] y_dir = new int[]{0,0,1,-1};
        int sum = 0;
        while(!queue.isEmpty()){
            Cell temCell =queue.poll();
            for(int i=0;i<4;i++){
                int nx = temCell.x+x_dir[i];
                int ny = temCell.y+y_dir[i];
                if(nx>=0 && nx<rowLen && ny>=0 && ny<colLen && !visit[nx][ny]){
                    sum+=Math.max(0,temCell.h-heightMap[nx][ny]);
                    queue.offer(new Cell(nx,ny,heightMap[nx][ny]));
                    visit[nx][ny] = true;
                }
            }
        }
        return sum;
    }
    class Cell{
        int x;
        int y;
        int h;
        Cell(int x,int y,int h){
            this.x = x;
            this.y = y;
            this.h = h;
        }
        public int getH(){
            return this.h;
        }
    }

    public static void main(String[] args) {
//        List<Integer> duplicates = new MasterClassicCode().findDuplicates(new int[]{4, 3, 2, 7, 8, 2, 3, 1});

        int x = new MasterClassicCode().trapRainWater(new int[][]{{1,3,3,1,3,2},{3,2,1,3,2,3},{3,3,3,2,3,1}});
        System.out.println(x);

    }

    public static void priArr(int[][] x){
        for(int i=0;i<x.length;i++){
            for(int j=0;j<x[0].length;j++){
                System.out.print(x[i][j]+" ");
            }
            System.out.println();
        }
    }
}
