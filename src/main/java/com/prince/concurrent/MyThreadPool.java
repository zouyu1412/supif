package com.prince.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author:zouyu
 * @Date:2020/5/5 12:23
 */
public class MyThreadPool {

    public static void main(String[] args) {

        System.out.print(Runtime.getRuntime().availableProcessors());
        int cpuCodes = Runtime.getRuntime().availableProcessors();

        ThreadPoolExecutor pool = new ThreadPoolExecutor(cpuCodes+1,
                cpuCodes+1, 2,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<Runnable>(3),
                new ThreadFactoryBuilder().setDaemon(true).setNameFormat("%s thread").build(),
                new ThreadPoolExecutor.CallerRunsPolicy());

    }

}
