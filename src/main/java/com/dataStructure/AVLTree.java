package com.dataStructure;

public class AVLTree<T extends Comparable<? super T>> {
    //内部节点类
    private static class AVLNode<T> {
        T element;
        AVLNode<T> left;//左孩子
        AVLNode<T> right;//右孩子
        int height;//当前高度

        AVLNode(T theElement) {
            this(theElement, null, null);
        }

        AVLNode(T theElement, AVLNode<T> lt, AVLNode<T> rt) {
            element = theElement;
            left = lt;
            right = rt;
            height = 0;
        }
    }

    private AVLNode<T> root;//根节点

    public AVLTree() {
        root = null;
    }

    //插入
    public void insert(T x) {
        root = insert(x, root);
    }

    //删除
    public void remove(T x) {
        root = remove(x, root);
    }

    //寻找最小值
    public T findMin() {
        if (isEmpty())
            throw new RuntimeException("空错误");
        return findMin(root).element;
    }

    //寻找最大值
    public T findMax() {
        if (isEmpty())
            throw new RuntimeException("空错误");
        return findMax(root).element;
    }

    //是否存在
    public boolean contains(T x) {
        return contains(x, root);
    }

    //清空
    public void empty() {
        root = null;
    }

    //判空
    public boolean isEmpty() {
        return root == null;
    }

    //打印树
    public void printTree() {
        if (isEmpty())
            System.out.println("空树");
        else
            printTree(root);
    }

    //判断是否平衡
    public void check() {
        check(root);
    }

    //判断是否平衡
    private int check(AVLNode<T> t) {
        if (t == null)
            return -1;
        if (t != null) {
            int hl = check(t.left);
            int hr = check(t.right);
            if (Math.abs(height(t.left) - height(t.right)) > 1 ||
                    height(t.left) != hl || height(t.right) != hr)
                System.out.println("出错了");
        }
        return height(t);
    }

    //获取高度
    private int  height(AVLNode<T> t) {
        return t == null ? -1 : t.height;
    }

    //中序遍历
    private void printTree(AVLNode<T> t) {
        if (t != null) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    //是否包含 递归遍历
    private boolean contains(T x, AVLNode<T> t) {
        while (t != null) {
            int res = x.compareTo(t.element);
            if (res < 0)
                t = t.left;
            else if (res > 0)
                t = t.right;
            else
                return true;//找到了
        }
        return false;
    }

    //寻找最小值
    private AVLNode<T> findMin(AVLNode<T> t) {
        if (t == null)
            return t;
        while (t.left != null)
            t = t.left;
        return t;
    }

    //寻找最大值
    private AVLNode<T> findMax(AVLNode<T> t) {
        if (t == null)
            return t;
        while (t.right != null)
            t = t.right;
        return t;
    }

    //插入操作
    private AVLNode<T> insert(T x, AVLNode<T> t) {
        if (t == null)
            return new AVLNode<T>(x, null, null);//插入当前的元素
        int res = x.compareTo(t.element);
        if (res < 0)
            t.left = insert(x, t.left);
        else if (res > 0)
            t.right = insert(x, t.right);
        else ;//冲突了
        return balance(t);//确保平衡
    }

    //删除操作
    private AVLNode<T> remove(T x, AVLNode<T> t) {
        if (t == null)
            return t;   // 啥也不做
        int res = x.compareTo(t.element);
        if (res < 0)
            t.left = remove(x, t.left);
        else if (res > 0)
            t.right = remove(x, t.right);
        else if (t.left != null && t.right != null) //两个 孩子
        {
            t.element = findMin(t.right).element;//寻找右子树最小的
            t.right = remove(t.element, t.right);
        } else
            t = (t.left != null) ? t.left : t.right;//一个孩子 这种情况只有一层
        return balance(t);
    }

    //确保平衡状态
    private AVLNode<T>  balance(AVLNode<T> t) {
        if (t == null)
            return t;
        if (height(t.left) - height(t.right) > 1)//左子树比右子树深两层及以上
            if (height(t.left.left) >= height(t.left.right))
                t = singleRotateLL(t);//左左单旋转
            else
                t = doubleRotateLR(t);//左右双旋转
        else if (height(t.right) - height(t.left) > 1)//右子树比左子树深两层以上
            if (height(t.right.right) >= height(t.right.left))//右右单旋转
                t = singleRotateRR(t);
            else
                t = doubleRotateRL(t);//右左双旋转
        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }

    //右右单旋转
    private AVLNode<T> singleRotateRR(AVLNode<T> k1) {
        AVLNode<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        k2.height = Math.max(height(k2.right), k1.height) + 1;
        return k2;
    }

    //左左单旋转
    private AVLNode<T> singleRotateLL(AVLNode<T> k2) {
        AVLNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }

    //右左双旋转
    private AVLNode<T> doubleRotateRL(AVLNode<T> k1) {
        //右子树先左左单旋转
        k1.right = singleRotateLL(k1.right);
        return singleRotateRR(k1);//再整个右右单旋转
    }

    //左右双旋转
    private AVLNode<T> doubleRotateLR(AVLNode<T> k3) {
        //左子树先右右单旋转
        k3.left = singleRotateRR(k3.left);
        return singleRotateLL(k3);//再整个左左单旋转
    }

    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<Integer>();
        for (int i = 1; i <= 7; i++) {
            tree.insert(i);
            tree.check();
        }
        System.out.println(tree.root.element);
        tree.printTree();
        if(1>0){
            return;
        }
        System.out.println("---------");
        for (int i = 10; i <= 16; i++) {
            tree.insert(i);
            tree.check();
        }
        tree.insert(8);
        tree.insert(9);
        tree.printTree();
        System.out.println("----------");
        System.out.println(tree.findMin());
        System.out.println(tree.findMax());
        System.out.println(tree.root.height);
        System.out.println("--------------");
        System.out.println("没有输出说明成功了...");
        final int NUM = 10000;
        for (int i = 37; i != 0; i = (i + 37) % NUM) {//随机插入
            //System.out.println(i);
            tree.insert(i);
        }
        for (int i = 1; i < NUM; i += 2) {//删除所有奇数
            tree.remove(i);
        }
        for (int i = 2; i < NUM; i += 2) {
            if (!tree.contains(i))
                System.out.println("应该存在的偶数缺失");
        }
        for (int i = 1; i < NUM; i += 2) {
            if (tree.contains(i))
                System.out.println("不该存在的奇数存在");
        }
    }
}