package com.zouyu.lintcode;

/**
 * Created by zouy-c on 2018/1/8.
 */
public class Software {
    /* 打劫房屋
     * @param A: An array of non-negative integers
     * @return: The maximum amount of money you can rob tonight
     */
    public long houseRobber(int[] A) {
        if( A==null || A.length==0){
            return 0l;
        }
        if(A.length==1){
            return A[0];
        }
        long dp[] = new long[A.length];
        dp[0] = A[0];
        dp[1] = Math.max(A[0],A[1]);
        if(A.length == 2){
            return dp[A.length-1];
        }
        for(int i=2;i<A.length;i++){
            dp[i] = Math.max(A[i]+dp[i-2],dp[i-1]);
        }
        return dp[A.length-1];
    }

    /* 打劫房屋2
     * @param nums: An array of non-negative integers.
     * @return: The maximum amount of money you can rob tonight
     */
    public int houseRobber2(int[] nums) {
        if(nums.length==0||nums==null){
            return 0;
        }
        if(nums.length==1){
            return nums[0];
        }
        int x1[] = new int[nums.length-1];
        int x2[] = new int[nums.length-1];
        System.arraycopy(nums,0,x1,0,x1.length);
        System.arraycopy(nums,1,x2,0,x2.length);
        return (int)Math.max(houseRobber(x1),houseRobber(x2));
    }

    /* 打劫房屋3
     * @param root: The root of binary tree.
     * @return: The maximum amount of money you can rob tonight
     */
    public int houseRobber3(TreeNode root) {
        int[] result = robTree(root);
        return Math.max(result[0],result[1]);
    }

    private static int[] robTree(TreeNode root) {
        if(root == null){
            return new int[]{0,0};
        }
        int[] result = new int[2];
        int[] left = robTree(root.left);
        int[] right = robTree(root.right);
        //偷当前节点
        result[0] = left[1]+right[1]+root.val;
        //不偷当前节点
        result[1] = Math.max(left[0],left[1])+Math.max(right[0],right[1]);
        return result;
    }


    public class TreeNode {
        public int val;
        public TreeNode left, right;
        public TreeNode(int x) {
            val = x;
        }
    }
}
