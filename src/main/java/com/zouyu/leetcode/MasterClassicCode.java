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

    public static void main(String[] args) {
//        System.out.println(new MasterClassicCode().simplifyPath("/a/./b/../../c/"));
        System.out.println(new MasterClassicCode().productExceptSelf(new int[]{1,2,3,4}));
//        System.out.println(new MasterClassicCode().simplifyPath("/home//foo/"));
//        System.out.println(new MasterClassicCode().simplifyPath("/home/"));
    }
}
