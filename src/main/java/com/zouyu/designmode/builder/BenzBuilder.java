package com.zouyu.designmode.builder;

import java.util.ArrayList;

/**
 * Created by zouy-c on 2017/12/18.
 */
public class BenzBuilder extends CarBuilder {

    private BenzModel benz = new BenzModel();

    public void setSequence(ArrayList<String> sequence) {
        this.benz.setSequence(sequence);
    }

    public CarModel getCarModel() {
        return this.benz;
    }
}
