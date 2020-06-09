package com.prince.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:zouyu
 * @Date:2020/4/23 10:13
 */
public class ABCCyclePrintTest {
    /**
     * 三个线程循环打印ABC 50次
     */

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Semaphore s1 = new Semaphore(1);
        Semaphore s2 = new Semaphore(1);
        Semaphore s3 = new Semaphore(1);
//        s1.acquire();
        s2.acquire();
        s3.acquire();

        Thread t1 = new Thread(() -> {
            try {
                for (int i = 0; i < 50; i++) {
                    while (true) {
                        if (s1.tryAcquire()) {
                            synchronized (lock) {
                                s2.release();
                                System.out.println("current index:" + i + "  A");
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                for (int i = 0; i < 50; i++) {
                    while (true) {
                        if (s2.tryAcquire()) {
                            synchronized (lock) {
                                s3.release();
                                System.out.println("current index:" + i + "  B");
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                for (int i = 0; i < 50; i++) {
                    while (true) {
                        if (s3.tryAcquire()) {
                            synchronized (lock) {
                                s1.release();
                                System.out.println("current index:" + i + "  C");
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }

    public static void fun2(String[] args) throws InterruptedException {
        Object lock = new Object();
        AtomicInteger s1 = new AtomicInteger(0);
        AtomicInteger s2 = new AtomicInteger(1);
        AtomicInteger s3 = new AtomicInteger(1);

        Thread t1 = new Thread(() -> {
            try {
                for (int i = 0; i < 50; i++) {
                    while (true) {
                        if (s1.compareAndSet(0, 1)) {
                            synchronized (lock) {
                                s2.compareAndSet(1, 0);
                                System.out.println("current index:" + i + "  A");
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                for (int i = 0; i < 50; i++) {
                    while (true) {
                        if (s2.compareAndSet(0, 1)) {
                            synchronized (lock) {
                                s3.compareAndSet(1, 0);
                                System.out.println("current index:" + i + "  B");
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                for (int i = 0; i < 50; i++) {
                    while (true) {
                        if (s3.compareAndSet(0, 1)) {
                            synchronized (lock) {
                                s1.compareAndSet(1, 0);
                                System.out.println("current index:" + i + "  C");
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }

}
