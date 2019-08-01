package com.zouyu.leetcode.concurrent;

class H2O {

    private volatile int oStatus;
    private volatile int hStatus;

    public H2O() {
        oStatus = 0;
        hStatus = 0;
    }

    public synchronized void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        while(hStatus == 2){
            wait();
        }
        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        releaseHydrogen.run();
        hStatus ++;
        if(hStatus == 2 && oStatus == 1){
            hStatus = 0;
            oStatus = 0;
        }
        notifyAll();

    }

    public synchronized void oxygen(Runnable releaseOxygen) throws InterruptedException {
        // releaseOxygen.run() outputs "H". Do not change or remove this line.
        while (oStatus == 1){
            wait();
        }
        releaseOxygen.run();
        oStatus++;
        if(hStatus == 2 && oStatus == 1){
            hStatus = 0;
            oStatus = 0;
        }
        notifyAll();
    }
}