package com.prince.RateLimiter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author zouyu <zouyu@kuaishou.com>
 * Created on 2020-07-07
 */
public class MyRateLimiter {

    static Semaphore semaphore = new Semaphore(10);
    static AtomicInteger tag = new AtomicInteger(0);
    static CountDownLatch cdl = new CountDownLatch(10);
    static List<Apple> allApple = new CopyOnWriteArrayList<>();
    static List<Person> allPerson = new ArrayList<>();
    static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        for(int i=0;i<100;i++){
            allApple.add(new Apple(i+100, "bean"+i));
        }
        for(int i=0;i<10;i++){
            allPerson.add(new Person("小明"+i+"号",null));
        }

        for(int i=0;i<10;i++){
            fixedThreadPool.submit(new RobAppleTask(allPerson.get(i)));
        }

        System.out.println("show time==============");
        for(Person p:allPerson){
            p.show();
        }

    }

    static class RobAppleTask implements Runnable{

        Person person;
        RobAppleTask(Person person){
            this.person = person;
        }

        @Override
        public void run() {
            try {
                cdl.countDown();
                System.out.println(person.getName()+"就绪");
                cdl.await();

                System.out.println(person.getName()+"开抢");
                while(true && allApple.size()>0) {
                    while (tag.compareAndSet(0, 1)) {
                        if (allApple.size() > 0) {
                            person.robApple(allApple.remove(0));
                        }
                        tag.set(0);
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Data
    @AllArgsConstructor
    static class Person {
        private String name;
        private List<Apple> apples;
        public void robApple(Apple apple){
            if(apples == null){
                apples = new ArrayList<>();
            }
            apples.add(apple);
            System.out.println(name+" has apple:"+apples.size() +" left apple:"+allApple.size());
        }
        public void show(){
            System.out.println(name+" has apple:"+(apples==null?0:apples.size()));
        }
    }

    @Data
    @ToString
    @AllArgsConstructor
    static class Apple{
        private Integer price;
        private String color;
    }

}
