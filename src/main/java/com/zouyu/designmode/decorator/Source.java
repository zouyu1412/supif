package com.zouyu.designmode.decorator;

/**
 * Created by zouy-c on 2017/12/19.
 */
public class Source implements Sourcable {
    public void operation() {
        System.out.println("原始类的方法");
    }
}
