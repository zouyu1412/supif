package com.prince.SynchronizedAndLockCompare;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by zouy-c on 2018/7/21.
 */
public class AtomicTest extends TestTemplate{

    public AtomicTest(String _id, int _rount, int _threadNum, CyclicBarrier _cb) {
        super(_id, _rount, _threadNum, _cb);
    }

    @Override
    /**
     * synchronized关键字不在方法签名里面，所以不涉及重载问题
     */
    void sumValue() {
        super.countValueAtomic.addAndGet(super.preInit[indexAtomic.get()%round]);
    }

    @Override
    long getValue() {
        return super.countValueAtomic.get();
    }
}
