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
                if      (a[left] + a[right] < target) left++;
                else if (a[left] + a[right] > target) right--;
                else {
                    result.add(new ArrayList<>(path));
                    result.get(result.size() - 1).addAll(Arrays.asList(a[left], a[right]));
                    left++; right--;
                    while (left < right && a[left] == a[left - 1]) left++;
                    while (left < right && a[right] == a[right + 1]) right--;
                }
            }
        }else {                        // k Sum
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
        if(board == null || board.length == 0)
            return;
        solve(board);
    }

    public boolean solve(char[][] board){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] == '.'){
                    for(char c = '1'; c <= '9'; c++){//trial. Try 1 through 9
                        if(isValid(board, i, j, c)){
                            board[i][j] = c; //Put c for this cell

                            if(solve(board))
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

    private boolean isValid(char[][] board, int row, int col, char c){
        for(int i = 0; i < 9; i++) {
            if(board[i][col] != '.' && board[i][col] == c) return false; //check row
            if(board[row][i] != '.' && board[row][i] == c) return false; //check column
            if(board[3 * (row / 3) + i / 3][ 3 * (col / 3) + i % 3] != '.' &&
                    board[3 * (row / 3) + i / 3][3 * (col / 3) + i % 3] == c) return false; //check 3*3 block
        }
        return true;
    }

    //路径简化
    public static String simplifyPath(String path) {
        String[] paths = path.split("/");
        StringBuilder out = new StringBuilder();
        int jump = 0;
        for(int i = paths.length - 1; i >= 0; i -= 1){
            String p = paths[i];
            System.out.println(p+" ===start==cur");
            if(p.equals(".") || p.equals("")){
                continue;
            }
            if(p.equals("..")){
                jump += 1;
                continue;
            }
            System.out.println(jump);
            if(jump == 0){
                out.insert(0, "/" + p);
            }else{
                jump -= 1;
            }
        }
        if(out.length() == 0){
            return "/";
        }

        return out.toString();
    }

    //直方图最大矩形面积
    public int largestRectangleArea(int[] height) {
        int len = height.length;
        Stack<Integer> s = new Stack<Integer>();
        int maxArea = 0;
        for(int i = 0; i <= len; i++){
            int h = (i == len ? 0 : height[i]);
            if(s.isEmpty() || h >= height[s.peek()]){
                s.push(i);
            }else{
                int tp = s.pop();
                maxArea = Math.max(maxArea, height[tp] * (s.isEmpty() ? i : i - 1 - s.peek()));
                i--;
            }
        }
        return maxArea;
    }

    //获取向下(非必须根到叶子)路径为固定和数量
    public int pathSum(TreeCluster.TreeNode root, int sum) {
        return root!=null?f(root,sum)+pathSum(root.left,sum)+pathSum(root.right,sum):0;
    }
    private int f(TreeCluster.TreeNode root, int sum) {
        return root!=null?((root.val==sum?1:0) + f(root.left,sum-root.val)+f(root.right,sum-root.val)):0;
    }

    //大神代码 换成常量空间
    public void connect(TreeCluster.TreeLinkNode root){
        Queue<TreeCluster.TreeLinkNode> queue = new LinkedList<>();
        if(root == null) return;
        queue.offer(root);
        while(!queue.isEmpty()){
            int size = queue.size();
            TreeCluster.TreeLinkNode tem = null;
            for(int i=0;i<size;i++){
                root = queue.poll();
                root.next = tem;
                tem = root;
                if(root.right!=null) queue.offer(root.right);
                if(root.left!=null) queue.offer(root.left);
            }
        }
    }

    //大神代码
    public int sumNumbers(TreeCluster.TreeNode root) {
        return rootToLeafSum(root,0);
    }

    private int rootToLeafSum(TreeCluster.TreeNode root, int sum) {
        if(root == null) return 0;
        if(root.left == null && root.right == null) return sum*10+root.val;
        return rootToLeafSum(root.left,sum*10+root.val)+rootToLeafSum(root.right,sum*10+root.val);
    }

    public static void main(String[] args) {
//        System.out.println(new MasterClassicCode().simplifyPath("/a/./b/../../c/"));
        System.out.println(new MasterClassicCode().largestRectangleArea(new int[]{2,1,5,6,2,3}));
//        System.out.println(new MasterClassicCode().simplifyPath("/home//foo/"));
//        System.out.println(new MasterClassicCode().simplifyPath("/home/"));
    }
}
