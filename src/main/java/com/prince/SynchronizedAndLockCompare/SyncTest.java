package com.prince.SynchronizedAndLockCompare;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by zouy-c on 2018/7/21.
 */
public class SyncTest extends TestTemplate {

    public SyncTest(String _id, int _rount, int _threadNum, CyclicBarrier _cb) {
        super(_id, _rount, _threadNum, _cb);
    }

    @Override
    /**
     * synchronized关键字不在方法签名里面，所以不涉及重载问题
     */
    synchronized void sumValue() {
        super.countValue += preInit[index++%round];
    }

    @Override
    synchronized long getValue() {
        return super.countValue;
    }
}
