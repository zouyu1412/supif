package com.zouyu.designmode.state;



/**
 * Created by zouy-c on 2018/1/3.
 */
public abstract class LiftState {
    //定义一个环境角色，也就是封装状态的变换引起的功能变化
    protected Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public abstract void open();
    public abstract void close();
    public abstract void run();
    public abstract void stop();
}
