package com.zouyu.designmode.ResponsibilityChain;

/**
 * 古代悲哀女性总称
 * Created by zouy-c on 2017/12/21.
 */
public interface IWomen {
    //获得个人状况
    public int getType();

    //获得个人请示，你要干什么？出去逛街？看电影？
    public String getRequest();
}
