package com.zouyu.designmode.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by zouy-c on 2017/12/20.
 */
public class LiSi implements Observer {

    public void update(Observable observable, Object obj) {
        System.out.println("观察到韩非有活动了，向老大汇报");
        this.reportToQinShiHuang(obj.toString());
        System.out.println("汇报完毕");
    }

    private void reportToQinShiHuang(String context) {
        System.out.println("报告老板，韩非有活动了:"+context);
    }
}
