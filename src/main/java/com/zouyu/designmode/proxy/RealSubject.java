package com.zouyu.designmode.proxy;

/**
 * Created by zouy-c on 2017/12/15.
 */
public class RealSubject implements Subject {
    public void visit() {
        System.out.println("我来拜访了");
    }
}
