package com.zouyu.designmode.builder;

import java.util.ArrayList;

/**
 * Created by zouy-c on 2017/12/18.
 */
public class Director {
    //导演安排顺序和生产车辆模型
    private ArrayList<String> sequence = new ArrayList<String>();
    private BenzBuilder benzBuilder = new BenzBuilder();
    private  BMWBuilder bmwBuilder = new BMWBuilder();

    public BenzModel getABenzModel(){   //获取A型号的奔驰
        //清理现场
        this.sequence.clear();

        this.sequence.add("start");
        this.sequence.add("stop");

        this.benzBuilder.setSequence(this.sequence);
        return (BenzModel)this.benzBuilder.getCarModel();
    }

    public BenzModel getBBenzModel(){   //获取B型号的奔驰
        //清理现场
        this.sequence.clear();

        this.sequence.add("engine boom");
        this.sequence.add("start");
        this.sequence.add("stop");

        this.benzBuilder.setSequence(this.sequence);
        return (BenzModel)this.benzBuilder.getCarModel();
    }

    public BMWModel getCBMWModel(){   //获取C型号的宝马
        //清理现场
        this.sequence.clear();

        this.sequence.add("alarm");
        this.sequence.add("start");
        this.sequence.add("stop");

        this.bmwBuilder.setSequence(this.sequence);
        return (BMWModel)this.bmwBuilder.getCarModel();
    }

    public BMWModel getDBMWModel(){   //获取B型号的奔驰
        //清理现场
        this.sequence.clear();

        this.sequence.add("start");

        this.bmwBuilder.setSequence(this.sequence);
        return (BMWModel)this.bmwBuilder.getCarModel();
    }
}
