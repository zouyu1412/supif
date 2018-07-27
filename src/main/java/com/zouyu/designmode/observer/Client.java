package com.zouyu.designmode.observer;

/**
 * 在一个观察者模式中最多出现一个对象既是观察者也是被观察者，也就是说消
    息最多转发一次（传递两次）
 * Created by zouy-c on 2017/12/20.
 */
public class Client {
    public static void main(String[] args)throws InterruptedException {
        //两观察者
        LiSi liSi = new LiSi();
        WangSi wangSi =new WangSi();
        //被观察者
        HanFeiZi hanFeiZi = new HanFeiZi();

        hanFeiZi.addObserver(liSi);
        hanFeiZi.addObserver(wangSi);

        hanFeiZi.haveBreakfast();
        hanFeiZi.haveFun();
    }
}
