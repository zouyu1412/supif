package com.zouyu.designmode.strategy;

/**
 * Created by zouy-c on 2017/12/15.
 */
public class SecondStrategy implements IStrategy {
    public void operator() {
        System.out.println("第二个策略是 赚钱");
    }
}
