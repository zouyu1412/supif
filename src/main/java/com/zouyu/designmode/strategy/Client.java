package com.zouyu.designmode.strategy;

/**
 * Created by zouy-c on 2017/12/15.
 */
public class Client {
    public static void main(String[] args) {
        Context context;
        System.out.println("遇见了，我要加油，开始以下策略");
        context = new Context(new FirstStrategy());
        context.operate();
        context = new Context(new SecondStrategy());
        context.operate();
        context = new Context(new ThirdStrategy());
        context.operate();
    }
}
