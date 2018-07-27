package com.zouyu.designmode.builder;

import java.util.ArrayList;

/**
 * Created by zouy-c on 2017/12/18.
 */
public class client {

    public static void main(String[] args) {
        ArrayList<String> sequence = new ArrayList<String>();  //启动顺序
        sequence.add("engine boom");
        sequence.add("start");
        sequence.add("stop");
        BenzBuilder benzBuilder = new BenzBuilder();
        benzBuilder.setSequence(sequence);
        BenzModel benz = (BenzModel) benzBuilder.getCarModel();
        benz.run();

        System.out.println("========================");
        Director director = new Director();
        for(int i=0;i<1;i++){
            director.getABenzModel().run();
            director.getBBenzModel().run();
        }
    }
}
