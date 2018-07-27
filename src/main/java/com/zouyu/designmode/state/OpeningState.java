package com.zouyu.designmode.state;

/**
 * Created by zouy-c on 2018/1/3.
 */
public class OpeningState extends LiftState {
    @Override
    public void open() {
        System.out.println("电梯门正开启");
    }

    @Override
    public void close() {
        //状态修改
        super.context.setLiftState(Context.closeingState);
        //动作委托为CloseState来执行
        super.context.getLiftState().close();
    }

    @Override
    public void run() {

    }

    @Override
    public void stop() {

    }
}
