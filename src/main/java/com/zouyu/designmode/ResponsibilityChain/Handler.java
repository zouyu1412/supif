package com.zouyu.designmode.ResponsibilityChain;

/**
 * 父系社会  男性有至高的权利  handler控制权
 * Created by zouy-c on 2017/12/21.
 */
public abstract class Handler {
    //能处理的级别
    private int level = 0;
    //责任传递 下一个责任人是谁
    private Handler nextHandler;

    public Handler(int level) {
        this.level = level;
    }

    public void handleMessage(IWomen women){
        if(women.getType() ==this.level){
            this.response(women);
        }else {
            if(this.nextHandler!=null){
                this.nextHandler.handleMessage(women);
            }else{  //后面没有人处理了,不需要处理了  她自由了
                System.out.println("你走吧 你无敌了");
            }
        }
    }

    public void setNext(Handler handler){
        this.nextHandler = handler;
    }

    public abstract void response(IWomen women);
}
