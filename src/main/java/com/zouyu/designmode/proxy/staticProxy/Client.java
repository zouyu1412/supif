package com.zouyu.designmode.proxy.staticProxy;

import com.zouyu.designmode.proxy.RealSubject;

/**
 * Created by zouy-c on 2017/12/15.
 */
public class Client {
    public static void main(String[] args) {
        ProxySubject subject = new ProxySubject(new RealSubject());
        subject.visit();
    }
}
