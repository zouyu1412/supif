//package com.zouyu.examination;
//
//import java.security.interfaces.ECPublicKey;
//import java.util.concurrent.*;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * @Author:zouyu
// * @Date:2020/6/2 19:43
// */
//public class Test {
//
//    static ThreadPoolExecutor pool = new ThreadPoolExecutor(3,3,2,
//            TimeUnit.MINUTES, new ArrayBlockingQueue<>(10));
//
//    static Semaphore semaphore = new Semaphore(1);
//
//    static AtomicInteger atomicInteger = new AtomicInteger(0);
//
//    static CountDownLatch cdl = new CountDownLatch(1);
//
//    public static void main(String[] args) {
//        Future future1 = pool.submit(new MyFutureTask(1));
//        Future future2 = pool.submit(new MyFutureTask(2));
//        Future future3 = pool.submit(new MyFutureTask(3));
//
//        //block.await()
//        try {
//            cdl.await();
//        }catch (Exception e){
//
//        }
//        switch (atomicInteger.get()) {
//            case 1:return future1.get();
//            case 2:return future2.get();
//            case 3:return future3.get();
//        }
//
//    }
//
//    static class MyFutureTask implements Callable {
//
//        int val;
//
//        public MyFutureTask(int val) {
//            this.val = val;
//        }
//
//        @Override
//        public Object call() throws Exception {
//            try {
//                return "111";
//            }finally {
//                cdl.countDown();
//                atomicInteger.compareAndSet(0,val);
//                //block.count()
//            }
////            if(semaphore.tryAcquire()) {
////                return "111";
////            }else{
////                try{
////                   Thread.sleep(1000);
////                   return "111";
////                }catch (Exception e){
////
////                }
////            }
//        }
//    }
//    cpu
//    top
//            cpu pid   80%
//    free -
//        内存  pid
//
//                jhat dump
//                        大对象 内存泄漏 full gc cpu
//
//                jstack xxx
//                        time-wait  dead_lock -> txt.txt  time-aait 调用链找代码的问题
//
//
//    public t.maxScore getAnd() {
//        return and;
//    }
//
//    select s.student_name as StuName, s.course as CoursNam, s.score as Soc from score s left join
//    (select max(score) as maxScore, course as courseName
//    from score where class_id = ? and test_time = ? group by course) t on s.score = t.maxScore and s.course=t.courseName
//    where s.class_id =? and s.test_time between
//    explain  index_length
//            innodb create_time   io  500W 查询时间  select
//    create index
//}
