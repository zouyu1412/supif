package com.zouyu.lintcode;

import java.text.SimpleDateFormat;
import java.util.*;

public class Solution {
	public List<List<Integer>> threeSum(int[] numbers) {
		List<List<Integer>> result = new ArrayList<>();
		Arrays.sort(numbers);
		int target = 0;
		for(int i=0;i<numbers.length;i++){
			if(i>0&&numbers[i-1]==numbers[i])continue;
			for(int j=i+1,k=numbers.length-1;j<k;){
				if(j>i+1 && numbers[j-1]==numbers[j]){
					j++;
					continue;
				}
				if(k<numbers.length-1 && numbers[k]==numbers[k+1]){
					k--;
					continue;
				}
				int sum = numbers[i]+numbers[j]+numbers[k];
				if(sum > target) --k;
				else if(sum<target) ++j;
				else{
					List<Integer> right = new ArrayList<>();
					right.add(numbers[i]);
					right.add(numbers[j++]);
					right.add(numbers[k--]);
					result.add(right);
				}
			}
		}
		return result;
    }
	
	public int threeSumClosest(int[] numbers, int target) {
		Arrays.sort(numbers);
        int ans = Integer.MAX_VALUE;
        for(int i=0; i<numbers.length; i++){
            for(int j=i+1, k=numbers.length-1; j<k;){
                int sum = numbers[i]+numbers[j]+numbers[k];
                ans = (Math.abs(target-sum)<Math.abs(target-ans) ? sum:ans);
                if (sum > target) --k;
                else if (sum < target) ++j;
                else return sum;    // sum equal to target
            }
        }
        return ans;
    }
	
	/*
     * @param root: the root of binary tree
     * @param target: An integer
     * @return: all valid paths
     */
    public List<List<Integer>> binaryTreePathSum(TreeNode root, int target) {
    	if(root ==null){
    		return new ArrayList<List<Integer>>();
    	}
    	List<List<Integer>> result = new ArrayList();
		List<Integer> li = new ArrayList<>();
		handleTree(root, target, 0, li, result);
		return result;
    }
	static void handleTree(TreeNode node,int tar,int curVal,List<Integer> tem,List<List<Integer>> re){
		int sum = node.val+curVal;
		if(node.left == null && node.right ==null){
			if(sum == tar){
				tem.add(node.val);
				re.add(tem);
			}
			return;
		}
		if(node.left != null){
			List<Integer> list = new ArrayList<>(tem);
			list.add(node.val);
			handleTree(node.left, tar, sum, list, re);
		}
		if(node.right != null){
			List<Integer> list = new ArrayList<>(tem);
			list.add(node.val);
			handleTree(node.right, tar, sum, list, re);
		}
	}
    
	/*
     * @param start: start value.
     * @param end: end value.
     * @return: The root of Segment Tree.
     */
    public SegmentTreeNode build(int start, int end) {
    	SegmentTreeNode node = new SegmentTreeNode(start, end);
    	if(start>end){
    		return null;
    	}
    	if(start == end){
    		return node;
    	}
    	if(start<end){
    		int mid = (start+end)/2;
    		node.left = build(start, mid);
    		node.right = build(mid+1, end);
    	}
    	return node;
    }
	
    public static ProSegmentTreeNode proBuild(int[] A){
    	if(A==null || A.length==0){
    		return null;
    	}
    	Arrays.sort(A);
    	int start = A[0];
    	int end = A[A.length-1];
    	if(start == end){
    		return new ProSegmentTreeNode(start, end, A.length);
    	}
    	int mid = (start+end)/2;
    	ProSegmentTreeNode node = new ProSegmentTreeNode(start, end, A.length);
    	node.left = new ProSegmentTreeNode(start, mid, getSectionNumber(start, mid, A));
    	node.right = new ProSegmentTreeNode(start, mid, getSectionNumber(mid+1, end, A));
    	return node;
    }
    static int getSectionNumber(int start,int end,int[] arr){
    	int count = 0;
    	for(int x:arr){
    		if(x>=start && x<=end){
    			count++;
    		}
    	}
    	return count;
    }
    
    /*
     * @param root: The root of segment tree.
     * @param start: start value.
     * @param end: end value.
     * @return: The count number in the interval [start, end]
     */
    public static int query(ProSegmentTreeNode root, int start, int end) {
    	if(root==null){
    		return 0;
    	}
    	if(start>root.end || end < root.start){
    		return 0;
    	}
        if(root.start>=start && root.end<=end){
        	return root.count;
        }
        int mid = (root.start + root.end)/2;
        if(start>mid){
        	return query(root.right,start,end);
        }else if(end<=mid){
        	return query(root.left,start,end);
        }else{
        	return query(root.left,start,mid)+query(root.right,mid+1,end);
        }
    }
 
    public List<Integer> countOfSmallerNumberII(int[] A) {
    	if(A==null || A.length==0){
    		return null;
    	}
    	List<Integer> result = new ArrayList<>();
    	result.add(0);
    	if(A.length==1){
    		return result;
    	}
//    	int[] x = new int[A.length];
//    	System.arraycopy(A, 0, x, 0, A.length);
//    	ProSegmentTreeNode root = proBuild(x);
    	for(int i=1;i<A.length;i++){
    		int[] tem = new int[i];
    		System.arraycopy(A, 0, tem, 0, i);
    		ProSegmentTreeNode pro = proBuild(tem);
    		result.add(query(pro,Integer.MIN_VALUE,A[i]-1));
    	}
    	return result;
    }
    
    /*
     * @param : a string to be split
     * @return: all possible split string array
     */
    public List<List<String>> splitString(String s) {
    	List<List<String>> re = new ArrayList<List<String>>();
    	List<String> item = new ArrayList<>();
    	if(s == null){
    		return re;
    	}
    	if(s ==" "){
    		re.add(Collections.EMPTY_LIST);
    		return re;
    	}
    	createList(s,0,item,re);
    	return re;
    }
    
	static void createList(String s, int ind, List<String> item, List<List<String>> re) {
		if(ind>=s.length()){
			re.add(item);
			return;
		}
		for(int i=ind;i<ind+2 && i<s.length();i++){
			List<String> tem = new ArrayList<>();
			tem.addAll(item);
			tem.add(s.substring(ind, i+1));
			createList(s, i+1, tem, re);
			
		}
	}
	
	/*
     * @param l1: the first list
     * @param l2: the second list
     * @return: the sum list of l1 and l2 
     */
    public ListNode addLists(ListNode l1, ListNode l2) {
        boolean isOverflow = false;
        if(l1 == null && l2 == null){
        	return null;
        }
        if(l1 == null){
        	return l2;
        }
        if(l2 == null){
        	return l1;
        }
        ListNode l3 = null;
        ListNode te = null;
        while(l1 != null || l2 !=null || isOverflow){
        	int l1Val = (l1 != null ? l1.val : 0);
        	int l2Val = (l2 != null ? l2.val : 0);
        	int tem;
        	if( isOverflow ){
        		tem = l1Val + l2Val +1;
        	}else{
        		tem = l1Val + l2Val;
        	}
        	if(tem/10 == 1){
        		isOverflow = true;
        	}else{
        		isOverflow = false;
        	}
        	if(l3==null){
        		te = new ListNode(tem%10);
        		l3 = te;
        	}else{
        		te.next = new ListNode(tem%10);
        		te = te.next;
        	}
			if (l1 != null) {
				l1 = (l1.next != null ? l1.next : null);
			}
			if (l2 != null) {
				l2 = (l2.next != null ? l2.next : null);
			}
        }
        return l3;
    }
	
    /*
     * @param head: The first node of linked list.
     * @param n: An integer
     * @return: Nth to last node of a singly linked list. 
     */
    public ListNode nthToLast(ListNode head, int n) {
        if(head == null){
    		return null;
    	}
        ListNode p1 = head;
        ListNode p2 = head;
        int i = 0;
        while(i<n){
        	if(p2.next!=null){
        		p2 = p2.next;
        	}else{
        	    p2 = null;
        	}
        	i++;
        }
        while(p2!=null){
        	p2 = p2.next;
        	p1 = p1.next;
        }
        return p1;
    }
    
    public int updateBits(int n, int m, int i, int j) {
        for(int p= i;p<=j;p++){
        	n &= ~(1<<p);
        }
        m <<= i;
        return n|m;
        
    }
    
    /*
     * @param n: An integer
     * @return: the nth prime number as description.
     */
    public int nthUglyNumber(int n) {
    	if(n < 7){
    		return n;
    	}
    	int[] res = new int[n];
    	res[0] = 1;
    	int t2 =0, t3 = 0, t5 = 0;
    	for(int i=1;i<n;i++){
    		res[i] = Math.min(res[t2]*2,Math.min(res[t3]*3,res[t5]*5));
    		if(res[i]==res[t2]*2) t2++;
    		if(res[i]==res[t3]*3) t3++;
    		if(res[i]==res[t5]*5) t5++;
    	}
    	return res[n-1];
    }
    
    /*
     *@param preorder : A list of integers that preorder traversal of a tree
     *@param inorder : A list of integers that inorder traversal of a tree
     *@return : Root of a tree
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
    	if(preorder == null || preorder.length == 0) return null;
    	if(inorder == null ||  inorder.length == 0) return null;
    	if(preorder.length == 1 ||  inorder.length == 1){
    		return new TreeNode(preorder[0]);
    	}
        TreeNode node = new TreeNode(preorder[0]);
        List<Integer> list = new ArrayList<>();
        int index = 0;
        while(inorder[index] != preorder[0]){
        	index++;
        }
        int[] leftInorder = new int[index];
        int[] rightInorder = new int[inorder.length-index-1];
        System.arraycopy(inorder, 0, leftInorder, 0, index);
        System.arraycopy(inorder, index+1, rightInorder, 0, inorder.length-index-1);
        int[] leftPreorder = new int[index];
        int[] rightPreorder = new int[preorder.length-index-1];
        System.arraycopy(preorder, 1,leftPreorder,0,index);
        System.arraycopy(preorder, index+1,rightPreorder,0,preorder.length-index-1);
        node.left = buildTree(leftPreorder,leftInorder);
        node.right = buildTree(rightPreorder, rightInorder);
        return node;
    }
    
    /*
     * @param n: An integer
     * @param edges: a list of undirected edges
     * @return: true if it's a valid tree, or false
     */
    public boolean validTree(int n, int[][] edges) {
        if(edges.length != n-1){
        	return false;
        }
        int[] ex = new int[n];
        Arrays.fill(ex, -1);
        for(int i=0;i<edges.length;i++){
        	int root1 = find(ex,edges[i][0]);
        	int root2 = find(ex,edges[i][1]);
        	if(root1 ==root2){
        		return false;
        	}
        	ex[root2] = root1;
        }
        return true;
    }
    static int find(int[] ex,int e){
    	//点未在图中出现过,返回该点；否则，找到该点的父节点
    	if(ex[e]==-1){
    		return e;
    	}else{
    		return find(ex,ex[e]);
    	}
    }
    
    /*
     * @param root: A Tree
     * @return: Level order a list of lists of integer
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
    	List<List<Integer>> re = new ArrayList<List<Integer>>();
		makeList(re, 0, root);
		return re;
    }
    static void makeList(List<List<Integer>> result,int level,TreeNode root){
    	if(root == null) return;
    	List<Integer> tem;
   		if(result.size()>level){
    		tem = (List<Integer>)result.get(level);
    		tem.add(root.val);
    	}else{
    		tem = new ArrayList<>();
    		tem.add(root.val);
    		result.add(tem);
    	}
    	if(root.left!=null){
    		makeList(result, level+1, root.left);
    	}
    	if(root.right!=null){
    		makeList(result, level+1, root.right);
    	}
    }
    
    /*
     * @param root: param root: The root of the binary search tree
     * @param k1: An integer
     * @param k2: An integer
     * @return: return: Return all keys that k1<=key<=k2 in ascending order
     */
    public List<Integer> searchRange(TreeNode root, int k1, int k2) {
    	List<Integer> re = new ArrayList<>();
    	if(root == null){
    		return re;
    	}
    	makeRange(root, k1, k2, re);
    	Collections.sort(re);
    	return re;
    }
    static void makeRange(TreeNode root, int k1, int k2, List<Integer> result){
    	int tem = root.val;
    	if(tem>=k1 && tem<=k2){
    		result.add(tem);
    		if(root.left!=null){
    			makeRange(root.left, k1, k2, result);
    		}
    		if(root.right!=null){
    			makeRange(root.right, k1, k2, result);
    		}
    	}else if(tem < k1){
    		if(root.right!=null){
    			makeRange(root.right, k1, k2, result);
    		}
    	}else{
    		if(root.left!=null){
    			makeRange(root.left, k1, k2, result);
    		}
    	}
    	return;
    }
    
    /*
     * @param root: The root of the binary search tree.
     * @param A: A TreeNode in a Binary.
     * @param B: A TreeNode in a Binary.
     * @return: Return the least common ancestor(LCA) of the two nodes.
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode A, TreeNode B) {
        if(root==null){
        	return null;
        }
        if(root==A || root==B){
        	return root;
        }
        TreeNode left = lowestCommonAncestor(root.left, A, B);
        TreeNode right = lowestCommonAncestor(root.right, A, B);
        if(left!=null&&right!=null){
        	return root;
        }
        if(left!=null){
        	return left;
        }
        return right;
    }
    
    /*
     * @param k : description of k
     * @param nums : array of nums
     * @return: description of return
     */
    public int kthLargestElement(int k, int[] nums) {
        // write your code here
        return quickSort(nums,0,nums.length-1,k);
        
    }
    public int quickSort(int[] nums,int left,int right,int k){
        int i = left;
        int j = right;
        int tmp = nums[i];
        while(i<j){
            while(i<j && tmp>=nums[j]) j--;
            if(i<j){
                nums[i]=nums[j];
                i++;
            }
            while(i<j && tmp<nums[i]) i++;
            if(i<j){
                nums[j]=nums[i];
                j--;
            }
            
        }
        if(i == k -1){
            return tmp;
        }else if(i< k-1){
            return quickSort(nums,i+1,right,k);
        }else{
            return quickSort(nums,left,i-1,k);
        }
    }

    /*
     * @param nums: A list of non negative integers
     * @return: A string
     */
    public String largestNumber(int[] nums) {
		String[] strs = new String[nums.length];
		for(int i=0;i<nums.length;i++){
			strs[i] = String.valueOf(nums[i]);
		}
		Arrays.sort(strs);
		StringBuilder sb = new StringBuilder();
		for(int i=strs.length-1;i>=0;i--){
			sb.append(strs[i]);
		}
		String result = sb.toString();
		String z = "0";
		if(result.replaceAll("[0]+", "").equals("")){
			return "0";
		}
		return result;
    }
    
    /*
     * @param nums: The integer array you should partition
     * @param k: An integer
     * @return: The index after partition
     */
    public int partitionArray(int[] nums, int k) {
        int start = 0;
        int end = nums.length-1;
        while(start<end){
        	while(start<end&&nums[start]<k) start++;
        	while(start<end&&nums[end]>=k) end--;
        	if(start<end){
	        	swap(nums,start,end);
	        	start++;
	        	end--;
        	}else{
        		if(nums[start]<k){
        			return start+1;
        		}else{
        			return start;
        		}
        	}
        }
        return start;
    }
    private static void swap(int[] arr,int p1,int p2){
    	int tem = arr[p1];
    	arr[p1] = arr[p2];
    	arr[p2] = tem;
    }
    
	public static void main(String[] args) {
		//longToDateToString(1512957026000l);
		System.out.println(new Solution().largestNumber(new int[]{0,0}));
	}
	
	private static void longToDateToString(long tar){
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        Date date = new Date(tar);  
        String str = sdf.format(date);  
        System.out.println(str);
	}
	
	private static void printBinaryNum(int n){
	    String num = Integer.toBinaryString(n);
	    if(num.length() == 32){
	        System.out.println(num);
	    }else{
	        StringBuilder sb = new StringBuilder("");
	        for(int i =0;i < 32 - num.length(); i ++){
	        sb.append("0");
	        }
	        System.out.println(sb.toString() + num);
	    }
	}
}

class TreeNode {
	public int val;
	public TreeNode left, right;

	public TreeNode(int val) {
		this.val = val;
		this.left = this.right = null;
	}
}

class SegmentTreeNode {
	public int start, end;
	public SegmentTreeNode left, right;

	public SegmentTreeNode(int start, int end) {
         this.start = start;
         this.end = end;
         this.left = this.right = null;
     }
}

class ProSegmentTreeNode {
	public int start, end, count;
	public ProSegmentTreeNode left, right;

	public ProSegmentTreeNode(int start, int end, int count) {
		this.start = start;
		this.end = end;
		this.count = count;
		this.left = this.right = null;
	}
}

class ListNode {
	int val;
	ListNode next;
	ListNode(int x) {
		val = x;
		next = null;
	}
}