package com.dataStructure;

/**
 * Created by zouy-c on 2018/1/16.
 */
public class Heap {

    /* 堆化—最小堆数组
     * @param A: Given an integer array
     * @return: nothing
     */
    public void heapify(int[] A) {
        for(int i=A.length/2;i>=0;i--){
            heapify(A,i);
        }
    }

    private void heapify(int[] A, int cur) {
        int left = 2*cur+1;
        int right = 2*cur+2;
        int small = cur;
        if(left<A.length && A[left]<A[small]){
            small = left;
        }
        if(right<A.length && A[right]<A[small]){
            small = right;
        }
        if(small != cur){
            int tmp = A[small];
            A[small] = A[cur];
            A[cur] = tmp;
            heapify(A,small);
        }
    }
}
