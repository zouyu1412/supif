package com.prince.concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Author:zouyu
 * @Date:2020/5/4 17:36
 */
public class MySpinLock {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock(){
        Thread thread = Thread.currentThread();
        while(!atomicReference.compareAndSet(null,thread)){

        }
    }

    public void unlock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
    }

}
