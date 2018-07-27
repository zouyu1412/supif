package com.prince.SynchronizedAndLockCompare;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zouy-c on 2018/7/21.
 */
public class LockTest extends TestTemplate {

    ReentrantLock lock = new ReentrantLock();

    public LockTest(String _id, int _rount, int _threadNum, CyclicBarrier _cb) {
        super(_id, _rount, _threadNum, _cb);
    }

    /**
     * synchronized关键字不在方法签名里面，所以不涉及重载问题
     */
    @Override
    void sumValue() {
        try{
            lock.lock();
            super.countValue+=preInit[index++%round];
        }finally {
            lock.unlock();
        }
    }

    @Override
    long getValue() {
        try{
            lock.lock();
            return super.countValue;
        }finally {
            lock.unlock();
        }
    }
}
