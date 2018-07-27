package com.zouyu.designmode.builder;

/**
 * Created by zouy-c on 2017/12/18.
 */
public class BMWModel extends CarModel {
    protected void start() {
        System.out.println("宝马要启动了");
    }

    protected void stop() {
        System.out.println("宝马要停车了");
    }

    protected void alarm() {
        System.out.println("宝马要叫了");
    }

    protected void engineBoom() {
        System.out.println("宝马要发声了");
    }
}
