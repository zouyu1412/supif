package com.zouyu.examination;

/**
 * @Author:zouyu
 * @Date:2020/5/17 13:40
 */
public class ListNodeDemo {

    static class ListNode{
        int val;
        ListNode next;
        public ListNode(int val){
            this.val = val;
        }
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode l3 = new ListNode(2);
        ListNode l2 = new ListNode(3);
        ListNode l1 = new ListNode(4);
//        ListNode l0 = new ListNode(5);
        head.next = l3;
        l3.next = l2;
        l2.next = l1;
//        l1.next =l0;
        ListNodeDemo demo = new ListNodeDemo();
        demo.splitListToParts(head, 5);
        System.out.println(head.val);
    }

    public ListNode[] splitListToParts(ListNode root, int k) {
        ListNode[] res = new ListNode[k];
        if(k== 0){
            return res;
        }else if(k==1){
            res[0] = root;
            return res;
        }
        int len = lengthing(root);
        int s = len/k;
        int r = len%k;
        int[] lenList = new int[k];

        for(int i=0;i<lenList.length;i++){
            lenList[i] = s + (r-->0?1:0);
        }
        ListNode orh = new ListNode(0);
        ListNode breakPoint = null;
        orh.next = root;
        ListNode cur = root;
        ListNode pre = orh;
        int count = 0;
        int i = 0;
        while(cur != null){
            count++;
            if(count == lenList[i]){
                breakPoint = cur;
                cur = cur.next;
                breakPoint.next = null;
                res[i] = pre.next;
                i++;
                count = 0;
                pre.next = cur;
            }else{
                cur = cur.next;
                pre = pre.next;
            }
        }
        return res;
    }

    private int lengthing(ListNode head){
        int count = 0;
        while(head != null){
            count++;
            head= head.next;
        }
        return count;
    }

    public ListNode rotateRight(ListNode head, int k) {
        ListNode orh = new ListNode(0);
        orh.next = head;
        ListNode fast = head;
        ListNode slow = head;
        int len = getListLen(head);
        ListNode pfast = orh;
        ListNode pslow = orh;
        int step = (len%k);
        while(step-->0){
            fast = fast.next;
            pfast = pfast.next;
        }
        while(fast != null){
            fast = fast.next;
            pfast = pfast.next;
            slow = slow.next;
            pslow = pslow.next;
        }
        pslow.next = null;
        orh.next = slow;
        pfast.next = head;
        return orh.next;

    }

    private int getListLen(ListNode head){
        if(head == null){
            return 0;
        }
        int count = 0;
        while(head != null){
            count++;
            head = head.next;
        }
        return count;
    }


    public ListNode mergeKLists(ListNode[] lists) {
        if(lists == null || lists.length==0){
            return null;
        }
        if(lists.length==1){
            return lists[0];
        }else if(lists.length == 2){
            return mergeTwoList(lists[0],lists[1]);
        }
        int len = lists.length;
        ListNode[] le = new ListNode[len/2];
        ListNode[] ri = new ListNode[len-len/2];
        for(int i=0;i<le.length;i++){
            le[i] = lists[i];
        }
        for(int j=0;j<ri.length;j++){
            ri[j] = lists[len-len/2+j-(len%2==0?0:1)];
        }
        ListNode l1 = mergeKLists(le);
        ListNode l2 = mergeKLists(ri);
        return mergeTwoList(l1,l2);
    }

    public ListNode mergeTwoList(ListNode l1, ListNode l2){
        if(l1 == null && l2 == null){
            return null;
        }else if(l1 == null){
            return l2;
        }else if(l2 == null){
            return l1;
        }
        ListNode re = new ListNode(0);
        ListNode cur = re;
        while(l1 != null ||l2 != null){
            if(l1 == null){
                cur.next = l2;
                return re.next;
            }else if(l2 == null){
                cur.next = l1;
                return re.next;
            }
            if(l1.val>l2.val){
                cur.next = l2;
                l2 = l2.next;
            }else{
                cur.next = l1;
                l1 =l1.next;
            }
            cur = cur.next;
        }
        return re.next;
    }

    public void deleteNode(ListNode node) {
        ListNode head = new ListNode(0);
        head.next= node;
        ListNode pre = new ListNode(-1);
        pre.next = head;
        ListNode f = head;
        ListNode s = head;
        ListNode toDelete = null;
        while(true){
            if(f == null){
                pre.next= pre.next.next;
                break;
            }else if(f.next == null){
                pre.next= pre.next.next;
                break;
            }
            f = f.next.next;
            s = s.next;
            pre = pre.next;
        }
    }

    public boolean isPalindrome(ListNode head) {
        if(head == null || head.next == null){
            return true;
        }
        ListNode newHead1 = new ListNode(0);
        newHead1.next = head;
        ListNode fast = newHead1;
        ListNode breakPoint = null;
        ListNode slow = newHead1;
        while(true){
            if(fast == null){
                break;
            }else if(fast.next== null){
                slow =slow.next;
                break;
            }
            fast = fast.next.next;
            slow = slow;
        }
        breakPoint = reverse(slow.next);
        while(breakPoint != null){
            if(head.val != breakPoint.val){
                return false;
            }
            breakPoint = breakPoint.next;
            head = head.next;
        }

        return true;
    }

    private ListNode reverse(ListNode head){
        ListNode pre = null;
        while(head != null){
            ListNode tem = head.next;
            head.next = pre;
            pre = head;
            head = tem;
        }
        return pre;
    }

    public ListNode sortList(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }
        ListNode newHead = new ListNode(0);
        newHead.next = head;
        ListNode fast = newHead;
        ListNode slow = newHead;
        while(fast != null && fast.next!= null){
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode rightNode = slow.next;
        slow.next = null;
        ListNode leftNode = head;
        int len1 = getLen(leftNode);
        int len2 = getLen(rightNode);

        if(len1 + len2 <=2){
            return merge(leftNode,rightNode);
        }else{
            ListNode leSort = sortList(leftNode);
            ListNode riSort = sortList(rightNode);
            return merge(leSort,riSort);
        }

    }

    public int getLen(ListNode node){
        if(node == null){
            return 0;
        }
        ListNode tem = new ListNode(0);
        tem.next = node;
        int count = 0;
        while(tem.next != null){
            tem = tem.next;
            count++;
        }
        return count;
    }

    public ListNode merge(ListNode n1, ListNode n2){
        ListNode cur = new ListNode(0);
        ListNode re = cur;
        while(n1 != null || n2 != null){
            if(n1 == null){
                cur.next = n2;
                break;
            }else if(n2 == null){
                cur.next = n1;
                break;
            }
            if(n1.val>n2.val){
                cur.next = n2;
                n2 = n2.next;
            }else{
                cur.next = n1;
                n1 = n1.next;
            }
            cur = cur.next;
        }
        return re.next;

    }
}
