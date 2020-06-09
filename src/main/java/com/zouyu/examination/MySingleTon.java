package com.zouyu.examination;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:zouyu
 * @Date:2020/5/12 18:36
 */
public class MySingleTon {

    private MySingleTon(){
        new ReentrantLock(true).lock();
    }

    private static class SingleTonHolder{
        private static MySingleTon mySingle = new MySingleTon();
    }

    public static MySingleTon getInstance(){
        return SingleTonHolder.mySingle;
    }


}
