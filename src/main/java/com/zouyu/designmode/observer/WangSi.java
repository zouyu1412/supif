package com.zouyu.designmode.observer;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by zouy-c on 2017/12/20.
 */
public class WangSi implements Observer {
    public void update(Observable observable,Object obj) {
        System.out.println("王思看到韩非有活动了 自己受不了了");
        this.cry(obj.toString());
        System.out.println("王思完事了");
    }

    private void cry(String context) {
        System.out.println("王思因为"+context+"开始呐喊");
    }
}
