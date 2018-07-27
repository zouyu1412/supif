package com.zouyu.designmode.ResponsibilityChain;

/**
 * Created by zouy-c on 2017/12/21.
 */
public class Women implements IWomen {
    //1 是未结婚，2 是已经结婚的，而且丈夫建在，3 是丈夫去世了
    private int type = 0;

    private String request="";

    public Women(int type, String request) {
        this.type = type;
        switch (this.type){
            case 1:
                this.request = "女儿的请求是："+request;
                break;
            case 2:
                this.request = "妻子的请求是："+request;
                break;
            case 3:
                this.request = "妈妈的请求是："+request;

        }
    }

    public int getType() {
        return this.type;
    }

    public String getRequest() {
        return this.request;
    }
}
