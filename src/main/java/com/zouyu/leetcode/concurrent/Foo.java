package com.zouyu.leetcode.concurrent;

class Foo {

    volatile int status;

    public Foo() {
       status = 0;
    }

    public void first(Runnable printFirst) throws InterruptedException {
        while(status != 0){

        }
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        status = 1;
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while(status != 1){

        }
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        status = 2;
    }

    public void third(Runnable printThird) throws InterruptedException {
        while(status!= 2){

        }
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
        status = 0;
    }

}