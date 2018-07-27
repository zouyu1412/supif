package com.zouyu.designmode.ResponsibilityChain;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zouy-c on 2017/12/21.
 */
@SuppressWarnings("all")
public class Client {
    public static void main(String[] args) {
        Random rand = new Random();
        ArrayList<Women> arrayList =  new ArrayList<Women>();
        for(int i=0;i<1;i++){
//            arrayList.add(new Women(rand.nextInt(4),"我要次窜窜"));
            arrayList.add(new Women(3,"我要次窜窜"));
        }

        Handler father = new Father();
        Handler husband = new Husband();
        Handler son = new Son();

        father.setNext(husband);
        husband.setNext(son);

        for(IWomen women:arrayList){
            father.handleMessage(women);
            System.out.println("=================");
        }
    }
}
