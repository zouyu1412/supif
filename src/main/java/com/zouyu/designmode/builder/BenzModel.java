package com.zouyu.designmode.builder;

/**
 * Created by zouy-c on 2017/12/18.
 */
public class BenzModel extends CarModel {
    protected void start() {
        System.out.println("奔驰要启动了");
    }

    protected void stop() {
        System.out.println("奔驰要停车了");
    }

    protected void alarm() {
        System.out.println("奔驰要叫了");
    }

    protected void engineBoom() {
        System.out.println("奔驰要发声了");
    }
}
