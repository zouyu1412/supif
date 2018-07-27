package com.zouyu.designmode.observer;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by zouy-c on 2017/12/20.
 */
public class HanFeiZi extends Observable {
    //存放所有观察者
    private ArrayList<Observer> observerList = new ArrayList<Observer>();

//    public void addObserver(Observer observer) {
//        this.observerList.add(observer);
//    }
//
//    public void deleteObserver(Observer observer) {
//        this.observerList.remove(observer);
//    }
//
//    public void nofifyObservers(String context) {
//        for(Observer observer:observerList){
//            observer.update(context);
//        }
//    }

    public void haveBreakfast() {
        System.out.println("韩非吃早饭了");
        super.setChanged();
        super.notifyObservers("韩非吃早饭了");
    }

    public void haveFun() {
        System.out.println("韩非娱乐了");
        super.setChanged();
        super.notifyObservers("韩非娱乐了");
    }
}
