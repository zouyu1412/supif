package com.prince.concurrent;


import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by zouy-c on 2018/5/31.
 */
public class CallableTest {

    public static final Stopwatch stopwatch = Stopwatch.createStarted();
    public static final ExecutorService executorPool = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Future<String>> tasks = new ArrayList<>();
        tasks.add(executorPool.submit(new MyCallable("1",1000l)));
        tasks.add(executorPool.submit(new MyCallable("2",1000l)));

        List<String> result = new ArrayList<>();
        while(!tasks.isEmpty()){
            for(int i=0;i<tasks.size();i++){
                if(tasks.get(i).isDone()){
                    result.add(tasks.get(i).get());
                    tasks.remove(i);
                    i--;
                }
            }
        }
        System.out.println("main["+stopwatch.elapsed(TimeUnit.MILLISECONDS)+"] list="+result);
        executorPool.shutdown();
    }

    private static final class MyCallable implements Callable{

        private final String s;
        private final Long time;

        public MyCallable(String s, Long time) {
            this.s = s;
            this.time = time;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(time);
            System.out.println(s+":"+stopwatch.elapsed(TimeUnit.MILLISECONDS));
            return s;
        }
    }
}
