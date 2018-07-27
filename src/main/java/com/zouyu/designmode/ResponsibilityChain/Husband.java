package com.zouyu.designmode.ResponsibilityChain;

/**
 * Created by zouy-c on 2017/12/21.
 */
public class Husband extends Handler {

    public Husband(){
        super(2);
    }

    @Override
    public void response(IWomen women) {
        System.out.println("======妻子向丈夫请示========");
        System.out.println("妻子的请示是："+women.getRequest());
        System.out.println("丈夫的应答：同意");
    }
}
