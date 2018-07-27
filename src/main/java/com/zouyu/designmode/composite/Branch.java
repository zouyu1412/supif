package com.zouyu.designmode.composite;

import java.util.ArrayList;

/**
 * Created by zouy-c on 2017/12/28.
 */
public class Branch extends Corp {

    ArrayList<Corp> sub = new ArrayList<Corp>();//下属
    //领导 经理等
    public Branch(String name, String position, int salary) {
        super(name, position, salary);
    }

    public void addSub(Corp crop){
        crop.setParent(this);
        this.sub.add(crop);
    }

    public ArrayList<Corp> getSub(){
        return this.sub;
    }
}
