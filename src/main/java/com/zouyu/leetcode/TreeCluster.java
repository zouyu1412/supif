package com.zouyu.leetcode;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zouy-c on 2018/6/12.
 */
public class TreeCluster {

    public static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int x) {
            val = x;
        }
    }

    public class TreeLinkNode {
        int val;
        TreeLinkNode left, right, next;
        TreeLinkNode(int x) {
            val = x;
        }
    }

    public class BSTIterator {

        private Stack<TreeNode> stack = new Stack<>();

        public BSTIterator(TreeNode root) {
            pushLeftToLeaf(root);
        }

        /** @return whether we have a next smallest number */
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /** @return the next smallest number */
        public int next() {
            TreeNode tem = stack.pop();
            pushLeftToLeaf(tem.right);
            return tem.val;
        }

        private void pushLeftToLeaf(TreeNode node) {
            for(;node!=null;node = node.left) stack.push(node);
        }

    }

    //判断相同树
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        } else if (p != null && q != null && p.val == q.val) {
            return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
        } else {
            return false;
        }
    }

    //中序遍历
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> re = new ArrayList<>();
        inorderRec(re, root);
        return re;
    }

    private void inorderRec(List<Integer> result, TreeNode node) {
        if (node == null) return;
        inorderRec(result, node.left);
        result.add(node.val);
        inorderRec(result, node.right);
    }

    //修复二叉查找树
    public void recoverTree(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
        inRec(list, root);
        TreeNode x = null;
        TreeNode y = null;
        int i = 0;
        while (i < list.size() - 1 && list.get(i).val <= list.get(i + 1).val) i++;
        x = list.get(i);
        y = list.get(i + 1);
        int j = i + 1;
        while (j < list.size() - 1 && list.get(j).val <= list.get(j + 1).val) j++;
        if (j != list.size() - 1) {
            y = list.get(j + 1);
        }
        x.val = x.val + y.val - (y.val = x.val);
    }

    private void inRec(List<TreeNode> result, TreeNode node) {
        if (node == null) return;
        inRec(result, node.left);
        result.add(node);
        inRec(result, node.right);
    }

    //锯齿遍历
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> re = new ArrayList<>();
        zigzagTraversal(root, re, 0);
        return re;
    }

    private void zigzagTraversal(TreeNode root, List<List<Integer>> re, int level) {
        if (root == null) return;
        LinkedList<Integer> curList;
        if (re.size() > level) {
            curList = (LinkedList<Integer>) re.get(level);
        } else {
            curList = new LinkedList<>();
        }
        if (level % 2 == 0) {
            curList.addLast(root.val);
        } else {
            curList.addFirst(root.val);
        }
        if (re.size() <= level) {
            re.add(curList);
        }
        zigzagTraversal(root.left, re, level + 1);
        zigzagTraversal(root.right, re, level + 1);
    }

    //根据先序 中序构建二叉树
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int len = preorder.length;
        TreeNode root = buildTree(preorder, inorder, 0, len - 1, 0, len - 1);
        return root;
    }

    private TreeNode buildTree(int[] preorder, int[] inorder, int ps, int pe, int is, int ie) {
        if (ps > pe || is > ie) return null;
        TreeNode root = new TreeNode(preorder[ps]);
        int ind = is;
        for (; ind <= ie; ind++) {
            if (preorder[ps] == inorder[ind]) break;
        }
        root.left = buildTree(preorder, inorder, ps + 1, ps + ind - is, is, ind - 1);
        root.right = buildTree(preorder, inorder, ps + ind - is + 1, pe, ind + 1, ie);
        return root;
    }

    //获取最小路径 父-叶子节点
    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null) {
            return minDepth(root.right) + 1;
        }
        if (root.right == null) {
            return minDepth(root.left) + 1;
        }
        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }

    //是否存在root->leaf路径为固定和
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) return false;
        if (root.left == null && root.right == null) return root.val == sum;
        if (root.left == null) return hasPathSum(root.right, sum - root.val);
        if (root.right == null) return hasPathSum(root.left, sum - root.val);
        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }

    //列举固定和路径(root->leaf)集合
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> re = new ArrayList<>();
        travel(root, sum, new ArrayList<Integer>(), re);
        return re;
    }

    private void travel(TreeNode node, int sum, List<Integer> curList, List<List<Integer>> re) {
        List<Integer> list = new ArrayList<>(curList);
        if (node == null) return;
        list.add(node.val);
        if (node.right == null && node.left == null) {
            if (sum == node.val) {
                re.add(new ArrayList<>(list));
            }
            return;
        }

        if (node.left != null) {
            travel(node.left, sum - node.val, list, re);
        }
        if (node.right != null) {
            travel(node.right, sum - node.val, list, re);
        }
    }

    //TODO 大神代码-两行
    //获取向下(非必须根到叶子)路径为固定和数量
    public int pathSumm(TreeNode root, int sum) {
        List<Integer> list = new ArrayList<>();
        int[] ans = new int[1];
        recurToFineAns(root, list, sum, ans);
        return ans[0];
    }

    private void recurToFineAns(TreeNode root, List<Integer> list, int sum, int[] ans) {
        if (root == null) return;
        List<Integer> curList = new ArrayList<>(list);
        for (int i = 0; i < curList.size(); i++) {
            int tem = curList.get(i);
            curList.set(i, tem + root.val);
        }
        curList.add(root.val);
        ans[0] += curList.stream().filter(i -> i == sum).count();
        recurToFineAns(root.left, curList, sum, ans);
        recurToFineAns(root.right, curList, sum, ans);
    }

    //树 链表化
    private TreeNode pre = null;
    public void flatten(TreeNode root) {
        if (root == null) return;
        flatten(root.right);
        flatten(root.left);
        root.right = pre;
        root.left = null;
        pre = root;
    }

    //TODO 大神代码4行
    //树从上到下所有路径成数 求和
    public int sumNumbers(TreeNode root) {
        if(root == null) return 0;
        List<List<Integer>> dataList = new ArrayList<>();
        newTravel(dataList,root,new ArrayList<Integer>());
        int ans = 0;
        for(List<Integer> list:dataList){
            int factor = 1;
            for(int i=list.size()-1;i>=0;i--){
                ans+=list.get(i)*factor;
                factor*=10;
            }
        }
        return ans;
    }

    private void newTravel(List<List<Integer>> dataList, TreeNode root, List<Integer> curList) {
        if(root == null) return;
        curList.add(root.val);
        if(root.left==null && root.right==null) dataList.add(new ArrayList<>(curList));
        if(root.left!= null) newTravel(dataList,root.left,curList);
        if(root.right!= null) newTravel(dataList,root.right,curList);
        curList.remove(curList.size()-1);
    }

    //TODO 换成常量空间呢？
    //TreeLinkNode 生成链表
    public void connect(TreeLinkNode root) {
        if(root == null) return;
        List<List<TreeLinkNode>> list = new ArrayList<>();
        inOr(root,list,0);
        for(List<TreeLinkNode> li:list){
            for(int i=0;i<li.size();i++){
                if(i!=li.size()-1){
                    li.get(i).next = li.get(i+1);
                }else{
                    li.get(i).next = null;
                }
            }
        }
    }

    private void inOr(TreeLinkNode root, List<List<TreeLinkNode>> list, int level) {
        if(root == null) return;
        List<TreeLinkNode> curList;
        if(list.size()<=level){
            curList = new ArrayList<>();
            list.add(curList);
        } else {
            curList = list.get(level);
        }
        curList.add(root);
        inOr(root.left,list,level+1);
        inOr(root.right,list,level+1);
    }

    //TODO 大神代码
    //寻找最大和的路径,不必从上到下顺序
    public int maxPathSum(TreeNode root) {
        if(root == null) return 0;
        int[] max = new int[1];
        max[0] = Integer.MIN_VALUE;
        maxSumContainCurrent(root,max);
        return max[0];
    }

    private int maxSumContainCurrent(TreeNode root,int[] max){
        if(root == null) return 0;
        int l = maxSumContainCurrent(root.left,max);
        int r = maxSumContainCurrent(root.right,max);
        if(l<0) l=0;
        if(r<0) r=0;
        if(l+r+root.val>max[0]) max[0] = l+r+root.val;
        return root.val+=Math.max(l,r);
    }

    //前序遍历
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        preOrder(root,list);
        return list;
    }

    private void preOrder(TreeNode root, List<Integer> list) {
        if(root == null) return;
        list.add(root.val);
        preOrder(root.left,list);
        preOrder(root.right,list);
    }

    //树->右视图
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> re = new ArrayList<>();
        recu(re,root,0);
        return re;
    }

    private void recu(List<Integer> re, TreeNode root, int level) {
        if(root == null) return;
        if(re.size()<=level){
            re.add(root.val);
        }else{
            re.set(level,root.val);
        }
        recu(re,root.left,level+1);
        recu(re,root.right,level+1);
    }

    //获取完全树的节点数
    public int countNodes(TreeNode root) {
        if(root == null) return 0;
        int leftDep = getLeftDep(root.left);
        int rightDep = getRightDep(root.left);
        if(leftDep==rightDep) return (int)Math.pow(2.0,leftDep)+countNodes(root.right);
        else return (int)Math.pow(2.0,rightDep)+countNodes(root.left);
    }

    private int getLeftDep(TreeNode node) {
        if(node == null) return 0;
        return 1+getLeftDep(node.left);
    }

    private int getRightDep(TreeNode node) {
        if(node == null) return 0;
        return 1+getRightDep(node.right);
    }

    //翻转树 不使用递归
    public TreeNode invertTree(TreeNode root) {

        if(root == null) return root;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            TreeNode cur = stack.pop();
            TreeNode tem = cur.left;
            cur.left = cur.right;
            cur.right = tem;
            if(cur.left != null) stack.push(cur.left);
            if(cur.right != null) stack.push(cur.right);
        }
        return root;
    }

//    public boolean isSymmetric(TreeNode root) {
//        return root==null || isSym(root.left,root.right);
//    }
//
//    private boolean isSym(TreeNode left, TreeNode right) {
//        if(left == null || right == null){
//            return left == right;
//        }
//        if(left.val == right.val) {
//            return isSym(left.left,right.right) && isSym(left.right,right.left);
//        }
//        return false;
//    }



    public int maxDepth(TreeNode root) {
        if(root == null) return 0;
        return Math.max(maxDepth(root.left),maxDepth(root.right))+1;
    }

    //盗贼 偷不连续房子 最多能偷多少钱
//    public int rob(TreeNode root) {
//
//    }

    //判断是否平衡
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        return Math.abs(getLevel(root.left) - getLevel(root.right)) < 2 && isBalanced(root.left) && isBalanced(root.right);
    }

    private int getLevel(TreeNode node) {
        if (null == node) return 0;
        return Math.max(getLevel(node.left) + 1, getLevel(node.right) + 1);
    }

    //判断是否是二叉查找树
    private boolean checkBST(TreeNode root) {
        if (root == null) return true;
        if (root.left != null && root.left.val > root.val) return false;
        if (root.right != null && root.right.val < root.val) return false;
        return checkBST(root.left) && checkBST(root.right);
    }

    /**输出索引叶子的路径
     * Input:
     *    1
     *  /   \
     * 2     3
     *  \
     *   5
     * Output: ["1->2->5", "1->3"]
     * @param root
     * @return
     * TODO 大神代码
     * public List<String> binaryTreePaths(TreeNode root) {
     *         List<String> paths = new LinkedList<>();
     *         if(root == null) return paths;
     *         if(root.left == null && root.right == null){
     *             paths.add(root.val+"");
     *             return paths;
     *         }
     *          for (String path : binaryTreePaths(root.left)) {
     *              paths.add(root.val + "->" + path);
     *          }
     *          for (String path : binaryTreePaths(root.right)) {
     *              paths.add(root.val + "->" + path);
     *          }
     *          return paths;
     *     }
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        if(root == null){
            return result;
        }
        if(root.left == null && root.right == null){
            result.add(String.valueOf(root.val));
            return result;
        }
        List<String> subs = new ArrayList<>();
        subs.addAll(binaryTreePaths(root.right));
        subs.addAll(binaryTreePaths(root.left));
        for(String s:subs){
            result.add(root.val+"->"+s);
        }
        return result;
    }

    //数组生成二叉树
    //TODO 没考虑null情况 null->Integer.MIN_VALUE ?
    private TreeNode toBiTree(int[] arr) {
        if (null == arr || arr.length == 0) return null;
        int len = arr.length;
        TreeNode root = buildTree(0, arr, len);
        return root;
    }

    private TreeNode buildTree(int index, int[] arr, int len) {
        TreeNode root = arr[index] == Integer.MIN_VALUE ? null : new TreeNode(arr[index]);
        if (root == null) return root;
        if (index * 2 + 1 < len) {
            root.left = buildTree(index * 2 + 1, arr, len);
        } else {
            root.left = null;
        }
        if (index * 2 + 2 < len) {
            root.right = buildTree(index * 2 + 2, arr, len);
        } else {
            root.right = null;
        }
        return root;
    }

    /**
     * 求所有的左叶子之和
     * Example:
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * There are two left leaves in the binary tree, with values 9 and 15 respectively. Return 24.
     * @param root
     * @return
     */
    public int sumOfLeftLeaves(TreeNode root) {
        if(root == null){
            return 0;
        }
        int leftVal = getSumOfLeftLeaves(root.left, true);
        int rightVal = getSumOfLeftLeaves(root.right, false);
        return leftVal + rightVal;
    }
    private int getSumOfLeftLeaves(TreeNode node,boolean isLeft){
        if(node == null){
            return 0;
        }
        if(node.left == null && node.right == null){
            if(isLeft) {
                return node.val;
            }else{
                return 0;
            }
        }
        int leftVal = 0;
        int rightVal = 0;
        if(node.left != null){
            leftVal = getSumOfLeftLeaves(node.left,true);
        }
        if(node.right != null){
            rightVal = getSumOfLeftLeaves(node.right,false);
        }
        return leftVal+rightVal;
    }

    /**
     * 使用迭代进行二叉树后序遍历
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if(root == null){
            return Collections.emptyList();
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        stack.push(node);
        while(!stack.isEmpty()){
            while(node != null && node.left != null){
                node = node.left;
                stack.push(node);
            }
            TreeNode peek = stack.peek();
            if(peek.right != null){
                stack.push(peek.right);
                node = peek.right;
            }else{
                TreeNode topEle = stack.pop();
                if(stack != null && !stack.isEmpty() && stack.peek().right == topEle){
                    stack.peek().right = null;
                }
                res.add(topEle.val);
            }
        }
        return res;
    }

    /**
     * BST删除节点
     * @param root
     * @param key
     * @return
     */
//    public TreeNode deleteNode(TreeNode root, int key) {
//        if(root == null){
//            return null;
//        }
//        if(root.val == key){
//            if(root)
//        }
//    }

    int sum = 0;

    public int sumRootToLeaf(TreeNode root) {
        travel(root, 0,0);
        return sum;
    }

    private void travel(TreeNode node, int curSum, int level){
        int innerSum = curSum + node.val * (int)(Math.pow(2,level));
        if(node.left == null && node.right == null){
            sum += innerSum;
            return;
        }
        if(node.left != null){
            travel(node.left, innerSum, level+1);
        }
        if(node.right != null){
            travel(node.right, innerSum, level+1);
        }
    }

    public List<List<Integer>> pathSumNew(TreeNode root, int sum) {
        List<List<Integer>> result = new ArrayList();
        List<Integer> cur = new ArrayList();
        recur(root, sum, cur, result);
        return result;
    }

    void recur(TreeNode root, int sum, List<Integer> cur, List<List<Integer>> re){
        if(root == null){
            return;
        }

        new AtomicInteger().incrementAndGet();
        if(root.left == null && root.right == null){
            if(sum == root.val){
                cur.add(root.val);
                re.add(new ArrayList<>(cur));
                cur.remove(cur.size()-1);
            }
        }

        if(root.left != null){
            cur.add(root.val);
            recur(root.left, sum-root.val, cur, re);
            cur.remove(cur.size()-1);
        }
        if(root.right != null){
            cur.add(root.val);
            recur(root.right, sum-root.val, cur, re);
            cur.remove(cur.size()-1);
        }
    }

    public static void main(String[] args) {
        TreeCluster tc = new TreeCluster();
        TreeNode root = new TreeNode(1);
        TreeNode l1 = new TreeNode(2);
        TreeNode r1 = new TreeNode(2);
        TreeNode l12 = null;
        TreeNode r12 = new TreeNode(3);
        TreeNode l21 = new TreeNode(3);
        TreeNode r22 = null;
        root.left = l1;
        root.right = r1;
        l1.left = l12;
        l1.right = r12;
        r1.left = l21;
        r1.right = r22;
        List<List<Integer>> lists = tc.pathSumNew(root, 6);
        System.out.println(lists.size());

//        tc.buildTree1(new int[]{3,9,20,15,7},new int[]{9,3,15,20,7});


//        List<Integer> list = new ArrayList<>();
//        tc.inorderRec(list, tree);
//        System.out.println(tc.checkBST(tree));
//        System.out.println(list);
//
//        System.out.println(tc.sumNumbers(tree));

    }

    /**
     * 树的序列话与反序列化 stateless无状态
     */
    public static class Codec {

        // Encodes a tree to a single string.
        public static String serialize(TreeNode root) {
            StringBuilder sb = new StringBuilder();
            Stack<TreeNode> stack = new Stack<>();
            TreeNode node = root;
            while(node != null || !stack.isEmpty()){
                if(node != null){
                    sb.append(node.val+" ");
                    stack.push(node);
                    node = node.left;
                }else{
                    sb.append("null ");
                    node = stack.pop().right;
                }
            }
            return sb.toString();
        }

        // Decodes your encoded data to tree.
        public static TreeNode deserialize(String data) {
            if(data == null || data.length()==0){
                return null;
            }
            String[] split = data.split(" ");
            if(split[0].equals("null")){
                return null;
            }
            TreeNode root = new TreeNode(Integer.valueOf(split[0]));
            TreeNode result = root;
            Stack<TreeNode> stack = new Stack<>();
            stack.push(result);
            int i = 1;
            while(i<split.length){
                while(i<split.length && !split[i].equals("null")){
                    result.left = new TreeNode(Integer.valueOf(split[i++]));
                    result = result.left;
                    stack.push(result);
                }
                while(i<split.length && split[i].equals("null")){
                    result = stack.pop();
                    i++;
                }
                if(i<split.length){
                    result.right = new TreeNode(Integer.valueOf(split[i++]));
                    result = result.right;
                    stack.push(result);
                }
            }
            return result;
        }
    }
}


