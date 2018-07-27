package com.zouyu.designmode.proxy.staticProxy;

import com.zouyu.designmode.proxy.Subject;

/**
 * Created by zouy-c on 2017/12/15.
 */
public class ProxySubject implements Subject {

    private Subject subject;

    public ProxySubject(Subject subject) {
        this.subject = subject;
    }

    public void visit() {
        subject.visit();
    }
}
