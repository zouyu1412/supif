package com.zouyu.designmode.ResponsibilityChain;

/**
 * Created by zouy-c on 2017/12/21.
 */
public class Son extends Handler {

    public Son(){
        super(3);
    }

    @Override
    public void response(IWomen women) {
        System.out.println("======妈妈向儿子请示========");
        System.out.println("妈妈的请示是："+women.getRequest());
        System.out.println("儿子的应答：同意");
    }
}
