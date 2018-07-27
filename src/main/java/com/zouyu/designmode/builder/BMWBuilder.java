package com.zouyu.designmode.builder;

import java.util.ArrayList;

/**
 * Created by zouy-c on 2017/12/18.
 */
public class BMWBuilder extends CarBuilder {

    private BMWModel bmw = new BMWModel();

    public void setSequence(ArrayList<String> sequence) {
        this.bmw.setSequence(sequence);
    }

    public CarModel getCarModel() {
        return this.bmw;
    }
}
