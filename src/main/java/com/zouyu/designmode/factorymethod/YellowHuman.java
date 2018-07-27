package com.zouyu.designmode.factorymethod;

/**
 * Created by zouy-c on 2017/12/15.
 */
public class YellowHuman implements Human {
    public void laugh() {
        System.out.println("黄种人会笑哈哈");
    }

    public void cry() {
        System.out.println("黄种人会哭哇哇");
    }

    public void talk() {
        System.out.println("黄种人会说叨叨");
    }
}
