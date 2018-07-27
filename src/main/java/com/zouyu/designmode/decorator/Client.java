package com.zouyu.designmode.decorator;

/**
 * Created by zouy-c on 2017/12/19.
 */
public class Client {

    public static void main(String[] args) {
        Sourcable source = new Source();
        //装饰类对象
        Sourcable obj = new Decorator1(new Decorator2(new Decorator3(source)));
        obj.operation();
    }
}
