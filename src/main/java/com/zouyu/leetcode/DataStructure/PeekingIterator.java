package com.zouyu.leetcode.DataStructure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class PeekingIterator implements Iterator<Integer> {

    private Iterator<Integer> it;
    private Integer top;

	public PeekingIterator(Iterator<Integer> iterator) {
        it = iterator;
        top = null;
	}

	public Integer peek() {
	    if(top != null){
	        return top;
        }
	    if(it.hasNext()){
	        top = it.next();
        }
        return top;
	}

	@Override
	public Integer next() {
	    if(top != null){
	        int tem = top;
	        top = null;
	        return tem;
        }
	    if(it.hasNext()){
	        return it.next();
        }
	    return null;
	}

	@Override
	public boolean hasNext() {
	    return it.hasNext() || top != null;
	}

    public static void main(String[] args) {
//        List<Integer> list = new ArrayList(){{add(1);add(2);add(3);}};
        List<Integer> list = new ArrayList(){{add(1);}};
        PeekingIterator iterator = new PeekingIterator(list.iterator());
        Integer next = iterator.next();
    }
}