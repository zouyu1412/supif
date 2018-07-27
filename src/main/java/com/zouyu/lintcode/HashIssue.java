package com.zouyu.lintcode;

/**
 * Created by zouy-c on 2018/1/9.
 */
public class HashIssue {

    public class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /**
     * @param hashTable: A list of The first node of linked list
     * @return: A list of The first node of linked list which have twice size
     */
    public ListNode[] rehashing(ListNode[] hashTable) {
        int len = hashTable.length;
        int newLen = len*2;
        ListNode[] newHashTable = new ListNode[newLen];
        for(int i=0;i<len;i++){
            ListNode tem = hashTable[i];
            while(tem!=null){
                if(newHashTable[(tem.val%newLen+newLen)%newLen]!=null){
                    ListNode temNode = newHashTable[(tem.val%newLen+newLen)%newLen];
                    while(temNode.next!=null){
                        temNode = temNode.next;
                    }
                    temNode.next = new ListNode(tem.val);
                }else{
                    newHashTable[(tem.val%newLen+newLen)%newLen] = new ListNode(tem.val);
                }
                tem = tem.next;
            }
        }
        return newHashTable;
    }

    /*
     * @param n: An integer
     * @return: An integer
     */
    public int numTrees(int n) {
        if(n==1 || n==0){
            return 1;
        }
        int re = 0;
        for(int i=0;i<n;i++){
            re+=numTrees(n-i-1)*numTrees(i);
        }
        return re;
    }

}
