package com.prince.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:zouyu
 * @Date:2020/5/5 9:38
 */
public class MyProdComsumer {

    int member;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void product(){
        lock.lock();
        try {
            while (member != 0) {
                condition.await();
            }
            member++;
            System.out.println(Thread.currentThread().getName()+"\t"+member);
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    private void consumer(){
        lock.lock();
        try{
            while(member == 0){  //用if会导致虚拟唤醒
                condition.await();
            }
            member--;
            System.out.println(Thread.currentThread().getName()+"\t"+member);
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        MyProdComsumer dome = new MyProdComsumer();

        new Thread(()->{
            for(int i=0;i<5;i++) {
                dome.product();
            }
        },"AA").start();

        new Thread(()->{
            for(int i=0;i<5;i++) {
                dome.consumer();
            }
        },"BB").start();

        new Thread(()->{
            for(int i=0;i<5;i++) {
                dome.product();
            }
        },"CC").start();

        new Thread(()->{
            for(int i=0;i<5;i++) {
                dome.consumer();
            }
        },"DD").start();


    }
}
