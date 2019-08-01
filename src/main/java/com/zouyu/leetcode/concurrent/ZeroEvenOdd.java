package com.zouyu.leetcode.concurrent;

import java.util.function.IntConsumer;

/**
 * Input: n = 2
 * Output: "0102"
 * Explanation: There are three threads being fired asynchronously. One of them calls zero(), the other calls even(), and the last one calls odd(). "0102" is the correct output.
 */
class ZeroEvenOdd {
    private int n;
    private volatile int status;
    
    public ZeroEvenOdd(int n) {
        this.n = n;
        status = 0;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public synchronized void zero(IntConsumer printNumber) throws InterruptedException {
        for(int i=0;i<n;i++) {
            while (status != 0) {
                wait();
            }
            printNumber.accept(0);
            if (i % 2 == 0) {
                status = 1;
            } else {
                status = 2;
            }
            notifyAll();
        }

    }

    public synchronized void even(IntConsumer printNumber) throws InterruptedException {
        for(int i=2;i<=n;i+=2) {
            while (status != 2) {
                wait();
            }
            printNumber.accept(i);
            status = 0;
            notifyAll();
        }

    }

    public synchronized void odd(IntConsumer printNumber) throws InterruptedException {
        for(int i=1;i<=n;i+=2) {
            while (status != 1) {
                wait();
            }
            printNumber.accept(i);
            status = 0;
            notifyAll();
        }
    }
}