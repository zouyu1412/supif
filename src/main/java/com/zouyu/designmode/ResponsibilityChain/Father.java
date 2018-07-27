package com.zouyu.designmode.ResponsibilityChain;

/**
 * Created by zouy-c on 2017/12/21.
 */
public class Father extends Handler {

    public Father(){
        super(1);
    }

    @Override
    public void response(IWomen women) {
        System.out.println("=====女儿向父亲请示=====");
        System.out.println("女儿的请示是："+women.getRequest());
        System.out.println("父亲的应答：同意");
    }
}
