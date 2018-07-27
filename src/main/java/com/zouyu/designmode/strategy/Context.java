package com.zouyu.designmode.strategy;

/**
 * Created by zouy-c on 2017/12/15.
 */
public class Context {
    private IStrategy strategy;
    public Context(IStrategy strategy){
        this.strategy = strategy;
    }
    public void operate(){
        this.strategy.operator();
    }
}
