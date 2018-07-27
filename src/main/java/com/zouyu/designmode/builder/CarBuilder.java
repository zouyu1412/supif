package com.zouyu.designmode.builder;

import java.util.ArrayList;

/**
 * Created by zouy-c on 2017/12/18.
 */
public abstract class CarBuilder {
    //建造一个模型 给一个组装顺序
    public abstract void setSequence(ArrayList<String> sequence);
    //设置完毕就可以拿到车辆模型
    public abstract CarModel getCarModel();
}
