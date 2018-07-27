package com.prince.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class BoundedBuffer {
    final Lock lock = new ReentrantLock();
    // condition 依赖于 lock 来产生
    final Condition notFull = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public BoundedBuffer() {
        this(0,0,0);
    }

    public BoundedBuffer(int putptr, int takeptr, int count) {
        this.putptr = putptr;
        this.takeptr = takeptr;
        this.count = count;
    }

    // 生产
    public void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length)
                notFull.await();  // 队列已满，等待，直到 not full 才能继续生产
            items[putptr] = x;
            if (++putptr == items.length) putptr = 0;
            ++count;
            System.out.println("插入元素:"+x.toString()+" putptr:"+putptr+" takeptr:"+takeptr+" count:"+count);
            notEmpty.signal(); // 生产成功，队列已经 not empty 了，发个通知出去
        } finally {
            lock.unlock();
            Thread.sleep(1*1000);
        }
    }

    // 消费
    public Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0)
                notEmpty.await(); // 队列为空，等待，直到队列 not empty，才能继续消费
            Object x = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            System.out.println("拿走元素:"+x.toString()+" putptr:"+putptr+" takeptr:"+takeptr+" count:"+count);
            notFull.signal(); // 被我消费掉一个，队列 not full 了，发个通知出去
            return x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        BoundedBuffer boundedBuffer = new BoundedBuffer();

        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable> tasks = new ArrayList<>();
        tasks.add(new Callable<Object>() {
            @Override
            public Object call() {
                try {
                    for (int i = 0; i < 100; i++) {
                        boundedBuffer.put(new Integer(i));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        });


        tasks.add(new Callable<Object>() {
            @Override
            public Object call() {
                while(true) {
                    try {
                        boundedBuffer.take();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        });

        executorService.submit(tasks.get(0));
        executorService.submit(tasks.get(1));
    }
}
